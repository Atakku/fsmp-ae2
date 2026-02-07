package appeng.api.behaviors;

import org.jetbrains.annotations.ApiStatus;

import appeng.api.config.Actionable;
import appeng.api.stacks.TLKey;

@ApiStatus.Experimental
@ApiStatus.NonExtendable
public interface PickupSink {
    long insert(TLKey what, long amount, Actionable mode);
}
