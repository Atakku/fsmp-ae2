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

package appeng.init.client;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import appeng.api.util.TLColor;
import appeng.client.render.StaticItemColor;
import appeng.core.definitions.ItemDefinition;
import appeng.core.definitions.TLBlocks;
import appeng.core.definitions.TLItems;
import appeng.items.parts.ColoredPartItem;
import appeng.items.parts.PartItem;
import appeng.items.storage.BasicStorageCell;
import appeng.items.tools.powered.ColorApplicatorItem;
import appeng.items.tools.powered.PortableCellItem;

public final class InitItemColors {
    private InitItemColors() {
    }

    @FunctionalInterface
    interface ItemColorRegistrar {
        void register(ItemColor itemColor, ItemLike... items);
    }

    public static void init(RegisterColorHandlersEvent.Item event) {
        // Automatically make all registered itemcolors create opaque colors
        init((itemColor, items) -> event.register(makeOpaque(itemColor), items));
    }

    private static void init(ItemColorRegistrar registrar) {
        // I checked, the ME chest doesn't keep its color in item form
        registrar.register(new StaticItemColor(TLColor.TRANSPARENT), TLBlocks.ME_CHEST.asItem());

        registrar.register(InitItemColors::getColorApplicatorColor, TLItems.COLOR_APPLICATOR);

        registrar.register(PortableCellItem::getColor, TLItems.PORTABLE_ITEM_CELL1K, TLItems.PORTABLE_FLUID_CELL1K,
                TLItems.PORTABLE_ITEM_CELL4K, TLItems.PORTABLE_FLUID_CELL4K,
                TLItems.PORTABLE_ITEM_CELL16K, TLItems.PORTABLE_FLUID_CELL16K,
                TLItems.PORTABLE_ITEM_CELL64K, TLItems.PORTABLE_FLUID_CELL64K);

        registrar.register(BasicStorageCell::getColor, TLItems.ITEM_CELL_1K, TLItems.FLUID_CELL_1K,
                TLItems.ITEM_CELL_4K, TLItems.FLUID_CELL_4K,
                TLItems.ITEM_CELL_16K, TLItems.FLUID_CELL_16K,
                TLItems.ITEM_CELL_64K, TLItems.FLUID_CELL_64K);

        // Automatically register colors for certain items we register
        for (ItemDefinition<?> definition : TLItems.getItems()) {
            Item item = definition.asItem();
            if (item instanceof PartItem) {
                TLColor color = TLColor.TRANSPARENT;
                if (item instanceof ColoredPartItem) {
                    color = ((ColoredPartItem<?>) item).getColor();
                }
                registrar.register(new StaticItemColor(color), item);
            }
        }
    }

    private static int getColorApplicatorColor(ItemStack itemStack, int idx) {
        if (idx == 0) {
            return -1;
        }

        final TLColor col = ((ColorApplicatorItem) itemStack.getItem()).getActiveColor(itemStack);

        if (col == null) {
            return -1;
        }

        return switch (idx) {
            case 1 -> col.blackVariant;
            case 2 -> col.mediumVariant;
            case 3 -> col.whiteVariant;
            default -> -1;
        };
    }

    private static ItemColor makeOpaque(ItemColor itemColor) {
        return (stack, tintIndex) -> FastColor.ARGB32.opaque(itemColor.getColor(stack, tintIndex));
    }
}
