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

package appeng.datagen.providers.recipes;

import static appeng.datagen.providers.recipes.RecipeCriteria.criterionName;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import appeng.core.AppEng;
import appeng.core.definitions.BlockDefinition;
import appeng.core.definitions.ItemDefinition;
import appeng.core.definitions.TLBlocks;
import appeng.core.definitions.TLItems;

public class DecorationBlockRecipes extends TL2RecipeProvider {
    public DecorationBlockRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public String getName() {
        return "TL2 Decoration Blocks";
    }

    @Override
    public void buildRecipes(RecipeOutput consumer) {
        crystalBlock(consumer, TLItems.FLUIX_CRYSTAL, TLBlocks.FLUIX_BLOCK);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLBlocks.QUARTZ_VIBRANT_GLASS)
                .pattern("aba")
                .define('a', Items.GLOWSTONE_DUST)
                .define('b', TLBlocks.QUARTZ_GLASS)
                .unlockedBy(criterionName(TLBlocks.QUARTZ_GLASS), has(TLBlocks.QUARTZ_GLASS))
                .save(consumer, AppEng.makeId("decorative/quartz_vibrant_glass"));
    }

    private void crystalBlock(RecipeOutput consumer, ItemDefinition<?> crystal, BlockDefinition<?> block) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, block)
                .pattern("aa")
                .pattern("aa")
                .define('a', crystal)
                .unlockedBy(criterionName(crystal), has(crystal))
                .save(consumer, AppEng.makeId("decorative/" + block.id().getPath()));
    }
}
