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

import org.jetbrains.annotations.Nullable;

import net.minecraft.network.chat.Component;

public enum GuiText implements LocalizationEnum {
    inventory(null, "container"), // mc's default Inventory localization.
    AdjacentToDifferentMachines("Adjacent to Different Machines"),
    And("and"),
    Or("or"),
    Automatic("Automatic"),
    Black("Black"),
    Blank("Blank"),
    Blue("Blue"),
    Brown("Brown"),
    BytesUsed("%s Bytes Used"),
    CPUs("CPU"),
    CalculatingWait("Calculating Please Wait..."),
    Cancel("Cancel"),
    CantStoreItems("Can't Store Contents!"),
    CantFitInsideStorageCell("This item does not fit in other storage cells"),
    CellWorkbench("Cell Workbench"),
    ChannelEnergyDrain("Channel Passive Drain: %s"),
    Clean("Clean"),
    CompatibleUpgrade("%s (%s)"),
    CompatibleUpgrades("Compatible Upgrades:"),
    Config("Config"),
    ConfigureVisibleTypes("Configure Visible Types"),
    CopyMode("Copy Mode"),
    CopyModeDesc("Controls if the contents of the configuration pane are cleared when you remove the cell."),
    Crafting("Crafting: %s"),
    CraftingTerminal("Crafting Terminal"),
    Crafts("Crafts"),
    CreativeTab("Theoretical Lethargics 2"),
    Cyan("Cyan"),
    Deprecated("Deprecated"),
    Drive("ME Drive"),
    ETAFormat("HH:mm:ss"),
    Efficiency("Efficiency: %s%%"),
    Empty("Empty"),
    Encoded("Encoded"),
    EnergyDrain("Passive Drain: %s"),
    EnergyGenerationCapacity("Generation Capacity: %s"),
    EnergyLevelEmitter("ME Energy Level Emitter"),
    Excluded("Excluded"),
    ExternalStorage("External Storage (%s)"),
    Fluids("Fluids"),
    Fluix("Fluix"),
    FromStorage("Available: %s"),
    Fuzzy("Fuzzy"),
    RestoredGenericSettingUpgrades("upgrades"),
    RestoredGenericSettingSettings("settings"),
    RestoredGenericSettingConfigInv("config inventory"),
    RestoredGenericSettingPriority("priority"),
    Gray("Gray"),
    Green("Green"),
    IOPort("ME IO Port"),
    IncompatibleWithCell("Incompatible with cell"),
    Included("Included"),
    Inscriber("Inscriber"),
    Installed("Installed: %s"),
    Interfaces("ME Interfaces"),
    IntrinsicEnchant("Always has at least %s"),
    InvalidNumber("Please enter a number or a mathematical expression e.g. : 3*4"),
    Items("Items"),
    LevelEmitter("ME Level Emitter"),
    LightBlue("Light Blue"),
    LightGray("Light Gray"),
    Lime("Lime"),
    Linked("Linked"),
    Magenta("Magenta"),
    MaxPower("Max Power: %s"),
    MEChest("ME Chest"),
    MENetworkStorage("ME Network Storage"),
    Missing("Missing: %s"),
    Next("Next"),
    No("No"),
    NoChannel("Missing Channel"),
    NoSecondOutput("No Secondary Output"),
    Nothing("Nothing"),
    NumberGreaterThanMaxValue("Please enter a number less than or equal to %s"),
    NumberLessThanMinValue("Please enter a number greater than or equal to %s"),
    NumberNonInteger("Must be whole number"),
    OCTunnel("OpenComputers"),
    Of("of"),
    OfSecondOutput("%1$d%% Chance for second output."),
    Orange("Orange"),
    PartialPlan("Partial Plan (Missing Ingredients)"),
    Partitioned("Partitioned"),
    Pink("Pink"),
    PowerInputRate("Energy Generation: %s"),
    PowerUsageRate("Energy Usage: %s"),
    Precise("Precise"),
    PressureTunnel("Pressure"),
    PressShiftForFullList("Press [Shift] for full list"),
    Priority("Priority"),
    PriorityExtractionHint("Extraction: Lower priority first"),
    PriorityInsertionHint("Insertion: Higher priority first"),
    Produces("Produces"),
    Purple("Purple"),
    PutAQuartzTool("Put a Quartz weapon or tool here"),
    PutAFluixBlock("Put a Fluix Block here"),
    QuartzCuttingKnife("Quartz Cutting Knife"),
    QuartzTools("Quartz Tools"),
    Red("Red"),
    Resume("Resume"),
    SelectAmount("Select Amount"),
    SerialNumber("Serial Number: %s"),
    Set("Set"),
    ShowingOf("Showing %d of %d"),
    Start("Start"),
    StorageCells("ME Storage Cells"),
    Suspend("Suspend"),
    SearchPlaceholder("Search..."),
    SearchSettingsTitle("Search Settings"),
    SearchSettingsUseInternalSearch("Use TL"),
    SearchSettingsUseExternalSearch("Use %s"),
    SearchSettingsRememberSearch("Remember last search"),
    SearchSettingsAutoFocus("Auto-Focus on open"),
    SearchSettingsSyncWithExternal("Sync with %s search"),
    SearchSettingsClearExternal("Clear %s search on open"),
    SearchSettingsReplaceWithExternal("Replace with %s search"),
    SearchTooltip("Search in Name"),
    SearchTooltipModId("Use @ to search by mod (@tl2)"),
    SearchTooltipToolTips("Use $ to search in tooltips ($looting)"),
    SearchTooltipItemId("Use * to search by id (*cell)"),
    SearchTooltipTag("Use # to search by tags (#ingot)"),
    StorageCellTooltipUpgrades("Upgrades:"),
    Stored("Stored"),
    StoredPower("Stored Power: %s"),
    StoredSize("Stored Size: %dx%dx%d"),
    Stores("Stores"),
    Terminal("Terminal"),
    TerminalSettingsTitle("Terminal Settings"),
    TerminalSettingsNotifyForFinishedJobs("Notify about finished crafting jobs (requires wireless terminal)"),
    TerminalSettingsClearGridOnClose("Automatically clear terminal grid on close (if applicable)"),
    TerminalViewCellsTooltip("View Cells"),
    ToCraft("To Craft: %s"),
    TransformTypeThrowInFluid("Throw in %s"),
    TransparentFacades("Transparent Facades"),
    TransparentFacadesHint("Controls visibility of facades while the network tool is on your toolbar."),
    Types("Types"),
    Unattached("Unattached"),
    Unformatted("Unformatted"),
    Unlinked("Unlinked"),
    UpgradeToolbelt("Upgrade Toolbelt"),
    White("White"),
    Wireless("Wireless Access Point"),
    WirelessTerminal("Wireless Term"),
    With("with"),
    Yellow("Yellow"),
    Yes("Yes");

    private final String root;

    @Nullable
    private final String englishText;

    private final Component text;

    GuiText(@Nullable String englishText) {
        this.root = "gui.tl2";
        this.englishText = englishText;
        this.text = Component.translatable(getTranslationKey());
    }

    GuiText(@Nullable String englishText, String r) {
        this.root = r;
        this.englishText = englishText;
        this.text = Component.translatable(getTranslationKey());
    }

    @Nullable
    public String getEnglishText() {
        return englishText;
    }

    @Override
    public String getTranslationKey() {
        return this.root + '.' + name();
    }

    public String getLocal() {
        return text.getString();
    }
}
