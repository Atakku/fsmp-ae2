/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2021, TeamAppliedEnergistics, All rights reserved.
 *
 * Applied Energistics 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Applied Energistics 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Applied Energistics 2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package appeng.parts.automation;

import com.google.common.collect.ImmutableList;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.inventory.MenuType;

import appeng.api.behaviors.StackExportStrategy;
import appeng.api.behaviors.StackTransferContext;
import appeng.api.config.SchedulingMode;
import appeng.api.config.Settings;
import appeng.api.networking.IGrid;
import appeng.api.networking.energy.IEnergyService;
import appeng.api.networking.storage.IStorageService;
import appeng.api.parts.IPartCollisionHelper;
import appeng.api.parts.IPartItem;
import appeng.api.parts.IPartModel;
import appeng.api.util.IConfigManagerBuilder;
import appeng.core.AppEng;
import appeng.core.definitions.AEItems;
import appeng.core.settings.TickRates;
import appeng.items.parts.PartModels;
import appeng.menu.implementations.IOBusMenu;
import appeng.parts.PartModel;
import appeng.util.prioritylist.DefaultPriorityList;

/**
 * Generalized base class for export buses that move stacks from network storage to an adjacent block using a non-AE
 * API.
 */
public class ExportBusPart extends IOBusPart {

    public static final ResourceLocation MODEL_BASE = AppEng.makeId("part/export_bus_base");

    @PartModels
    public static final IPartModel MODELS_OFF = new PartModel(MODEL_BASE,
            AppEng.makeId("part/export_bus_off"));

    @PartModels
    public static final IPartModel MODELS_ON = new PartModel(MODEL_BASE,
            AppEng.makeId("part/export_bus_on"));

    @PartModels
    public static final IPartModel MODELS_HAS_CHANNEL = new PartModel(MODEL_BASE,
            AppEng.makeId("part/export_bus_has_channel"));

    private int nextSlot = 0;
    @Nullable
    private StackExportStrategy exportStrategy;

    public ExportBusPart(IPartItem<?> partItem) {
        super(TickRates.ExportBus, StackWorldBehaviors.withExportStrategy(), partItem);
    }

    @Override
    protected void registerSettings(IConfigManagerBuilder builder) {
        super.registerSettings(builder);
        builder.registerSetting(Settings.SCHEDULING_MODE, SchedulingMode.DEFAULT);
    }

    @Override
    public void readFromNBT(CompoundTag extra, HolderLookup.Provider registries) {
        super.readFromNBT(extra, registries);
        this.nextSlot = extra.getInt("nextSlot");
    }

    @Override
    public void writeToNBT(CompoundTag extra, HolderLookup.Provider registries) {
        super.writeToNBT(extra, registries);
        extra.putInt("nextSlot", this.nextSlot);
    }

    protected final StackExportStrategy getExportStrategy() {
        if (exportStrategy == null) {
            var self = this.getHost().getBlockEntity();
            var fromPos = self.getBlockPos().relative(this.getSide());
            var fromSide = getSide().getOpposite();
            exportStrategy = StackWorldBehaviors.createExportFacade((ServerLevel) getLevel(), fromPos, fromSide);
        }
        return exportStrategy;
    }

    @Override
    protected boolean doBusWork(IGrid grid) {
        var storageService = grid.getStorageService();
        var fzMode = this.getConfigManager().getSetting(Settings.FUZZY_MODE);
        var schedulingMode = this.getConfigManager().getSetting(Settings.SCHEDULING_MODE);

        var context = createTransferContext(storageService, grid.getEnergyService());

        int x = 0;
        for (x = 0; x < this.availableSlots() && context.hasOperationsLeft(); x++) {
            final int slotToExport = this.getStartingSlot(schedulingMode, x);
            var what = getConfig().getKey(slotToExport);

            if (what == null) {
                continue;
            }

            var before = context.getOperationsRemaining();

            if (isUpgradedWith(AEItems.FUZZY_CARD)) {
                // When fuzzy exporting, simply attempt export of all items in the set of fuzzy-equals keys
                for (var fuzzyWhat : ImmutableList
                        .copyOf(storageService.getCachedInventory().findFuzzy(what, fzMode))) {
                    // The max amount exported is scaled by the key-space's transfer factor (think millibuckets vs.
                    // items)
                    var transferFactory = fuzzyWhat.getKey().getAmountPerOperation();
                    long amount = (long) context.getOperationsRemaining() * transferFactory;
                    amount = getExportStrategy().transfer(context, fuzzyWhat.getKey(), amount);
                    context.reduceOperationsRemaining(Math.max(1, amount / transferFactory));
                    if (!context.hasOperationsLeft()) {
                        break;
                    }
                }
            } else {
                // The max amount exported is scaled by the key-space's transfer factor (think millibuckets vs. items)
                var transferFactor = what.getAmountPerOperation();
                long amount = (long) context.getOperationsRemaining() * transferFactor;
                amount = getExportStrategy().transfer(context, what, amount);
                if (amount > 0) {
                    context.reduceOperationsRemaining(Math.max(1, amount / transferFactor));
                }
            }
        }

        // Round-robin should only advance if something was actually exported
        if (context.hasDoneWork()) {
            this.updateSchedulingMode(schedulingMode, x);
        }

        return context.hasDoneWork();
    }

    @NotNull
    private StackTransferContext createTransferContext(IStorageService storageService, IEnergyService energyService) {
        return new StackTransferContextImpl(
                storageService,
                energyService,
                this.source,
                getOperationsPerTick(),
                DefaultPriorityList.INSTANCE);
    }

    protected int getStartingSlot(SchedulingMode schedulingMode, int x) {
        if (schedulingMode == SchedulingMode.RANDOM) {
            return getLevel().getRandom().nextInt(this.availableSlots());
        }

        if (schedulingMode == SchedulingMode.ROUNDROBIN) {
            return (this.nextSlot + x) % this.availableSlots();
        }

        return x;
    }

    protected void updateSchedulingMode(SchedulingMode schedulingMode, int x) {
        if (schedulingMode == SchedulingMode.ROUNDROBIN) {
            this.nextSlot = (this.nextSlot + x) % this.availableSlots();
        }
    }

    @Override
    protected MenuType<?> getMenuType() {
        return IOBusMenu.EXPORT_TYPE;
    }

    @Override
    public void getBoxes(IPartCollisionHelper bch) {
        bch.addBox(4, 4, 12, 12, 12, 14);
        bch.addBox(5, 5, 14, 11, 11, 15);
        bch.addBox(6, 6, 15, 10, 10, 16);
        bch.addBox(6, 6, 11, 10, 10, 12);
    }

    @Override
    public IPartModel getStaticModels() {
        if (this.isActive() && this.isPowered()) {
            return MODELS_HAS_CHANNEL;
        } else if (this.isPowered()) {
            return MODELS_ON;
        } else {
            return MODELS_OFF;
        }
    }
}
