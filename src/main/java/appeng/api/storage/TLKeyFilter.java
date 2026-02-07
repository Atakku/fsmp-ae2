package appeng.api.storage;

import appeng.api.stacks.TLKey;

@FunctionalInterface
public interface TLKeyFilter {
    static TLKeyFilter none() {
        return NoOpKeyFilter.INSTANCE;
    }

    boolean matches(TLKey what);
}
