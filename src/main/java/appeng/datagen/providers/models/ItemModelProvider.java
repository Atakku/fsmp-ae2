package appeng.datagen.providers.models;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import appeng.api.ids.AEItemIds;
import appeng.core.AppEng;
import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEItems;
import appeng.core.definitions.ItemDefinition;
import appeng.datagen.providers.IAE2DataProvider;

public class ItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider
        implements IAE2DataProvider {
    public ItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, AppEng.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        flatSingleLayer(AEItems.MISSING_CONTENT, "minecraft:item/barrier");

        builtInItemModel("facade");
        builtInItemModel("meteorite_compass");

        flatSingleLayer(AEItems.ADVANCED_CARD, "item/advanced_card");
        flatSingleLayer(AEItems.VOID_CARD, "item/card_void");
        flatSingleLayer(AEItems.ANNIHILATION_CORE, "item/annihilation_core");
        flatSingleLayer(AEItems.BASIC_CARD, "item/basic_card");
        flatSingleLayer(AEItems.CALCULATION_PROCESSOR, "item/calculation_processor");
        flatSingleLayer(AEItems.CALCULATION_PROCESSOR_PRESS, "item/calculation_processor_press");
        flatSingleLayer(AEItems.CALCULATION_PROCESSOR_PRINT, "item/printed_calculation_processor");
        storageCell(AEItems.ITEM_CELL_1K, "item/item_storage_cell_1k");
        storageCell(AEItems.ITEM_CELL_4K, "item/item_storage_cell_4k");
        storageCell(AEItems.ITEM_CELL_16K, "item/item_storage_cell_16k");
        storageCell(AEItems.ITEM_CELL_64K, "item/item_storage_cell_64k");
        storageCell(AEItems.ITEM_CELL_256K, "item/item_storage_cell_256k");
        flatSingleLayer(AEItems.CERTUS_QUARTZ_CRYSTAL, "item/certus_quartz_crystal");
        flatSingleLayer(AEItems.CERTUS_QUARTZ_CRYSTAL_CHARGED, "item/certus_quartz_crystal_charged");
        flatSingleLayer(AEItems.CERTUS_QUARTZ_DUST, "item/certus_quartz_dust");
        flatSingleLayer(AEItems.DEBUG_CARD, "item/debug_card");
        flatSingleLayer(AEItems.DEBUG_ERASER, "item/debug/eraser");
        flatSingleLayer(AEItems.DEBUG_METEORITE_PLACER, "item/debug/meteorite_placer");
        flatSingleLayer(AEItems.DEBUG_REPLICATOR_CARD, "item/debug/replicator_card");
        flatSingleLayer(AEItems.ENGINEERING_PROCESSOR, "item/engineering_processor");
        flatSingleLayer(AEItems.ENGINEERING_PROCESSOR_PRESS, "item/engineering_processor_press");
        flatSingleLayer(AEItems.ENGINEERING_PROCESSOR_PRINT, "item/printed_engineering_processor");
        flatSingleLayer(AEItems.EQUAL_DISTRIBUTION_CARD, "item/card_equal_distribution");
        storageCell(AEItems.FLUID_CELL_1K, "item/fluid_storage_cell_1k");
        storageCell(AEItems.FLUID_CELL_4K, "item/fluid_storage_cell_4k");
        storageCell(AEItems.FLUID_CELL_16K, "item/fluid_storage_cell_16k");
        storageCell(AEItems.FLUID_CELL_64K, "item/fluid_storage_cell_64k");
        storageCell(AEItems.FLUID_CELL_256K, "item/fluid_storage_cell_256k");
        flatSingleLayer(AEItems.FLUID_CELL_HOUSING, "item/fluid_cell_housing");
        flatSingleLayer(AEItems.FLUIX_CRYSTAL, "item/fluix_crystal");
        flatSingleLayer(AEItems.FLUIX_DUST, "item/fluix_dust");
        flatSingleLayer(AEItems.FLUIX_PEARL, "item/fluix_pearl");
        flatSingleLayer(AEItems.FORMATION_CORE, "item/formation_core");
        flatSingleLayer(AEItems.FUZZY_CARD, "item/card_fuzzy");
        flatSingleLayer(AEItems.INVERTER_CARD, "item/card_inverter");
        flatSingleLayer(AEItems.CELL_COMPONENT_16K, "item/cell_component_16k");
        flatSingleLayer(AEItems.CELL_COMPONENT_1K, "item/cell_component_1k");
        flatSingleLayer(AEItems.CELL_COMPONENT_4K, "item/cell_component_4k");
        flatSingleLayer(AEItems.CELL_COMPONENT_64K, "item/cell_component_64k");
        flatSingleLayer(AEItems.CELL_COMPONENT_256K, "item/cell_component_256k");
        flatSingleLayer(AEItems.CREATIVE_CELL, "item/creative_storage_cell");
        flatSingleLayer(AEItems.ITEM_CELL_HOUSING, "item/item_cell_housing");
        flatSingleLayer(AEItems.LOGIC_PROCESSOR, "item/logic_processor");
        flatSingleLayer(AEItems.LOGIC_PROCESSOR_PRESS, "item/logic_processor_press");
        flatSingleLayer(AEItems.LOGIC_PROCESSOR_PRINT, "item/printed_logic_processor");
        flatSingleLayer(AEItems.MATTER_BALL, "item/matter_ball");
        portableCell(AEItems.PORTABLE_ITEM_CELL1K, "item", "1k");
        portableCell(AEItems.PORTABLE_ITEM_CELL4K, "item", "4k");
        portableCell(AEItems.PORTABLE_ITEM_CELL16K, "item", "16k");
        portableCell(AEItems.PORTABLE_ITEM_CELL64K, "item", "64k");
        portableCell(AEItems.PORTABLE_ITEM_CELL256K, "item", "256k");
        portableCell(AEItems.PORTABLE_FLUID_CELL1K, "fluid", "1k");
        portableCell(AEItems.PORTABLE_FLUID_CELL4K, "fluid", "4k");
        portableCell(AEItems.PORTABLE_FLUID_CELL16K, "fluid", "16k");
        portableCell(AEItems.PORTABLE_FLUID_CELL64K, "fluid", "64k");
        portableCell(AEItems.PORTABLE_FLUID_CELL256K, "fluid", "256k");
        flatSingleLayer(AEItems.REDSTONE_CARD, "item/card_redstone");
        flatSingleLayer(AEItems.SILICON, "item/silicon");
        flatSingleLayer(AEItems.SILICON_PRESS, "item/silicon_press");
        flatSingleLayer(AEItems.SILICON_PRINT, "item/printed_silicon");
        flatSingleLayer(AEItems.SKY_DUST, "item/sky_dust");
        flatSingleLayer(AEItems.SPEED_CARD, "item/card_speed");
        flatSingleLayer(AEItemIds.GUIDE, "item/guide");
        flatSingleLayer(AEItems.VIEW_CELL, "item/view_cell");
        flatSingleLayer(AEItems.WIRELESS_CRAFTING_TERMINAL, "item/wireless_crafting_terminal");
        flatSingleLayer(AEItems.WIRELESS_RECEIVER, "item/wireless_receiver");
        flatSingleLayer(AEItems.WIRELESS_TERMINAL, "item/wireless_terminal");
        registerEmptyModel(AEItems.WRAPPED_GENERIC_STACK);
        registerEmptyModel(AEBlocks.CABLE_BUS.item());
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
