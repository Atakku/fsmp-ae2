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

        builtInItemModel("facade");
        builtInItemModel("meteorite_compass");

        flatSingleLayer(TLItems.ADVANCED_CARD, "item/advanced_card");
        flatSingleLayer(TLItems.VOID_CARD, "item/card_void");
        flatSingleLayer(TLItems.ANNIHILATION_CORE, "item/annihilation_core");
        flatSingleLayer(TLItems.BASIC_CARD, "item/basic_card");
        flatSingleLayer(TLItems.CALCULATION_PROCESSOR, "item/calculation_processor");
        flatSingleLayer(TLItems.CALCULATION_PROCESSOR_PRINT, "item/printed_calculation_processor");
        storageCell(TLItems.ITEM_CELL_1K, "item/item_storage_cell_1k");
        storageCell(TLItems.ITEM_CELL_4K, "item/item_storage_cell_4k");
        storageCell(TLItems.ITEM_CELL_16K, "item/item_storage_cell_16k");
        storageCell(TLItems.ITEM_CELL_64K, "item/item_storage_cell_64k");
        flatSingleLayer(TLItems.DEBUG_CARD, "item/debug_card");
        flatSingleLayer(TLItems.DEBUG_ERASER, "item/debug/eraser");
        flatSingleLayer(TLItems.DEBUG_REPLICATOR_CARD, "item/debug/replicator_card");
        flatSingleLayer(TLItems.ENGINEERING_PROCESSOR, "item/engineering_processor");
        flatSingleLayer(TLItems.ENGINEERING_PROCESSOR_PRINT, "item/printed_engineering_processor");
        flatSingleLayer(TLItems.EQUAL_DISTRIBUTION_CARD, "item/card_equal_distribution");
        storageCell(TLItems.FLUID_CELL_1K, "item/fluid_storage_cell_1k");
        storageCell(TLItems.FLUID_CELL_4K, "item/fluid_storage_cell_4k");
        storageCell(TLItems.FLUID_CELL_16K, "item/fluid_storage_cell_16k");
        storageCell(TLItems.FLUID_CELL_64K, "item/fluid_storage_cell_64k");
        flatSingleLayer(TLItems.FLUID_CELL_HOUSING, "item/fluid_cell_housing");
        flatSingleLayer(TLItems.FLUIX_CRYSTAL, "item/fluix_crystal");
        flatSingleLayer(TLItems.FLUIX_DUST, "item/fluix_dust");
        flatSingleLayer(TLItems.FLUIX_PEARL, "item/fluix_pearl");
        flatSingleLayer(TLItems.FORMATION_CORE, "item/formation_core");
        flatSingleLayer(TLItems.FUZZY_CARD, "item/card_fuzzy");
        flatSingleLayer(TLItems.INVERTER_CARD, "item/card_inverter");
        flatSingleLayer(TLItems.CELL_COMPONENT_16K, "item/cell_component_16k");
        flatSingleLayer(TLItems.CELL_COMPONENT_1K, "item/cell_component_1k");
        flatSingleLayer(TLItems.CELL_COMPONENT_4K, "item/cell_component_4k");
        flatSingleLayer(TLItems.CELL_COMPONENT_64K, "item/cell_component_64k");
        flatSingleLayer(TLItems.CREATIVE_CELL, "item/creative_storage_cell");
        flatSingleLayer(TLItems.ITEM_CELL_HOUSING, "item/item_cell_housing");
        flatSingleLayer(TLItems.LOGIC_PROCESSOR, "item/logic_processor");
        flatSingleLayer(TLItems.LOGIC_PROCESSOR_PRINT, "item/printed_logic_processor");
        portableCell(TLItems.PORTABLE_ITEM_CELL1K, "item", "1k");
        portableCell(TLItems.PORTABLE_ITEM_CELL4K, "item", "4k");
        portableCell(TLItems.PORTABLE_ITEM_CELL16K, "item", "16k");
        portableCell(TLItems.PORTABLE_ITEM_CELL64K, "item", "64k");
        portableCell(TLItems.PORTABLE_FLUID_CELL1K, "fluid", "1k");
        portableCell(TLItems.PORTABLE_FLUID_CELL4K, "fluid", "4k");
        portableCell(TLItems.PORTABLE_FLUID_CELL16K, "fluid", "16k");
        portableCell(TLItems.PORTABLE_FLUID_CELL64K, "fluid", "64k");
        flatSingleLayer(TLItems.REDSTONE_CARD, "item/card_redstone");
        flatSingleLayer(TLItems.SILICON, "item/silicon");
        flatSingleLayer(TLItems.SILICON_PRINT, "item/printed_silicon");
        flatSingleLayer(TLItems.SPEED_CARD, "item/card_speed");
        flatSingleLayer(TLItemIds.GUIDE, "item/guide");
        flatSingleLayer(TLItems.VIEW_CELL, "item/view_cell");
        flatSingleLayer(TLItems.WIRELESS_CRAFTING_TERMINAL, "item/wireless_crafting_terminal");
        flatSingleLayer(TLItems.WIRELESS_RECEIVER, "item/wireless_receiver");
        flatSingleLayer(TLItems.WIRELESS_TERMINAL, "item/wireless_terminal");
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

    private void portableCell(ItemDefinition<?> item, String housingType, String tier) {
        String id = item.id().getPath();
        singleTexture(
                id,
                mcLoc("item/generated"),
                "layer0",
                makeId("item/portable_cell_%s_housing".formatted(housingType)))
                .texture("layer1", "item/portable_cell_led")
                .texture("layer2", "item/portable_cell_screen")
                .texture("layer3", "item/portable_cell_side_%s".formatted(tier));
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
