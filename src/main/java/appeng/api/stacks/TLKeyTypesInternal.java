package appeng.api.stacks;

import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Preconditions;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Registry;
import net.neoforged.neoforge.registries.callback.BakeCallback;

/**
 * Manages the registry used to synchronize key spaces to the client.
 */
@ApiStatus.Internal
public final class TLKeyTypesInternal {
    @Nullable
    private static Registry<TLKeyType> registry;

    @Nullable
    private static Set<TLKeyType> allTypes;

    private TLKeyTypesInternal() {
    }

    public static Registry<TLKeyType> getRegistry() {
        Preconditions.checkState(registry != null, "TL2 isn't initialized yet.");
        return registry;
    }

    public static void setRegistry(Registry<TLKeyType> registry) {
        Preconditions.checkState(TLKeyTypesInternal.registry == null);
        TLKeyTypesInternal.registry = registry;
        registry.addCallback((BakeCallback<TLKeyType>) (ignored -> {
            var types = new HashSet<TLKeyType>();
            for (var aeKeyType : registry) {
                types.add(aeKeyType);
            }
            allTypes = Set.copyOf(types);
        }));
    }

    public static Set<TLKeyType> getAllTypes() {
        Preconditions.checkState(allTypes != null, "TL2 isn't initialized yet.");
        return allTypes;
    }

    public static void register(TLKeyType keyType) {
        Registry.register(getRegistry(), keyType.getId(), keyType);
    }
}
