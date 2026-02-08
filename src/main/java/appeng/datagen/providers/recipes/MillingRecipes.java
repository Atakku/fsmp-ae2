package appeng.datagen.providers.recipes;

import java.util.concurrent.CompletableFuture;

import com.simibubi.create.api.data.recipe.MillingRecipeGen;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import appeng.core.AppEng;
import appeng.core.definitions.TLItems;
import appeng.datagen.providers.tags.ConventionTags;

public class MillingRecipes extends MillingRecipeGen {
    @Override
    public String getName() {
        return "TL2 Milling Recipes";
    }

    public MillingRecipes(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries, AppEng.MOD_ID);

        dust("amethyst", ConventionTags.GEMS_AMETHYST, TLItems.DUST_AMETHYST);
        dust("quartz", ConventionTags.GEMS_QUARTZ, TLItems.DUST_QUARTZ);
        dust("fluix", ConventionTags.GEMS_FLUIX, TLItems.DUST_FLUIX);
    }

    private void dust(String name, TagKey<Item> input, ItemLike result) {
        create("dust_" + name, b -> b.require(input).duration(200).output(result));
    }
}
