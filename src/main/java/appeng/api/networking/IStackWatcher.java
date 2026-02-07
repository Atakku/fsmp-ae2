package appeng.api.networking;

import org.jetbrains.annotations.ApiStatus;

import appeng.api.networking.storage.IStorageWatcherNode;
import appeng.api.stacks.TLKey;

/**
 * DO NOT IMPLEMENT. Will be injected when adding an {@link IStorageWatcherNode} to a grid.
 */
@ApiStatus.NonExtendable
public interface IStackWatcher {
    /**
     * Request that ALL changes be broadcast to this watcher.
     *
     * @param watchAll true to enable watching all stacks
     */
    void setWatchAll(boolean watchAll);

    /**
     * Add a specific {@link TLKey} to watch.
     *
     * Supports multiple values, duplicate ones will not be added.
     */
    void add(TLKey stack);

    /**
     * Remove a specific {@link TLKey} from the watcher.
     */
    void remove(TLKey stack);

    /**
     * Removes all watched stacks and resets the watcher to a clean state.
     */
    void reset();
}
