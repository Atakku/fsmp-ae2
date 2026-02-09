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

package appeng.core;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.EnumValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

import appeng.api.config.TerminalStyle;
import appeng.util.Platform;

public final class TLConfig {

    private final ClientConfig client = new ClientConfig();
    private final CommonConfig common = new CommonConfig();

    private static TLConfig instance;

    private TLConfig(ModContainer container) {
        container.registerConfig(ModConfig.Type.CLIENT, client.spec);
        container.registerConfig(ModConfig.Type.COMMON, common.spec);
        container.getEventBus().addListener((ModConfigEvent.Loading evt) -> {
            if (evt.getConfig().getSpec() == common.spec) {
                common.sync();
            }
        });
        container.getEventBus().addListener((ModConfigEvent.Reloading evt) -> {
            if (evt.getConfig().getSpec() == common.spec) {
                common.sync();
            }
        });
    }

    public static void register(ModContainer container) {
        if (!container.getModId().equals(AppEng.MOD_ID)) {
            throw new IllegalArgumentException();
        }
        instance = new TLConfig(container);
    }

    public static TLConfig instance() {
        return instance;
    }

    public boolean isSearchModNameInTooltips() {
        return client.searchModNameInTooltips.get();
    }

    public void setSearchModNameInTooltips(boolean enable) {
        if (enable != client.searchModNameInTooltips.getAsBoolean()) {
            client.searchModNameInTooltips.set(enable);
            client.spec.save();
        }
    }

    public boolean isUseExternalSearch() {
        return client.useExternalSearch.get();
    }

    public void setUseExternalSearch(boolean enable) {
        if (enable != client.useExternalSearch.getAsBoolean()) {
            client.useExternalSearch.set(enable);
            client.spec.save();
        }
    }

    public boolean isClearExternalSearchOnOpen() {
        return client.clearExternalSearchOnOpen.get();
    }

    public void setClearExternalSearchOnOpen(boolean enable) {
        if (enable != client.clearExternalSearchOnOpen.getAsBoolean()) {
            client.clearExternalSearchOnOpen.set(enable);
            client.spec.save();
        }
    }

    public boolean isRememberLastSearch() {
        return client.rememberLastSearch.get();
    }

    public void setRememberLastSearch(boolean enable) {
        if (enable != client.rememberLastSearch.getAsBoolean()) {
            client.rememberLastSearch.set(enable);
            client.spec.save();
        }
    }

    public boolean isAutoFocusSearch() {
        return client.autoFocusSearch.get();
    }

    public void setAutoFocusSearch(boolean enable) {
        if (enable != client.autoFocusSearch.getAsBoolean()) {
            client.autoFocusSearch.set(enable);
            client.spec.save();
        }
    }

    public boolean isSyncWithExternalSearch() {
        return client.syncWithExternalSearch.get();
    }

    public void setSyncWithExternalSearch(boolean enable) {
        if (enable != client.syncWithExternalSearch.getAsBoolean()) {
            client.syncWithExternalSearch.set(enable);
            client.spec.save();
        }
    }

    public TerminalStyle getTerminalStyle() {
        return client.terminalStyle.get();
    }

    public void setTerminalStyle(TerminalStyle setting) {
        if (setting != client.terminalStyle.get()) {
            client.terminalStyle.set(setting);
            client.spec.save();
        }
    }

    // Getters
    public boolean isDebugToolsEnabled() {
        return common.debugTools.get();
    }

    public boolean isEnableEffects() {
        return client.enableEffects.getAsBoolean();
    }

    public boolean isUseLargeFonts() {
        return client.useLargeFonts.getAsBoolean();
    }

    public boolean isDisableColoredCableRecipesInRecipeViewer() {
        return client.disableColoredCableRecipesInRecipeViewer.getAsBoolean();
    }

    public boolean isExposeNetworkInventoryToEmi() {
        return client.exposeNetworkInventoryToEmi.getAsBoolean();
    }

    public boolean isShowDebugGuiOverlays() {
        return client.debugGuiOverlays.get();
    }

    public void setShowDebugGuiOverlays(boolean enable) {
        if (enable != client.debugGuiOverlays.getAsBoolean()) {
            client.debugGuiOverlays.set(enable);
            client.spec.save();
        }
    }

    public boolean isBlockUpdateLogEnabled() {
        return common.blockUpdateLog.get();
    }

    public boolean isChunkLoggerTraceEnabled() {
        return common.chunkLoggerTrace.get();
    }

    /**
     * @return True if an in-world preview of parts and facade placement should be shown when holding one in hand.
     */
    public boolean isPlacementPreviewEnabled() {
        return client.showPlacementPreview.get();
    }

    // Tooltip settings

    /**
     * Show upgrade inventory in tooltips of storage cells and similar devices.
     */
    public boolean isTooltipShowCellUpgrades() {
        return client.tooltipShowCellUpgrades.get();
    }

    /**
     * Show part of the content in tooltips of storage cells and similar devices.
     */
    public boolean isTooltipShowCellContent() {
        return client.tooltipShowCellContent.get();
    }

    /**
     * How much of the content to show in storage cellls and similar devices.
     */
    public int getTooltipMaxCellContentShown() {
        return client.tooltipMaxCellContentShown.get();
    }

    public boolean isClearGridOnClose() {
        return client.clearGridOnClose.get();
    }

    public void setClearGridOnClose(boolean enabled) {
        if (enabled != client.clearGridOnClose.getAsBoolean()) {
            client.clearGridOnClose.set(enabled);
            client.spec.save();
        }
    }

    public int getTerminalMargin() {
        return client.terminalMargin.get();
    }

    public void save() {
        common.spec.save();
        client.spec.save();
    }

    private static class ClientConfig {
        private final ModConfigSpec spec;

        // Misc
        public final BooleanValue enableEffects;
        public final BooleanValue useLargeFonts;
        public final BooleanValue disableColoredCableRecipesInRecipeViewer;
        public final BooleanValue exposeNetworkInventoryToEmi;
        public final BooleanValue debugGuiOverlays;
        public final BooleanValue showPlacementPreview;

        // Terminal Settings
        public final EnumValue<TerminalStyle> terminalStyle;
        public final BooleanValue clearGridOnClose;
        public final IntValue terminalMargin;

        // Search Settings
        public final BooleanValue searchModNameInTooltips;
        public final BooleanValue useExternalSearch;
        public final BooleanValue clearExternalSearchOnOpen;
        public final BooleanValue syncWithExternalSearch;
        public final BooleanValue rememberLastSearch;
        public final BooleanValue autoFocusSearch;

        // Tooltip settings
        public final BooleanValue tooltipShowCellUpgrades;
        public final BooleanValue tooltipShowCellContent;
        public final IntValue tooltipMaxCellContentShown;

        public ClientConfig() {
            var builder = new ModConfigSpec.Builder();

            builder.push("recipeViewers");
            this.disableColoredCableRecipesInRecipeViewer = define(builder, "disableColoredCableRecipesInRecipeViewer",
                    true);
            this.exposeNetworkInventoryToEmi = define(builder, "provideNetworkInventoryToEmi", false,
                    "Expose the full network inventory to EMI, which might cause performance problems.");
            builder.pop();

            builder.push("client");
            this.enableEffects = define(builder, "enableEffects", true);
            this.useLargeFonts = define(builder, "useTerminalUseLargeFont", false);
            this.debugGuiOverlays = define(builder, "showDebugGuiOverlays", false, "Show debugging GUI overlays");
            this.showPlacementPreview = define(builder, "showPlacementPreview", true,
                    "Show a preview of part and facade placement");
            builder.pop();

            var terminals = builder.push("terminals");
            this.terminalStyle = defineEnum(terminals, "terminalStyle", TerminalStyle.SMALL);
            this.clearGridOnClose = define(builder, "clearGridOnClose", false,
                    "Automatically clear the crafting/encoding grid when closing the terminal");
            this.terminalMargin = define(builder, "terminalMargin", 25,
                    "The vertical margin to apply when sizing terminals. Used to make room for centered item mod search bars");
            builder.pop();

            // Search Settings
            builder.push("search");
            this.searchModNameInTooltips = define(builder, "searchModNameInTooltips", false,
                    "Should the mod name be included when searching in tooltips.");
            this.useExternalSearch = define(builder, "useExternalSearch", false,
                    "Replaces TLs own search with the search of REI or JEI");
            this.clearExternalSearchOnOpen = define(builder, "clearExternalSearchOnOpen", true,
                    "When using useExternalSearch, clears the search when the terminal opens");
            this.syncWithExternalSearch = define(builder, "syncWithExternalSearch", true,
                    "When REI/JEI is installed, automatically set the TL or REI/JEI search text when either is changed while the terminal is open");
            this.rememberLastSearch = define(builder, "rememberLastSearch", true,
                    "Remembers the last search term and restores it when the terminal opens");
            this.autoFocusSearch = define(builder, "autoFocusSearch", false,
                    "Automatically focuses the search field when the terminal opens");
            builder.pop();

            builder.push("tooltips");
            this.tooltipShowCellUpgrades = define(builder, "showCellUpgrades", true,
                    "Show installed upgrades in the tooltips of storage cells, color applicators and matter cannons");
            this.tooltipShowCellContent = define(builder, "showCellContent", true,
                    "Show a preview of the content in the tooltips of storage cells, color applicators and matter cannons");
            this.tooltipMaxCellContentShown = define(builder, "maxCellContentShown", 5, 1, 32,
                    "The maximum number of content entries to show in the tooltip of storage cells, color applicators and matter cannons");
            builder.pop();

            this.spec = builder.build();
        }

    }

    private static class CommonConfig {
        private final ModConfigSpec spec;

        // Misc
        public final BooleanValue debugTools;

        // Logging
        public final BooleanValue blockUpdateLog;
        public final BooleanValue debugLog;
        public final BooleanValue gridLog;
        public final BooleanValue chunkLoggerTrace;

        public CommonConfig() {
            var builder = new ModConfigSpec.Builder();

            builder.push("general");
            debugTools = define(builder, "unsupportedDeveloperTools", Platform.isDevelopmentEnvironment());
            builder.pop();

            builder.push("logging");
            blockUpdateLog = define(builder, "blockUpdateLog", false);
            debugLog = define(builder, "debugLog", false);
            gridLog = define(builder, "gridLog", false);
            chunkLoggerTrace = define(builder, "chunkLoggerTrace", false,
                    "Enable stack trace logging for the chunk loading debug command");
            builder.pop();
            spec = builder.build();
        }

        public void sync() {
            TLLog.setDebugLogEnabled(debugLog.get());
            TLLog.setGridLogEnabled(gridLog.get());
        }
    }

    private static BooleanValue define(ModConfigSpec.Builder builder, String name, boolean defaultValue,
            String comment) {
        builder.comment(comment);
        return define(builder, name, defaultValue);
    }

    private static BooleanValue define(ModConfigSpec.Builder builder, String name, boolean defaultValue) {
        return builder.define(name, defaultValue);
    }

    private static IntValue define(ModConfigSpec.Builder builder, String name, int defaultValue, String comment) {
        builder.comment(comment);
        return define(builder, name, defaultValue);
    }

    private static IntValue define(ModConfigSpec.Builder builder, String name, int defaultValue, int min, int max,
            String comment) {
        builder.comment(comment);
        return define(builder, name, defaultValue, min, max);
    }

    private static IntValue define(ModConfigSpec.Builder builder, String name, int defaultValue, int min, int max) {
        return builder.defineInRange(name, defaultValue, min, max);
    }

    private static IntValue define(ModConfigSpec.Builder builder, String name, int defaultValue) {
        return define(builder, name, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private static <T extends Enum<T>> EnumValue<T> defineEnum(ModConfigSpec.Builder builder, String name,
            T defaultValue) {
        return builder.defineEnum(name, defaultValue);
    }

}
