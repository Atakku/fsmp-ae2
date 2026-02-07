package appeng.api.behaviors;

import org.jetbrains.annotations.ApiStatus;

import appeng.api.networking.security.IActionSource;
import appeng.api.networking.storage.IStorageService;
import appeng.api.stacks.TLItemKey;
import appeng.api.stacks.TLKey;
import appeng.api.stacks.TLKeyType;
import appeng.util.prioritylist.IPartitionList;

/**
 * Context for import and export bus transfer operations.
 */
@ApiStatus.Experimental
@ApiStatus.NonExtendable
public interface StackTransferContext {

    IStorageService getInternalStorage();

    IActionSource getActionSource();

    int getOperationsRemaining();

    void setOperationsRemaining(int operationsRemaining);

    boolean hasOperationsLeft();

    boolean hasDoneWork();

    boolean isKeyTypeEnabled(TLKeyType space);

    boolean isInFilter(TLKey key);

    IPartitionList getFilter();

    void setInverted(boolean inverted);

    boolean isInverted();

    boolean canInsert(TLItemKey what, long amount);

    void reduceOperationsRemaining(long inserted);
}
