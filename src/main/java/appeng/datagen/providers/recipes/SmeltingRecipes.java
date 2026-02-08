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

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;

import appeng.core.AppEng;
import appeng.core.definitions.TLItems;
import appeng.datagen.providers.tags.ConventionTags;

public class SmeltingRecipes extends TL2RecipeProvider {

    public SmeltingRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public String getName() {
        return "TL2 Smelting Recipes";
    }

    @Override
    public void buildRecipes(RecipeOutput consumer) {
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(ConventionTags.DUSTS_QUARTZ), RecipeCategory.MISC, TLItems.SILICON, .35f,
                        200)
                .unlockedBy("has_quartz_dust", has(ConventionTags.DUSTS_QUARTZ))
                .save(consumer, AppEng.makeId("smelting/silicon_from_quartz_dust"));
        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(ConventionTags.DUSTS_QUARTZ), RecipeCategory.MISC, TLItems.SILICON, .35f,
                        100)
                .unlockedBy("has_quartz_dust", has(ConventionTags.DUSTS_QUARTZ))
                .save(consumer, AppEng.makeId("blasting/silicon_from_quartz_dust"));
    }
}
