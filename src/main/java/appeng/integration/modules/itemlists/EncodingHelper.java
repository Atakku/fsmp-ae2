package appeng.integration.modules.itemlists;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.menu.me.common.GridInventoryEntry;
import appeng.menu.me.common.MEStorageMenu;

public final class EncodingHelper {
    private EncodingHelper() {
    }

    /**
     * Order of priority: - Craftable Items - Undamaged Items - Items the player has the most of
     */
    static final Comparator<GridInventoryEntry> ENTRY_COMPARATOR = Comparator
            .comparing(GridInventoryEntry::isCraftable)
            .thenComparing(EncodingHelper::isUndamaged)
            .thenComparing(GridInventoryEntry::getStoredAmount);

    private static Boolean isUndamaged(GridInventoryEntry entry) {
        return !(entry.getWhat() instanceof AEItemKey itemKey) || !itemKey.isDamaged();
    }

    public static boolean isSupportedCraftingRecipe(@Nullable Recipe<?> recipe) {
        if (recipe == null) {
            return false;
        }
        var recipeType = recipe.getType();

        return recipeType == RecipeType.CRAFTING
                || recipeType == RecipeType.STONECUTTING
                || recipeType == RecipeType.SMITHING;
    }

    /**
     * Compute a map from all keys in the network inventory to their position when sorted by priority. Also takes the
     * player inventory into account for any items that are not already in the grid.
     * <p/>
     * Higher means higher priority.
     */
    public static Map<AEKey, Integer> getIngredientPriorities(MEStorageMenu menu,
            Comparator<GridInventoryEntry> comparator) {
        var orderedEntries = menu.getClientRepo().getAllEntries()
                .stream()
                .sorted(comparator)
                .map(GridInventoryEntry::getWhat)
                .toList();

        var result = new HashMap<AEKey, Integer>(orderedEntries.size());
        for (int i = 0; i < orderedEntries.size(); i++) {
            result.put(orderedEntries.get(i), i);
        }

        // Also consider the player inventory, but only as the last resort
        for (var item : menu.getPlayerInventory().items) {
            var key = AEItemKey.of(item);
            if (key != null) {
                // Use -1 as lower priority than the lowest network entry (which start at 0)
                result.putIfAbsent(key, -1);
            }
        }

        return result;
    }
}
