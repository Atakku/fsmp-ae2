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

package appeng.core;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import appeng.api.ids.TLCreativeTabIds;
import appeng.block.TLBaseBlock;
import appeng.block.TLBaseBlockItem;
import appeng.core.definitions.ItemDefinition;
import appeng.core.definitions.TLBlocks;
import appeng.core.localization.GuiText;
import appeng.items.TLBaseItem;

public final class MainCreativeTab {

    private static final Multimap<ResourceKey<CreativeModeTab>, ItemDefinition<?>> externalItemDefs = HashMultimap
            .create();
    private static final List<ItemDefinition<?>> itemDefs = new ArrayList<>();

    public static void init(Registry<CreativeModeTab> registry) {
        var tab = CreativeModeTab.builder()
                .title(GuiText.CreativeTab.text())
                .icon(() -> TLBlocks.CONTROLLER.stack(1))
                .displayItems(MainCreativeTab::buildDisplayItems)
                .build();
        Registry.register(registry, TLCreativeTabIds.MAIN, tab);
    }

    public static void initExternal(BuildCreativeModeTabContentsEvent contents) {
        for (var itemDefinition : externalItemDefs.get(contents.getTabKey())) {
            contents.accept(itemDefinition);
        }
    }

    public static void add(ItemDefinition<?> itemDef) {
        itemDefs.add(itemDef);
    }

    public static void addExternal(ResourceKey<CreativeModeTab> tab, ItemDefinition<?> itemDef) {
        externalItemDefs.put(tab, itemDef);
    }

    private static void buildDisplayItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters,
            CreativeModeTab.Output output) {
        for (var itemDef : itemDefs) {
            var item = itemDef.asItem();

            // For block items, the block controls the creative tab
            if (item instanceof TLBaseBlockItem baseItem
                    && baseItem.getBlock() instanceof TLBaseBlock baseBlock) {
                baseBlock.addToMainCreativeTab(itemDisplayParameters, output);
            } else if (item instanceof TLBaseItem baseItem) {
                baseItem.addToMainCreativeTab(itemDisplayParameters, output);
            } else {
                output.accept(itemDef);
            }
        }
    }
}
