package appeng.datagen.providers.models;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import appeng.api.ids.TLItemIds;
import appeng.core.AppEng;
import appeng.core.definitions.ItemDefinition;
import appeng.core.definitions.TLBlocks;
import appeng.core.definitions.TLItems;
import appeng.datagen.providers.ITL2DataProvider;

public class ItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider
        implements ITL2DataProvider {
    public ItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, AppEng.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        flatSingleLayer(TLItems.MISSING_CONTENT, "minecraft:item/barrier");

        // Base materials
        flatSingleLayer(TLItems.FLUIX, "item/fluix");
        flatSingleLayer(TLItems.SILICON, "item/silicon");

        // Dusts
        flatSingleLayer(TLItems.DUST_AMETHYST, "item/dust_amethyst");
        flatSingleLayer(TLItems.DUST_QUARTZ, "item/dust_quartz");
        flatSingleLayer(TLItems.DUST_FLUIX, "item/dust_fluix");

        // Processor parts
        flatSingleLayer(TLItems.PRESSED_SILICON, "item/pressed_silicon");
        flatSingleLayer(TLItems.CIRCUIT_LOGIC, "item/circuit_logic");
        flatSingleLayer(TLItems.CIRCUIT_CALCULATION, "item/circuit_calculation");
        flatSingleLayer(TLItems.CIRCUIT_ENGINEERING, "item/circuit_engineering");

        // Incomplete processors
        flatSingleLayer(TLItems.INCOMPLETE_PROCESSOR_LOGIC, "item/processor_logic");
        flatSingleLayer(TLItems.INCOMPLETE_PROCESSOR_CALCULATION, "item/processor_calculation");
        flatSingleLayer(TLItems.INCOMPLETE_PROCESSOR_ENGINEERING, "item/processor_engineering");

        // Processors
        flatSingleLayer(TLItems.PROCESSOR_LOGIC, "item/processor_logic");
        flatSingleLayer(TLItems.PROCESSOR_CALCULATION, "item/processor_calculation");
        flatSingleLayer(TLItems.PROCESSOR_ENGINEERING, "item/processor_engineering");

        // Incomplete cell components
        flatSingleLayer(TLItems.INCOMPLETE_COMPONENT_1K, "item/component_1k");
        flatSingleLayer(TLItems.INCOMPLETE_COMPONENT_4K, "item/component_4k");
        flatSingleLayer(TLItems.INCOMPLETE_COMPONENT_16K, "item/component_16k");
        flatSingleLayer(TLItems.INCOMPLETE_COMPONENT_64K, "item/component_64k");

        // Cell components
        flatSingleLayer(TLItems.COMPONENT_1K, "item/component_1k");
        flatSingleLayer(TLItems.COMPONENT_4K, "item/component_4k");
        flatSingleLayer(TLItems.COMPONENT_16K, "item/component_16k");
        flatSingleLayer(TLItems.COMPONENT_64K, "item/component_64k");
        flatSingleLayer(TLItems.HOUSING_ITEM, "item/housing_item");
        flatSingleLayer(TLItems.HOUSING_FLUID, "item/housing_fluid");

        // Complex ingredients
        flatSingleLayer(TLItems.ANNIHILATION_CORE, "item/annihilation_core");
        flatSingleLayer(TLItems.FORMATION_CORE, "item/formation_core");
        flatSingleLayer(TLItems.FLUIX_PEARL, "item/fluix_pearl");

        // Cells
        storageCell(TLItems.ITEM_CELL_1K, "item/item_storage_cell_1k");
        storageCell(TLItems.ITEM_CELL_4K, "item/item_storage_cell_4k");
        storageCell(TLItems.ITEM_CELL_16K, "item/item_storage_cell_16k");
        storageCell(TLItems.ITEM_CELL_64K, "item/item_storage_cell_64k");
        storageCell(TLItems.FLUID_CELL_1K, "item/fluid_storage_cell_1k");
        storageCell(TLItems.FLUID_CELL_4K, "item/fluid_storage_cell_4k");
        storageCell(TLItems.FLUID_CELL_16K, "item/fluid_storage_cell_16k");
        storageCell(TLItems.FLUID_CELL_64K, "item/fluid_storage_cell_64k");

        // Cards
        flatSingleLayer(TLItems.VIEW_CELL, "item/view_cell");
        flatSingleLayer(TLItems.BASIC_CARD, "item/basic_card");
        flatSingleLayer(TLItems.ADVANCED_CARD, "item/advanced_card");
        flatSingleLayer(TLItems.VOID_CARD, "item/card_void");
        flatSingleLayer(TLItems.SPEED_CARD, "item/card_speed");
        flatSingleLayer(TLItems.FUZZY_CARD, "item/card_fuzzy");
        flatSingleLayer(TLItems.INVERTER_CARD, "item/card_inverter");
        flatSingleLayer(TLItems.REDSTONE_CARD, "item/card_redstone");
        flatSingleLayer(TLItems.EQUAL_DISTRIBUTION_CARD, "item/card_equal_distribution");

        // Wireless & terminals
        flatSingleLayer(TLItems.WIRELESS_RECEIVER, "item/wireless_receiver");
        flatSingleLayer(TLItems.WIRELESS_TERMINAL, "item/wireless_terminal");
        flatSingleLayer(TLItems.WIRELESS_CRAFTING_TERMINAL, "item/wireless_crafting_terminal");

        // Misc
        flatSingleLayer(TLItemIds.GUIDE, "item/guide");
        flatSingleLayer(TLItems.CREATIVE_CELL, "item/creative_storage_cell");
        flatSingleLayer(TLItems.DEBUG_CARD, "item/debug_card");
        flatSingleLayer(TLItems.DEBUG_ERASER, "item/debug/eraser");
        flatSingleLayer(TLItems.DEBUG_REPLICATOR_CARD, "item/debug/replicator_card");
        registerEmptyModel(TLItems.WRAPPED_GENERIC_STACK);
        registerEmptyModel(TLBlocks.CABLE_BUS.item());
    }

    private void storageCell(ItemDefinition<?> item, String background) {
        String id = item.id().getPath();
        singleTexture(
                id,
                mcLoc("item/generated"),
                "layer0",
                makeId(background))
                .texture("layer1", "item/storage_cell_led");
    }

    private void registerEmptyModel(ItemDefinition<?> item) {
        this.getBuilder(item.id().getPath());
    }

    private ItemModelBuilder flatSingleLayer(ItemDefinition<?> item, String texture) {
        String id = item.id().getPath();
        return singleTexture(
                id,
                mcLoc("item/generated"),
                "layer0",
                makeId(texture));
    }

    private ItemModelBuilder flatSingleLayer(ResourceLocation id, String texture) {
        return singleTexture(
                id.getPath(),
                mcLoc("item/generated"),
                "layer0",
                makeId(texture));
    }

    private ItemModelBuilder builtInItemModel(String name) {
        var model = getBuilder("item/" + name);
        return model;
    }

    private static ResourceLocation makeId(String id) {
        return id.contains(":") ? ResourceLocation.parse(id) : AppEng.makeId(id);
    }
}
