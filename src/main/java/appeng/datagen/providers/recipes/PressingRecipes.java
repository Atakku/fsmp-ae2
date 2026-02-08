package appeng.datagen.providers.recipes;

import java.util.concurrent.CompletableFuture;

import com.simibubi.create.api.data.recipe.PressingRecipeGen;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;

import appeng.core.AppEng;
import appeng.core.definitions.TLItems;
import appeng.datagen.providers.tags.ConventionTags;

public class PressingRecipes extends PressingRecipeGen {
    @Override
    public String getName() {
        return "TL2 Pressing Recipes";
    }

    public PressingRecipes(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries, AppEng.MOD_ID);

        create("pressed_silicon",
                b -> b.require(ConventionTags.SILICON)
                        .output(TLItems.PRESSED_SILICON));
    }
}
