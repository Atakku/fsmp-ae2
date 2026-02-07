package appeng.api.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import appeng.api.ids.TLComponents;
import appeng.api.stacks.TLKeyType;
import appeng.api.stacks.TLKeyTypes;

/**
 * Helper class to store the selection of key types.
 */
public class KeyTypeSelection {
    private final Listener listener;
    private final Map<TLKeyType, Boolean> keyTypes = new LinkedHashMap<>();

    public KeyTypeSelection(Runnable listener, Predicate<TLKeyType> allowKeyType) {
        this(selection -> listener.run(), allowKeyType);
    }

    public KeyTypeSelection(Listener listener, Predicate<TLKeyType> allowKeyType) {
        this.listener = listener;
        for (var keyType : TLKeyTypes.getAll()) {
            if (allowKeyType.test(keyType)) {
                keyTypes.put(keyType, true);
            }
        }
    }

    public static KeyTypeSelection forStack(ItemStack stack, Predicate<TLKeyType> allowKeyType) {
        var out = new KeyTypeSelection(selection -> {
            stack.set(TLComponents.ENABLED_KEY_TYPES, selection.enabledSet());
        }, allowKeyType);
        var selected = stack.get(TLComponents.ENABLED_KEY_TYPES);
        if (selected != null) {
            out.setEnabledSet(selected);
        }
        return out;
    }

    public void setEnabled(TLKeyType type, boolean enabled) {
        if (!keyTypes.containsKey(type)) {
            throw new IllegalArgumentException("Key type " + type + " is not allowed.");
        }

        // Disabling all key types is not allowed
        if (!enabled && enabledSet().size() <= 1) {
            return;
        }

        keyTypes.put(type, enabled);
        listener.onKeyTypeSelectionChanged(this);
    }

    public boolean isEnabled(TLKeyType type) {
        if (!keyTypes.containsKey(type)) {
            throw new IllegalArgumentException("Key type " + type + " is not allowed.");
        }

        return keyTypes.get(type);
    }

    public Map<TLKeyType, Boolean> enabled() {
        return new LinkedHashMap<>(keyTypes);
    }

    public List<TLKeyType> enabledSet() {
        return keyTypes.entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).toList();
    }

    public void setEnabledSet(List<TLKeyType> selected) {
        for (var entry : keyTypes.entrySet()) {
            entry.setValue(selected.contains(entry.getKey()));
        }
    }

    public Predicate<TLKeyType> enabledPredicate() {
        return keyType -> keyTypes.getOrDefault(keyType, Boolean.FALSE);
    }

    public void writeToNBT(CompoundTag tag) {
        ListTag enabledKeyTypesTag = new ListTag();
        for (var entry : keyTypes.entrySet()) {
            if (entry.getValue()) {
                enabledKeyTypesTag.add(StringTag.valueOf(entry.getKey().getId().toString()));
            }
        }
        tag.put("enabledKeyTypes", enabledKeyTypesTag);
    }

    public void readFromNBT(CompoundTag tag, HolderLookup.Provider registries) {
        for (var entry : keyTypes.entrySet()) {
            entry.setValue(false);
        }
        ListTag enabledKeyTypesTag = tag.getList("enabledKeyTypes", 8);
        for (int i = 0; i < enabledKeyTypesTag.size(); i++) {
            try {
                var keyType = TLKeyTypes.get(ResourceLocation.parse(enabledKeyTypesTag.getString(i)));
                if (keyTypes.containsKey(keyType)) {
                    keyTypes.put(keyType, true);
                }
            } catch (IllegalArgumentException e) {
                // Ignore invalid key types
            }
        }

        // Make sure that one type is always enabled
        if (enabledSet().isEmpty()) {
            for (var entry : keyTypes.entrySet()) {
                entry.setValue(true);
                break;
            }
        }
    }

    @FunctionalInterface
    public interface Listener {
        void onKeyTypeSelectionChanged(KeyTypeSelection keyTypeSelection);
    }
}
