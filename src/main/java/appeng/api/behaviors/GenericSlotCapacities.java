package appeng.api.behaviors;

import java.util.Map;

import com.google.common.base.Preconditions;

import org.jetbrains.annotations.ApiStatus;

import net.minecraft.world.item.Item;

import appeng.api.stacks.TLFluidKey;
import appeng.api.stacks.TLKeyType;
import appeng.util.CowMap;

/**
 * Allows custom key types to define slot capacities for pattern providers and interfaces.
 */
@ApiStatus.Experimental
public class GenericSlotCapacities {
    private static final CowMap<TLKeyType, Long> map = CowMap.identityHashMap();

    static {
        register(TLKeyType.items(), (long) Item.ABSOLUTE_MAX_STACK_SIZE);
        register(TLKeyType.fluids(), 4L * TLFluidKey.AMOUNT_BUCKET);
    }

    public static void register(TLKeyType type, Long capacity) {
        Preconditions.checkArgument(capacity >= 0, "capacity >= 0");
        map.putIfAbsent(type, capacity);
    }

    public static Map<TLKeyType, Long> getMap() {
        return map.getMap();
    }

    private GenericSlotCapacities() {
    }
}
