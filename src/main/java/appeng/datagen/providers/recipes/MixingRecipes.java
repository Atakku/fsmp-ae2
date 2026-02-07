package appeng.datagen.providers.recipes;

import java.util.concurrent.CompletableFuture;

import com.simibubi.create.api.data.recipe.MixingRecipeGen;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;

import appeng.core.AppEng;
import appeng.core.definitions.TLItems;
import appeng.datagen.providers.tags.ConventionTags;

public class MixingRecipes extends MixingRecipeGen {
    public MixingRecipes(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries, AppEng.MOD_ID);
    }

    @Override
    public String getName() {
        return "TL2 Mixing Recipes";
    }

    GeneratedRecipe

    FLUIX_CRYSTAL = create("fluix_crystal",
            b -> b.require(Fluids.WATER, 250).require(ConventionTags.DUSTS_AMETHYST)
                    .require(ConventionTags.DUSTS_REDSTONE)
                    .require(ConventionTags.DUSTS_QUARTZ)
                    .withFluidOutputs(new FluidStack(Fluids.WATER, 250))
                    .output(TLItems.FLUIX_DUST.get(), 2));
}
