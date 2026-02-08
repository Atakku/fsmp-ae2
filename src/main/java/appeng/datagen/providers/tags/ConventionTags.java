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

package appeng.datagen.providers.tags;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

import appeng.core.AppEng;

/**
 * Contains various tags:
 * <ul>
 * <li>Convention tags defined by the modding API for mod-compatibility purposes.</li>
 * <li>Tags defined by TL2 itself for recipe use.</li>
 * <li>Tags provided by TL2 for mod compatibility in the convention namespace.</li>
 * </ul>
 */
public final class ConventionTags {

    private ConventionTags() {
    }

    /**
     * This tag contains all data component types that should be cleared from a memory card when it is
     * shift+right-clicked.
     */
    public static final TagKey<DataComponentType<?>> EXPORTED_SETTINGS = net.minecraft.tags.TagKey.create(
            Registries.DATA_COMPONENT_TYPE,
            AppEng.makeId("exported_settings"));

    public static final TagKey<Item> DUSTS = Tags.Items.DUSTS;
    public static final TagKey<Item> DUSTS_REDSTONE = Tags.Items.DUSTS_REDSTONE;
    public static final TagKey<Item> DUSTS_GLOWSTONE = Tags.Items.DUSTS_GLOWSTONE;
    public static final TagKey<Item> DUSTS_AMETHYST = tag("c:dusts/amethyst");
    public static final TagKey<Item> DUSTS_QUARTZ = tag("c:dusts/quartz");
    public static final TagKey<Item> DUSTS_FLUIX = tag("c:dusts/fluix");

    public static final TagKey<Item> GEMS = Tags.Items.GEMS;
    public static final TagKey<Item> GEMS_DIAMOND = Tags.Items.GEMS_DIAMOND;
    public static final TagKey<Item> GEMS_AMETHYST = Tags.Items.GEMS_AMETHYST;
    public static final TagKey<Item> GEMS_QUARTZ = Tags.Items.GEMS_QUARTZ;
    public static final TagKey<Item> GEMS_FLUIX = tag("c:gems/fluix");

    public static final TagKey<Item> SILICON = tag("c:silicon");

    public static final TagKey<Item> COPPER_INGOT = Tags.Items.INGOTS_COPPER;
    public static final TagKey<Item> GOLD_NUGGET = Tags.Items.NUGGETS_GOLD;
    public static final TagKey<Item> GOLD_INGOT = Tags.Items.INGOTS_GOLD;
    public static final TagKey<Item> IRON_NUGGET = Tags.Items.NUGGETS_IRON;
    public static final TagKey<Item> IRON_INGOT = Tags.Items.INGOTS_IRON;
    public static final TagKey<Item> ENDER_PEARL = Tags.Items.ENDER_PEARLS;

    public static final TagKey<Item> WOOD_STICK = Tags.Items.RODS_WOODEN;
    public static final TagKey<Item> CHEST = Tags.Items.CHESTS_WOODEN;

    public static final TagKey<Item> STONE = Tags.Items.STONES;
    public static final TagKey<Item> GLASS = Tags.Items.GLASS_BLOCKS;
    public static final TagKey<Item> GLASS_CHEAP = Tags.Items.GLASS_BLOCKS_CHEAP;
    public static final TagKey<Block> GLASS_BLOCK = Tags.Blocks.GLASS_BLOCKS;

    public static final TagKey<Item> GLASS_CABLE = tag("tl2:glass_cable");
    public static final TagKey<Item> SMART_CABLE = tag("tl2:smart_cable");
    public static final TagKey<Item> SMART_DENSE_CABLE = tag("tl2:smart_dense_cable");
    public static final TagKey<Item> ILLUMINATED_PANEL = tag("tl2:illuminated_panel");
    /**
     * Items that can be used in recipes to remove color from colored items.
     */
    public static final TagKey<Item> CAN_REMOVE_COLOR = tag("tl2:can_remove_color");

    /**
     * Used to identify items that act as wrenches.
     */
    public static final TagKey<Item> WRENCH = tag("c:tools/wrench");

    public static final Map<DyeColor, TagKey<Item>> DYES = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(
                    Function.identity(),
                    dye -> tag("c:dyes/" + dye.getSerializedName())));

    public static final TagKey<Item> CURIOS = tag("curios:curio");

    public static TagKey<Item> dye(DyeColor color) {
        return DYES.get(color);
    }

    private static TagKey<Item> tag(String name) {
        return net.minecraft.tags.TagKey.create(Registries.ITEM, ResourceLocation.parse(name));
    }
}
