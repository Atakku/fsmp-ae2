/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2015, AlgorithmX2, All rights reserved.
 *
 * Applied Energistics 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Applied Energistics 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Applied Energistics 2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package appeng.core.definitions;

import static appeng.block.TLBaseBlock.glassProps;
import static appeng.block.TLBaseBlock.stoneProps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import com.google.common.base.Preconditions;

import org.jetbrains.annotations.Nullable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockBehaviour.StateArgumentPredicate;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredRegister;

import appeng.api.ids.TLBlockIds;
import appeng.block.TLBaseBlock;
import appeng.block.TLBaseBlockItem;
import appeng.block.misc.CellWorkbenchBlock;
import appeng.block.networking.CableBusBlock;
import appeng.block.networking.ControllerBlock;
import appeng.block.networking.WirelessAccessPointBlock;
import appeng.block.storage.DriveBlock;
import appeng.block.storage.IOPortBlock;
import appeng.block.storage.MEChestBlock;
import appeng.core.AppEng;
import appeng.core.MainCreativeTab;
import appeng.debug.CubeGeneratorBlock;
import appeng.debug.ItemGenBlock;
import appeng.debug.PhantomNodeBlock;
import appeng.decorative.TLDecorativeBlock;
import appeng.decorative.solid.QuartzGlassBlock;
import appeng.decorative.solid.QuartzLampBlock;

/**
 * Internal implementation for the API blocks
 */
public final class TLBlocks {
    public static final DeferredRegister.Blocks DR = DeferredRegister.createBlocks(AppEng.MOD_ID);

    private static final List<BlockDefinition<?>> BLOCKS = new ArrayList<>();
    private static final StateArgumentPredicate<EntityType<?>> NEVER_ALLOW_SPAWN = (p1, p2, p3,
            p4) -> false;
    private static final Properties FLUIX_PROPERTIES = stoneProps().mapColor(MapColor.COLOR_PURPLE)
            .strength(3, 5).requiresCorrectToolForDrops();

    // spotless:off
    public static final BlockDefinition<QuartzGlassBlock> QUARTZ_GLASS = block("Quartz Glass", TLBlockIds.QUARTZ_GLASS, () -> new QuartzGlassBlock(glassProps().noOcclusion().isValidSpawn(NEVER_ALLOW_SPAWN)));
    public static final BlockDefinition<QuartzLampBlock> QUARTZ_VIBRANT_GLASS = block("Vibrant Quartz Glass", TLBlockIds.QUARTZ_VIBRANT_GLASS, () -> new QuartzLampBlock(glassProps().lightLevel(b -> 15).noOcclusion()
            .isValidSpawn(NEVER_ALLOW_SPAWN)));
    public static final BlockDefinition<TLDecorativeBlock> FLUIX_BLOCK = block("Fluix Block", TLBlockIds.FLUIX_BLOCK, () -> new TLDecorativeBlock(FLUIX_PROPERTIES));

    public static final BlockDefinition<WirelessAccessPointBlock> WIRELESS_ACCESS_POINT = block("ME Wireless Access Point", TLBlockIds.WIRELESS_ACCESS_POINT, WirelessAccessPointBlock::new);
    public static final BlockDefinition<ControllerBlock> CONTROLLER = block("ME Controller", TLBlockIds.CONTROLLER, ControllerBlock::new);
    public static final BlockDefinition<DriveBlock> DRIVE = block("ME Drive", TLBlockIds.DRIVE, DriveBlock::new);
    public static final BlockDefinition<MEChestBlock> ME_CHEST = block("ME Chest", TLBlockIds.ME_CHEST, MEChestBlock::new);
    public static final BlockDefinition<CellWorkbenchBlock> CELL_WORKBENCH = block("Cell Workbench", TLBlockIds.CELL_WORKBENCH, CellWorkbenchBlock::new);
    public static final BlockDefinition<IOPortBlock> IO_PORT = block("ME IO Port", TLBlockIds.IO_PORT, IOPortBlock::new);
    public static final BlockDefinition<CableBusBlock> CABLE_BUS = block("TL2 Cable and/or Bus", TLBlockIds.CABLE_BUS, CableBusBlock::new);

    ///
    /// DEBUG BLOCKS
    ///
    public static final BlockDefinition<ItemGenBlock> DEBUG_ITEM_GEN = block("Dev.ItemGen", AppEng.makeId("debug_item_gen"), ItemGenBlock::new);
    public static final BlockDefinition<PhantomNodeBlock> DEBUG_PHANTOM_NODE = block("Dev.PhantomNode", AppEng.makeId("debug_phantom_node"), PhantomNodeBlock::new);
    public static final BlockDefinition<CubeGeneratorBlock> DEBUG_CUBE_GEN = block("Dev.CubeGen", AppEng.makeId("debug_cube_gen"), CubeGeneratorBlock::new);

    // spotless:on

    public static List<BlockDefinition<?>> getBlocks() {
        return Collections.unmodifiableList(BLOCKS);
    }

    private static <T extends Block> BlockDefinition<T> block(String englishName, ResourceLocation id,
            Supplier<T> blockSupplier) {
        return block(englishName, id, blockSupplier, null);
    }

    private static <T extends Block> BlockDefinition<T> block(
            String englishName,
            ResourceLocation id,
            Supplier<T> blockSupplier,
            @Nullable BiFunction<Block, Item.Properties, BlockItem> itemFactory) {
        Preconditions.checkArgument(id.getNamespace().equals(AppEng.MOD_ID));

        // Create block and matching item
        var deferredBlock = DR.register(id.getPath(), blockSupplier);
        var deferredItem = TLItems.DR.register(id.getPath(), () -> {
            var block = deferredBlock.get();
            var itemProperties = new Item.Properties();
            if (itemFactory != null) {
                var item = itemFactory.apply(block, itemProperties);
                if (item == null) {
                    throw new IllegalArgumentException("BlockItem factory for " + id + " returned null");
                }
                return item;
            } else if (block instanceof TLBaseBlock) {
                return new TLBaseBlockItem(block, itemProperties);
            } else {
                return new BlockItem(block, itemProperties);
            }
        });

        var itemDef = new ItemDefinition<>(englishName, deferredItem);
        MainCreativeTab.add(itemDef);
        BlockDefinition<T> definition = new BlockDefinition<>(englishName, deferredBlock, itemDef);

        BLOCKS.add(definition);

        return definition;

    }

}
