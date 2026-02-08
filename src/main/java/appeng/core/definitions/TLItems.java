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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import com.google.common.base.Preconditions;

import org.jetbrains.annotations.Nullable;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredRegister;

import appeng.api.ids.TLCreativeTabIds;
import appeng.api.ids.TLItemIds;
import appeng.api.stacks.TLKeyType;
import appeng.api.upgrades.Upgrades;
import appeng.core.AppEng;
import appeng.core.MainCreativeTab;
import appeng.debug.DebugCardItem;
import appeng.debug.EraserItem;
import appeng.debug.ReplicatorCardItem;
import appeng.items.materials.MaterialItem;
import appeng.items.materials.StorageComponentItem;
import appeng.items.misc.MissingContentItem;
import appeng.items.misc.WrappedGenericStack;
import appeng.items.storage.BasicStorageCell;
import appeng.items.storage.CreativeCellItem;
import appeng.items.storage.ViewCellItem;
import appeng.items.tools.GuideItem;
import appeng.items.tools.powered.WirelessCraftingTerminalItem;
import appeng.items.tools.powered.WirelessTerminalItem;

/**
 * Internal implementation for the API items
 */
public final class TLItems {
    public static final DeferredRegister.Items DR = DeferredRegister.createItems(AppEng.MOD_ID);

    // spotless:off
    private static final List<ItemDefinition<?>> ITEMS = new ArrayList<>();

    // Used to represent missing content if a mod got uninstalled
    public static final ItemDefinition<Item> MISSING_CONTENT = item("Missing Content", TLItemIds.MISSING_CONTENT, MissingContentItem::new, null);

    // Basic materials
    public static final ItemDefinition<MaterialItem> AMETHYST_DUST = item("Amethyst Dust", TLItemIds.AMETHYST_DUST, MaterialItem::new);
    public static final ItemDefinition<MaterialItem> QUARTZ_DUST = item("Quartz Dust", TLItemIds.QUARTZ_DUST, MaterialItem::new);
    public static final ItemDefinition<MaterialItem> FLUIX_DUST = item("Fluix Dust", TLItemIds.FLUIX_DUST, MaterialItem::new);
    public static final ItemDefinition<MaterialItem> FLUIX_CRYSTAL = item("Fluix Crystal", TLItemIds.FLUIX_CRYSTAL, MaterialItem::new);


    public static final ItemDefinition<MaterialItem> SILICON = item("Silicon", TLItemIds.SILICON, MaterialItem::new);
    public static final ItemDefinition<MaterialItem> SILICON_PRINT = item("Printed Silicon", TLItemIds.SILICON_PRINT, MaterialItem::new);
    public static final ItemDefinition<MaterialItem> LOGIC_PROCESSOR = item("Logic Processor", TLItemIds.LOGIC_PROCESSOR, MaterialItem::new);
    public static final ItemDefinition<MaterialItem> LOGIC_PROCESSOR_PRINT = item("Printed Logic Circuit", TLItemIds.LOGIC_PROCESSOR_PRINT, MaterialItem::new);
    public static final ItemDefinition<MaterialItem> CALCULATION_PROCESSOR = item("Calculation Processor", TLItemIds.CALCULATION_PROCESSOR, MaterialItem::new);
    public static final ItemDefinition<MaterialItem> CALCULATION_PROCESSOR_PRINT = item("Printed Calculation Circuit", TLItemIds.CALCULATION_PROCESSOR_PRINT, MaterialItem::new);
    public static final ItemDefinition<MaterialItem> ENGINEERING_PROCESSOR = item("Engineering Processor", TLItemIds.ENGINEERING_PROCESSOR, MaterialItem::new);
    public static final ItemDefinition<MaterialItem> ENGINEERING_PROCESSOR_PRINT = item("Printed Engineering Circuit", TLItemIds.ENGINEERING_PROCESSOR_PRINT, MaterialItem::new);

    public static final ItemDefinition<MaterialItem> ANNIHILATION_CORE = item("Annihilation Core", TLItemIds.ANNIHILATION_CORE, MaterialItem::new);
    public static final ItemDefinition<MaterialItem> FORMATION_CORE = item("Formation Core", TLItemIds.FORMATION_CORE, MaterialItem::new);
    public static final ItemDefinition<MaterialItem> FLUIX_PEARL = item("Fluix Pearl", TLItemIds.FLUIX_PEARL, MaterialItem::new);
    public static final ItemDefinition<Item> TABLET = item("Guide", TLItemIds.GUIDE, p -> new GuideItem(p.stacksTo(1)));
    public static final ItemDefinition<ViewCellItem> VIEW_CELL = item("View Cell", TLItemIds.VIEW_CELL, p -> new ViewCellItem(p.stacksTo(1)));

    // Cell components
    public static final ItemDefinition<StorageComponentItem> CELL_COMPONENT_1K = item("1k ME Storage Component", TLItemIds.CELL_COMPONENT_1K, p -> new StorageComponentItem(p, 1));
    public static final ItemDefinition<StorageComponentItem> CELL_COMPONENT_4K = item("4k ME Storage Component", TLItemIds.CELL_COMPONENT_4K, p -> new StorageComponentItem(p, 4));
    public static final ItemDefinition<StorageComponentItem> CELL_COMPONENT_16K = item("16k ME Storage Component", TLItemIds.CELL_COMPONENT_16K, p -> new StorageComponentItem(p, 16));
    public static final ItemDefinition<StorageComponentItem> CELL_COMPONENT_64K = item("64k ME Storage Component", TLItemIds.CELL_COMPONENT_64K, p -> new StorageComponentItem(p, 64));
    public static final ItemDefinition<MaterialItem> ITEM_CELL_HOUSING = item("ME Item Cell Housing", TLItemIds.ITEM_CELL_HOUSING, MaterialItem::new);
    public static final ItemDefinition<MaterialItem> FLUID_CELL_HOUSING = item("ME Fluid Cell Housing", TLItemIds.FLUID_CELL_HOUSING, MaterialItem::new);

    // Cells
    public static final ItemDefinition<BasicStorageCell> ITEM_CELL_1K = item("1k ME Item Storage Cell", TLItemIds.ITEM_CELL_1K, p -> new BasicStorageCell(p.stacksTo(1), 0.5f, 1, 8, 63, TLKeyType.items()));
    public static final ItemDefinition<BasicStorageCell> ITEM_CELL_4K = item("4k ME Item Storage Cell", TLItemIds.ITEM_CELL_4K, p -> new BasicStorageCell(p.stacksTo(1), 1.0f, 4, 32, 63, TLKeyType.items()));
    public static final ItemDefinition<BasicStorageCell> ITEM_CELL_16K = item("16k ME Item Storage Cell", TLItemIds.ITEM_CELL_16K, p -> new BasicStorageCell(p.stacksTo(1), 1.5f, 16, 128, 63, TLKeyType.items()));
    public static final ItemDefinition<BasicStorageCell> ITEM_CELL_64K = item("64k ME Item Storage Cell", TLItemIds.ITEM_CELL_64K, p -> new BasicStorageCell(p.stacksTo(1), 2.0f, 64, 512, 63, TLKeyType.items()));
    public static final ItemDefinition<BasicStorageCell> FLUID_CELL_1K = item("1k ME Fluid Storage Cell", TLItemIds.FLUID_CELL_1K, p -> new BasicStorageCell(p.stacksTo(1), 0.5f, 1, 8, 18, TLKeyType.fluids()));
    public static final ItemDefinition<BasicStorageCell> FLUID_CELL_4K = item("4k ME Fluid Storage Cell", TLItemIds.FLUID_CELL_4K, p -> new BasicStorageCell(p.stacksTo(1), 1.0f, 4, 32, 18, TLKeyType.fluids()));
    public static final ItemDefinition<BasicStorageCell> FLUID_CELL_16K = item("16k ME Fluid Storage Cell", TLItemIds.FLUID_CELL_16K, p -> new BasicStorageCell(p.stacksTo(1), 1.5f, 16, 128, 18, TLKeyType.fluids()));
    public static final ItemDefinition<BasicStorageCell> FLUID_CELL_64K = item("64k ME Fluid Storage Cell", TLItemIds.FLUID_CELL_64K, p -> new BasicStorageCell(p.stacksTo(1), 2.0f, 64, 512, 18, TLKeyType.fluids()));

    // Cards
    public static final ItemDefinition<MaterialItem> BASIC_CARD = item("Basic Card", TLItemIds.BASIC_CARD, MaterialItem::new);
    public static final ItemDefinition<Item> REDSTONE_CARD = item("Redstone Card", TLItemIds.REDSTONE_CARD, Upgrades::createUpgradeCardItem);
    public static final ItemDefinition<Item> VOID_CARD = item("Overflow Destruction Card", TLItemIds.VOID_CARD, Upgrades::createUpgradeCardItem);
    public static final ItemDefinition<MaterialItem> ADVANCED_CARD = item("Advanced Card", TLItemIds.ADVANCED_CARD, MaterialItem::new);
    public static final ItemDefinition<Item> FUZZY_CARD = item("Fuzzy Card", TLItemIds.FUZZY_CARD, Upgrades::createUpgradeCardItem);
    public static final ItemDefinition<Item> SPEED_CARD = item("Acceleration Card", TLItemIds.SPEED_CARD, Upgrades::createUpgradeCardItem);
    public static final ItemDefinition<Item> INVERTER_CARD = item("Inverter Card", TLItemIds.INVERTER_CARD, Upgrades::createUpgradeCardItem);
    public static final ItemDefinition<Item> EQUAL_DISTRIBUTION_CARD = item("Equal Distribution Card", TLItemIds.EQUAL_DISTRIBUTION_CARD, Upgrades::createUpgradeCardItem);

    // Wireless & terminals
    public static final ItemDefinition<MaterialItem> WIRELESS_RECEIVER = item("Wireless Receiver", TLItemIds.WIRELESS_RECEIVER, MaterialItem::new);
    public static final ItemDefinition<WirelessTerminalItem> WIRELESS_TERMINAL = item("Wireless Terminal", TLItemIds.WIRELESS_TERMINAL, p -> new WirelessTerminalItem(p.stacksTo(1)));
    public static final ItemDefinition<WirelessTerminalItem> WIRELESS_CRAFTING_TERMINAL = item("Wireless Crafting Terminal", TLItemIds.WIRELESS_CRAFTING_TERMINAL, p -> new WirelessCraftingTerminalItem(p.stacksTo(1)));

    // Misc
    public static final ItemDefinition<CreativeCellItem> CREATIVE_CELL = item("Creative ME Storage Cell", TLItemIds.CREATIVE_CELL, p -> new CreativeCellItem(p.stacksTo(1).rarity(Rarity.EPIC)));
    public static final ItemDefinition<EraserItem> DEBUG_ERASER = item("Dev.Eraser", AppEng.makeId("debug_eraser"), EraserItem::new);
    public static final ItemDefinition<DebugCardItem> DEBUG_CARD = item("Dev.DebugCard", AppEng.makeId("debug_card"), DebugCardItem::new);
    public static final ItemDefinition<ReplicatorCardItem> DEBUG_REPLICATOR_CARD = item("Dev.ReplicatorCard", AppEng.makeId("debug_replicator_card"), ReplicatorCardItem::new);
    public static final ItemDefinition<WrappedGenericStack> WRAPPED_GENERIC_STACK = item("Wrapped Generic Stack", TLItemIds.WRAPPED_GENERIC_STACK, WrappedGenericStack::new);

    // spotless:on

    public static List<ItemDefinition<?>> getItems() {
        return Collections.unmodifiableList(ITEMS);
    }

    static <T extends Item> ItemDefinition<T> item(String name, ResourceLocation id,
            Function<Item.Properties, T> factory) {
        return item(name, id, factory, TLCreativeTabIds.MAIN);
    }

    static <T extends Item> ItemDefinition<T> item(String name, ResourceLocation id,
            Function<Item.Properties, T> factory,
            @Nullable ResourceKey<CreativeModeTab> group) {

        Preconditions.checkArgument(id.getNamespace().equals(AppEng.MOD_ID), "Can only register for TL2");
        var definition = new ItemDefinition<>(name, DR.registerItem(id.getPath(), factory));

        if (Objects.equals(group, TLCreativeTabIds.MAIN)) {
            MainCreativeTab.add(definition);
        } else if (group != null) {
            MainCreativeTab.add(definition);
            MainCreativeTab.addExternal(group, definition);
        }

        ITEMS.add(definition);

        return definition;
    }
}
