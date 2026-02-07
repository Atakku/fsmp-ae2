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

package appeng.datagen.providers.tags;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import appeng.api.ids.TLTags;
import appeng.api.util.TLColor;
import appeng.core.AppEng;
import appeng.core.definitions.TLItems;
import appeng.core.definitions.TLParts;
import appeng.datagen.providers.ITL2DataProvider;

public class ItemTagsProvider extends net.minecraft.data.tags.ItemTagsProvider implements ITL2DataProvider {

    public ItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries,
            CompletableFuture<TagLookup<Block>> blockTagsProvider, ExistingFileHelper existingFileHelper) {
        super(packOutput, registries, blockTagsProvider, AppEng.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        copyBlockTags();

        for (TLColor color : TLColor.values()) {
            tag(ConventionTags.SMART_DENSE_CABLE).add(TLParts.SMART_DENSE_CABLE.item(color));
            tag(ConventionTags.SMART_CABLE).add(TLParts.SMART_CABLE.item(color));
            tag(ConventionTags.GLASS_CABLE).add(TLParts.GLASS_CABLE.item(color));
            tag(ConventionTags.COVERED_CABLE).add(TLParts.COVERED_CABLE.item(color));
            tag(ConventionTags.COVERED_DENSE_CABLE).add(TLParts.COVERED_DENSE_CABLE.item(color));
        }

        tag(ConventionTags.SILICON)
                .add(TLItems.SILICON.asItem());

        tag(TLTags.METAL_INGOTS)
                .addOptionalTag(ResourceLocation.parse("c:ingots/copper"))
                .addOptionalTag(ResourceLocation.parse("c:ingots/tin"))
                .addOptionalTag(ResourceLocation.parse("c:ingots/iron"))
                .addOptionalTag(ResourceLocation.parse("c:ingots/gold"))
                .addOptionalTag(ResourceLocation.parse("c:ingots/brass"))
                .addOptionalTag(ResourceLocation.parse("c:ingots/nickel"))
                .addOptionalTag(ResourceLocation.parse("c:ingots/aluminium"));

        tag(ConventionTags.ILLUMINATED_PANEL)
                .add(TLParts.MONITOR.asItem())
                .add(TLParts.SEMI_DARK_MONITOR.asItem())
                .add(TLParts.DARK_MONITOR.asItem());

        tag(ConventionTags.FLUIX_DUST)
                .add(TLItems.FLUIX_DUST.asItem());

        tag(ConventionTags.FLUIX_CRYSTAL)
                .add(TLItems.FLUIX_CRYSTAL.asItem());

        tag(ConventionTags.DUSTS)
                .add(TLItems.FLUIX_DUST.asItem());

        tag(ConventionTags.GEMS)
                .add(TLItems.FLUIX_CRYSTAL.asItem());

        tag(ConventionTags.CURIOS).add(
                TLItems.WIRELESS_TERMINAL.asItem(),
                TLItems.WIRELESS_CRAFTING_TERMINAL.asItem(),
                TLItems.PORTABLE_ITEM_CELL1K.asItem(),
                TLItems.PORTABLE_ITEM_CELL4K.asItem(),
                TLItems.PORTABLE_ITEM_CELL16K.asItem(),
                TLItems.PORTABLE_ITEM_CELL64K.asItem(),
                TLItems.PORTABLE_ITEM_CELL256K.asItem(),
                TLItems.PORTABLE_FLUID_CELL1K.asItem(),
                TLItems.PORTABLE_FLUID_CELL4K.asItem(),
                TLItems.PORTABLE_FLUID_CELL16K.asItem(),
                TLItems.PORTABLE_FLUID_CELL64K.asItem(),
                TLItems.PORTABLE_FLUID_CELL256K.asItem());

        tag(ConventionTags.CAN_REMOVE_COLOR).add(Items.WATER_BUCKET, Items.SNOWBALL);

        // Manually add tags for mods that are unlikely to do it themselves since we don't want to force users to craft
        tag(ConventionTags.WRENCH).addOptional(ResourceLocation.parse("immersiveengineering:hammer"));
    }

    // Copy the entries TL2 added to certain block tags over to item tags of the same name
    // Assumes that items or item tags generally have the same name as the block equivalent.
    private void copyBlockTags() {
        copy(ConventionTags.GLASS_BLOCK, ConventionTags.GLASS);
    }
}
