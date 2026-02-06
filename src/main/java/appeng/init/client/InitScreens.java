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

package appeng.init.client;

import java.util.IdentityHashMap;
import java.util.Map;

import com.google.common.annotations.VisibleForTesting;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import appeng.client.gui.AEBaseScreen;
import appeng.client.gui.implementations.CellWorkbenchScreen;
import appeng.client.gui.implementations.CondenserScreen;
import appeng.client.gui.implementations.DriveScreen;
import appeng.client.gui.implementations.IOPortScreen;
import appeng.client.gui.implementations.MEChestScreen;
import appeng.client.gui.implementations.PriorityScreen;
import appeng.client.gui.implementations.SkyChestScreen;
import appeng.client.gui.implementations.StorageLevelEmitterScreen;
import appeng.client.gui.implementations.WirelessAccessPointScreen;
import appeng.client.gui.me.common.MEStorageScreen;
import appeng.client.gui.me.items.CraftingTermScreen;
import appeng.client.gui.style.ScreenStyle;
import appeng.client.gui.style.StyleManager;
import appeng.menu.AEBaseMenu;
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

/**
 * The server sends the client a menu identifier, which the client then maps onto a screen using {@link MenuScreens}.
 * This class registers our screens.
 */
public final class InitScreens {

    @VisibleForTesting
    static final Map<MenuType<?>, String> MENU_STYLES = new IdentityHashMap<>();

    private InitScreens() {
    }

    public static void init(RegisterMenuScreensEvent event) {
        // spotless:off
        register(event, SkyChestMenu.TYPE, SkyChestScreen::new, "/screens/sky_chest.json");
        register(event, MEChestMenu.TYPE, MEChestScreen::new, "/screens/me_chest.json");
        register(event, WirelessAccessPointMenu.TYPE, WirelessAccessPointScreen::new, "/screens/wireless_access_point.json");
        register(event, DriveMenu.TYPE, DriveScreen::new, "/screens/drive.json");
        register(event, CondenserMenu.TYPE, CondenserScreen::new, "/screens/condenser.json");
        register(event, IOPortMenu.TYPE, IOPortScreen::new, "/screens/io_port.json");
        register(event, PriorityMenu.TYPE, PriorityScreen::new, "/screens/priority.json");
        register(event, StorageLevelEmitterMenu.TYPE, StorageLevelEmitterScreen::new, "/screens/level_emitter.json");
        register(event, CellWorkbenchMenu.TYPE, CellWorkbenchScreen::new, "/screens/cell_workbench.json");

        // Terminals
        InitScreens.<MEStorageMenu, MEStorageScreen<MEStorageMenu>>register(event,
                MEStorageMenu.TYPE,
                MEStorageScreen::new,
                "/screens/terminals/terminal.json");
        InitScreens.<MEStorageMenu, MEStorageScreen<MEStorageMenu>>register(event,
                BasicCellChestMenu.TYPE,
                MEStorageScreen::new,
                "/screens/terminals/terminal.json");
        InitScreens.<MEStorageMenu, MEStorageScreen<MEStorageMenu>>register(event,
                MEStorageMenu.PORTABLE_ITEM_CELL_TYPE,
                MEStorageScreen::new,
                "/screens/terminals/portable_item_cell.json");
        InitScreens.<MEStorageMenu, MEStorageScreen<MEStorageMenu>>register(event,
                MEStorageMenu.PORTABLE_FLUID_CELL_TYPE,
                MEStorageScreen::new,
                "/screens/terminals/portable_fluid_cell.json");
        InitScreens.<MEStorageMenu, MEStorageScreen<MEStorageMenu>>register(event,
                MEStorageMenu.WIRELESS_TYPE,
                MEStorageScreen::new,
                "/screens/terminals/wireless_terminal.json");
        InitScreens.<CraftingTermMenu, CraftingTermScreen<CraftingTermMenu>>register(event,
                CraftingTermMenu.TYPE,
                CraftingTermScreen::new,
                "/screens/terminals/crafting_terminal.json");
        InitScreens.<WirelessCraftingTermMenu, CraftingTermScreen<WirelessCraftingTermMenu>>register(event,
                WirelessCraftingTermMenu.TYPE,
                CraftingTermScreen::new,
                "/screens/terminals/crafting_terminal.json");
        // spotless:on
    }

    /**
     * Registers a screen for a given menu and ensures the given style is applied after opening the screen.
     */
    public static <M extends AEBaseMenu, U extends AEBaseScreen<M>> void register(RegisterMenuScreensEvent event,
            MenuType<M> type,
            StyledScreenFactory<M, U> factory,
            String stylePath) {
        MENU_STYLES.put(type, stylePath);
        event.<M, U>register(type, (menu, playerInv, title) -> {
            var style = StyleManager.loadStyleDoc(stylePath);

            return factory.create(menu, playerInv, title, style);
        });
    }

    /**
     * A type definition that matches the constructors of our screens, which take an additional {@link ScreenStyle}
     * argument.
     */
    @FunctionalInterface
    public interface StyledScreenFactory<T extends AbstractContainerMenu, U extends Screen & MenuAccess<T>> {
        U create(T t, Inventory pi, Component title, ScreenStyle style);
    }

}
