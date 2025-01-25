/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2015, AlgorithmX2, All rights reserved.
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

package appeng.helpers;

import java.util.function.BiConsumer;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import appeng.api.config.Actionable;
import appeng.api.config.PowerMultiplier;
import appeng.api.features.HotkeyAction;
import appeng.api.implementations.blockentities.IWirelessAccessPoint;
import appeng.api.implementations.menuobjects.IPortableTerminal;
import appeng.api.implementations.menuobjects.ItemMenuHost;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridNode;
import appeng.api.networking.security.IActionHost;
import appeng.api.networking.storage.IStorageService;
import appeng.api.storage.MEStorage;
import appeng.api.util.IConfigManager;
import appeng.blockentity.networking.WirelessAccessPointBlockEntity;
import appeng.core.AEConfig;
import appeng.core.localization.PlayerMessages;
import appeng.items.tools.powered.WirelessTerminalItem;
import appeng.menu.ISubMenu;

public class WirelessTerminalMenuHost extends ItemMenuHost implements IPortableTerminal, IActionHost {

    private final WirelessTerminalItem terminal;
    private final BiConsumer<Player, ISubMenu> returnToMainMenu;
    private final IGrid targetGrid;
    private IStorageService sg;
    @Nullable
    private IWirelessAccessPoint myWap;
    /**
     * The distance to the currently connected access point in blocks.
     */
    private double currentDistanceFromGrid = Double.MAX_VALUE;

    public WirelessTerminalMenuHost(Player player, @Nullable Integer slot, ItemStack itemStack,
            BiConsumer<Player, ISubMenu> returnToMainMenu) {
        super(player, slot, itemStack);
        if (!(itemStack.getItem() instanceof WirelessTerminalItem wirelessTerminalItem)) {
            throw new IllegalArgumentException("Can only use this class with subclasses of WirelessTerminalItem");
        }
        this.terminal = wirelessTerminalItem;
        this.returnToMainMenu = returnToMainMenu;

        this.targetGrid = wirelessTerminalItem.getLinkedGrid(itemStack, player.level(), player);
        if (this.targetGrid != null) {
            this.sg = this.targetGrid.getStorageService();
        }
    }

    @Override
    public MEStorage getInventory() {
        return this.sg != null ? this.sg.getInventory() : null;
    }

    @Override
    public double extractAEPower(double amt, Actionable mode, PowerMultiplier usePowerMultiplier) {
        if (this.terminal != null) {
            final double extracted = Math.min(amt, this.terminal.getAECurrentPower(getItemStack()));

            if (mode == Actionable.SIMULATE) {
                return extracted;
            }

            return this.terminal.usePower(getPlayer(), extracted, getItemStack()) ? extracted : 0;
        }
        return 0.0;
    }

    @Override
    public IConfigManager getConfigManager() {
        return this.terminal.getConfigManager(getItemStack());
    }

    @Override
    public IGridNode getActionableNode() {
        this.rangeCheck();
        if (this.myWap != null) {
            return this.myWap.getActionableNode();
        }
        return null;
    }

    public boolean rangeCheck() {
        this.currentDistanceFromGrid = Double.MAX_VALUE;

        if (this.targetGrid != null) {
            @Nullable
            IWirelessAccessPoint bestWap = null;
            double bestSqDistance = Double.MAX_VALUE;

            // Find closest WAP
            for (var wap : this.targetGrid.getMachines(WirelessAccessPointBlockEntity.class)) {
                double sqDistance = getWapSqDistance(wap);

                // If the WAP is not suitable then MAX_VALUE will be returned and the check will fail
                if (sqDistance < bestSqDistance) {
                    bestSqDistance = sqDistance;
                    bestWap = wap;
                }
            }

            // If no WAP is found this will work too
            this.myWap = bestWap;
            this.currentDistanceFromGrid = Math.sqrt(bestSqDistance);
            return this.myWap != null;
        }

        return false;
    }

    /**
     * @return square distance to WAP if the WAP can be used, or {@link Double#MAX_VALUE} if it cannot be used.
     */
    protected double getWapSqDistance(IWirelessAccessPoint wap) {
        var dc = wap.getLocation();

        if (!wap.isActive()) {
            return Double.MAX_VALUE;
        }

        if (dc.getLevel() == this.getPlayer().level()) {
            var offX = dc.getPos().getX() - this.getPlayer().getX();
            var offY = dc.getPos().getY() - this.getPlayer().getY();
            var offZ = dc.getPos().getZ() - this.getPlayer().getZ();
    
            return offX * offX + offY * offY + offZ * offZ;
        } else {
            var o1X = dc.getPos().getX();
            var o1Y = dc.getPos().getY();
            var o1Z = dc.getPos().getZ();
            double r1 = o1X * o1X + o1Y * o1Y + o1Z * o1Z;
            
            var o2X = this.getPlayer().getX();
            var o2Y = this.getPlayer().getY();
            var o2Z = this.getPlayer().getZ();
            double r2 = o2X * o2X + o2Y * o2Y + o2Z * o2Z;

            return r1 + r2;
        }
    }

    @Override
    public boolean onBroadcastChanges(AbstractContainerMenu menu) {
        return super.onBroadcastChanges(menu)
                && checkWirelessRange(menu)
                && drainPower();
    }

    /**
     * Can only be used with a host that extends {@link WirelessTerminalMenuHost}
     */
    private boolean checkWirelessRange(AbstractContainerMenu menu) {
        if (!rangeCheck()) {
            if (!isClientSide()) {
                getPlayer().displayClientMessage(PlayerMessages.OutOfRange.text(), true);
            }
            return false;
        }

        setPowerDrainPerTick(AEConfig.instance().wireless_getDrainRate(currentDistanceFromGrid));
        return true;
    }

    @Override
    public void returnToMainMenu(Player player, ISubMenu subMenu) {
        returnToMainMenu.accept(player, subMenu);
    }

    @Override
    public ItemStack getMainMenuIcon() {
        return getItemStack();
    }

    public String getCloseHotkey() {
        return HotkeyAction.WIRELESS_TERMINAL;
    }
}
