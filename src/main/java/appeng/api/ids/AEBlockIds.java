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
 * Contains {@link Block} ids for various blocks defined by Ae2.
 */
@SuppressWarnings("unused")
public final class AEBlockIds {

    ///
    /// WORLDGEN/MISC
    ///
    // Budding certus quartz
    public static final ResourceLocation FLAWLESS_BUDDING_QUARTZ = id("flawless_budding_quartz");
    public static final ResourceLocation FLAWED_BUDDING_QUARTZ = id("flawed_budding_quartz");
    public static final ResourceLocation CHIPPED_BUDDING_QUARTZ = id("chipped_budding_quartz");
    public static final ResourceLocation DAMAGED_BUDDING_QUARTZ = id("damaged_budding_quartz");
    // Certus quartz clusters
    public static final ResourceLocation SMALL_QUARTZ_BUD = id("small_quartz_bud");
    public static final ResourceLocation MEDIUM_QUARTZ_BUD = id("medium_quartz_bud");
    public static final ResourceLocation LARGE_QUARTZ_BUD = id("large_quartz_bud");
    public static final ResourceLocation QUARTZ_CLUSTER = id("quartz_cluster");

    public static final ResourceLocation MYSTERIOUS_CUBE = id("mysterious_cube");
    public static final ResourceLocation NOT_SO_MYSTERIOUS_CUBE = id("not_so_mysterious_cube");
    public static final ResourceLocation SKY_STONE_CHEST = id("sky_stone_chest");
    public static final ResourceLocation SMOOTH_SKY_STONE_CHEST = id("smooth_sky_stone_chest");

    ///
    /// ME NETWORK
    ///
    public static final ResourceLocation WIRELESS_ACCESS_POINT = id("wireless_access_point");
    public static final ResourceLocation CONTROLLER = id("controller");
    public static final ResourceLocation DRIVE = id("drive");
    public static final ResourceLocation ME_CHEST = id("chest");
    public static final ResourceLocation CELL_WORKBENCH = id("cell_workbench");
    public static final ResourceLocation IO_PORT = id("io_port");
    public static final ResourceLocation CONDENSER = id("condenser");
    public static final ResourceLocation CABLE_BUS = id("cable_bus");

    ///
    /// DECORATIVE BLOCKS
    ///
    public static final ResourceLocation QUARTZ_BLOCK = id("quartz_block");
    public static final ResourceLocation CUT_QUARTZ_BLOCK = id("cut_quartz_block");
    public static final ResourceLocation SMOOTH_QUARTZ_BLOCK = id("smooth_quartz_block");
    public static final ResourceLocation QUARTZ_BRICKS = id("quartz_bricks");
    public static final ResourceLocation QUARTZ_PILLAR = id("quartz_pillar");
    public static final ResourceLocation CHISELED_QUARTZ_BLOCK = id("chiseled_quartz_block");
    public static final ResourceLocation FLUIX_BLOCK = id("fluix_block");
    public static final ResourceLocation SKY_STONE_BLOCK = id("sky_stone_block");
    public static final ResourceLocation SMOOTH_SKY_STONE_BLOCK = id("smooth_sky_stone_block");
    public static final ResourceLocation SKY_STONE_BRICK = id("sky_stone_brick");
    public static final ResourceLocation SKY_STONE_SMALL_BRICK = id("sky_stone_small_brick");
    public static final ResourceLocation QUARTZ_GLASS = id("quartz_glass");
    public static final ResourceLocation QUARTZ_VIBRANT_GLASS = id("quartz_vibrant_glass");

    ///
    /// STAIRS
    ///
    public static final ResourceLocation SKY_STONE_STAIRS = id("sky_stone_stairs");
    public static final ResourceLocation SMOOTH_SKY_STONE_STAIRS = id("smooth_sky_stone_stairs");
    public static final ResourceLocation SKY_STONE_BRICK_STAIRS = id("sky_stone_brick_stairs");
    public static final ResourceLocation SKY_STONE_SMALL_BRICK_STAIRS = id("sky_stone_small_brick_stairs");
    public static final ResourceLocation FLUIX_STAIRS = id("fluix_stairs");
    public static final ResourceLocation QUARTZ_STAIRS = id("quartz_stairs");
    public static final ResourceLocation CUT_QUARTZ_STAIRS = id("cut_quartz_stairs");
    public static final ResourceLocation SMOOTH_QUARTZ_STAIRS = id("smooth_quartz_stairs");
    public static final ResourceLocation QUARTZ_BRICK_STAIRS = id("quartz_brick_stairs");
    public static final ResourceLocation CHISELED_QUARTZ_STAIRS = id("chiseled_quartz_stairs");
    public static final ResourceLocation QUARTZ_PILLAR_STAIRS = id("quartz_pillar_stairs");

    ///
    /// WALLS
    ///
    public static final ResourceLocation SKY_STONE_WALL = id("sky_stone_wall");
    public static final ResourceLocation SMOOTH_SKY_STONE_WALL = id("smooth_sky_stone_wall");
    public static final ResourceLocation SKY_STONE_BRICK_WALL = id("sky_stone_brick_wall");
    public static final ResourceLocation SKY_STONE_SMALL_BRICK_WALL = id("sky_stone_small_brick_wall");
    public static final ResourceLocation FLUIX_WALL = id("fluix_wall");
    public static final ResourceLocation QUARTZ_WALL = id("quartz_wall");
    public static final ResourceLocation CUT_QUARTZ_WALL = id("cut_quartz_wall");
    public static final ResourceLocation SMOOTH_QUARTZ_WALL = id("smooth_quartz_wall");
    public static final ResourceLocation QUARTZ_BRICK_WALL = id("quartz_brick_wall");
    public static final ResourceLocation CHISELED_QUARTZ_WALL = id("chiseled_quartz_wall");
    public static final ResourceLocation QUARTZ_PILLAR_WALL = id("quartz_pillar_wall");

    ///
    /// SLABS
    ///
    public static final ResourceLocation SKY_STONE_SLAB = id("sky_stone_slab");
    public static final ResourceLocation SMOOTH_SKY_STONE_SLAB = id("smooth_sky_stone_slab");
    public static final ResourceLocation SKY_STONE_BRICK_SLAB = id("sky_stone_brick_slab");
    public static final ResourceLocation SKY_STONE_SMALL_BRICK_SLAB = id("sky_stone_small_brick_slab");
    public static final ResourceLocation FLUIX_SLAB = id("fluix_slab");
    public static final ResourceLocation QUARTZ_SLAB = id("quartz_slab");
    public static final ResourceLocation CUT_QUARTZ_SLAB = id("cut_quartz_slab");
    public static final ResourceLocation SMOOTH_QUARTZ_SLAB = id("smooth_quartz_slab");
    public static final ResourceLocation QUARTZ_BRICK_SLAB = id("quartz_brick_slab");
    public static final ResourceLocation CHISELED_QUARTZ_SLAB = id("chiseled_quartz_slab");
    public static final ResourceLocation QUARTZ_PILLAR_SLAB = id("quartz_pillar_slab");

    private static ResourceLocation id(String id) {
        return ResourceLocation.fromNamespaceAndPath(AEConstants.MOD_ID, id);
    }
}
