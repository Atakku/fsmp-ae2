package appeng.api.storage;

import appeng.api.stacks.TLKey;

class NoOpKeyFilter implements TLKeyFilter {
    static NoOpKeyFilter INSTANCE = new NoOpKeyFilter();

    @Override
    public boolean matches(TLKey what) {
        return true;
    }

}
