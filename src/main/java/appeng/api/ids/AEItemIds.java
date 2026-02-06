/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 TeamAppliedEnergistics
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package appeng.api.ids;

import net.minecraft.resources.ResourceLocation;

/**
 * Contains {@link net.minecraft.world.item.Item} ids for various items defined by Ae2.
 */
@SuppressWarnings("unused")
public final class AEItemIds {
    public static final ResourceLocation VIEW_CELL = id("view_cell");

    public static final ResourceLocation MISSING_CONTENT = id("missing_content");
    public static final ResourceLocation COLOR_APPLICATOR = id("color_applicator");
    public static final ResourceLocation WIRELESS_TERMINAL = id("wireless_terminal");
    public static final ResourceLocation WIRELESS_CRAFTING_TERMINAL = id("wireless_crafting_terminal");
    public static final ResourceLocation WRAPPED_GENERIC_STACK = id("wrapped_generic_stack");

    ///
    /// STORAGE CELLS
    ///
    public static final ResourceLocation STORAGE_CELL_1K = id("storage_cell_1k");
    public static final ResourceLocation STORAGE_CELL_4K = id("storage_cell_4k");
    public static final ResourceLocation STORAGE_CELL_16K = id("storage_cell_16k");
    public static final ResourceLocation STORAGE_CELL_64K = id("storage_cell_64k");
    public static final ResourceLocation STORAGE_CELL_256K = id("storage_cell_256k");
    public static final ResourceLocation ITEM_CELL_1K = id("item_storage_cell_1k");
    public static final ResourceLocation ITEM_CELL_4K = id("item_storage_cell_4k");
    public static final ResourceLocation ITEM_CELL_16K = id("item_storage_cell_16k");
    public static final ResourceLocation ITEM_CELL_64K = id("item_storage_cell_64k");
    public static final ResourceLocation ITEM_CELL_256K = id("item_storage_cell_256k");
    public static final ResourceLocation FLUID_CELL_1K = id("fluid_storage_cell_1k");
    public static final ResourceLocation FLUID_CELL_4K = id("fluid_storage_cell_4k");
    public static final ResourceLocation FLUID_CELL_16K = id("fluid_storage_cell_16k");
    public static final ResourceLocation FLUID_CELL_64K = id("fluid_storage_cell_64k");
    public static final ResourceLocation FLUID_CELL_256K = id("fluid_storage_cell_256k");
    public static final ResourceLocation CREATIVE_CELL = id("creative_storage_cell");
    public static final ResourceLocation PORTABLE_ITEM_CELL1K = id("portable_item_cell_1k");
    public static final ResourceLocation PORTABLE_ITEM_CELL4K = id("portable_item_cell_4k");
    public static final ResourceLocation PORTABLE_ITEM_CELL16K = id("portable_item_cell_16k");
    public static final ResourceLocation PORTABLE_ITEM_CELL64K = id("portable_item_cell_64k");
    public static final ResourceLocation PORTABLE_ITEM_CELL256K = id("portable_item_cell_256k");
    public static final ResourceLocation PORTABLE_FLUID_CELL1K = id("portable_fluid_cell_1k");
    public static final ResourceLocation PORTABLE_FLUID_CELL4K = id("portable_fluid_cell_4k");
    public static final ResourceLocation PORTABLE_FLUID_CELL16K = id("portable_fluid_cell_16k");
    public static final ResourceLocation PORTABLE_FLUID_CELL64K = id("portable_fluid_cell_64k");
    public static final ResourceLocation PORTABLE_FLUID_CELL256K = id("portable_fluid_cell_256k");

    ///
    /// The following items were previously part of ApiItems
    ///
    public static final ResourceLocation CERTUS_QUARTZ_CRYSTAL = id("certus_quartz_crystal");
    public static final ResourceLocation CERTUS_QUARTZ_CRYSTAL_CHARGED = id("charged_certus_quartz_crystal");
    public static final ResourceLocation CERTUS_QUARTZ_DUST = id("certus_quartz_dust");
    public static final ResourceLocation SILICON = id("silicon");
    public static final ResourceLocation MATTER_BALL = id("matter_ball");
    public static final ResourceLocation FLUIX_CRYSTAL = id("fluix_crystal");
    public static final ResourceLocation FLUIX_DUST = id("fluix_dust");
    public static final ResourceLocation FLUIX_PEARL = id("fluix_pearl");
    public static final ResourceLocation PURIFIED_CERTUS_QUARTZ_CRYSTAL = id("purified_certus_quartz_crystal");
    public static final ResourceLocation PURIFIED_NETHER_QUARTZ_CRYSTAL = id("purified_nether_quartz_crystal");
    public static final ResourceLocation PURIFIED_FLUIX_CRYSTAL = id("purified_fluix_crystal");
    public static final ResourceLocation CALCULATION_PROCESSOR_PRESS = id("calculation_processor_press");
    public static final ResourceLocation ENGINEERING_PROCESSOR_PRESS = id("engineering_processor_press");
    public static final ResourceLocation LOGIC_PROCESSOR_PRESS = id("logic_processor_press");
    public static final ResourceLocation CALCULATION_PROCESSOR_PRINT = id("printed_calculation_processor");
    public static final ResourceLocation ENGINEERING_PROCESSOR_PRINT = id("printed_engineering_processor");
    public static final ResourceLocation LOGIC_PROCESSOR_PRINT = id("printed_logic_processor");
    public static final ResourceLocation SILICON_PRESS = id("silicon_press");
    public static final ResourceLocation SILICON_PRINT = id("printed_silicon");
    public static final ResourceLocation LOGIC_PROCESSOR = id("logic_processor");
    public static final ResourceLocation CALCULATION_PROCESSOR = id("calculation_processor");
    public static final ResourceLocation ENGINEERING_PROCESSOR = id("engineering_processor");
    public static final ResourceLocation BASIC_CARD = id("basic_card");
    public static final ResourceLocation REDSTONE_CARD = id("redstone_card");
    public static final ResourceLocation VOID_CARD = id("void_card");
    public static final ResourceLocation ADVANCED_CARD = id("advanced_card");
    public static final ResourceLocation FUZZY_CARD = id("fuzzy_card");
    public static final ResourceLocation SPEED_CARD = id("speed_card");
    public static final ResourceLocation INVERTER_CARD = id("inverter_card");
    public static final ResourceLocation EQUAL_DISTRIBUTION_CARD = id("equal_distribution_card");
    public static final ResourceLocation CELL_COMPONENT_1K = id("cell_component_1k");
    public static final ResourceLocation CELL_COMPONENT_4K = id("cell_component_4k");
    public static final ResourceLocation CELL_COMPONENT_16K = id("cell_component_16k");
    public static final ResourceLocation CELL_COMPONENT_64K = id("cell_component_64k");
    public static final ResourceLocation CELL_COMPONENT_256K = id("cell_component_256k");
    public static final ResourceLocation ITEM_CELL_HOUSING = id("item_cell_housing");
    public static final ResourceLocation FLUID_CELL_HOUSING = id("fluid_cell_housing");
    public static final ResourceLocation WIRELESS_RECEIVER = id("wireless_receiver");
    public static final ResourceLocation FORMATION_CORE = id("formation_core");
    public static final ResourceLocation ANNIHILATION_CORE = id("annihilation_core");
    public static final ResourceLocation SKY_DUST = id("sky_dust");
    public static final ResourceLocation GUIDE = id("guide");

    private static ResourceLocation id(String id) {
        return ResourceLocation.fromNamespaceAndPath(AEConstants.MOD_ID, id);
    }
}
