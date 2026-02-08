package appeng.datagen.providers.recipes;

import java.util.concurrent.CompletableFuture;

import com.simibubi.create.api.data.recipe.CuttingRecipeGen;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import appeng.core.AppEng;
import appeng.core.definitions.TLItems;
import appeng.datagen.providers.tags.ConventionTags;

public class CuttingRecipes extends CuttingRecipeGen {
    @Override
    public String getName() {
        return "TL2 Cutting Recipes";
    }

    public CuttingRecipes(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries, AppEng.MOD_ID);

        circuit("logic", ConventionTags.INGOT_GOLD, TLItems.CIRCUIT_LOGIC);
        circuit("calculation", ConventionTags.GEMS_QUARTZ, TLItems.CIRCUIT_CALCULATION);
        circuit("engineering", ConventionTags.GEMS_DIAMOND, TLItems.CIRCUIT_ENGINEERING);
    }

    private void circuit(String name, TagKey<Item> input, ItemLike result) {
        create("circuit_" + name, b -> b.require(input).duration(200).output(result));
    }
}
