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

public enum ButtonToolTips implements LocalizationEnum {
    Amount("Amount: %d"),
    ActiveOnPulse("Activate once per pulse"),
    ActiveWithSignal("Active with signal"),
    ActiveWithoutSignal("Active without signal"),
    AlwaysActive("Always active"),
    Ascending("Ascending"),
    BlockPlacement("Block Placement"),
    BlockPlacementNo("Blocks will be dropped as item."),
    BlockPlacementYes("Blocks will be placed as block."),
    Clear("Clear"),
    ClearSettings("Clear Config/Settings"),
    DurationFormatDays("%sd"),
    DurationFormatHours("%sh"),
    DurationFormatMinutes("%sm"),
    DurationFormatSeconds("%ss"),
    Descending("Descending"),
    DoesntDespawn("This item won't de-spawn."),
    EmitLevelAbove("Emit when levels are above or equal to limit."),
    EmitLevelsBelow("Emit when levels are below limit."),
    EmitterMode("Crafting Emitter Mode"),
    FZIgnoreAll("Match Any"),
    FZPercent_25("Split Damage at 25%"),
    FZPercent_50("Split Damage at 50%"),
    FZPercent_75("Split Damage at 75%"),
    FZPercent_99("Split Damaged Items"),
    FilterMode("Search Filter Mode"),
    FilterModeClear("Clear on each opening."),
    FilterModeKeep("Restore previous search filter."),
    TypeFilter("Filter Types"),
    FluidSubstitutions("Fluid Substitutions"),
    FluidSubstitutionsDescDisabled("Don't use fluids."),
    FluidSubstitutionsDescEnabled(
            "Try to use fluids instead of items.\nSubstitutable ingredients are marked in green."),
    FuzzyMode("Fuzzy Comparison"),
    IOMode("Input/Output Mode"),
    InventoryTweaks("Inventory Tweaks"),
    ItemName("Item name"),
    LevelType("Level Type"),
    LevelType_Energy("Energy"),
    LevelType_Item("Item"),
    Mod("Mod"),
    MoveWhenEmpty("Move to output when empty."),
    MoveWhenFull("Move to output when full."),
    MoveWhenWorkIsDone("Move to output when work is done."),
    NoSuchMessage("No Such Message"),
    NumberOfItems("Number of items"),
    Off("Off"),
    On("On"),
    OpenGuide("Open Guide"),
    OpenGuideDetail("Get help from the TL2 guide"),
    OperationMode("Operation Mode"),
    OverlayMode("Overlay Mode"),
    OverlayModeNo("Loaded area is hidden."),
    OverlayModeYes("Shows the loaded area within the world."),
    PartitionStorage("Partition Storage"),
    PartitionStorageHint("Configures Partition based on currently stored items."),
    Read("Extract Only"),
    ReadWrite("Bi-Directional"),
    RedstoneMode("Redstone Mode"),
    ReportInaccessibleFluids("Report Inaccessible Fluids"),
    ReportInaccessibleFluidsNo("No: Only extractable fluids will be visible."),
    ReportInaccessibleFluidsYes("Yes: Fluids that cannot be extracted will be visible."),
    ReportInaccessibleItems("Report Inaccessible Items"),
    ReportInaccessibleItemsNo("No: Only extractable items will be visible."),
    ReportInaccessibleItemsYes("Yes: Items that cannot be extracted will be visible."),
    RequestableAmount("Requestable: %s"),
    SearchSettingsTooltip("Show Search Settings"),
    Serial("Serial: %d"),
    SortBy("Sort By"),
    SortOrder("Sort Order"),
    Stash("Store Items"),
    StashDesc("Return items on the crafting grid to network storage."),
    StashToPlayer("Take Items"),
    StashToPlayerDesc("Return items on the crafting grid to player inventory."),
    StoredAmount("Stored: %s"),
    TerminalSettings("Terminal Settings"),
    TerminalStyle("Terminal Style"),
    TerminalStyle_Small("Small Centered Terminal"),
    TerminalStyle_Medium("Medium Centered Terminal"),
    TerminalStyle_Tall("Tall Centered Terminal"),
    TerminalStyle_Full("Full-Height Terminal"),
    TransferDirection("Transfer Direction"),
    TransferToNetwork("Transfer data to Network"),
    TransferToStorageCell("Transfer data to Storage Cell"),
    Write("Insert Only"),
    CanInsertFrom("Can insert from %s"),
    CanExtractFrom("Can extract from %s"),
    SideTop("top"),
    SideBottom("bottom"),
    SideLeft("left"),
    SideRight("right"),
    SideFront("front"),
    SideBack("back"),
    SideAny("any side"),
    LeftClick("Left-Click"),
    MiddleClick("Middle-Click"),
    RightClick("Right-Click"),
    MouseButton("Mouse %d"),
    StoreAction("%s: Store %s"),
    SetAction("%s: Set %s"),
    ModifyAmountAction("%s: Modify Amount"),
    SupportedBy("Supported by:"),
    LinkWirelessTerminal("Link Wireless Terminals here"),
    ;

    private final String englishText;

    ButtonToolTips(String englishText) {
        this.englishText = englishText;
    }

    @Override
    public String getTranslationKey() {
        return "gui.tooltips.tl2." + name();
    }

    @Override
    public String getEnglishText() {
        return englishText;
    }

}
