/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2021, TeamAppliedEnergistics, All rights reserved.
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

package appeng.items.tools.powered;

import java.util.List;
import java.util.Objects;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import appeng.api.util.TLColor;

/**
 * Allows recoloring a variety of vanilla blocks.
 */
public final class BlockRecolorer {

    private BlockRecolorer() {
    }

    private static final BiMap<TLColor, Block> STAINED_GLASS_BY_COLOR = EnumHashBiMap.create(ImmutableMap
            .<TLColor, Block>builder().put(TLColor.WHITE, Blocks.WHITE_STAINED_GLASS)
            .put(TLColor.ORANGE, Blocks.ORANGE_STAINED_GLASS).put(TLColor.MAGENTA, Blocks.MAGENTA_STAINED_GLASS)
            .put(TLColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_STAINED_GLASS).put(TLColor.YELLOW, Blocks.YELLOW_STAINED_GLASS)
            .put(TLColor.LIME, Blocks.LIME_STAINED_GLASS).put(TLColor.PINK, Blocks.PINK_STAINED_GLASS)
            .put(TLColor.GRAY, Blocks.GRAY_STAINED_GLASS).put(TLColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_STAINED_GLASS)
            .put(TLColor.CYAN, Blocks.CYAN_STAINED_GLASS).put(TLColor.PURPLE, Blocks.PURPLE_STAINED_GLASS)
            .put(TLColor.BLUE, Blocks.BLUE_STAINED_GLASS).put(TLColor.BROWN, Blocks.BROWN_STAINED_GLASS)
            .put(TLColor.GREEN, Blocks.GREEN_STAINED_GLASS).put(TLColor.RED, Blocks.RED_STAINED_GLASS)
            .put(TLColor.BLACK, Blocks.BLACK_STAINED_GLASS).build());

    private static final BiMap<TLColor, Block> STAINED_GLASS_PANE_BY_COLOR = EnumHashBiMap.create(ImmutableMap
            .<TLColor, Block>builder().put(TLColor.WHITE, Blocks.WHITE_STAINED_GLASS_PANE)
            .put(TLColor.ORANGE, Blocks.ORANGE_STAINED_GLASS_PANE)
            .put(TLColor.MAGENTA, Blocks.MAGENTA_STAINED_GLASS_PANE)
            .put(TLColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_STAINED_GLASS_PANE)
            .put(TLColor.YELLOW, Blocks.YELLOW_STAINED_GLASS_PANE).put(TLColor.LIME, Blocks.LIME_STAINED_GLASS_PANE)
            .put(TLColor.PINK, Blocks.PINK_STAINED_GLASS_PANE).put(TLColor.GRAY, Blocks.GRAY_STAINED_GLASS_PANE)
            .put(TLColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_STAINED_GLASS_PANE)
            .put(TLColor.CYAN, Blocks.CYAN_STAINED_GLASS_PANE).put(TLColor.PURPLE, Blocks.PURPLE_STAINED_GLASS_PANE)
            .put(TLColor.BLUE, Blocks.BLUE_STAINED_GLASS_PANE).put(TLColor.BROWN, Blocks.BROWN_STAINED_GLASS_PANE)
            .put(TLColor.GREEN, Blocks.GREEN_STAINED_GLASS_PANE).put(TLColor.RED, Blocks.RED_STAINED_GLASS_PANE)
            .put(TLColor.BLACK, Blocks.BLACK_STAINED_GLASS_PANE).build());

    private static final BiMap<TLColor, Block> WOOL_BY_COLOR = EnumHashBiMap.create(ImmutableMap
            .<TLColor, Block>builder().put(TLColor.WHITE, Blocks.WHITE_WOOL).put(TLColor.ORANGE, Blocks.ORANGE_WOOL)
            .put(TLColor.MAGENTA, Blocks.MAGENTA_WOOL).put(TLColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL)
            .put(TLColor.YELLOW, Blocks.YELLOW_WOOL).put(TLColor.LIME, Blocks.LIME_WOOL)
            .put(TLColor.PINK, Blocks.PINK_WOOL).put(TLColor.GRAY, Blocks.GRAY_WOOL)
            .put(TLColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL).put(TLColor.CYAN, Blocks.CYAN_WOOL)
            .put(TLColor.PURPLE, Blocks.PURPLE_WOOL).put(TLColor.BLUE, Blocks.BLUE_WOOL)
            .put(TLColor.BROWN, Blocks.BROWN_WOOL).put(TLColor.GREEN, Blocks.GREEN_WOOL)
            .put(TLColor.RED, Blocks.RED_WOOL).put(TLColor.BLACK, Blocks.BLACK_WOOL).build());

    private static final BiMap<TLColor, Block> BANNER_BY_COLOR = EnumHashBiMap.create(ImmutableMap
            .<TLColor, Block>builder().put(TLColor.WHITE, Blocks.WHITE_BANNER).put(TLColor.ORANGE, Blocks.ORANGE_BANNER)
            .put(TLColor.MAGENTA, Blocks.MAGENTA_BANNER).put(TLColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_BANNER)
            .put(TLColor.YELLOW, Blocks.YELLOW_BANNER).put(TLColor.LIME, Blocks.LIME_BANNER)
            .put(TLColor.PINK, Blocks.PINK_BANNER).put(TLColor.GRAY, Blocks.GRAY_BANNER)
            .put(TLColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_BANNER).put(TLColor.CYAN, Blocks.CYAN_BANNER)
            .put(TLColor.PURPLE, Blocks.PURPLE_BANNER).put(TLColor.BLUE, Blocks.BLUE_BANNER)
            .put(TLColor.BROWN, Blocks.BROWN_BANNER).put(TLColor.GREEN, Blocks.GREEN_BANNER)
            .put(TLColor.RED, Blocks.RED_BANNER).put(TLColor.BLACK, Blocks.BLACK_BANNER).build());

    private static final BiMap<TLColor, Block> WALL_BANNER_BY_COLOR = EnumHashBiMap
            .create(ImmutableMap.<TLColor, Block>builder().put(TLColor.WHITE, Blocks.WHITE_WALL_BANNER)
                    .put(TLColor.ORANGE, Blocks.ORANGE_WALL_BANNER).put(TLColor.MAGENTA, Blocks.MAGENTA_WALL_BANNER)
                    .put(TLColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WALL_BANNER)
                    .put(TLColor.YELLOW, Blocks.YELLOW_WALL_BANNER).put(TLColor.LIME, Blocks.LIME_WALL_BANNER)
                    .put(TLColor.PINK, Blocks.PINK_WALL_BANNER).put(TLColor.GRAY, Blocks.GRAY_WALL_BANNER)
                    .put(TLColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WALL_BANNER).put(TLColor.CYAN, Blocks.CYAN_WALL_BANNER)
                    .put(TLColor.PURPLE, Blocks.PURPLE_WALL_BANNER).put(TLColor.BLUE, Blocks.BLUE_WALL_BANNER)
                    .put(TLColor.BROWN, Blocks.BROWN_WALL_BANNER).put(TLColor.GREEN, Blocks.GREEN_WALL_BANNER)
                    .put(TLColor.RED, Blocks.RED_WALL_BANNER).put(TLColor.BLACK, Blocks.BLACK_WALL_BANNER).build());

    private static final BiMap<TLColor, Block> CARPET_BY_COLOR = EnumHashBiMap.create(ImmutableMap
            .<TLColor, Block>builder().put(TLColor.WHITE, Blocks.WHITE_CARPET).put(TLColor.ORANGE, Blocks.ORANGE_CARPET)
            .put(TLColor.MAGENTA, Blocks.MAGENTA_CARPET).put(TLColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_CARPET)
            .put(TLColor.YELLOW, Blocks.YELLOW_CARPET).put(TLColor.LIME, Blocks.LIME_CARPET)
            .put(TLColor.PINK, Blocks.PINK_CARPET).put(TLColor.GRAY, Blocks.GRAY_CARPET)
            .put(TLColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_CARPET).put(TLColor.CYAN, Blocks.CYAN_CARPET)
            .put(TLColor.PURPLE, Blocks.PURPLE_CARPET).put(TLColor.BLUE, Blocks.BLUE_CARPET)
            .put(TLColor.BROWN, Blocks.BROWN_CARPET).put(TLColor.GREEN, Blocks.GREEN_CARPET)
            .put(TLColor.RED, Blocks.RED_CARPET).put(TLColor.BLACK, Blocks.BLACK_CARPET).build());

    private static final BiMap<TLColor, Block> TERRACOTTA_BY_COLOR = EnumHashBiMap
            .create(ImmutableMap.<TLColor, Block>builder().put(TLColor.WHITE, Blocks.WHITE_TERRACOTTA)
                    .put(TLColor.ORANGE, Blocks.ORANGE_TERRACOTTA).put(TLColor.MAGENTA, Blocks.MAGENTA_TERRACOTTA)
                    .put(TLColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_TERRACOTTA).put(TLColor.YELLOW, Blocks.YELLOW_TERRACOTTA)
                    .put(TLColor.LIME, Blocks.LIME_TERRACOTTA).put(TLColor.PINK, Blocks.PINK_TERRACOTTA)
                    .put(TLColor.GRAY, Blocks.GRAY_TERRACOTTA).put(TLColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_TERRACOTTA)
                    .put(TLColor.CYAN, Blocks.CYAN_TERRACOTTA).put(TLColor.PURPLE, Blocks.PURPLE_TERRACOTTA)
                    .put(TLColor.BLUE, Blocks.BLUE_TERRACOTTA).put(TLColor.BROWN, Blocks.BROWN_TERRACOTTA)
                    .put(TLColor.GREEN, Blocks.GREEN_TERRACOTTA).put(TLColor.RED, Blocks.RED_TERRACOTTA)
                    .put(TLColor.BLACK, Blocks.BLACK_TERRACOTTA).build());

    private static final BiMap<TLColor, Block> GLAZED_TERRACOTTA_BY_COLOR = EnumHashBiMap.create(ImmutableMap
            .<TLColor, Block>builder().put(TLColor.WHITE, Blocks.WHITE_GLAZED_TERRACOTTA)
            .put(TLColor.ORANGE, Blocks.ORANGE_GLAZED_TERRACOTTA).put(TLColor.MAGENTA, Blocks.MAGENTA_GLAZED_TERRACOTTA)
            .put(TLColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA)
            .put(TLColor.YELLOW, Blocks.YELLOW_GLAZED_TERRACOTTA).put(TLColor.LIME, Blocks.LIME_GLAZED_TERRACOTTA)
            .put(TLColor.PINK, Blocks.PINK_GLAZED_TERRACOTTA).put(TLColor.GRAY, Blocks.GRAY_GLAZED_TERRACOTTA)
            .put(TLColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA)
            .put(TLColor.CYAN, Blocks.CYAN_GLAZED_TERRACOTTA).put(TLColor.PURPLE, Blocks.PURPLE_GLAZED_TERRACOTTA)
            .put(TLColor.BLUE, Blocks.BLUE_GLAZED_TERRACOTTA).put(TLColor.BROWN, Blocks.BROWN_GLAZED_TERRACOTTA)
            .put(TLColor.GREEN, Blocks.GREEN_GLAZED_TERRACOTTA).put(TLColor.RED, Blocks.RED_GLAZED_TERRACOTTA)
            .put(TLColor.BLACK, Blocks.BLACK_GLAZED_TERRACOTTA).build());

    private static final BiMap<TLColor, Block> CONCRETE_BY_COLOR = EnumHashBiMap
            .create(ImmutableMap.<TLColor, Block>builder().put(TLColor.WHITE, Blocks.WHITE_CONCRETE)
                    .put(TLColor.ORANGE, Blocks.ORANGE_CONCRETE).put(TLColor.MAGENTA, Blocks.MAGENTA_CONCRETE)
                    .put(TLColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_CONCRETE).put(TLColor.YELLOW, Blocks.YELLOW_CONCRETE)
                    .put(TLColor.LIME, Blocks.LIME_CONCRETE).put(TLColor.PINK, Blocks.PINK_CONCRETE)
                    .put(TLColor.GRAY, Blocks.GRAY_CONCRETE).put(TLColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_CONCRETE)
                    .put(TLColor.CYAN, Blocks.CYAN_CONCRETE).put(TLColor.PURPLE, Blocks.PURPLE_CONCRETE)
                    .put(TLColor.BLUE, Blocks.BLUE_CONCRETE).put(TLColor.BROWN, Blocks.BROWN_CONCRETE)
                    .put(TLColor.GREEN, Blocks.GREEN_CONCRETE).put(TLColor.RED, Blocks.RED_CONCRETE)
                    .put(TLColor.BLACK, Blocks.BLACK_CONCRETE).build());

    private static final List<RecolorableBlockGroup> BLOCK_GROUPS = ImmutableList.of(
            new RecolorableBlockGroup(Blocks.GLASS, STAINED_GLASS_BY_COLOR),
            new RecolorableBlockGroup(Blocks.GLASS_PANE, STAINED_GLASS_PANE_BY_COLOR),
            new RecolorableBlockGroup(Blocks.WHITE_WOOL, WOOL_BY_COLOR),
            new RecolorableBlockGroup(Blocks.WHITE_BANNER, BANNER_BY_COLOR),
            new RecolorableBlockGroup(Blocks.WHITE_WALL_BANNER, WALL_BANNER_BY_COLOR),
            new RecolorableBlockGroup(Blocks.WHITE_CARPET, CARPET_BY_COLOR),
            new RecolorableBlockGroup(Blocks.TERRACOTTA, TERRACOTTA_BY_COLOR),
            new RecolorableBlockGroup(null, GLAZED_TERRACOTTA_BY_COLOR),
            new RecolorableBlockGroup(null, CONCRETE_BY_COLOR));

    public static Block recolor(Block block, TLColor newColor) {
        Objects.requireNonNull(block);

        for (RecolorableBlockGroup group : BLOCK_GROUPS) {
            if (group.uncoloredVariant == block || group.coloredVariants.containsValue(block)) {
                Block newBlock = group.coloredVariants.get(newColor);
                if (newBlock == null) {
                    if (group.uncoloredVariant != null) {
                        newBlock = group.uncoloredVariant;
                    } else {
                        newBlock = block;
                    }
                }
                return newBlock;
            }
        }

        return block;
    }

    private static class RecolorableBlockGroup {

        final Block uncoloredVariant;

        final BiMap<TLColor, Block> coloredVariants;

        public RecolorableBlockGroup(Block uncoloredVariant, BiMap<TLColor, Block> coloredVariants) {
            this.uncoloredVariant = uncoloredVariant;
            this.coloredVariants = coloredVariants;
        }

    }

}
