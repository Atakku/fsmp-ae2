package appeng.items.storage;

import java.util.function.Supplier;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import appeng.api.ids.TLItemIds;

public record StorageTier(int index, String namePrefix, int bytes, Supplier<Item> componentSupplier) {
    public static final StorageTier SIZE_1K = new StorageTier(1, "1k", 1024,
            () -> BuiltInRegistries.ITEM.get(TLItemIds.COMPONENT_1K));
    public static final StorageTier SIZE_4K = new StorageTier(2, "4k", 4096,
            () -> BuiltInRegistries.ITEM.get(TLItemIds.COMPONENT_4K));
    public static final StorageTier SIZE_16K = new StorageTier(3, "16k", 16384,
            () -> BuiltInRegistries.ITEM.get(TLItemIds.COMPONENT_16K));
    public static final StorageTier SIZE_64K = new StorageTier(4, "64k", 65536,
            () -> BuiltInRegistries.ITEM.get(TLItemIds.COMPONENT_64K));
}
