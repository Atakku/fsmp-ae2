
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
import appeng.core.definitions.ColoredItemDefinition;
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

        // Cells
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLItems.VIEW_CELL)
                .requires(ConventionTags.HOUSING)
                .requires(TLItems.FLUIX)
                .unlockedBy("has_terminal", has(ConventionTags.HOUSING))
                .save(consumer, AppEng.makeId("network/cells/view_cell_storage"));
        addCells(consumer, "item", TLItems.HOUSING_ITEM, TLItems.ITEM_CELL_1K, TLItems.ITEM_CELL_4K,
                TLItems.ITEM_CELL_16K, TLItems.ITEM_CELL_64K);
        addCells(consumer, "fluid", TLItems.HOUSING_FLUID, TLItems.FLUID_CELL_1K, TLItems.FLUID_CELL_4K,
                TLItems.FLUID_CELL_16K, TLItems.FLUID_CELL_64K);

        // Basic Cards
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLItems.BASIC_CARD, 2)
                .pattern("ab ")
                .pattern("cdb")
                .pattern("ab ")
                .define('a', ConventionTags.INGOTS_GOLD)
                .define('b', ConventionTags.INGOTS_IRON)
                .define('c', ConventionTags.DUSTS_REDSTONE)
                .define('d', TLItems.PROCESSOR_CALCULATION)
                .unlockedBy("has_processor_calculation", has(TLItems.PROCESSOR_CALCULATION))
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

        // Advanced Cards
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLItems.ADVANCED_CARD, 2)
                .pattern("ab ")
                .pattern("cdb")
                .pattern("ab ")
                .define('a', ConventionTags.GEMS_DIAMOND)
                .define('b', ConventionTags.INGOTS_IRON)
                .define('c', ConventionTags.DUSTS_REDSTONE)
                .define('d', TLItems.PROCESSOR_CALCULATION)
                .unlockedBy("has_processor_calculation", has(TLItems.PROCESSOR_CALCULATION))
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

        // Fluix compacting
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLBlocks.FLUIX_BLOCK)
                .pattern("aa")
                .pattern("aa")
                .define('a', TLItems.FLUIX)
                .unlockedBy("has_fluix", has(TLItems.FLUIX))
                .save(consumer, AppEng.makeId("misc/fluix_to_block"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLItems.FLUIX, 4)
                .requires(TLBlocks.FLUIX_BLOCK)
                .unlockedBy("has_fluix_block", has(TLBlocks.FLUIX_BLOCK))
                .save(consumer, AppEng.makeId("misc/fluix_from_block"));

        addCables(consumer);

        // wireless
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
                .define('b', ConventionTags.INGOTS_IRON)
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
                .unlockedBy("has_processor_calculation", has(TLItems.PROCESSOR_CALCULATION))
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
                .define('c', ConventionTags.INGOTS_IRON)
                .define('d', ConventionTags.CHEST)
                .unlockedBy("has_processor_calculation", has(TLItems.PROCESSOR_CALCULATION))
                .save(consumer, AppEng.makeId("network/blocks/cell_workbench"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLBlocks.CONTROLLER)
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', Blocks.SMOOTH_STONE)
                .define('b', TLItems.FLUIX)
                .define('c', TLItems.PROCESSOR_ENGINEERING)
                .unlockedBy("has_fluix", has(TLItems.FLUIX))
                .unlockedBy("has_processor_engineering", has(TLItems.PROCESSOR_ENGINEERING))
                .save(consumer, AppEng.makeId("network/blocks/controller"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLBlocks.IO_PORT)
                .pattern("aaa")
                .pattern("bcb")
                .pattern("ded")
                .define('a', ConventionTags.GLASS_CHEAP)
                .define('b', TLBlocks.DRIVE)
                .define('c', ConventionTags.GLASS_CABLE)
                .define('d', ConventionTags.INGOTS_IRON)
                .define('e', TLItems.PROCESSOR_LOGIC)
                .unlockedBy("has_drive", has(TLBlocks.DRIVE))
                .save(consumer, AppEng.makeId("network/blocks/io_port"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLBlocks.ME_CHEST)
                .pattern("aba")
                .pattern("c c")
                .pattern("ded")
                .define('a', ConventionTags.GLASS_CHEAP)
                .define('b', TLParts.TERMINAL)
                .define('c', ConventionTags.GLASS_CABLE)
                .define('d', ConventionTags.INGOTS_IRON)
                .define('e', ConventionTags.INGOTS_COPPER)
                .unlockedBy("has_glass_cable", has(ConventionTags.GLASS_CABLE))
                .unlockedBy("has_terminal", has(TLParts.TERMINAL))
                .unlockedBy("has_crystals/fluix", has(TLItems.FLUIX))
                .save(consumer, AppEng.makeId("network/blocks/storage_chest"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLBlocks.DRIVE)
                .pattern("aba")
                .pattern("c c")
                .pattern("aba")
                .define('a', ConventionTags.INGOTS_IRON)
                .define('b', TLItems.PROCESSOR_ENGINEERING)
                .define('c', ConventionTags.GLASS_CABLE)
                .unlockedBy("has_processor_engineering", has(TLItems.PROCESSOR_ENGINEERING))
                .save(consumer, AppEng.makeId("network/blocks/storage_drive"));

        // ====================================================
        // recipes/network/cells
        // ====================================================

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLItems.HOUSING_ITEM)
                .pattern("aba")
                .pattern("b b")
                .pattern("cdc")
                .define('a', TLBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.DUSTS_REDSTONE)
                .define('c', ConventionTags.INGOTS_IRON)
                .define('d', ConventionTags.INGOTS_COPPER)
                .unlockedBy("has_dusts/redstone", has(ConventionTags.DUSTS_REDSTONE))
                .save(consumer, AppEng.makeId("network/cells/housing_item"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLItems.HOUSING_FLUID)
                .pattern("aba")
                .pattern("b b")
                .pattern("ccc")
                .define('a', TLBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.DUSTS_REDSTONE)
                .define('c', ConventionTags.INGOTS_COPPER)
                .unlockedBy("has_dusts/redstone", has(ConventionTags.DUSTS_REDSTONE))
                .save(consumer, AppEng.makeId("network/cells/housing_fluid"));

        // ====================================================
        // recipes/network/parts
        // ====================================================

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.LEVEL_EMITTER)
                .requires(Items.REDSTONE_TORCH)
                .requires(TLItems.PROCESSOR_CALCULATION)
                .unlockedBy("has_processor_calculation", has(TLItems.PROCESSOR_CALCULATION))
                .save(consumer, AppEng.makeId("network/parts/level_emitter"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.CONVERSION_MONITOR)
                .requires(TLItems.FORMATION_CORE)
                .requires(TLParts.STORAGE_MONITOR)
                .requires(TLItems.ANNIHILATION_CORE)
                .unlockedBy("has_storage_monitor", has(TLParts.STORAGE_MONITOR))
                .save(consumer, AppEng.makeId("network/parts/monitors_conversion"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.STORAGE_MONITOR)
                .requires(TLParts.LEVEL_EMITTER)
                .requires(TLParts.PANEL)
                .unlockedBy("has_illuminated_panel", has(TLParts.PANEL))
                .unlockedBy("has_level_emitter", has(TLParts.LEVEL_EMITTER))
                .save(consumer, AppEng.makeId("network/parts/monitors_storage"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLParts.PANEL, 3)
                .pattern(" ab")
                .pattern("cdb")
                .pattern(" ab")
                .define('a', ConventionTags.DUSTS_GLOWSTONE)
                .define('b', TLBlocks.QUARTZ_GLASS)
                .define('c', ConventionTags.INGOTS_IRON)
                .define('d', ConventionTags.DUSTS_REDSTONE)
                .unlockedBy("has_quartz_glass", has(TLBlocks.QUARTZ_GLASS))
                .save(consumer, AppEng.makeId("network/parts/panels_semi_dark_monitor"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.TERMINAL)
                .requires(TLItems.FORMATION_CORE)
                .requires(TLParts.PANEL)
                .requires(TLItems.PROCESSOR_LOGIC)
                .requires(TLItems.ANNIHILATION_CORE)
                .unlockedBy("has_formation_core", has(TLItems.FORMATION_CORE))
                .unlockedBy("has_illuminated_panel", has(TLParts.PANEL))
                .unlockedBy("has_processor_logic", has(TLItems.PROCESSOR_LOGIC))
                .unlockedBy("has_annihilation_core", has(TLItems.ANNIHILATION_CORE))
                .save(consumer, AppEng.makeId("network/parts/terminals"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.CRAFTING_TERMINAL)
                .requires(TLParts.TERMINAL)
                .requires(Items.CRAFTING_TABLE)
                .requires(TLItems.PROCESSOR_CALCULATION)
                .unlockedBy("has_terminal", has(TLParts.TERMINAL))
                .unlockedBy("has_processor_calculation", has(TLItems.PROCESSOR_CALCULATION))
                .save(consumer, AppEng.makeId("network/parts/terminals_crafting"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLParts.TOGGLE_BUS)
                .pattern(" a ")
                .pattern("bcb")
                .pattern(" a ")
                .define('a', ConventionTags.DUSTS_REDSTONE)
                .define('b', ConventionTags.GLASS_CABLE)
                .define('c', Items.LEVER)
                .unlockedBy("has_glass_cable", has(ConventionTags.GLASS_CABLE))
                .save(consumer, AppEng.makeId("network/parts/toggle_bus"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.TOGGLE_BUS)
                .requires(TLParts.INVERTED_TOGGLE_BUS)
                .unlockedBy("has_inverted_toggle_bus", has(TLParts.INVERTED_TOGGLE_BUS))
                .save(consumer, AppEng.makeId("network/parts/toggle_bus_alt"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.INVERTED_TOGGLE_BUS)
                .requires(TLParts.TOGGLE_BUS)
                .unlockedBy("has_toggle_bus", has(TLParts.TOGGLE_BUS))
                .save(consumer, AppEng.makeId("network/parts/toggle_bus_inverted_alt"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLBlocks.QUARTZ_VIBRANT_GLASS)
                .pattern("aba")
                .define('a', Items.GLOWSTONE_DUST)
                .define('b', TLBlocks.QUARTZ_GLASS)
                .unlockedBy("has_quartz_glass", has(TLBlocks.QUARTZ_GLASS))
                .save(consumer, AppEng.makeId("decorative/quartz_vibrant_glass"));
    }

    private void addCells(RecipeOutput consumer, String type, ItemLike housing, ItemLike cell1k, ItemLike cell4k,
            ItemLike cell16k, ItemLike cell64k) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, cell1k)
                .requires(housing)
                .requires(TLItems.COMPONENT_1K)
                .unlockedBy("has_housing_" + type, has(housing))
                .unlockedBy("has_component_1k", has(TLItems.COMPONENT_1K))
                .save(consumer, AppEng.makeId("network/cells/" + type + "_cell_1k_storage"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, cell4k)
                .requires(housing)
                .requires(TLItems.COMPONENT_4K)
                .unlockedBy("has_housing_" + type, has(housing))
                .unlockedBy("has_component_4k", has(TLItems.COMPONENT_4K))
                .save(consumer, AppEng.makeId("network/cells/" + type + "_cell_4k_storage"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, cell16k)
                .requires(housing)
                .requires(TLItems.COMPONENT_16K)
                .unlockedBy("has_housing_" + type, has(housing))
                .unlockedBy("has_component_16k", has(TLItems.COMPONENT_16K))
                .save(consumer, AppEng.makeId("network/cells/" + type + "_cell_16k_storage"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, cell64k)
                .requires(housing)
                .requires(TLItems.COMPONENT_64K)
                .unlockedBy("has_housing_" + type, has(housing))
                .unlockedBy("has_component_64k", has(TLItems.COMPONENT_64K))
                .save(consumer, AppEng.makeId("network/cells/" + type + "_cell_64k_storage"));
    }

    // Cable recipes
    private static void addCables(RecipeOutput consumer) {
        cableDying(consumer, "glass", ConventionTags.GLASS_CABLE, TLParts.GLASS_CABLE);
        cableDying(consumer, "smart", ConventionTags.SMART_CABLE, TLParts.SMART_CABLE);
        cableDying(consumer, "dense", ConventionTags.DENSE_CABLE, TLParts.DENSE_CABLE);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TLParts.GLASS_CABLE.item(TLColor.TRANSPARENT), 4)
                .pattern(" b ")
                .pattern("cac")
                .pattern(" b ")
                .define('a', ConventionTags.DUSTS_QUARTZ)
                .define('b', ConventionTags.GLASS_CHEAP)
                .define('c', ConventionTags.GEMS_FLUIX)
                .unlockedBy("has_quartz_dust", has(ConventionTags.DUSTS_QUARTZ))
                .unlockedBy("has_glass", has(ConventionTags.GLASS_CHEAP))
                .unlockedBy("has_fluix", has(ConventionTags.GEMS_FLUIX))
                .save(consumer, AppEng.makeId("network/cables/glass"));

        for (var color : TLColor.values()) {
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.SMART_CABLE.item(color))
                    .requires(TLParts.GLASS_CABLE.item(color))
                    .requires(ConventionTags.DUSTS_REDSTONE)
                    .requires(ConventionTags.DUSTS_GLOWSTONE)
                    .unlockedBy("has_dusts/redstone", has(ConventionTags.DUSTS_REDSTONE))
                    .unlockedBy("has_dusts/glowstone", has(ConventionTags.DUSTS_GLOWSTONE))
                    .unlockedBy("has_glass_cable/" + color.registryPrefix, has(TLParts.GLASS_CABLE.item(color)))
                    .save(consumer, AppEng.makeId("network/cables/smart_" + color.registryPrefix));

            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TLParts.DENSE_CABLE.item(color))
                    .requires(TLParts.SMART_CABLE.item(color))
                    .requires(TLParts.SMART_CABLE.item(color))
                    .requires(TLParts.SMART_CABLE.item(color))
                    .requires(TLParts.SMART_CABLE.item(color))
                    .unlockedBy("has_smart_cable/" + color.registryPrefix, has(TLParts.SMART_CABLE.item(color)))
                    .save(consumer, AppEng.makeId("network/cables/dense_" + color.registryPrefix));
        }
    }

    private static void cableDying(RecipeOutput consumer, String name, TagKey<Item> tag,
            ColoredItemDefinition<?> item) {
        for (var color : TLColor.VALID_COLORS) {
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, item.item(color), 8)
                    .pattern("aaa")
                    .pattern("aba")
                    .pattern("aaa")
                    .define('a', tag)
                    .define('b', ConventionTags.dye(color.dye))
                    .unlockedBy("has_cable_" + name, has(tag))
                    .unlockedBy("has_dye/" + color.registryPrefix, has(ConventionTags.dye(color.dye)))
                    .save(consumer, AppEng.makeId("network/cables/dying_" + name + "_" + color.registryPrefix));
        }

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, item.item(TLColor.TRANSPARENT))
                .requires(tagExcept(tag, item.item(TLColor.TRANSPARENT)))
                .requires(ConventionTags.CAN_REMOVE_COLOR)
                .unlockedBy("has_cable_" + name, has(tag))
                .save(consumer, AppEng.makeId("network/cables/cleaning_" + name));
    }

    private static Ingredient tagExcept(TagKey<Item> tag, ItemLike exception) {
        return DifferenceIngredient.of(Ingredient.of(tag), Ingredient.of(exception));
    }
}
