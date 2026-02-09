package appeng.datagen.providers.localization;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import appeng.core.definitions.TLBlocks;
import appeng.core.definitions.TLItems;
import appeng.core.localization.ButtonToolTips;
import appeng.core.localization.GuiText;
import appeng.core.localization.InGameTooltip;
import appeng.core.localization.ItemModText;
import appeng.core.localization.LocalizationEnum;
import appeng.core.localization.PlayerMessages;
import appeng.datagen.providers.ITL2DataProvider;
import appeng.integration.modules.emi.EmiText;
import appeng.integration.modules.igtooltip.TooltipIds;

public class LocalizationProvider implements ITL2DataProvider {
    private final Map<String, String> localizations = new HashMap<>();

    private final DataGenerator generator;

    private boolean wasSaved = false;

    public LocalizationProvider(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public final CompletableFuture<?> run(CachedOutput cache) {
        for (var block : TLBlocks.getBlocks()) {
            add("block.tl2." + block.id().getPath(), block.getEnglishName());
        }
        for (var item : TLItems.getItems()) {
            add("item.tl2." + item.id().getPath(), item.getEnglishName());
        }

        addEnum(GuiText.class);
        addEnum(ButtonToolTips.class);
        addEnum(PlayerMessages.class);
        addEnum(InGameTooltip.class);
        addEnum(ItemModText.class);
        addEnum(EmiText.class);

        generateJadeLocalizations();
        generateLocalizations();

        return save(cache, localizations);
    }

    private void generateJadeLocalizations() {
        addJadeProviderDisplayName(TooltipIds.DEBUG, "TL2 Debug Info");
        addJadeProviderDisplayName(TooltipIds.GRID_NODE_STATE, "TL2 Network State");
        addJadeProviderDisplayName(TooltipIds.PART_NAME, "TL2 Part Name");
        addJadeProviderDisplayName(TooltipIds.PART_ICON, "TL2 Part Icon");
        addJadeProviderDisplayName(TooltipIds.PART_MOD_NAME, "TL2 Mod Name");
        addJadeProviderDisplayName(TooltipIds.PART_TOOLTIP, "TL2 Part Tooltip");
    }

    private void addJadeProviderDisplayName(ResourceLocation providerId, String name) {
        add("config.jade.plugin_" + providerId.getNamespace() + "." + providerId.getPath(), name);
    }

    public <T extends Enum<T> & LocalizationEnum> void addEnum(Class<T> localizedEnum) {
        for (var enumConstant : localizedEnum.getEnumConstants()) {
            add(enumConstant.getTranslationKey(), enumConstant.getEnglishText());
        }
    }

    public Component component(String key, String text) {
        add(key, text);
        return Component.translatable(key);
    }

    public void add(String key, String text) {
        Preconditions.checkState(!wasSaved, "Cannot add more translations after they were already saved");
        var previous = localizations.put(key, text);
        if (previous != null) {
            throw new IllegalStateException("Localization key " + key + " is already translated to: " + previous);
        }
    }

    private void generateLocalizations() {
        add("tl2.permission_denied", "You lack permission to access this.");
        add("commands.tl2.ChunkLoggerOff", "Chunk Logging is now off");
        add("commands.tl2.ChunkLoggerOn", "Chunk Logging is now on");
        add("commands.tl2.permissions", "You do not have adequate permissions to run this command.");
        add("commands.tl2.usage",
                "Commands provided by Theoretical Lethargics 2 - use /tl2 list for a list, and /tl2 help _____ for help with a command.");
        add("key.tl2.category", "Theoretical Lethargics 2");
        add("key.tl2.wireless_terminal", "Open Wireless Terminal");
        add("key.tl2.guide", "Open Guide for Items");
        add("key.tl2.mouse_wheel_item_modifier", "Modifier for Mouse-Wheel Items");
        add("key.tl2.part_placement_opposite", "Place Parts on Opposite Side");
        add("key.toggle_focus.desc", "Toggle search box focus");
        add("stat.tl2.items_extracted", "Items extracted from ME Storage");
        add("stat.tl2.items_inserted", "Items added to ME Storage");
        add("theoneprobe.tl2.channels", "%1$d Channels");
        add("theoneprobe.tl2.channels_of", "%1$d of %2$d Channels");
        add("theoneprobe.tl2.contains", "Contains");
        add("theoneprobe.tl2.crafting", "Crafting: %1$s");
        add("theoneprobe.tl2.device_missing_channel", "Device Missing Channel");
        add("theoneprobe.tl2.device_offline", "Device Offline");
        add("theoneprobe.tl2.device_online", "Device Online");
        add("theoneprobe.tl2.locked", "Locked");
        add("theoneprobe.tl2.showing", "Showing");
        add("theoneprobe.tl2.unlocked", "Unlocked");
    }

    private CompletableFuture<?> save(CachedOutput cache, Map<String, String> localizations) {
        wasSaved = true;

        var path = this.generator.getPackOutput().getOutputFolder().resolve("assets/tl2/lang/en_us.json");

        // Dump the translation in ascending order
        var sorted = new TreeMap<>(localizations);
        var jsonLocalization = new JsonObject();
        for (var entry : sorted.entrySet()) {
            jsonLocalization.addProperty(entry.getKey(), entry.getValue());
        }

        return DataProvider.saveStable(cache, jsonLocalization, path);
    }

    @Override
    public String getName() {
        return "Localization (en_us)";
    }
}
