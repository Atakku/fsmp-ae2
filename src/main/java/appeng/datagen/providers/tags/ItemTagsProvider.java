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

        // Foreign gems
        tag(ConventionTags.GEMS)
                .add(TLItems.FLUIX.asItem());

        // Foreign dusts
        tag(ConventionTags.DUSTS)
                .add(TLItems.DUST_AMETHYST.asItem())
                .add(TLItems.DUST_QUARTZ.asItem())
                .add(TLItems.DUST_FLUIX.asItem());

        // Basic materials
        tag(ConventionTags.GEMS_FLUIX)
                .add(TLItems.FLUIX.asItem());
        tag(ConventionTags.SILICON)
                .add(TLItems.SILICON.asItem());

        // Dusts
        tag(ConventionTags.DUSTS_AMETHYST)
                .add(TLItems.DUST_AMETHYST.asItem());
        tag(ConventionTags.DUSTS_QUARTZ)
                .add(TLItems.DUST_QUARTZ.asItem());
        tag(ConventionTags.DUSTS_FLUIX)
                .add(TLItems.DUST_FLUIX.asItem());
        ;

        // Housing
        tag(ConventionTags.HOUSING)
                .add(TLItems.HOUSING_ITEM.asItem())
                .add(TLItems.HOUSING_FLUID.asItem());

        // Cables and colors
        for (TLColor color : TLColor.values()) {
            tag(ConventionTags.SMALL_CABLES)
                    .add(TLParts.GLASS_CABLE.item(color))
                    .add(TLParts.SMART_CABLE.item(color));

            tag(ConventionTags.GLASS_CABLE)
                    .add(TLParts.GLASS_CABLE.item(color));
            tag(ConventionTags.SMART_CABLE)
                    .add(TLParts.SMART_CABLE.item(color));
            tag(ConventionTags.DENSE_CABLE)
                    .add(TLParts.DENSE_CABLE.item(color));
        }

        tag(ConventionTags.CURIOS).add(
                TLItems.WIRELESS_TERMINAL.asItem(),
                TLItems.WIRELESS_CRAFTING_TERMINAL.asItem());

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
