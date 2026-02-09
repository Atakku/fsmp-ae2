package appeng.integration.modules.itemlists;

import net.minecraft.world.item.ItemStack;

import appeng.api.util.TLColor;
import appeng.core.TLConfig;
import appeng.core.definitions.TLBlocks;
import appeng.core.definitions.TLItems;
import appeng.core.definitions.TLParts;

public final class ItemPredicates {
    private ItemPredicates() {
    }

    public static boolean shouldBeHidden(ItemStack stack) {
        if (isInternal(stack)) {
            return true;
        }

        if (!TLConfig.instance().isDebugToolsEnabled() && isDeveloperTool(stack)) {
            return true;
        }

        if (TLConfig.instance().isDisableColoredCableRecipesInRecipeViewer() && isColoredCable(stack)) {
            return true;
        }

        return false;
    }

    private static boolean isInternal(ItemStack stack) {
        return TLItems.WRAPPED_GENERIC_STACK.is(stack)
                || TLItems.MISSING_CONTENT.is(stack)
                || TLBlocks.CABLE_BUS.is(stack);
    }

    private static boolean isDeveloperTool(ItemStack stack) {
        return TLBlocks.DEBUG_CUBE_GEN.is(stack) ||
                TLBlocks.DEBUG_ITEM_GEN.is(stack) ||
                TLBlocks.DEBUG_PHANTOM_NODE.is(stack) ||
                TLItems.DEBUG_CARD.is(stack) ||
                TLItems.DEBUG_ERASER.is(stack) ||
                TLItems.DEBUG_REPLICATOR_CARD.is(stack);
    }

    private static boolean isColoredCable(ItemStack stack) {
        for (var color : TLColor.values()) {
            if (color == TLColor.TRANSPARENT) {
                continue; // Keep the Fluix variant
            }
            if (stack.getItem() == TLParts.GLASS_CABLE.item(color) ||
                    stack.getItem() == TLParts.SMART_CABLE.item(color) ||
                    stack.getItem() == TLParts.DENSE_CABLE.item(color)) {
                return true;
            }
        }
        return false;
    }
}
