package appeng.api.behaviors;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import appeng.api.config.Actionable;
import appeng.api.stacks.GenericStack;
import appeng.api.stacks.TLKey;
import appeng.api.stacks.TLKeyType;

/**
 * Abstraction layer over the "generic" (meaning that it can accept any TLKey in each slot) inventory used by various
 * TL2 devices. This is exposed to allow addons to adapt them for foreign capabilities/API lookups.
 */
@ApiStatus.Experimental
public interface GenericInternalInventory {
    /**
     * @return The number of slots in this inventory. Never changes.
     */
    int size();

    @Nullable
    GenericStack getStack(int slot);

    @Nullable
    TLKey getKey(int slot);

    long getAmount(int slot);

    /**
     * @return The key-specific max amount (for items this takes the max stack size into account).
     */
    long getMaxAmount(TLKey key);

    /**
     * @return The type-specific max amount (or an estimate if it depends on the stored item).
     */
    long getCapacity(TLKeyType keyType);

    boolean canInsert();

    boolean canExtract();

    void setStack(int slot, @Nullable GenericStack newStack);

    /**
     * Return true if the key would generally be allowed, ignoring the current state of the inventory.
     */
    boolean isSupportedType(TLKeyType type);

    /**
     * Return true if the key would generally be allowed, ignoring the current state of the inventory.
     */
    default boolean isSupportedType(TLKey what) {
        return isSupportedType(what.getType());
    }

    /**
     * Return true if the key is {@link #isSupportedType(TLKey) of a supported type} and would pass a potential filter
     * configured for the given slot.
     */
    boolean isAllowedIn(int slot, TLKey what);

    /**
     * Try to insert something into a given slot.
     */
    long insert(int slot, TLKey what, long amount, Actionable mode);

    /**
     * Try to extract something from a given slot.
     */
    long extract(int slot, TLKey what, long amount, Actionable mode);

    /**
     * Start a new change notifications batch, deferring change notifications.
     */
    void beginBatch();

    /**
     * Finish the current batch and send any pending notification.
     */
    void endBatch();

    /**
     * Finish the current batch and suppress any pending notification.
     */
    void endBatchSuppressed();

    /**
     * Send a change notification manually, for example because the automatic notification was suppressed.
     */
    void onChange();
}
