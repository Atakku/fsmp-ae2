package appeng.datagen.providers.recipes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import appeng.core.AppEng;
import appeng.core.definitions.BlockDefinition;
import appeng.core.definitions.ItemDefinition;
import appeng.core.definitions.TLBlocks;
import appeng.core.definitions.TLItems;
import appeng.recipes.game.AddItemUpgradeRecipe;
import appeng.recipes.game.RemoveItemUpgradeRecipe;
import appeng.recipes.game.StorageCellDisassemblyRecipe;
import appeng.recipes.game.StorageCellUpgradeRecipe;

public class UpgradeRecipes extends TL2RecipeProvider {
    public UpgradeRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    // Defaults will always be Cell Component for upgrade/disassembly. Additional options are for modpack developers.
    record UnitTransformTier(BlockDefinition<?> baseBlock, ItemDefinition<?> upgradeItem) {
    }

    record CellDisassemblyTier(ItemDefinition<?> cell, ItemDefinition<?> component) {
    }

    record CellUpgradeTier(String suffix, ItemDefinition<?> cell, ItemLike component) {
    }

    @Override
    public void buildRecipes(RecipeOutput consumer) {
        itemUpgradeRecipe(consumer);

        storageCellUpgradeRecipes(consumer);
    }

    private void storageCellUpgradeRecipes(RecipeOutput output) {
        storageCellUpgradeRecipes(
                output,
                List.of(
                        new CellUpgradeTier("1k", TLItems.ITEM_CELL_1K, TLItems.CELL_COMPONENT_1K),
                        new CellUpgradeTier("4k", TLItems.ITEM_CELL_4K, TLItems.CELL_COMPONENT_4K),
                        new CellUpgradeTier("16k", TLItems.ITEM_CELL_16K, TLItems.CELL_COMPONENT_16K),
                        new CellUpgradeTier("64k", TLItems.ITEM_CELL_64K, TLItems.CELL_COMPONENT_64K),
                        new CellUpgradeTier("256k", TLItems.ITEM_CELL_256K, TLItems.CELL_COMPONENT_256K)),
                List.of(TLItems.ITEM_CELL_HOUSING));
        storageCellUpgradeRecipes(
                output,
                List.of(
                        new CellUpgradeTier("1k", TLItems.FLUID_CELL_1K, TLItems.CELL_COMPONENT_1K),
                        new CellUpgradeTier("4k", TLItems.FLUID_CELL_4K, TLItems.CELL_COMPONENT_4K),
                        new CellUpgradeTier("16k", TLItems.FLUID_CELL_16K, TLItems.CELL_COMPONENT_16K),
                        new CellUpgradeTier("64k", TLItems.FLUID_CELL_64K, TLItems.CELL_COMPONENT_64K),
                        new CellUpgradeTier("256k", TLItems.FLUID_CELL_256K, TLItems.CELL_COMPONENT_256K)),
                List.of(TLItems.FLUID_CELL_HOUSING));
        storageCellUpgradeRecipes(
                output,
                List.of(
                        new CellUpgradeTier("1k", TLItems.PORTABLE_ITEM_CELL1K, TLItems.CELL_COMPONENT_1K),
                        new CellUpgradeTier("4k", TLItems.PORTABLE_ITEM_CELL4K, TLItems.CELL_COMPONENT_4K),
                        new CellUpgradeTier("16k", TLItems.PORTABLE_ITEM_CELL16K, TLItems.CELL_COMPONENT_16K),
                        new CellUpgradeTier("64k", TLItems.PORTABLE_ITEM_CELL64K, TLItems.CELL_COMPONENT_64K),
                        new CellUpgradeTier("256k", TLItems.PORTABLE_ITEM_CELL256K, TLItems.CELL_COMPONENT_256K)),
                List.of(TLBlocks.ME_CHEST, Blocks.REDSTONE_BLOCK, TLItems.ITEM_CELL_HOUSING));
        storageCellUpgradeRecipes(
                output,
                List.of(
                        new CellUpgradeTier("1k", TLItems.PORTABLE_FLUID_CELL1K, TLItems.CELL_COMPONENT_1K),
                        new CellUpgradeTier("4k", TLItems.PORTABLE_FLUID_CELL4K, TLItems.CELL_COMPONENT_4K),
                        new CellUpgradeTier("16k", TLItems.PORTABLE_FLUID_CELL16K, TLItems.CELL_COMPONENT_16K),
                        new CellUpgradeTier("64k", TLItems.PORTABLE_FLUID_CELL64K, TLItems.CELL_COMPONENT_64K),
                        new CellUpgradeTier("256k", TLItems.PORTABLE_FLUID_CELL256K, TLItems.CELL_COMPONENT_256K)),
                List.of(TLBlocks.ME_CHEST, Blocks.REDSTONE_BLOCK, TLItems.FLUID_CELL_HOUSING));
    }

    private void storageCellUpgradeRecipes(RecipeOutput output, List<CellUpgradeTier> tiers,
            List<ItemLike> additionalDisassemblyItems) {
        for (int i = 0; i < tiers.size(); i++) {
            var fromTier = tiers.get(i);
            var inputCell = fromTier.cell().asItem();
            var inputId = fromTier.cell().id();
            var resultComponent = fromTier.component().asItem();

            cellDisassembly(output, additionalDisassemblyItems, fromTier);

            // Allow a direct upgrade to any higher tier
            for (int j = i + 1; j < tiers.size(); j++) {
                var toTier = tiers.get(j);
                var resultCell = toTier.cell().asItem();
                var inputComponent = toTier.component().asItem();

                var recipeId = inputId.withPath(path -> "upgrade/" + path + "_to_" + toTier.suffix);

                output.accept(
                        recipeId,
                        new StorageCellUpgradeRecipe(
                                inputCell, inputComponent,
                                resultCell, resultComponent),
                        null);
            }
        }
    }

    private void itemUpgradeRecipe(RecipeOutput output) {
        output.accept(AppEng.makeId("add_item_upgrade"), AddItemUpgradeRecipe.INSTANCE, null);
        output.accept(AppEng.makeId("remove_item_upgrade"), RemoveItemUpgradeRecipe.INSTANCE, null);
    }

    private void cellDisassembly(RecipeOutput consumer, List<ItemLike> additionalReturn, CellUpgradeTier tier) {
        List<ItemStack> results = new ArrayList<>();
        for (var itemLike : additionalReturn) {
            results.add(itemLike.asItem().getDefaultInstance());
        }
        results.add(tier.component.asItem().getDefaultInstance());

        consumer.accept(
                tier.cell.id().withPrefix("cell_upgrade/"),
                new StorageCellDisassemblyRecipe(
                        tier.cell.asItem(),
                        results),
                null);
    }

    @Override
    public String getName() {
        return "TL2 Storage Upgrade/Disassembly Recipes";
    }
}
