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

package appeng.init.internal;

import java.util.List;

import appeng.api.upgrades.Upgrades;
import appeng.core.definitions.TLBlocks;
import appeng.core.definitions.TLItems;
import appeng.core.definitions.TLParts;
import appeng.core.localization.GuiText;

public final class InitUpgrades {

    private InitUpgrades() {
    }

    public static void init() {
        // Block and part interface have different translation keys, but support the
        // same upgrades
        String storageCellGroup = GuiText.StorageCells.getTranslationKey();
        String portableCellGroup = GuiText.PortableCells.getTranslationKey();

        // IO Port!
        Upgrades.add(TLItems.SPEED_CARD, TLBlocks.IO_PORT, 3);
        Upgrades.add(TLItems.REDSTONE_CARD, TLBlocks.IO_PORT, 1);

        // Level Emitter!
        Upgrades.add(TLItems.FUZZY_CARD, TLParts.LEVEL_EMITTER, 1);

        // Storage Cells
        var itemCells = List.of(TLItems.ITEM_CELL_1K, TLItems.ITEM_CELL_4K, TLItems.ITEM_CELL_16K,
                TLItems.ITEM_CELL_64K);
        for (var itemCell : itemCells) {
            Upgrades.add(TLItems.FUZZY_CARD, itemCell, 1, storageCellGroup);
            Upgrades.add(TLItems.INVERTER_CARD, itemCell, 1, storageCellGroup);
            Upgrades.add(TLItems.EQUAL_DISTRIBUTION_CARD, itemCell, 1, storageCellGroup);
            Upgrades.add(TLItems.VOID_CARD, itemCell, 1, storageCellGroup);
        }

        var fluidCells = List.of(TLItems.FLUID_CELL_1K, TLItems.FLUID_CELL_4K, TLItems.FLUID_CELL_16K,
                TLItems.FLUID_CELL_64K);
        for (var fluidCell : fluidCells) {
            Upgrades.add(TLItems.INVERTER_CARD, fluidCell, 1, storageCellGroup);
            Upgrades.add(TLItems.EQUAL_DISTRIBUTION_CARD, fluidCell, 1, storageCellGroup);
            Upgrades.add(TLItems.VOID_CARD, fluidCell, 1, storageCellGroup);
        }

        var portableCells = List.of(TLItems.PORTABLE_ITEM_CELL1K, TLItems.PORTABLE_ITEM_CELL4K,
                TLItems.PORTABLE_ITEM_CELL16K, TLItems.PORTABLE_ITEM_CELL64K);
        for (var portableCell : portableCells) {
            Upgrades.add(TLItems.FUZZY_CARD, portableCell, 1, portableCellGroup);
            Upgrades.add(TLItems.INVERTER_CARD, portableCell, 1, portableCellGroup);
            Upgrades.add(TLItems.EQUAL_DISTRIBUTION_CARD, portableCell, 1, portableCellGroup);
            Upgrades.add(TLItems.VOID_CARD, portableCell, 1, portableCellGroup);
        }

        var portableFluidCells = List.of(TLItems.PORTABLE_FLUID_CELL1K, TLItems.PORTABLE_FLUID_CELL4K,
                TLItems.PORTABLE_FLUID_CELL16K, TLItems.PORTABLE_FLUID_CELL64K);
        for (var portableFluidCell : portableFluidCells) {
            Upgrades.add(TLItems.INVERTER_CARD, portableFluidCell, 1, portableCellGroup);
            Upgrades.add(TLItems.EQUAL_DISTRIBUTION_CARD, portableFluidCell, 1, portableCellGroup);
            Upgrades.add(TLItems.VOID_CARD, portableFluidCell, 1, portableCellGroup);
        }

        // View Cell
        Upgrades.add(TLItems.FUZZY_CARD, TLItems.VIEW_CELL, 1);
        Upgrades.add(TLItems.INVERTER_CARD, TLItems.VIEW_CELL, 1);

        // Color Applicator
        Upgrades.add(TLItems.EQUAL_DISTRIBUTION_CARD, TLItems.COLOR_APPLICATOR, 1);
        Upgrades.add(TLItems.VOID_CARD, TLItems.COLOR_APPLICATOR, 1);
    }

}
