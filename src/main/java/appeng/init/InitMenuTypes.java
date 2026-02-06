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

package appeng.init;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;

import appeng.menu.implementations.CellWorkbenchMenu;
import appeng.menu.implementations.CondenserMenu;
import appeng.menu.implementations.DriveMenu;
import appeng.menu.implementations.IOPortMenu;
import appeng.menu.implementations.MEChestMenu;
import appeng.menu.implementations.PriorityMenu;
import appeng.menu.implementations.SkyChestMenu;
import appeng.menu.implementations.StorageLevelEmitterMenu;
import appeng.menu.implementations.WirelessAccessPointMenu;
import appeng.menu.me.common.MEStorageMenu;
import appeng.menu.me.items.BasicCellChestMenu;
import appeng.menu.me.items.CraftingTermMenu;
import appeng.menu.me.items.WirelessCraftingTermMenu;

public final class InitMenuTypes {
    private static final Map<ResourceLocation, MenuType<?>> REGISTRATION_QUEUE = new HashMap<>();

    private InitMenuTypes() {
    }

    public static void init(Registry<MenuType<?>> registry) {
        registerAll(registry,
                BasicCellChestMenu.TYPE,
                CellWorkbenchMenu.TYPE,
                MEChestMenu.TYPE,
                CondenserMenu.TYPE,
                CraftingTermMenu.TYPE,
                DriveMenu.TYPE,
                IOPortMenu.TYPE,
                MEStorageMenu.TYPE,
                MEStorageMenu.PORTABLE_FLUID_CELL_TYPE,
                MEStorageMenu.PORTABLE_ITEM_CELL_TYPE,
                MEStorageMenu.WIRELESS_TYPE,
                PriorityMenu.TYPE,
                SkyChestMenu.TYPE,
                StorageLevelEmitterMenu.TYPE,
                WirelessCraftingTermMenu.TYPE,
                WirelessAccessPointMenu.TYPE);
    }

    private static void registerAll(Registry<MenuType<?>> registry, MenuType<?>... types) {
        // Flush the registration queue. Calling the static ctor of each menu class will have
        // filled it.
        for (var entry : REGISTRATION_QUEUE.entrySet()) {
            Registry.register(registry, entry.getKey(), entry.getValue());
        }
        REGISTRATION_QUEUE.clear();

        for (var type : types) {
            if (registry.getResourceKey(type).isEmpty()) {
                throw new IllegalStateException("Menu Type " + type + " is not registered");
            }
        }
    }

    public static void queueRegistration(ResourceLocation id, MenuType<?> menuType) {
        if (REGISTRATION_QUEUE.put(id, menuType) != null) {
            throw new IllegalStateException("Duplicate menu id: " + id);
        }
    }
}
