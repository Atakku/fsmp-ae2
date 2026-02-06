package appeng.server.testplots;

import java.lang.annotation.ElementType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.fml.ModList;

import appeng.api.config.Actionable;
import appeng.api.orientation.BlockOrientation;
import appeng.api.stacks.AEFluidKey;
import appeng.api.stacks.AEItemKey;
import appeng.api.storage.StorageCells;
import appeng.api.util.AEColor;
import appeng.blockentity.storage.MEChestBlockEntity;
import appeng.core.AELog;
import appeng.core.AppEng;
import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEItems;
import appeng.core.definitions.AEParts;
import appeng.me.helpers.BaseActionSource;
import appeng.server.testworld.Plot;
import appeng.server.testworld.PlotBuilder;
import appeng.util.Platform;

@TestPlotClass
public final class TestPlots {
    private static final Logger LOG = LoggerFactory.getLogger(TestPlots.class);

    @Nullable
    private static Map<ResourceLocation, Consumer<PlotBuilder>> plots;

    private TestPlots() {
    }

    private static synchronized Map<ResourceLocation, Consumer<PlotBuilder>> getPlots() {
        if (plots == null) {
            plots = scanForPlots();
        }
        return plots;
    }

    private static Map<ResourceLocation, Consumer<PlotBuilder>> scanForPlots() {
        var plots = new HashMap<ResourceLocation, Consumer<PlotBuilder>>();

        try {
            for (var clazz : findAllTestPlotClasses()) {
                AELog.info("Scanning %s for plots", clazz);

                for (var method : clazz.getMethods()) {
                    var annotation = method.getAnnotation(TestPlot.class);
                    var generatorAnnotation = method.getAnnotation(TestPlotGenerator.class);
                    if (annotation == null && generatorAnnotation == null) {
                        continue;
                    }
                    if (annotation != null && generatorAnnotation != null) {
                        throw new IllegalStateException("Cannot annotate method " + method + " with both "
                                + "@TestPlot and @TestPlotGenerator");
                    }

                    if (!Modifier.isPublic(method.getModifiers())) {
                        throw new IllegalStateException("Method " + method + " must be public");
                    }
                    if (!Modifier.isStatic(method.getModifiers())) {
                        throw new IllegalStateException("Method " + method + " must be static");
                    }
                    if (!void.class.equals(method.getReturnType())) {
                        throw new IllegalStateException("Method " + method + " must return void");
                    }

                    if (annotation != null) {
                        if (!Arrays.asList(method.getParameterTypes()).equals(List.of(PlotBuilder.class))) {
                            throw new IllegalStateException(
                                    "Method " + method + " must take a single PlotBuilder argument");
                        }

                        var id = AppEng.makeId(annotation.value());
                        plots.put(id, builder -> {
                            try {
                                method.invoke(null, builder);
                            } catch (InvocationTargetException e) {
                                throw new RuntimeException("Failed building " + id, e.getCause());
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException("Failed to access " + method, e);
                            }
                        });
                    } else if (generatorAnnotation != null) {
                        if (!Arrays.asList(method.getParameterTypes()).equals(List.of(TestPlotCollection.class))) {
                            throw new IllegalStateException(
                                    "Method " + method + " must take a single TestPlotCollection argument");
                        }

                        var tpc = new TestPlotCollection(plots);

                        try {
                            method.invoke(null, tpc);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException("Failed building " + method, e.getCause());
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("Failed to access " + method, e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            AELog.error("Failed to scan for plots: %s", e);
        }

        return plots;
    }

    private static List<Class<?>> findAllTestPlotClasses() {
        var result = new ArrayList<Class<?>>();

        for (var data : ModList.get().getAllScanData()) {
            for (var annotation : data.getAnnotations()) {
                if (annotation.targetType() == ElementType.TYPE
                        && annotation.annotationType().getClassName().equals(TestPlotClass.class.getName())) {
                    try {
                        result.add(Class.forName(annotation.memberName()));
                    } catch (Throwable e) {
                        LOG.error("Failed to load class {} annotated with @TestPlotClass", annotation.memberName(), e);
                    }
                }
            }
        }

        return result;
    }

    public static List<ResourceLocation> getPlotIds() {
        var list = new ArrayList<>(getPlots().keySet());
        list.sort(Comparator.comparing(ResourceLocation::toString));
        return list;
    }

    public static List<Plot> createPlots() {
        var plots = new ArrayList<Plot>();
        for (var entry : getPlots().entrySet()) {
            var plot = new Plot(entry.getKey());
            entry.getValue().accept(plot);
            plots.add(plot);
        }
        return plots;
    }

    @Nullable
    public static Plot getById(ResourceLocation name) {
        var factory = getPlots().get(name);
        if (factory == null) {
            return null;
        }
        var plot = new Plot(name);
        factory.accept(plot);
        return plot;
    }

    private static AEItemKey createEnchantedPickaxe(Level level) {
        var enchantmentRegistry = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);

        var enchantedPickaxe = new ItemStack(Items.DIAMOND_PICKAXE);
        enchantedPickaxe.enchant(enchantmentRegistry.getHolderOrThrow(Enchantments.FORTUNE), 3);
        return AEItemKey.of(enchantedPickaxe);
    }

    /**
     * A wall of all terminals/monitors in all color combinations.
     */
    @TestPlot("all_terminals")
    public static void allTerminals(PlotBuilder plot) {
        plot.creativeEnergyCell("0 -1 0");

        plot.cable("[-1,0] [0,8] 0", AEParts.COVERED_DENSE_CABLE);
        plot.part("0 [0,8] 0", Direction.WEST, AEParts.CABLE_ANCHOR);
        plot.block("[-1,0] 5 0", AEBlocks.CONTROLLER);
        plot.storageDrive(new BlockPos(0, 5, 1));
        plot.afterGridInitAt(new BlockPos(0, 5, 1), (grid, gridNode) -> {
            var enchantedPickaxe = createEnchantedPickaxe(gridNode.getLevel());
            var storage = grid.getStorageService().getInventory();
            var src = new BaseActionSource();
            storage.insert(AEItemKey.of(Items.DIAMOND_PICKAXE), 10, Actionable.MODULATE, src);
            storage.insert(enchantedPickaxe, 1234, Actionable.MODULATE, src);
            storage.insert(AEItemKey.of(Items.ACACIA_LOG), Integer.MAX_VALUE, Actionable.MODULATE, src);
        });

        // Generate a "line" of cable+terminals that extends from the center
        // Only go up to 9 in height, then flip the X axis and continue on the other side
        var y = 0;
        for (var color : getColorsTransparentFirst()) {
            PlotBuilder line;
            if (y >= 9) {
                line = plot.transform(bb -> new BoundingBox(
                        -1 - bb.maxX(), bb.minY(), bb.minZ(),
                        -1 - bb.minX(), bb.maxY(), bb.maxZ())).offset(0, y - 9, 0);
            } else {
                line = plot.offset(0, y, 0);
            }
            y++;
            line.cable("[1,9] 0 0", AEParts.GLASS_CABLE, color);
            if (color == AEColor.TRANSPARENT) {
                line.part("[1,9] 0 0", Direction.UP, AEParts.CABLE_ANCHOR);
            }
            line.part("1 0 0", Direction.NORTH, AEParts.TERMINAL);
            line.part("2 0 0", Direction.NORTH, AEParts.CRAFTING_TERMINAL);
            line.part("3 0 0", Direction.NORTH, AEParts.MONITOR);
            line.part("4 0 0", Direction.NORTH, AEParts.MONITOR);
            line.part("5 0 0", Direction.NORTH, AEParts.STORAGE_MONITOR, monitor -> {
                var enchantedPickaxe = createEnchantedPickaxe(monitor.getLevel());
                monitor.setConfiguredItem(enchantedPickaxe);
                monitor.setLocked(true);
            });
            line.part("6 0 0", Direction.NORTH, AEParts.CONVERSION_MONITOR, monitor -> {
                monitor.setConfiguredItem(AEItemKey.of(Items.ACACIA_LOG));
                monitor.setLocked(true);
            });
            line.part("7 0 0", Direction.NORTH, AEParts.MONITOR);
            line.part("8 0 0", Direction.NORTH, AEParts.SEMI_DARK_MONITOR);
            line.part("9 0 0", Direction.NORTH, AEParts.DARK_MONITOR);
        }
    }

    public static ArrayList<AEColor> getColorsTransparentFirst() {
        var colors = new ArrayList<AEColor>();
        Collections.addAll(colors, AEColor.values());
        colors.remove(AEColor.TRANSPARENT);
        colors.add(0, AEColor.TRANSPARENT);
        return colors;
    }

    @TestPlot("item_chest")
    public static void itemChest(PlotBuilder plot) {
        plot.blockEntity("0 0 0", AEBlocks.ME_CHEST, chest -> {
            var cellItem = AEItems.ITEM_CELL_1K.stack();
            var cellInv = StorageCells.getCellInventory(cellItem, null);
            var r = RandomSource.create();
            for (var i = 0; i < 100; i++) {
                var item = BuiltInRegistries.ITEM.getRandom(r).map(Holder::value).get();
                if (cellInv.insert(AEItemKey.of(item), 64, Actionable.MODULATE, new BaseActionSource()) == 0) {
                    break;
                }
            }
            chest.setCell(cellItem);
        });
        plot.creativeEnergyCell("0 -1 0");
    }

    @TestPlot("fluid_chest")
    public static void fluidChest(PlotBuilder plot) {
        plot.blockEntity("0 0 0", AEBlocks.ME_CHEST, chest -> {
            var cellItem = AEItems.FLUID_CELL_1K.stack();
            var cellInv = StorageCells.getCellInventory(cellItem, null);
            var r = RandomSource.create();
            for (var i = 0; i < 100; i++) {
                var fluid = BuiltInRegistries.FLUID.getRandom(r).map(Holder::value).get();
                if (fluid.isSame(Fluids.EMPTY) || !fluid.isSource(fluid.defaultFluidState())) {
                    continue;
                }
                if (cellInv.insert(AEFluidKey.of(fluid), 64 * AEFluidKey.AMOUNT_BUCKET,
                        Actionable.MODULATE, new BaseActionSource()) == 0) {
                    break;
                }
            }
            chest.setCell(cellItem);
        });
        plot.creativeEnergyCell("0 -1 0");
    }

    @TestPlot("inscriber")
    public static void inscriber(PlotBuilder plot) {
        processorInscriber(plot.offset(0, 1, 2), AEItems.LOGIC_PROCESSOR_PRESS, Items.GOLD_INGOT);
        processorInscriber(plot.offset(5, 1, 2), AEItems.ENGINEERING_PROCESSOR_PRESS, Items.DIAMOND);
        processorInscriber(plot.offset(10, 1, 2), AEItems.CALCULATION_PROCESSOR_PRESS, AEItems.CERTUS_QUARTZ_CRYSTAL);
    }

    public static void processorInscriber(PlotBuilder plot, ItemLike processorPress, ItemLike processorMaterial) {
        // Set up the inscriber for the processor print
        plot.filledHopper("-1 3 0", Direction.DOWN, processorMaterial);
        plot.creativeEnergyCell("-1 2 1");
        plot.blockEntity("-1 2 0", AEBlocks.INSCRIBER, inscriber -> {
            inscriber.getInternalInventory().setItemDirect(0, new ItemStack(processorPress));
            BlockOrientation.NORTH_WEST.setOn(inscriber);
        });

        // Set up the inscriber for the silicon print
        plot.filledHopper("1 3 0", Direction.DOWN, AEItems.SILICON);
        plot.creativeEnergyCell("1 2 1");
        plot.blockEntity("1 2 0", AEBlocks.INSCRIBER, inscriber -> {
            inscriber.getInternalInventory().setItemDirect(0, AEItems.SILICON_PRESS.stack());
            BlockOrientation.NORTH_WEST.setOn(inscriber);
        });

        // Set up the inscriber for assembly
        plot.hopper("1 1 0", Direction.WEST);
        plot.hopper("-1 1 0", Direction.EAST);
        plot.filledHopper("0 2 0", Direction.DOWN, Items.REDSTONE);
        plot.creativeEnergyCell("0 1 1");
        plot.blockEntity("0 1 0", AEBlocks.INSCRIBER, BlockOrientation.NORTH_WEST::setOn);
        plot.hopper("0 0 0", Direction.DOWN);
    }

    /**
     * Regression test for https://github.com/AppliedEnergistics/Applied-Energistics-2/issues/6582
     */
    @TestPlot("insert_item_into_mechest")
    public static void testInsertItemsIntoMEChest(PlotBuilder plot) {
        var origin = BlockPos.ZERO;
        plot.creativeEnergyCell(origin.below());
        plot.blockEntity(origin, AEBlocks.ME_CHEST, chest -> {
            var cell = AEItems.ITEM_CELL_1K.stack();
            AEItems.ITEM_CELL_1K.get().getConfigInventory(cell).addFilter(Items.REDSTONE);
            chest.setCell(cell);
        });
        // Hopper to test insertion of stuff. It should try to insert stick first.
        plot.hopper(origin.above(), Direction.DOWN, Items.STICK, Items.REDSTONE);

        plot.test(helper -> helper.succeedWhen(() -> {
            var meChest = (MEChestBlockEntity) helper.getBlockEntity(origin);
            helper.assertContains(meChest.getInventory(), AEItemKey.of(Items.REDSTONE));
            // The stick should still be in the hopper
            helper.assertContainerContains(origin.above(), Items.STICK);
        }));
    }

    /**
     * Simple terminal full of enchanted items to test rendering performance.
     */
    @TestPlot("terminal_fullof_enchanteditems")
    public static void terminalFullOfEnchantedItems(PlotBuilder plot) {
        var origin = BlockPos.ZERO;
        plot.creativeEnergyCell(origin.below());
        plot.cable(origin).part(Direction.NORTH, AEParts.TERMINAL);
        var drive = plot.drive(origin.east());

        plot.addPostBuildAction((level, player, ignored) -> {
            var enchantment = Platform.getEnchantment(level, Enchantments.FORTUNE);
            var pickaxe = new ItemStack(Items.DIAMOND_PICKAXE);
            pickaxe.enchant(enchantment, 1);
            for (var i = 0; i < 10; i++) {
                var cell = drive.addItemCell64k();
                for (var j = 0; j < 63; j++) {
                    pickaxe.setDamageValue(pickaxe.getDamageValue() + 1);
                    cell.add(AEItemKey.of(pickaxe), 2);
                }
            }
        });
    }

}
