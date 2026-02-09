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

import static appeng.core.definitions.TLItems.item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import net.minecraft.resources.ResourceLocation;

import appeng.api.ids.TLPartIds;
import appeng.api.parts.IPart;
import appeng.api.parts.IPartItem;
import appeng.api.parts.PartModels;
import appeng.api.util.TLColor;
import appeng.core.AppEng;
import appeng.items.parts.ColoredPartItem;
import appeng.items.parts.PartItem;
import appeng.items.parts.PartModelsHelper;
import appeng.parts.automation.StorageLevelEmitterPart;
import appeng.parts.misc.InvertedToggleBusPart;
import appeng.parts.misc.ToggleBusPart;
import appeng.parts.networking.DenseCablePart;
import appeng.parts.networking.GlassCablePart;
import appeng.parts.networking.SmartCablePart;
import appeng.parts.reporting.ConversionMonitorPart;
import appeng.parts.reporting.CraftingTerminalPart;
import appeng.parts.reporting.ItemTerminalPart;
import appeng.parts.reporting.PanelPart;
import appeng.parts.reporting.StorageMonitorPart;

/**
 * Internal implementation for the API parts
 */
public final class TLParts {
    public static final List<ColoredItemDefinition<?>> COLORED_PARTS = new ArrayList<>();

    // spotless:off
    public static final ColoredItemDefinition<ColoredPartItem<GlassCablePart>> GLASS_CABLE = constructColoredDefinition("ME Glass Cable", "glass_cable", GlassCablePart.class, GlassCablePart::new);
    public static final ColoredItemDefinition<ColoredPartItem<SmartCablePart>> SMART_CABLE = constructColoredDefinition("ME Smart Cable", "smart_cable", SmartCablePart.class, SmartCablePart::new);
    public static final ColoredItemDefinition<ColoredPartItem<DenseCablePart>> DENSE_CABLE = constructColoredDefinition("ME Dense Cable", "dense_cable",  DenseCablePart.class, DenseCablePart::new);
    public static final ItemDefinition<PartItem<ToggleBusPart>> TOGGLE_BUS = createPart("ME Toggle Bus", TLPartIds.TOGGLE_BUS, ToggleBusPart.class, ToggleBusPart::new);
    public static final ItemDefinition<PartItem<InvertedToggleBusPart>> INVERTED_TOGGLE_BUS = createPart("ME Inverted Toggle Bus", TLPartIds.INVERTED_TOGGLE_BUS, InvertedToggleBusPart.class, InvertedToggleBusPart::new);
    public static final ItemDefinition<PartItem<PanelPart>> PANEL = createPart("Illuminated Panel", TLPartIds.PANEL, PanelPart.class, PanelPart::new);
    public static final ItemDefinition<PartItem<StorageLevelEmitterPart>> LEVEL_EMITTER = createPart("ME Level Emitter", TLPartIds.LEVEL_EMITTER, StorageLevelEmitterPart.class, StorageLevelEmitterPart::new);
    public static final ItemDefinition<PartItem<CraftingTerminalPart>> CRAFTING_TERMINAL = createPart("ME Crafting Terminal", TLPartIds.CRAFTING_TERMINAL, CraftingTerminalPart.class, CraftingTerminalPart::new);
    public static final ItemDefinition<PartItem<ItemTerminalPart>> TERMINAL = createPart("ME Terminal", TLPartIds.TERMINAL, ItemTerminalPart.class, ItemTerminalPart::new);
    public static final ItemDefinition<PartItem<StorageMonitorPart>> STORAGE_MONITOR = createPart("ME Storage Monitor", TLPartIds.STORAGE_MONITOR, StorageMonitorPart.class, StorageMonitorPart::new);
    public static final ItemDefinition<PartItem<ConversionMonitorPart>> CONVERSION_MONITOR = createPart("ME Conversion Monitor", TLPartIds.CONVERSION_MONITOR, ConversionMonitorPart.class, ConversionMonitorPart::new);
    // spotless:on

    private static <T extends IPart> ItemDefinition<PartItem<T>> createPart(
            String englishName,
            ResourceLocation id,
            Class<T> partClass,
            Function<IPartItem<T>, T> factory) {

        PartModels.registerModels(PartModelsHelper.createModels(partClass));
        return item(englishName, id, props -> new PartItem<>(props, partClass, factory));
    }

    private static <T extends IPart> ColoredItemDefinition<ColoredPartItem<T>> constructColoredDefinition(
            String nameSuffix,
            String idSuffix,
            Class<T> partClass,
            Function<ColoredPartItem<T>, T> factory) {

        PartModels.registerModels(PartModelsHelper.createModels(partClass));

        var definition = new ColoredItemDefinition<ColoredPartItem<T>>();
        for (TLColor color : TLColor.values()) {
            var id = color.registryPrefix + '_' + idSuffix;
            var name = color.englishName + " " + nameSuffix;

            var itemDef = item(name, AppEng.makeId(id),
                    props -> new ColoredPartItem<>(props, partClass, factory, color));

            definition.add(color, AppEng.makeId(id), itemDef);
        }

        COLORED_PARTS.add(definition);

        return definition;
    }

    // Used to control in which order static constructors are called
    public static void init() {
    }

}
