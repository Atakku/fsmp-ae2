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

package appeng.core.localization;

public enum PlayerMessages implements LocalizationEnum {
    ChestCannotReadStorageCell("ME Chest cannot read storage cell."),
    ChannelModeSet("Channel mode set to %s. Updated %d grids."),
    ChannelModeCurrent("Current channel mode: %s"),
    DeviceNotLinked("Device is not linked."),
    LinkedNetworkNotFound("Linked network cannot be found"),
    Origin("Origin"),
    OutOfRange("Wireless Out Of Range."),
    Owner("Owner"),
    PlayerConnected("%s [Connected]"),
    PlayerDisconnected("%s [Disconnected]"),
    Size("Size"),
    Source("Source"),
    Unknown("Unknown"),
    When("When"),
    TestWorldNotInCreativeMode("Command can only be used in creative mode."),
    TestWorldNotInSuperflat("A test world can only be set up in a Superflat world!"),
    TestWorldSetupComplete("Test world setup completed in %s"),
    TestWorldSetupFailed("Setting up the test world failed: %s"),
    isNowLocked("Monitor is now Locked."),
    isNowUnlocked("Monitor is now Unlocked."),
    OnlyEmptyCellsCanBeDisassembled("Only empty storage cells can be disassembled."),
    UnsupportedUpgrade("This upgrade is not supported by this machine."),
    MaxUpgradesOfTypeInstalled("No further upgrade cards of this type can be installed."),
    MaxUpgradesInstalled("The upgrade capacity of this machine has been reached."),
    UnknownHotkey("Unknown Hotkey: "),
    ;

    private final String englishText;

    PlayerMessages(String englishText) {
        this.englishText = englishText;
    }

    @Override
    public String getEnglishText() {
        return englishText;
    }

    @Override
    public String getTranslationKey() {
        return "chat.tl2." + name();
    }
}
