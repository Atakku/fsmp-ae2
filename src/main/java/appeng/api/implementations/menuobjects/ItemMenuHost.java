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

package appeng.api.implementations.menuobjects;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import appeng.api.config.Actionable;
import appeng.api.stacks.AEKey;
import appeng.api.upgrades.IUpgradeInventory;
import appeng.api.upgrades.IUpgradeableObject;
import appeng.menu.locator.ItemMenuHostLocator;

/**
 * Base interface for an adapter that connects an item stack in a player inventory with a menu that is opened by it.
 */
public class ItemMenuHost<T extends Item> implements IUpgradeableObject {

    private final T item;
    private final Player player;
    private final ItemMenuHostLocator locator;
    private final IUpgradeInventory upgrades;

    public ItemMenuHost(T item, Player player, ItemMenuHostLocator locator) {
        this.player = player;
        this.locator = locator;
        this.item = item;
        var currentStack = getItemStack();
        if (!currentStack.is(item)) {
            throw new IllegalArgumentException("The current item in-slot is " + currentStack.getItem() + " but " +
                    "this menu requires " + item);
        }
        this.upgrades = new DelegateItemUpgradeInventory(this::getItemStack);
    }

    /**
     * @return The player holding the item.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return The index of the item hosting the menu in the {@link #getPlayer() players} inventory. Null if the item is
     *         not directly accessible via the inventory.
     */
    @Nullable
    public Integer getPlayerInventorySlot() {
        return locator.getPlayerInventorySlot();
    }

    @Nullable
    public ItemMenuHostLocator getLocator() {
        return locator;
    }

    public T getItem() {
        return item;
    }

    /**
     * @return The item stack hosting the menu. This can change.
     */
    public ItemStack getItemStack() {
        return locator.locateItem(player);
    }

    /**
     * @return True if this host is on the client-side.
     */
    public boolean isClientSide() {
        return player.level().isClientSide;
    }

    /**
     * Gives the item hosting the GUI a chance to do periodic actions when the menu is being ticked.
     */
    public void tick() {
    }

    /**
     * Checks if the item underlying this host is still in place.
     */
    public boolean isValid() {
        var currentItem = getItemStack();
        return !currentItem.isEmpty() && currentItem.is(item);
    }

    @Override
    public final IUpgradeInventory getUpgrades() {
        return upgrades;
    }

    /**
     * Insert something into the host of this menu (i.e. by dropping onto the hosting item in the player inventory or by
     * similar mechanisms).
     *
     * @return The amount that was inserted.
     */
    public long insert(Player player, AEKey what, long amount, Actionable mode) {
        return 0;
    }
}
