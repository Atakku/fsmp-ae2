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

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import net.minecraft.resources.ResourceLocation;

import appeng.api.util.TLColor;

/**
 * Contains {@link net.minecraft.world.item.Item} ids for various cable bus parts defined by TL2.
 */
public final class TLPartIds {

    ///
    /// CABLES
    ///

    public static final ResourceLocation CABLE_GLASS_WHITE = id("white_glass_cable");
    public static final ResourceLocation CABLE_GLASS_ORANGE = id("orange_glass_cable");
    public static final ResourceLocation CABLE_GLASS_MAGENTA = id("magenta_glass_cable");
    public static final ResourceLocation CABLE_GLASS_LIGHT_BLUE = id("light_blue_glass_cable");
    public static final ResourceLocation CABLE_GLASS_YELLOW = id("yellow_glass_cable");
    public static final ResourceLocation CABLE_GLASS_LIME = id("lime_glass_cable");
    public static final ResourceLocation CABLE_GLASS_PINK = id("pink_glass_cable");
    public static final ResourceLocation CABLE_GLASS_GRAY = id("gray_glass_cable");
    public static final ResourceLocation CABLE_GLASS_LIGHT_GRAY = id("light_gray_glass_cable");
    public static final ResourceLocation CABLE_GLASS_CYAN = id("cyan_glass_cable");
    public static final ResourceLocation CABLE_GLASS_PURPLE = id("purple_glass_cable");
    public static final ResourceLocation CABLE_GLASS_BLUE = id("blue_glass_cable");
    public static final ResourceLocation CABLE_GLASS_BROWN = id("brown_glass_cable");
    public static final ResourceLocation CABLE_GLASS_GREEN = id("green_glass_cable");
    public static final ResourceLocation CABLE_GLASS_RED = id("red_glass_cable");
    public static final ResourceLocation CABLE_GLASS_BLACK = id("black_glass_cable");
    public static final ResourceLocation CABLE_GLASS_TRANSPARENT = id("fluix_glass_cable");
    public static final Map<TLColor, ResourceLocation> CABLE_GLASS = ImmutableMap.<TLColor, ResourceLocation>builder()
            .put(TLColor.WHITE, CABLE_GLASS_WHITE)
            .put(TLColor.ORANGE, CABLE_GLASS_ORANGE)
            .put(TLColor.MAGENTA, CABLE_GLASS_MAGENTA)
            .put(TLColor.LIGHT_BLUE, CABLE_GLASS_LIGHT_BLUE)
            .put(TLColor.YELLOW, CABLE_GLASS_YELLOW)
            .put(TLColor.LIME, CABLE_GLASS_LIME)
            .put(TLColor.PINK, CABLE_GLASS_PINK)
            .put(TLColor.GRAY, CABLE_GLASS_GRAY)
            .put(TLColor.LIGHT_GRAY, CABLE_GLASS_LIGHT_GRAY)
            .put(TLColor.CYAN, CABLE_GLASS_CYAN)
            .put(TLColor.PURPLE, CABLE_GLASS_PURPLE)
            .put(TLColor.BLUE, CABLE_GLASS_BLUE)
            .put(TLColor.BROWN, CABLE_GLASS_BROWN)
            .put(TLColor.GREEN, CABLE_GLASS_GREEN)
            .put(TLColor.RED, CABLE_GLASS_RED)
            .put(TLColor.BLACK, CABLE_GLASS_BLACK)
            .put(TLColor.TRANSPARENT, CABLE_GLASS_TRANSPARENT)
            .build();

    public static final ResourceLocation CABLE_SMART_WHITE = id("white_smart_cable");
    public static final ResourceLocation CABLE_SMART_ORANGE = id("orange_smart_cable");
    public static final ResourceLocation CABLE_SMART_MAGENTA = id("magenta_smart_cable");
    public static final ResourceLocation CABLE_SMART_LIGHT_BLUE = id("light_blue_smart_cable");
    public static final ResourceLocation CABLE_SMART_YELLOW = id("yellow_smart_cable");
    public static final ResourceLocation CABLE_SMART_LIME = id("lime_smart_cable");
    public static final ResourceLocation CABLE_SMART_PINK = id("pink_smart_cable");
    public static final ResourceLocation CABLE_SMART_GRAY = id("gray_smart_cable");
    public static final ResourceLocation CABLE_SMART_LIGHT_GRAY = id("light_gray_smart_cable");
    public static final ResourceLocation CABLE_SMART_CYAN = id("cyan_smart_cable");
    public static final ResourceLocation CABLE_SMART_PURPLE = id("purple_smart_cable");
    public static final ResourceLocation CABLE_SMART_BLUE = id("blue_smart_cable");
    public static final ResourceLocation CABLE_SMART_BROWN = id("brown_smart_cable");
    public static final ResourceLocation CABLE_SMART_GREEN = id("green_smart_cable");
    public static final ResourceLocation CABLE_SMART_RED = id("red_smart_cable");
    public static final ResourceLocation CABLE_SMART_BLACK = id("black_smart_cable");
    public static final ResourceLocation CABLE_SMART_TRANSPARENT = id("fluix_smart_cable");
    public static final Map<TLColor, ResourceLocation> CABLE_SMART = ImmutableMap.<TLColor, ResourceLocation>builder()
            .put(TLColor.WHITE, CABLE_SMART_WHITE)
            .put(TLColor.ORANGE, CABLE_SMART_ORANGE)
            .put(TLColor.MAGENTA, CABLE_SMART_MAGENTA)
            .put(TLColor.LIGHT_BLUE, CABLE_SMART_LIGHT_BLUE)
            .put(TLColor.YELLOW, CABLE_SMART_YELLOW)
            .put(TLColor.LIME, CABLE_SMART_LIME)
            .put(TLColor.PINK, CABLE_SMART_PINK)
            .put(TLColor.GRAY, CABLE_SMART_GRAY)
            .put(TLColor.LIGHT_GRAY, CABLE_SMART_LIGHT_GRAY)
            .put(TLColor.CYAN, CABLE_SMART_CYAN)
            .put(TLColor.PURPLE, CABLE_SMART_PURPLE)
            .put(TLColor.BLUE, CABLE_SMART_BLUE)
            .put(TLColor.BROWN, CABLE_SMART_BROWN)
            .put(TLColor.GREEN, CABLE_SMART_GREEN)
            .put(TLColor.RED, CABLE_SMART_RED)
            .put(TLColor.BLACK, CABLE_SMART_BLACK)
            .put(TLColor.TRANSPARENT, CABLE_SMART_TRANSPARENT)
            .build();

    public static final ResourceLocation CABLE_DENSE_WHITE = id("white_dense_cable");
    public static final ResourceLocation CABLE_DENSE_ORANGE = id("orange_dense_cable");
    public static final ResourceLocation CABLE_DENSE_MAGENTA = id("magenta_dense_cable");
    public static final ResourceLocation CABLE_DENSE_LIGHT_BLUE = id("light_blue_dense_cable");
    public static final ResourceLocation CABLE_DENSE_YELLOW = id("yellow_dense_cable");
    public static final ResourceLocation CABLE_DENSE_LIME = id("lime_dense_cable");
    public static final ResourceLocation CABLE_DENSE_PINK = id("pink_dense_cable");
    public static final ResourceLocation CABLE_DENSE_GRAY = id("gray_dense_cable");
    public static final ResourceLocation CABLE_DENSE_LIGHT_GRAY = id("light_gray_dense_cable");
    public static final ResourceLocation CABLE_DENSE_CYAN = id("cyan_dense_cable");
    public static final ResourceLocation CABLE_DENSE_PURPLE = id("purple_dense_cable");
    public static final ResourceLocation CABLE_DENSE_BLUE = id("blue_dense_cable");
    public static final ResourceLocation CABLE_DENSE_BROWN = id("brown_dense_cable");
    public static final ResourceLocation CABLE_DENSE_GREEN = id("green_dense_cable");
    public static final ResourceLocation CABLE_DENSE_RED = id("red_smart_dense_cable");
    public static final ResourceLocation CABLE_DENSE_BLACK = id("black_dense_cable");
    public static final ResourceLocation CABLE_DENSE_TRANSPARENT = id("fluix_dense_cable");
    public static final Map<TLColor, ResourceLocation> CABLE_DENSE = ImmutableMap
            .<TLColor, ResourceLocation>builder().put(TLColor.WHITE, CABLE_DENSE_WHITE)
            .put(TLColor.ORANGE, CABLE_DENSE_ORANGE)
            .put(TLColor.MAGENTA, CABLE_DENSE_MAGENTA)
            .put(TLColor.LIGHT_BLUE, CABLE_DENSE_LIGHT_BLUE)
            .put(TLColor.YELLOW, CABLE_DENSE_YELLOW)
            .put(TLColor.LIME, CABLE_DENSE_LIME)
            .put(TLColor.PINK, CABLE_DENSE_PINK)
            .put(TLColor.GRAY, CABLE_DENSE_GRAY)
            .put(TLColor.LIGHT_GRAY, CABLE_DENSE_LIGHT_GRAY)
            .put(TLColor.CYAN, CABLE_DENSE_CYAN)
            .put(TLColor.PURPLE, CABLE_DENSE_PURPLE)
            .put(TLColor.BLUE, CABLE_DENSE_BLUE)
            .put(TLColor.BROWN, CABLE_DENSE_BROWN)
            .put(TLColor.GREEN, CABLE_DENSE_GREEN)
            .put(TLColor.RED, CABLE_DENSE_RED)
            .put(TLColor.BLACK, CABLE_DENSE_BLACK)
            .put(TLColor.TRANSPARENT, CABLE_DENSE_TRANSPARENT)
            .build();

    ///
    /// Buses
    ///
    public static final ResourceLocation TOGGLE_BUS = id("toggle_bus");
    public static final ResourceLocation INVERTED_TOGGLE_BUS = id("inverted_toggle_bus");
    public static final ResourceLocation LEVEL_EMITTER = id("level_emitter");
    public static final ResourceLocation CONVERSION_MONITOR = id("conversion_monitor");

    ///
    /// Monitors and terminals
    ///
    public static final ResourceLocation PANEL = id("panel");
    public static final ResourceLocation TERMINAL = id("terminal");
    public static final ResourceLocation CRAFTING_TERMINAL = id("crafting_terminal");
    public static final ResourceLocation STORAGE_MONITOR = id("storage_monitor");

    private static ResourceLocation id(String id) {
        return ResourceLocation.fromNamespaceAndPath(TLConstants.MOD_ID, id);
    }
}
