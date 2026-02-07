package appeng.api.ids;

import java.util.List;
import java.util.function.Consumer;

import com.mojang.serialization.Codec;

import org.jetbrains.annotations.ApiStatus;

import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.registries.DeferredRegister;

import appeng.api.config.FuzzyMode;
import appeng.api.stacks.GenericStack;
import appeng.api.stacks.TLKeyType;
import appeng.api.util.TLColor;
import appeng.core.AppEng;
import appeng.core.definitions.TLItems;

public final class TLComponents {
    @ApiStatus.Internal
    public static final DeferredRegister<DataComponentType<?>> DR = DeferredRegister
            .create(Registries.DATA_COMPONENT_TYPE, AppEng.MOD_ID);

    private TLComponents() {
    }

    /**
     * An upgrade inventory.
     */
    public static final DataComponentType<ItemContainerContents> UPGRADES = register("upgrades",
            builder -> builder.persistent(ItemContainerContents.CODEC)
                    .networkSynchronized(ItemContainerContents.STREAM_CODEC));

    /**
     * List of TL key types enabled in a terminal
     */
    public static final DataComponentType<List<TLKeyType>> ENABLED_KEY_TYPES = register("enabled_key_types",
            builder -> builder.persistent(TLKeyType.CODEC.listOf())
                    .networkSynchronized(TLKeyType.STREAM_CODEC.apply(ByteBufCodecs.list())));

    /**
     * Encodes the link between a wireless item and a wireless access point.
     */
    public static final DataComponentType<GlobalPos> WIRELESS_LINK_TARGET = register("wireless_link_target",
            builder -> builder.persistent(GlobalPos.CODEC).networkSynchronized(GlobalPos.STREAM_CODEC));

    /**
     * Which paint item is currently selected in a color applicator.
     */
    public static final DataComponentType<TLColor> SELECTED_COLOR = register("selected_color",
            builder -> builder.persistent(TLColor.CODEC).networkSynchronized(TLColor.STREAM_CODEC));

    /**
     * Defines the fuzzy mode for a storage cell.
     */
    public static final DataComponentType<FuzzyMode> STORAGE_CELL_FUZZY_MODE = register("storage_cell_fuzzy_mode",
            builder -> builder.persistent(FuzzyMode.CODEC).networkSynchronized(FuzzyMode.STREAM_CODEC));

    /**
     * Content of a storage cell.
     */
    public static final DataComponentType<List<GenericStack>> STORAGE_CELL_INV = register("storage_cell_inv",
            builder -> builder.persistent(GenericStack.FAULT_TOLERANT_LIST_CODEC)
                    .networkSynchronized(GenericStack.STREAM_CODEC.apply(ByteBufCodecs.list())));

    /**
     * Defines partitioning for a storage cell.
     */
    public static final DataComponentType<List<GenericStack>> STORAGE_CELL_CONFIG_INV = register(
            "storage_cell_config_inv",
            builder -> builder.persistent(GenericStack.FAULT_TOLERANT_NULLABLE_LIST_CODEC)
                    .networkSynchronized(GenericStack.STREAM_CODEC.apply(ByteBufCodecs.list())));

    /**
     * The generic stack wrapped in a {@link TLItems#WRAPPED_GENERIC_STACK}
     */
    public static final DataComponentType<GenericStack> WRAPPED_STACK = register("wrapped_stack",
            builder -> builder.persistent(GenericStack.CODEC).networkSynchronized(GenericStack.STREAM_CODEC));

    /**
     * A crafting inventory.
     */
    public static final DataComponentType<ItemContainerContents> CRAFTING_INV = register("crafting_inv",
            builder -> builder.persistent(ItemContainerContents.CODEC)
                    .networkSynchronized(ItemContainerContents.STREAM_CODEC));

    public static final DataComponentType<CustomData> MISSING_CONTENT_ITEMSTACK_DATA = register(
            "missing_content_itemstack_data",
            builder -> builder.persistent(CustomData.CODEC).networkSynchronized(CustomData.STREAM_CODEC));

    public static final DataComponentType<CustomData> MISSING_CONTENT_TLKEY_DATA = register(
            "missing_content_aekey_data",
            builder -> builder.persistent(CustomData.CODEC).networkSynchronized(CustomData.STREAM_CODEC));

    public static final DataComponentType<String> MISSING_CONTENT_ERROR = register("missing_content_error",
            builder -> builder.persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8));

    private static <T> DataComponentType<T> register(String name, Consumer<DataComponentType.Builder<T>> customizer) {
        var builder = DataComponentType.<T>builder();
        customizer.accept(builder);
        var componentType = builder.build();
        DR.register(name, () -> componentType);
        return componentType;
    }
}
