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

package appeng.helpers;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.item.ItemStack;

import appeng.api.inventories.InternalInventory;
import appeng.api.networking.IGridNode;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEItemKey;
import appeng.api.storage.ILinkStatus;

public interface ICraftingGridMenu {
    /**
     * @return The current link status of the menu, allowing for gating access to {@link #getGridNode()}.
     */
    default ILinkStatus getLinkStatus() {
        return ILinkStatus.ofConnected();
    }

    /**
     * @return gain access to network infrastructure.
     */
    @Nullable
    IGridNode getGridNode();

    /**
     * @return the inventory used for the crafting matrix.
     */
    InternalInventory getCraftingMatrix();

    /**
     * @return who are we?
     */
    IActionSource getActionSource();

    /**
     * @return array of view cells. can contain empty itemstacks.
     */
    List<ItemStack> getViewCells();

    /**
     * @return True if the given player inventory slot is locked by the current menu and should not be used for
     *         crafting. (i.e. the wireless terminal itself in case of a wireless crafting terminal).
     */
    boolean isPlayerInventorySlotLocked(int invSlot);

    record AutoCraftEntry(AEItemKey what, List<Integer> slots) {
    }
}
