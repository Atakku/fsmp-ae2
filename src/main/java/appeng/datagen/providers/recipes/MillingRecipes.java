package appeng.datagen.providers.recipes;

import java.util.concurrent.CompletableFuture;

import com.simibubi.create.api.data.recipe.MillingRecipeGen;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

import appeng.core.AppEng;
import appeng.core.definitions.TLItems;

public class MillingRecipes extends MillingRecipeGen {
    public MillingRecipes(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries, AppEng.MOD_ID);
    }

    @Override
    public String getName() {
        return "TL2 Crushing Recipes";
    }

    GeneratedRecipe

    AMETHYST_DUST = create(() -> Items.AMETHYST_SHARD,
            b -> b.duration(200).output(TLItems.AMETHYST_DUST)),
            QUARTZ_DUST = create(() -> Items.QUARTZ,
                    b -> b.duration(200).output(TLItems.QUARTZ_DUST)),
            FLUIX_DUST = create(() -> TLItems.FLUIX_CRYSTAL,
                    b -> b.duration(200).output(TLItems.FLUIX_DUST));
}
