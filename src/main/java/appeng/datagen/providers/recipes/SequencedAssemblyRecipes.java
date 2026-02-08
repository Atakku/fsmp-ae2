package appeng.datagen.providers.recipes;

import java.util.concurrent.CompletableFuture;

import com.simibubi.create.api.data.recipe.SequencedAssemblyRecipeGen;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import appeng.core.AppEng;
import appeng.core.definitions.TLItems;
import appeng.datagen.providers.tags.ConventionTags;

public class SequencedAssemblyRecipes extends SequencedAssemblyRecipeGen {

    @Override
    public String getName() {
        return "TL2 Sequenced Recipes";
    }

    public SequencedAssemblyRecipes(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries, AppEng.MOD_ID);

        // spotless:off
        processor("logic", TLItems.CIRCUIT_LOGIC, TLItems.INCOMPLETE_PROCESSOR_LOGIC, TLItems.PROCESSOR_LOGIC);
        processor("calculation", TLItems.CIRCUIT_CALCULATION, TLItems.INCOMPLETE_PROCESSOR_CALCULATION, TLItems.PROCESSOR_CALCULATION);
        processor("engineering", TLItems.CIRCUIT_ENGINEERING, TLItems.INCOMPLETE_PROCESSOR_ENGINEERING, TLItems.PROCESSOR_ENGINEERING);

        component("1k", TLItems.PROCESSOR_LOGIC,  TLItems.INCOMPLETE_COMPONENT_1K, ConventionTags.DUSTS_REDSTONE, Ingredient.of(ConventionTags.GEMS_AMETHYST), TLItems.COMPONENT_1K, 4);
        component("4k", TLItems.PROCESSOR_CALCULATION,  TLItems.INCOMPLETE_COMPONENT_4K, ConventionTags.DUSTS_REDSTONE, Ingredient.of(TLItems.COMPONENT_1K), TLItems.COMPONENT_4K, 3);
        component("16k", TLItems.PROCESSOR_CALCULATION, TLItems.INCOMPLETE_COMPONENT_16K, ConventionTags.DUSTS_GLOWSTONE, Ingredient.of(TLItems.COMPONENT_4K), TLItems.COMPONENT_16K, 3);
        component("64k", TLItems.PROCESSOR_ENGINEERING, TLItems.INCOMPLETE_COMPONENT_64K, ConventionTags.DUSTS_FLUIX, Ingredient.of(TLItems.COMPONENT_16K), TLItems.COMPONENT_64K, 3);
        // spotless:on
    }

    private void processor(String name, ItemLike input, ItemLike incomplete, ItemLike result) {
        create("processor_" + name,
                b -> b.require(input)
                        .transitionTo(incomplete)
                        .addStep(DeployerApplicationRecipe::new, rb -> rb.require(ConventionTags.DUSTS_REDSTONE))
                        .addStep(DeployerApplicationRecipe::new, rb -> rb.require(ConventionTags.SILICON))
                        .addStep(PressingRecipe::new, rb -> rb)
                        .addOutput(result, 1));
    }

    private void component(String name, ItemLike input, ItemLike incomplete, TagKey<Item> dust, Ingredient resource,
            ItemLike result, int loops) {
        create("component_" + name,
                b -> b.require(input)
                        .transitionTo(incomplete)
                        .addStep(DeployerApplicationRecipe::new, rb -> rb.require(dust))
                        .addStep(DeployerApplicationRecipe::new, rb -> rb.require(resource))
                        .addStep(PressingRecipe::new, rb -> rb)
                        .loops(loops)
                        .addOutput(result, 1)

        );
    }
}
