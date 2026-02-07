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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import appeng.core.AppEng;
import appeng.core.definitions.BlockDefinition;
import appeng.core.definitions.TLBlocks;
import appeng.datagen.providers.ITL2DataProvider;

public class BlockTagsProvider extends IntrinsicHolderTagsProvider<Block> implements ITL2DataProvider {
    public BlockTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries,
            ExistingFileHelper existingFileHelper) {
        super(packOutput, Registries.BLOCK, registries, block -> block.builtInRegistryHolder().key(), AppEng.MOD_ID,
                existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(ConventionTags.GLASS_BLOCK).add(TLBlocks.QUARTZ_GLASS.block(), TLBlocks.QUARTZ_VIBRANT_GLASS.block());

        addEffectiveTools();
    }

    private void addEffectiveTools() {
        Map<BlockDefinition<?>, List<TagKey<Block>>> specialTags = new HashMap<>();
        var defaultTags = List.of(BlockTags.MINEABLE_WITH_PICKAXE);

        for (var block : TLBlocks.getBlocks()) {
            for (var desiredTag : specialTags.getOrDefault(block, defaultTags)) {
                tag(desiredTag).add(block.block());
            }
        }

    }
}
