
package appeng.datagen.providers.recipes;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.crafting.DifferenceIngredient;

import appeng.api.util.TLColor;
import appeng.core.AppEng;
import appeng.core.definitions.TLBlocks;
import appeng.core.definitions.TLItems;
import appeng.core.definitions.TLParts;
import appeng.datagen.providers.tags.ConventionTags;

public class CraftingRecipes extends TL2RecipeProvider {
    public CraftingRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public String getName() {
        return "TL2 Crafting Recipes";
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        // Complex ingredients
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLItems.FLUIX_PEARL)
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', ConventionTags.DUSTS_FLUIX)
                .define('b', ConventionTags.GEMS_FLUIX)
                .define('c', ConventionTags.ENDER_PEARL)
                .unlockedBy("has_dusts/fluix", has(ConventionTags.DUSTS_FLUIX))
                .unlockedBy("has_gems/fluix", has(ConventionTags.GEMS_FLUIX))
                .save(consumer, AppEng.makeId("misc/fluixpearl"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLItems.ANNIHILATION_CORE, 2)
                .pattern("abc")
                .define('a', ConventionTags.GEMS_QUARTZ)
                .define('b', ConventionTags.DUSTS_FLUIX)
                .define('c', TLItems.PROCESSOR_LOGIC)
                .unlockedBy("has_processor/logic", has(TLItems.PROCESSOR_LOGIC))
                .save(consumer, AppEng.makeId("materials/annihilationcore"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLItems.FORMATION_CORE, 2)
                .pattern("abc")
                .define('a', ConventionTags.GEMS_AMETHYST)
                .define('b', ConventionTags.DUSTS_FLUIX)
                .define('c', TLItems.PROCESSOR_LOGIC)
                .unlockedBy("has_processor/logic", has(TLItems.PROCESSOR_LOGIC))
                .save(consumer, AppEng.makeId("materials/formationcore"));

        // Cell components

        // ====================================================
        // Basic Cards
        // ====================================================
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLItems.BASIC_CARD, 2)
                .pattern("ab ")
                .pattern("cdb")
                .pattern("ab ")
                .define('a', ConventionTags.INGOT_GOLD)
                .define('b', ConventionTags.IRON_INGOT)
                .define('c', ConventionTags.DUSTS_REDSTONE)
                .define('d', TLItems.PROCESSOR_CALCULATION)
                .unlockedBy("has_calculation_processor", has(TLItems.PROCESSOR_CALCULATION))
                .save(consumer, AppEng.makeId("materials/basiccard"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLItems.REDSTONE_CARD)
                .requires(Items.REDSTONE_TORCH)
                .requires(TLItems.BASIC_CARD)
                .unlockedBy("has_basic_card", has(TLItems.BASIC_CARD))
                .save(consumer, AppEng.makeId("materials/cardredstone"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLItems.VOID_CARD)
                .requires(TLItems.PROCESSOR_CALCULATION)
                .requires(TLItems.BASIC_CARD)
                .unlockedBy("has_basic_card", has(TLItems.BASIC_CARD))
                .save(consumer, AppEng.makeId("materials/cardvoid"));

        // ====================================================
        // Advanced Cards
        // ====================================================
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLItems.ADVANCED_CARD, 2)
                .pattern("ab ")
                .pattern("cdb")
                .pattern("ab ")
                .define('a', ConventionTags.GEMS_DIAMOND)
                .define('b', ConventionTags.IRON_INGOT)
                .define('c', ConventionTags.DUSTS_REDSTONE)
                .define('d', TLItems.PROCESSOR_CALCULATION)
                .unlockedBy("has_calculation_processor", has(TLItems.PROCESSOR_CALCULATION))
                .save(consumer, AppEng.makeId("materials/advancedcard"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLItems.FUZZY_CARD)
                .requires(TLItems.ADVANCED_CARD)
                .requires(ItemTags.WOOL)
                .unlockedBy("has_advanced_card", has(TLItems.ADVANCED_CARD))
                .save(consumer, AppEng.makeId("materials/cardfuzzy"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLItems.INVERTER_CARD)
                .requires(Items.REDSTONE_TORCH)
                .requires(TLItems.ADVANCED_CARD)
                .unlockedBy("has_advanced_card", has(TLItems.ADVANCED_CARD))
                .save(consumer, AppEng.makeId("materials/cardinverter"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLItems.SPEED_CARD)
                .requires(TLItems.ADVANCED_CARD)
                .requires(TLItems.FLUIX)
                .unlockedBy("has_advanced_card", has(TLItems.ADVANCED_CARD))
                .save(consumer, AppEng.makeId("materials/cardspeed"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLItems.EQUAL_DISTRIBUTION_CARD)
                .requires(TLItems.ADVANCED_CARD)
                .requires(TLItems.PROCESSOR_CALCULATION)
                .unlockedBy("has_advanced_card", has(TLItems.ADVANCED_CARD))
                .save(consumer, AppEng.makeId("materials/carddistribution"));

        // ====================================================
        // Misc Materials
        // ====================================================

        // ====================================================
        // recipes/misc
        // ====================================================

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLItems.FLUIX, 4)
                .requires(TLBlocks.FLUIX_BLOCK)
                .unlockedBy("has_fluix_block", has(TLBlocks.FLUIX_BLOCK))
                .save(consumer, AppEng.makeId("misc/deconstruction_fluix_block"));

        // ====================================================
        // recipes/network
        // ====================================================

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLBlocks.WIRELESS_ACCESS_POINT)
                .pattern("a")
                .pattern("b")
                .pattern("c")
                .define('a', TLItems.WIRELESS_RECEIVER)
                .define('b', TLItems.PROCESSOR_CALCULATION)
                .define('c', TLParts.GLASS_CABLE.item(TLColor.TRANSPARENT))
                .unlockedBy("has_wireless_receiver", has(TLItems.WIRELESS_RECEIVER))
                .save(consumer, AppEng.makeId("network/wireless_access_point"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLItems.WIRELESS_RECEIVER)
                .pattern(" a ")
                .pattern("bcb")
                .pattern(" b ")
                .define('a', TLItems.FLUIX_PEARL)
                .define('b', ConventionTags.IRON_INGOT)
                .define('c', Items.QUARTZ)
                .unlockedBy("has_fluix_pearl", has(TLItems.FLUIX_PEARL))
                .save(consumer, AppEng.makeId("network/wireless_part"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLItems.WIRELESS_TERMINAL)
                .pattern("a")
                .pattern("b")
                .pattern("c")
                .define('a', TLItems.WIRELESS_RECEIVER)
                .define('b', TLParts.TERMINAL)
                .define('c', Blocks.REDSTONE_BLOCK)
                .unlockedBy("has_terminal", has(TLParts.TERMINAL))
                .unlockedBy("has_redstone_block", has(Blocks.REDSTONE_BLOCK))
                .unlockedBy("has_wireless_receiver", has(TLItems.WIRELESS_RECEIVER))
                .save(consumer, AppEng.makeId("network/wireless_terminal"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLItems.WIRELESS_CRAFTING_TERMINAL)
                .pattern("a")
                .pattern("b")
                .pattern("c")
                .define('a', TLItems.WIRELESS_RECEIVER)
                .define('b', TLParts.CRAFTING_TERMINAL)
                .define('c', Blocks.REDSTONE_BLOCK)
                .unlockedBy("has_terminal", has(TLParts.CRAFTING_TERMINAL))
                .unlockedBy("has_redstone_block", has(Blocks.REDSTONE_BLOCK))
                .unlockedBy("has_wireless_receiver", has(TLItems.WIRELESS_RECEIVER))
                .save(consumer, AppEng.makeId("network/wireless_crafting_terminal"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLItems.WIRELESS_CRAFTING_TERMINAL)
                .requires(TLItems.WIRELESS_TERMINAL)
                .requires(Items.CRAFTING_TABLE)
                .requires(TLItems.PROCESSOR_CALCULATION)
                .unlockedBy("has_terminal", has(TLItems.WIRELESS_TERMINAL))
                .unlockedBy("has_calculation_processor", has(TLItems.PROCESSOR_CALCULATION))
                .save(consumer, AppEng.makeId("network/upgrade_wireless_crafting_terminal"));

        // ====================================================
        // recipes/network/blocks
        // ====================================================

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLBlocks.CELL_WORKBENCH)
                .pattern("aba")
                .pattern("cdc")
                .pattern("ccc")
                .define('a', ItemTags.WOOL)
                .define('b', TLItems.PROCESSOR_CALCULATION)
                .define('c', ConventionTags.IRON_INGOT)
                .define('d', ConventionTags.CHEST)
                .unlockedBy("has_calculation_processor", has(TLItems.PROCESSOR_CALCULATION))
                .save(consumer, AppEng.makeId("network/blocks/cell_workbench"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLBlocks.CONTROLLER)
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', Blocks.SMOOTH_STONE)
                .define('b', TLItems.FLUIX)
                .define('c', TLItems.PROCESSOR_ENGINEERING)
                .unlockedBy("has_fluix_crystal", has(TLItems.FLUIX))
                .unlockedBy("has_engineering_processor", has(TLItems.PROCESSOR_ENGINEERING))
                .save(consumer, AppEng.makeId("network/blocks/controller"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLBlocks.IO_PORT)
                .pattern("aaa")
                .pattern("bcb")
                .pattern("ded")
                .define('a', ConventionTags.GLASS_CHEAP)
                .define('b', TLBlocks.DRIVE)
                .define('c', TLParts.GLASS_CABLE.item(TLColor.TRANSPARENT))
                .define('d', ConventionTags.IRON_INGOT)
                .define('e', TLItems.PROCESSOR_LOGIC)
                .unlockedBy("has_drive", has(TLBlocks.DRIVE))
                .save(consumer, AppEng.makeId("network/blocks/io_port"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLBlocks.ME_CHEST)
                .pattern("aba")
                .pattern("c c")
                .pattern("ded")
                .define('a', ConventionTags.GLASS_CHEAP)
                .define('b', TLParts.TERMINAL)
                .define('c', TLParts.GLASS_CABLE.item(TLColor.TRANSPARENT))
                .define('d', ConventionTags.IRON_INGOT)
                .define('e', ConventionTags.COPPER_INGOT)
                .unlockedBy("has_glass_cable", has(TLParts.GLASS_CABLE.item(TLColor.TRANSPARENT)))
                .unlockedBy("has_terminal", has(TLParts.TERMINAL))
                .unlockedBy("has_crystals/fluix", has(TLItems.FLUIX))
                .save(consumer, AppEng.makeId("network/blocks/storage_chest"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLBlocks.DRIVE)
                .pattern("aba")
                .pattern("c c")
                .pattern("aba")
                .define('a', ConventionTags.IRON_INGOT)
                .define('b', TLItems.PROCESSOR_ENGINEERING)
                .define('c', TLParts.GLASS_CABLE.item(TLColor.TRANSPARENT))
                .unlockedBy("has_engineering_processor", has(TLItems.PROCESSOR_ENGINEERING))
                .save(consumer, AppEng.makeId("network/blocks/storage_drive"));

        addCables(consumer);

        // ====================================================
        // recipes/network/cells
        // ====================================================

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLItems.HOUSING_ITEM)
                .pattern("aba")
                .pattern("b b")
                .pattern("cdc")
                .define('a', TLBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.DUSTS_REDSTONE)
                .define('c', ConventionTags.IRON_INGOT)
                .define('d', ConventionTags.COPPER_INGOT)
                .unlockedBy("has_dusts/redstone", has(ConventionTags.DUSTS_REDSTONE))
                .save(consumer, AppEng.makeId("network/cells/item_cell_housing"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLItems.HOUSING_FLUID)
                .pattern("aba")
                .pattern("b b")
                .pattern("ccc")
                .define('a', TLBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.DUSTS_REDSTONE)
                .define('c', ConventionTags.COPPER_INGOT)
                .unlockedBy("has_dusts/redstone", has(ConventionTags.DUSTS_REDSTONE))
                .save(consumer, AppEng.makeId("network/cells/fluid_cell_housing"));

        addFluidCells(consumer);
        addItemCells(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLItems.VIEW_CELL)
                .pattern("aba")
                .pattern("bcb")
                .pattern("ddd")
                .define('a', TLBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.DUSTS_REDSTONE)
                .define('c', TLItems.FLUIX)
                .define('d', ConventionTags.IRON_INGOT)
                .unlockedBy("has_terminal", has(TLParts.TERMINAL))
                .save(consumer, AppEng.makeId("network/cells/view_cell"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLItems.VIEW_CELL)
                .requires(TLItems.HOUSING_ITEM)
                .requires(TLItems.FLUIX)
                .unlockedBy("has_terminal", has(TLParts.TERMINAL))
                .save(consumer, AppEng.makeId("network/cells/view_cell_storage"));

        // ====================================================
        // recipes/network/parts
        // ====================================================

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.LEVEL_EMITTER)
                .requires(Items.REDSTONE_TORCH)
                .requires(TLItems.PROCESSOR_CALCULATION)
                .unlockedBy("has_calculation_processor", has(TLItems.PROCESSOR_CALCULATION))
                .save(consumer, AppEng.makeId("network/parts/level_emitter"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.CONVERSION_MONITOR)
                .requires(TLItems.FORMATION_CORE)
                .requires(TLParts.STORAGE_MONITOR)
                .requires(TLItems.ANNIHILATION_CORE)
                .unlockedBy("has_storage_monitor", has(TLParts.STORAGE_MONITOR))
                .save(consumer, AppEng.makeId("network/parts/monitors_conversion"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.STORAGE_MONITOR)
                .requires(TLParts.LEVEL_EMITTER)
                .requires(ConventionTags.ILLUMINATED_PANEL)
                .unlockedBy("has_illuminated_panel", has(ConventionTags.ILLUMINATED_PANEL))
                .unlockedBy("has_level_emitter", has(TLParts.LEVEL_EMITTER))
                .save(consumer, AppEng.makeId("network/parts/monitors_storage"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.DARK_MONITOR)
                .requires(TLParts.MONITOR)
                .unlockedBy("has_monitor", has(TLParts.MONITOR))
                .save(consumer, AppEng.makeId("network/parts/panels_dark_monitor"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.MONITOR)
                .requires(TLParts.SEMI_DARK_MONITOR)
                .unlockedBy("has_semi_dark_monitor", has(TLParts.SEMI_DARK_MONITOR))
                .save(consumer, AppEng.makeId("network/parts/panels_monitor"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLParts.SEMI_DARK_MONITOR, 3)
                .pattern(" ab")
                .pattern("cdb")
                .pattern(" ab")
                .define('a', ConventionTags.DUSTS_GLOWSTONE)
                .define('b', TLBlocks.QUARTZ_GLASS)
                .define('c', ConventionTags.IRON_INGOT)
                .define('d', ConventionTags.DUSTS_REDSTONE)
                .unlockedBy("has_quartz_glass", has(TLBlocks.QUARTZ_GLASS))
                .save(consumer, AppEng.makeId("network/parts/panels_semi_dark_monitor"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.SEMI_DARK_MONITOR)
                .requires(TLParts.DARK_MONITOR)
                .unlockedBy("has_dark_monitor", has(TLParts.DARK_MONITOR))
                .save(consumer, AppEng.makeId("network/parts/panels_semi_dark_monitor_alt"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.TERMINAL)
                .requires(TLItems.FORMATION_CORE)
                .requires(ConventionTags.ILLUMINATED_PANEL)
                .requires(TLItems.PROCESSOR_LOGIC)
                .requires(TLItems.ANNIHILATION_CORE)
                .unlockedBy("has_formation_core", has(TLItems.FORMATION_CORE))
                .unlockedBy("has_illuminated_panel", has(ConventionTags.ILLUMINATED_PANEL))
                .unlockedBy("has_logic_processor", has(TLItems.PROCESSOR_LOGIC))
                .unlockedBy("has_annihilation_core", has(TLItems.ANNIHILATION_CORE))
                .save(consumer, AppEng.makeId("network/parts/terminals"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.CRAFTING_TERMINAL)
                .requires(TLParts.TERMINAL)
                .requires(Items.CRAFTING_TABLE)
                .requires(TLItems.PROCESSOR_CALCULATION)
                .unlockedBy("has_terminal", has(TLParts.TERMINAL))
                .unlockedBy("has_calculation_processor", has(TLItems.PROCESSOR_CALCULATION))
                .save(consumer, AppEng.makeId("network/parts/terminals_crafting"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLParts.TOGGLE_BUS)
                .pattern(" a ")
                .pattern("bcb")
                .pattern(" a ")
                .define('a', ConventionTags.DUSTS_REDSTONE)
                .define('b', TLParts.GLASS_CABLE.item(TLColor.TRANSPARENT))
                .define('c', Items.LEVER)
                .unlockedBy("has_glass_cable", has(TLParts.GLASS_CABLE.item(TLColor.TRANSPARENT)))
                .save(consumer, AppEng.makeId("network/parts/toggle_bus"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.TOGGLE_BUS)
                .requires(TLParts.INVERTED_TOGGLE_BUS)
                .unlockedBy("has_inverted_toggle_bus", has(TLParts.INVERTED_TOGGLE_BUS))
                .save(consumer, AppEng.makeId("network/parts/toggle_bus_alt"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.INVERTED_TOGGLE_BUS)
                .requires(TLParts.TOGGLE_BUS)
                .unlockedBy("has_toggle_bus", has(TLParts.TOGGLE_BUS))
                .save(consumer, AppEng.makeId("network/parts/toggle_bus_inverted_alt"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLBlocks.FLUIX_BLOCK)
                .pattern("aa")
                .pattern("aa")
                .define('a', TLItems.FLUIX)
                .unlockedBy("has_fluix_crystal", has(TLItems.FLUIX))
                .save(consumer, AppEng.makeId("decorative/" + TLBlocks.FLUIX_BLOCK.id().getPath()));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLBlocks.QUARTZ_VIBRANT_GLASS)
                .pattern("aba")
                .define('a', Items.GLOWSTONE_DUST)
                .define('b', TLBlocks.QUARTZ_GLASS)
                .unlockedBy("has_quartz_glass", has(TLBlocks.QUARTZ_GLASS))
                .save(consumer, AppEng.makeId("decorative/quartz_vibrant_glass"));
    }

    private void addItemCells(RecipeOutput consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLItems.ITEM_CELL_1K)
                .pattern("aba")
                .pattern("bcb")
                .pattern("ded")
                .define('a', TLBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.DUSTS_REDSTONE)
                .define('c', TLItems.COMPONENT_1K)
                .define('d', ConventionTags.IRON_INGOT)
                .define('e', ConventionTags.COPPER_INGOT)
                .unlockedBy("has_cell_component_1k", has(TLItems.COMPONENT_1K))
                .save(consumer, AppEng.makeId("network/cells/item_storage_cell_1k"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLItems.ITEM_CELL_1K)
                .requires(TLItems.HOUSING_ITEM)
                .requires(TLItems.COMPONENT_1K)
                .unlockedBy("has_cell_component_1k", has(TLItems.COMPONENT_1K))
                .unlockedBy("has_item_cell_housing", has(TLItems.HOUSING_ITEM))
                .save(consumer, AppEng.makeId("network/cells/item_storage_cell_1k_storage"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLItems.ITEM_CELL_4K)
                .pattern("aba")
                .pattern("bcb")
                .pattern("ded")
                .define('a', TLBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.DUSTS_REDSTONE)
                .define('c', TLItems.COMPONENT_4K)
                .define('d', ConventionTags.IRON_INGOT)
                .define('e', ConventionTags.COPPER_INGOT)
                .unlockedBy("has_cell_component_4k", has(TLItems.COMPONENT_4K))
                .save(consumer, AppEng.makeId("network/cells/item_storage_cell_4k"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLItems.ITEM_CELL_4K)
                .requires(TLItems.HOUSING_ITEM)
                .requires(TLItems.COMPONENT_4K)
                .unlockedBy("has_cell_component_4k", has(TLItems.COMPONENT_4K))
                .save(consumer, AppEng.makeId("network/cells/item_storage_cell_4k_storage"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLItems.ITEM_CELL_16K)
                .pattern("aba")
                .pattern("bcb")
                .pattern("ded")
                .define('a', TLBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.DUSTS_REDSTONE)
                .define('c', TLItems.COMPONENT_16K)
                .define('d', ConventionTags.IRON_INGOT)
                .define('e', ConventionTags.COPPER_INGOT)
                .unlockedBy("has_cell_component_16k", has(TLItems.COMPONENT_16K))
                .save(consumer, AppEng.makeId("network/cells/item_storage_cell_16k"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLItems.ITEM_CELL_16K)
                .requires(TLItems.COMPONENT_16K)
                .requires(TLItems.HOUSING_ITEM)
                .unlockedBy("has_cell_component_16k", has(TLItems.COMPONENT_16K))
                .save(consumer, AppEng.makeId("network/cells/item_storage_cell_16k_storage"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLItems.ITEM_CELL_64K)
                .pattern("aba")
                .pattern("bcb")
                .pattern("ded")
                .define('a', TLBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.DUSTS_REDSTONE)
                .define('c', TLItems.COMPONENT_64K)
                .define('d', ConventionTags.IRON_INGOT)
                .define('e', ConventionTags.COPPER_INGOT)
                .unlockedBy("has_cell_component_64k", has(TLItems.COMPONENT_64K))
                .save(consumer, AppEng.makeId("network/cells/item_storage_cell_64k"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLItems.ITEM_CELL_64K)
                .requires(TLItems.HOUSING_ITEM)
                .requires(TLItems.COMPONENT_64K)
                .unlockedBy("has_cell_component_64k", has(TLItems.COMPONENT_64K))
                .save(consumer, AppEng.makeId("network/cells/item_storage_cell_64k_storage"));
    }

    private void addFluidCells(RecipeOutput consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLItems.FLUID_CELL_1K)
                .pattern("aba")
                .pattern("bcb")
                .pattern("ddd")
                .define('a', TLBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.DUSTS_REDSTONE)
                .define('c', TLItems.COMPONENT_1K)
                .define('d', ConventionTags.COPPER_INGOT)
                .unlockedBy("has_cell_component_1k", has(TLItems.COMPONENT_1K))
                .save(consumer, AppEng.makeId("network/cells/fluid_storage_cell_1k"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLItems.FLUID_CELL_1K)
                .requires(TLItems.HOUSING_FLUID)
                .requires(TLItems.COMPONENT_1K)
                .unlockedBy("has_item_cell_housing", has(TLItems.HOUSING_FLUID))
                .unlockedBy("has_cell_component_1k", has(TLItems.COMPONENT_1K))
                .save(consumer, AppEng.makeId("network/cells/fluid_storage_cell_1k_storage"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLItems.FLUID_CELL_4K)
                .pattern("aba")
                .pattern("bcb")
                .pattern("ddd")
                .define('a', TLBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.DUSTS_REDSTONE)
                .define('c', TLItems.COMPONENT_4K)
                .define('d', ConventionTags.COPPER_INGOT)
                .unlockedBy("has_cell_component_4k", has(TLItems.COMPONENT_4K))
                .save(consumer, AppEng.makeId("network/cells/fluid_storage_cell_4k"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLItems.FLUID_CELL_4K)
                .requires(TLItems.HOUSING_FLUID)
                .requires(TLItems.COMPONENT_4K)
                .unlockedBy("has_cell_component_4k", has(TLItems.COMPONENT_4K))
                .save(consumer, AppEng.makeId("network/cells/fluid_storage_cell_4k_storage"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLItems.FLUID_CELL_16K)
                .pattern("aba")
                .pattern("bcb")
                .pattern("ddd")
                .define('a', TLBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.DUSTS_REDSTONE)
                .define('c', TLItems.COMPONENT_16K)
                .define('d', ConventionTags.COPPER_INGOT)
                .unlockedBy("has_cell_component_16k", has(TLItems.COMPONENT_16K))
                .save(consumer, AppEng.makeId("network/cells/fluid_storage_cell_16k"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLItems.FLUID_CELL_16K)
                .requires(TLItems.HOUSING_FLUID)
                .requires(TLItems.COMPONENT_16K)
                .unlockedBy("has_cell_component_16k", has(TLItems.COMPONENT_16K))
                .save(consumer, AppEng.makeId("network/cells/fluid_storage_cell_16k_storage"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLItems.FLUID_CELL_64K)
                .pattern("aba")
                .pattern("bcb")
                .pattern("ddd")
                .define('a', TLBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.DUSTS_REDSTONE)
                .define('c', TLItems.COMPONENT_64K)
                .define('d', ConventionTags.COPPER_INGOT)
                .unlockedBy("has_cell_component_64k", has(TLItems.COMPONENT_64K))
                .save(consumer, AppEng.makeId("network/cells/fluid_storage_cell_64k"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLItems.FLUID_CELL_64K)
                .requires(TLItems.HOUSING_FLUID)
                .requires(TLItems.COMPONENT_64K)
                .unlockedBy("has_cell_component_64k", has(TLItems.COMPONENT_64K))
                .save(consumer, AppEng.makeId("network/cells/fluid_storage_cell_64k_storage"));
    }

    // ====================================================
    // recipes/network/cables
    // ====================================================
    private static void addCables(RecipeOutput consumer) {
        for (var color : TLColor.VALID_COLORS) {
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLParts.SMART_DENSE_CABLE.item(color), 8)
                    .pattern("aaa")
                    .pattern("aba")
                    .pattern("aaa")
                    .define('a', TLParts.SMART_DENSE_CABLE.item(TLColor.TRANSPARENT))
                    .define('b', ConventionTags.dye(color.dye))
                    .unlockedBy("has_dyes/black", has(ConventionTags.dye(color.dye)))
                    .unlockedBy("has_fluix_smart_dense_cable", has(TLParts.SMART_DENSE_CABLE.item(TLColor.TRANSPARENT)))
                    .save(consumer, AppEng.makeId("network/cables/dense_smart_" + color.registryPrefix));
        }

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.SMART_DENSE_CABLE.item(TLColor.TRANSPARENT))
                .requires(TLParts.SMART_CABLE.item(TLColor.TRANSPARENT))
                .requires(TLParts.SMART_CABLE.item(TLColor.TRANSPARENT))
                .requires(TLParts.SMART_CABLE.item(TLColor.TRANSPARENT))
                .requires(TLParts.SMART_CABLE.item(TLColor.TRANSPARENT))
                .unlockedBy("has_fluix_smart_cable", has(TLParts.SMART_CABLE.item(TLColor.TRANSPARENT)))
                .save(consumer, AppEng.makeId("network/cables/dense_smart_from_smart"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.SMART_DENSE_CABLE.item(TLColor.TRANSPARENT))
                .requires(tagExcept(ConventionTags.SMART_DENSE_CABLE,
                        TLParts.SMART_DENSE_CABLE.item(TLColor.TRANSPARENT)))
                .requires(ConventionTags.CAN_REMOVE_COLOR)
                .unlockedBy("has_smart_dense_cable", has(ConventionTags.SMART_DENSE_CABLE))
                .save(consumer, AppEng.makeId("network/cables/dense_smart_fluix_clean"));

        for (var color : TLColor.VALID_COLORS) {
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLParts.GLASS_CABLE.item(color), 8)
                    .pattern("aaa")
                    .pattern("aba")
                    .pattern("aaa")
                    .define('a', TLParts.GLASS_CABLE.item(TLColor.TRANSPARENT))
                    .define('b', ConventionTags.dye(color.dye))
                    .unlockedBy("has_dyes/black", has(ConventionTags.dye(color.dye)))
                    .unlockedBy("has_fluix_glass_cable", has(TLParts.GLASS_CABLE.item(TLColor.TRANSPARENT)))
                    .save(consumer, AppEng.makeId("network/cables/glass_" + color.registryPrefix));
        }

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.GLASS_CABLE.item(TLColor.TRANSPARENT), 4)
                .requires(Items.QUARTZ)
                .requires(TLItems.FLUIX)
                .unlockedBy("has_nether_quartz", has(ConventionTags.GEMS_QUARTZ))
                .unlockedBy("has_crystals/fluix", has(TLItems.FLUIX))
                .save(consumer, AppEng.makeId("network/cables/glass_fluix"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.GLASS_CABLE.item(TLColor.TRANSPARENT))
                .requires(tagExcept(ConventionTags.GLASS_CABLE, TLParts.GLASS_CABLE.item(TLColor.TRANSPARENT)))
                .requires(ConventionTags.CAN_REMOVE_COLOR)
                .unlockedBy("has_glass_cable", has(ConventionTags.GLASS_CABLE))
                .save(consumer, AppEng.makeId("network/cables/glass_fluix_clean"));

        for (var color : TLColor.VALID_COLORS) {
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLParts.SMART_CABLE.item(color), 8)
                    .pattern("aaa")
                    .pattern("aba")
                    .pattern("aaa")
                    .define('a', TLParts.SMART_CABLE.item(TLColor.TRANSPARENT))
                    .define('b', ConventionTags.dye(color.dye))
                    .unlockedBy("has_dyes/black", has(ConventionTags.dye(color.dye)))
                    .unlockedBy("has_fluix_smart_cable", has(TLParts.SMART_CABLE.item(TLColor.TRANSPARENT)))
                    .save(consumer, AppEng.makeId("network/cables/smart_" + color.registryPrefix));
        }

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.SMART_CABLE.item(TLColor.TRANSPARENT))
                .requires(TLParts.GLASS_CABLE.item(TLColor.TRANSPARENT))
                .requires(ConventionTags.DUSTS_REDSTONE)
                .requires(ConventionTags.DUSTS_GLOWSTONE)
                .unlockedBy("has_dusts/redstone", has(ConventionTags.DUSTS_REDSTONE))
                .unlockedBy("has_dusts/glowstone", has(ConventionTags.DUSTS_GLOWSTONE))
                .unlockedBy("has_cable", has(TLParts.GLASS_CABLE.item(TLColor.TRANSPARENT)))
                .save(consumer, AppEng.makeId("network/cables/smart_fluix"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.SMART_CABLE.item(TLColor.TRANSPARENT))
                .requires(tagExcept(ConventionTags.SMART_CABLE, TLParts.SMART_CABLE.item(TLColor.TRANSPARENT)))
                .requires(ConventionTags.CAN_REMOVE_COLOR)
                .unlockedBy("has_smart_cable", has(ConventionTags.SMART_CABLE))
                .save(consumer, AppEng.makeId("network/cables/smart_fluix_clean"));
    }

    private static Ingredient tagExcept(TagKey<Item> tag, ItemLike exception) {
        return DifferenceIngredient.of(Ingredient.of(tag), Ingredient.of(exception));
    }
}
