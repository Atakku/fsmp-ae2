package appeng.hotkeys;

import static appeng.api.features.HotkeyAction.WIRELESS_TERMINAL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.world.level.ItemLike;

import appeng.api.features.HotkeyAction;
import appeng.core.AppEng;
import appeng.core.definitions.TLItems;

/**
 * Registry of {@link HotkeyAction}
 */
public class HotkeyActions {
    public static final Map<String, List<HotkeyAction>> REGISTRY = new HashMap<>();

    public static void init() {
        register(TLItems.WIRELESS_TERMINAL,
                (player, locator) -> TLItems.WIRELESS_TERMINAL.get().openFromInventory(player, locator),
                WIRELESS_TERMINAL);
        register(TLItems.WIRELESS_CRAFTING_TERMINAL,
                (player, locator) -> TLItems.WIRELESS_CRAFTING_TERMINAL.get().openFromInventory(player, locator),
                WIRELESS_TERMINAL);
    }

    /**
     * a convenience Helper for registering Hotkeys for both the Inventory and Curios (if applicable)
     */
    public static void register(ItemLike item, InventoryHotkeyAction.Opener opener, String id) {
        register(new InventoryHotkeyAction(item, opener), id);
        register(new CuriosHotkeyAction(item, opener), id);
    }

    /**
     * see {@link HotkeyAction#register(HotkeyAction, String)}
     */
    public static synchronized void register(HotkeyAction hotkeyAction, String id) {
        if (REGISTRY.containsKey(id)) {
            REGISTRY.get(id).addFirst(hotkeyAction);
        } else {
            REGISTRY.put(id, new ArrayList<>(List.of(hotkeyAction)));
            AppEng.instance().registerHotkey(id);
        }
    }
}
