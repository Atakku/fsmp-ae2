package appeng.integration.modules.emi;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiCraftingRecipe;
import dev.emi.emi.api.recipe.EmiInfoRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.stack.EmiStack;

import appeng.api.integrations.emi.EmiStackConverters;
import appeng.api.upgrades.Upgrades;
import appeng.core.AppEng;
import appeng.core.definitions.AEItems;
import appeng.core.definitions.AEParts;
import appeng.core.definitions.ItemDefinition;
import appeng.core.localization.GuiText;
import appeng.core.localization.LocalizationEnum;
import appeng.integration.abstraction.ItemListMod;
import appeng.integration.modules.itemlists.ItemPredicates;
import appeng.menu.me.items.CraftingTermMenu;
import appeng.menu.me.items.WirelessCraftingTermMenu;
import appeng.recipes.AERecipeTypes;
import appeng.recipes.game.StorageCellUpgradeRecipe;

@EmiEntrypoint
public class AppEngEmiPlugin implements EmiPlugin {
    static final ResourceLocation TEXTURE = AppEng.makeId("textures/guis/jei.png");

    @Override
    public void register(EmiRegistry registry) {

        ItemListMod.setAdapter(new EmiItemListModAdapter());

        EmiStackConverters.register(new EmiItemStackConverter());
        EmiStackConverters.register(new EmiFluidStackConverter());

        // Screen handling
        registry.addGenericExclusionArea(new EmiAeBaseScreenExclusionZones());
        registry.addGenericStackProvider(new EmiAeBaseScreenStackProvider());
        registry.addGenericDragDropHandler(new EmiAeBaseScreenDragDropHandler());

        // Additional Workstations
        registerWorkstations(registry);

        // Descriptions
        registerDescriptions(registry);

        // Recipe transfer
        registry.addRecipeHandler(CraftingTermMenu.TYPE, new EmiUseCraftingRecipeHandler<>(CraftingTermMenu.class));
        registry.addRecipeHandler(WirelessCraftingTermMenu.TYPE,
                new EmiUseCraftingRecipeHandler<>(WirelessCraftingTermMenu.class));

        // Special upgrade recipes
        adaptSpecialRecipes(registry, StorageCellUpgradeRecipe.class, this::convertStorageCellUpgradeRecipe);

        // In-World Transformation
        registry.addCategory(EmiTransformRecipe.CATEGORY);
        adaptRecipeType(registry, AERecipeTypes.TRANSFORM, EmiTransformRecipe::new);

        // Simple item upgrades
        for (var entry : Upgrades.getUpgradableItems().entrySet()) {
            for (var upgrade : entry.getValue()) {
                registry.addRecipe(new EmiAddItemUpgradeRecipe(entry.getKey(), upgrade));
            }
        }

        // Remove items
        registry.removeEmiStacks(emiStack -> {
            var stack = emiStack.getItemStack();
            return !stack.isEmpty() && ItemPredicates.shouldBeHidden(stack);
        });
    }

    private EmiRecipe convertStorageCellUpgradeRecipe(RecipeHolder<StorageCellUpgradeRecipe> holder) {
        var recipe = holder.value();
        var cellStack = EmiStack.of(recipe.getInputCell());
        cellStack.setRemainder(EmiStack.of(recipe.getResultComponent()));
        return new EmiCraftingRecipe(
                List.of(cellStack, EmiStack.of(recipe.getInputComponent())),
                EmiStack.of(recipe.getResultCell()),
                holder.id(),
                true) {
            @Override
            public boolean supportsRecipeTree() {
                return false; // Since this is an upgrade recipe, do not show on tree
            }
        };
    }

    private void registerWorkstations(EmiRegistry registry) {
        ItemStack craftingTerminal = AEParts.CRAFTING_TERMINAL.stack();
        registry.addWorkstation(VanillaEmiRecipeCategories.CRAFTING, EmiStack.of(craftingTerminal));

        ItemStack wirelessCraftingTerminal = AEItems.WIRELESS_CRAFTING_TERMINAL.stack();
        registry.addWorkstation(VanillaEmiRecipeCategories.CRAFTING, EmiStack.of(wirelessCraftingTerminal));
    }

    private void registerDescriptions(EmiRegistry registry) {

        addDescription(registry, AEItems.CERTUS_QUARTZ_CRYSTAL, GuiText.CertusQuartzObtain);
    }

    private void addDescription(EmiRegistry registry, ItemDefinition<?> item, LocalizationEnum... lines) {

        var info = new EmiInfoRecipe(
                List.of(EmiStack.of(item)),
                Arrays.stream(lines).<Component>map(LocalizationEnum::text).toList(),
                null);
        registry.addRecipe(info);

    }

    private static <C extends RecipeInput, T extends Recipe<C>> void adaptRecipeType(EmiRegistry registry,
            RecipeType<T> recipeType,
            Function<RecipeHolder<T>, ? extends EmiRecipe> adapter) {
        registry.getRecipeManager().getAllRecipesFor(recipeType)
                .stream()
                .map(adapter)
                .forEach(registry::addRecipe);
    }

    private static <T extends Recipe<?>> void adaptSpecialRecipes(EmiRegistry registry,
            Class<T> recipeClass,
            Function<RecipeHolder<T>, ? extends EmiRecipe> adapter) {
        registry.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING)
                .stream()
                .filter(r -> recipeClass.isInstance(r.value()))
                .map(r -> adapter.apply(new RecipeHolder<>(r.id(), recipeClass.cast(r.value()))))
                .forEach(registry::addRecipe);
    }
}
