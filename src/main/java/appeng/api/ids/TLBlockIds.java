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
import net.minecraft.world.level.block.Block;

/**
 * Contains {@link Block} ids for various blocks defined by TL2.
 */
public final class TLBlockIds {
    ///
    /// ME NETWORK
    ///
    public static final ResourceLocation WIRELESS_ACCESS_POINT = id("wireless_access_point");
    public static final ResourceLocation CONTROLLER = id("controller");
    public static final ResourceLocation DRIVE = id("drive");
    public static final ResourceLocation ME_CHEST = id("chest");
    public static final ResourceLocation CELL_WORKBENCH = id("cell_workbench");
    public static final ResourceLocation IO_PORT = id("io_port");
    public static final ResourceLocation CABLE_BUS = id("cable_bus");

    ///
    /// DECORATIVE BLOCKS
    ///
    public static final ResourceLocation FLUIX_BLOCK = id("fluix_block");
    public static final ResourceLocation QUARTZ_GLASS = id("quartz_glass");
    public static final ResourceLocation QUARTZ_VIBRANT_GLASS = id("quartz_vibrant_glass");

    private static ResourceLocation id(String id) {
        return ResourceLocation.fromNamespaceAndPath(TLConstants.MOD_ID, id);
    }
}
