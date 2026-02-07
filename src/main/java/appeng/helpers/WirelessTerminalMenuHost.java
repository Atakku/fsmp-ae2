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

package appeng.helpers;

import java.util.function.BiConsumer;

import org.apache.commons.lang3.mutable.MutableObject;
import org.jetbrains.annotations.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import appeng.api.config.Actionable;
import appeng.api.features.HotkeyAction;
import appeng.api.implementations.blockentities.IWirelessAccessPoint;
import appeng.api.implementations.menuobjects.IPortableTerminal;
import appeng.api.implementations.menuobjects.ItemMenuHost;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridNode;
import appeng.api.networking.security.IActionHost;
import appeng.api.stacks.TLKey;
import appeng.api.storage.ILinkStatus;
import appeng.api.storage.MEStorage;
import appeng.api.storage.StorageHelper;
import appeng.api.storage.SupplierStorage;
import appeng.api.util.IConfigManager;
import appeng.api.util.KeyTypeSelection;
import appeng.api.util.KeyTypeSelectionHost;
import appeng.blockentity.networking.WirelessAccessPointBlockEntity;
import appeng.core.localization.PlayerMessages;
import appeng.items.contents.StackDependentSupplier;
import appeng.items.tools.powered.WirelessTerminalItem;
import appeng.me.helpers.PlayerSource;
import appeng.me.storage.NullInventory;
import appeng.menu.ISubMenu;
import appeng.menu.locator.ItemMenuHostLocator;

public class WirelessTerminalMenuHost<T extends WirelessTerminalItem> extends ItemMenuHost<T>
        implements IPortableTerminal, IActionHost, KeyTypeSelectionHost {

    private final BiConsumer<Player, ISubMenu> returnToMainMenu;
    @Nullable
    private IWirelessAccessPoint currentAccessPoint;
    private final MEStorage storage;
    private ILinkStatus linkStatus = ILinkStatus.ofDisconnected();

    public WirelessTerminalMenuHost(T item, Player player, ItemMenuHostLocator locator,
            BiConsumer<Player, ISubMenu> returnToMainMenu) {
        super(item, player, locator);
        this.returnToMainMenu = returnToMainMenu;

        this.storage = new SupplierStorage(new StackDependentSupplier<>(
                this::getItemStack, this::getStorageFromStack));

        updateConnectedAccessPoint();
        updateLinkStatus();
    }

    @Override
    public ILinkStatus getLinkStatus() {
        return linkStatus;
    }

    @Nullable
    private MEStorage getStorageFromStack(ItemStack stack) {
        var targetGrid = getLinkedGrid(stack);
        if (targetGrid != null) {
            return targetGrid.getStorageService().getInventory();
        }
        return NullInventory.of();
    }

    @Nullable
    private IGrid getLinkedGrid(ItemStack stack) {
        return getItem().getLinkedGrid(stack, getPlayer().level(), null);
    }

    @Override
    public MEStorage getInventory() {
        return this.storage;
    }

    @Override
    public IConfigManager getConfigManager() {
        return getItem().getConfigManager(this::getItemStack);
    }

    @Override
    public KeyTypeSelection getKeyTypeSelection() {
        return KeyTypeSelection.forStack(getItemStack(), keyType -> true);
    }

    @Override
    public IGridNode getActionableNode() {
        if (this.currentAccessPoint != null) {
            return this.currentAccessPoint.getActionableNode();
        }
        return null;
    }

    protected void updateConnectedAccessPoint() {
        this.currentAccessPoint = null;
        var targetGrid = getLinkedGrid(getItemStack());
        if (targetGrid != null) {
            this.currentAccessPoint = targetGrid.getMachines(WirelessAccessPointBlockEntity.class).iterator().next();
        }

    }

    @Override
    public void tick() {
        updateConnectedAccessPoint();
        updateLinkStatus();
    }

    /**
     * Recalculate the current {@linkplain #getLinkStatus() link status}.
     */
    protected void updateLinkStatus() {
        if (currentAccessPoint != null) {
            this.linkStatus = ILinkStatus.ofConnected();
        } else {
            MutableObject<Component> errorHolder = new MutableObject<>();
            if (getItem().getLinkedGrid(getItemStack(), getPlayer().level(), errorHolder::setValue) == null) {
                this.linkStatus = ILinkStatus.ofDisconnected(errorHolder.getValue());
            } else {
                // If a grid exists, but no access point, we're out of range
                this.linkStatus = ILinkStatus.ofDisconnected(PlayerMessages.OutOfRange.text());
            }
        }
    }

    @Override
    public void returnToMainMenu(Player player, ISubMenu subMenu) {
        returnToMainMenu.accept(player, subMenu);
    }

    @Override
    public ItemStack getMainMenuIcon() {
        return getItemStack();
    }

    public String getCloseHotkey() {
        return HotkeyAction.WIRELESS_TERMINAL;
    }

    @Override
    public long insert(Player player, TLKey what, long amount, Actionable mode) {
        // We do not know the real link-status on the client-side
        if (isClientSide()) {
            return 0;
        }

        if (getLinkStatus().connected()) {
            return StorageHelper.insert(getInventory(), what, amount, new PlayerSource(player), mode);
        } else {
            var statusText = getLinkStatus().statusDescription();
            if (statusText != null && !mode.isSimulate()) {
                player.displayClientMessage(statusText, false);
            }
            return 0;
        }
    }
}
