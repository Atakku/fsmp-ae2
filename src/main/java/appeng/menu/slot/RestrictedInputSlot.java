/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2014, AlgorithmX2, All rights reserved.
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

package appeng.menu.slot;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import appeng.api.features.GridLinkables;
import appeng.api.features.IGridLinkableHandler;
import appeng.api.ids.AETags;
import appeng.api.implementations.items.IStorageComponent;
import appeng.api.inventories.InternalInventory;
import appeng.api.storage.StorageCells;
import appeng.api.storage.cells.ICellWorkbenchItem;
import appeng.api.upgrades.Upgrades;
import appeng.blockentity.misc.InscriberRecipes;
import appeng.client.gui.Icon;
import appeng.core.definitions.AEItems;

/**
 * @author AlgorithmX2
 * @author thatsIch
 * @version rv2
 * @since rv0
 */
public class RestrictedInputSlot extends AppEngSlot {

    private final PlacableItemType which;
    private boolean allowEdit = true;
    private int stackLimit = -1;

    public RestrictedInputSlot(PlacableItemType valid, InternalInventory inv, int invSlot) {
        super(inv, invSlot);
        this.which = valid;
        this.setIcon(valid.icon);
    }

    @Override
    public int getMaxStackSize() {
        if (this.stackLimit != -1) {
            return this.stackLimit;
        }
        return super.getMaxStackSize();
    }

    public Slot setStackLimit(int i) {
        this.stackLimit = i;
        return this;
    }

    private Level getLevel() {
        return getMenu().getPlayerInventory().player.getCommandSenderWorld();
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if (!this.getMenu().isValidForSlot(this, stack)) {
            return false;
        }

        if (stack.isEmpty()) {
            return false;
        }

        if (stack.getItem() == Items.AIR) {
            return false;
        }

        if (!super.mayPlace(stack)) {
            return false;
        }

        if (!this.isAllowEdit()) {
            return false;
        }

        // TODO: might need to check for our own patterns in some cases
        switch (this.which) {
            case INSCRIBER_PLATE:
                if (AEItems.NAME_PRESS.is(stack)) {
                    return true;
                }

                return InscriberRecipes.isValidOptionalIngredient(getLevel(), stack);

            case INSCRIBER_INPUT:
                return true;/*
                             * for (ItemStack is : Inscribe.inputs) if ( Platform.isSameItemPrecise( is, i ) ) return
                             * true; return false;
                             */

            case METAL_INGOTS:

                return isMetalIngot(stack);

            case VIEW_CELL:
                return AEItems.VIEW_CELL.is(stack);

            case STORAGE_CELLS:
                return StorageCells.isCellHandled(stack);
            case WORKBENCH_CELL:
                return stack.getItem() instanceof ICellWorkbenchItem
                        && ((ICellWorkbenchItem) stack.getItem()).isEditable(stack);
            case STORAGE_COMPONENT:
                return stack.getItem() instanceof IStorageComponent
                        && ((IStorageComponent) stack.getItem()).isStorageComponent(stack);
            case TRASH:
                if (StorageCells.isCellHandled(stack)) {
                    return false;
                }

                return !(stack.getItem() instanceof IStorageComponent
                        && ((IStorageComponent) stack.getItem()).isStorageComponent(stack));
            case GRID_LINKABLE_ITEM: {
                var handler = GridLinkables.get(stack.getItem());
                return handler != null && handler.canLink(stack);
            }
            case UPGRADES:
                return Upgrades.isUpgradeCardItem(stack);
            default:
                break;
        }

        return false;
    }

    @Override
    public boolean mayPickup(Player player) {
        return this.isAllowEdit();
    }

    public static boolean isMetalIngot(ItemStack i) {
        return i.getItem().builtInRegistryHolder().is(AETags.METAL_INGOTS);
    }

    private boolean isAllowEdit() {
        return this.allowEdit;
    }

    public void setAllowEdit(boolean allowEdit) {
        this.allowEdit = allowEdit;
    }

    public enum PlacableItemType {
        STORAGE_CELLS(Icon.BACKGROUND_STORAGE_CELL),
        ORE(Icon.BACKGROUND_ORE),
        STORAGE_COMPONENT(Icon.BACKGROUND_STORAGE_COMPONENT),
        /**
         * Only allows items that have a registered {@link IGridLinkableHandler}.
         */
        GRID_LINKABLE_ITEM(Icon.BACKGROUND_WIRELESS_TERM),
        TRASH(Icon.BACKGROUND_TRASH),
        UPGRADES(Icon.BACKGROUND_UPGRADE),
        WORKBENCH_CELL(Icon.BACKGROUND_STORAGE_CELL),
        VIEW_CELL(Icon.BACKGROUND_VIEW_CELL),
        INSCRIBER_PLATE(Icon.BACKGROUND_PLATE),
        INSCRIBER_INPUT(Icon.BACKGROUND_INGOT),
        METAL_INGOTS(Icon.BACKGROUND_INGOT);

        public final Icon icon;

        PlacableItemType(Icon o) {
            this.icon = o;
        }
    }
}
