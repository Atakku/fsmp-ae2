/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2014, AlgorithmX2, All rights reserved.
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

package appeng.menu.implementations;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

import appeng.api.config.Settings;
import appeng.api.util.IConfigManager;
import appeng.client.gui.implementations.InterfaceScreen;
import appeng.helpers.InterfaceLogicHost;
import appeng.menu.SlotSemantics;
import appeng.menu.slot.AppEngSlot;
import appeng.menu.slot.FakeSlot;

/**
 * @see InterfaceScreen
 */
public class InterfaceMenu extends UpgradeableMenu<InterfaceLogicHost> {
    public static final MenuType<InterfaceMenu> TYPE = MenuTypeBuilder
            .create(InterfaceMenu::new, InterfaceLogicHost.class)
            .build("interface");

    public InterfaceMenu(MenuType<? extends InterfaceMenu> menuType, int id, Inventory ip, InterfaceLogicHost host) {
        super(menuType, id, ip, host);

        var logic = host.getInterfaceLogic();

        var config = logic.getConfig().createMenuWrapper();
        for (int x = 0; x < config.size(); x++) {
            this.addSlot(new FakeSlot(config, x), SlotSemantics.CONFIG);
        }

        var storage = logic.getStorage().createMenuWrapper();
        for (int x = 0; x < storage.size(); x++) {
            this.addSlot(new AppEngSlot(storage, x), SlotSemantics.STORAGE);
        }
    }

    @Override
    protected void loadSettingsFromHost(IConfigManager cm) {
        this.setFuzzyMode(cm.getSetting(Settings.FUZZY_MODE));
    }
}
