package appeng.api.storage;

import appeng.api.stacks.TLKey;

@FunctionalInterface
public interface TLKeySlotFilter {
    boolean isAllowed(int slot, TLKey what);
}
