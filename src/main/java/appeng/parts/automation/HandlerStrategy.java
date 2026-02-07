package appeng.parts.automation;

import javax.annotation.Nullable;

import com.google.common.primitives.Ints;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import appeng.api.config.Actionable;
import appeng.api.stacks.TLFluidKey;
import appeng.api.stacks.TLItemKey;
import appeng.api.stacks.TLKey;
import appeng.api.stacks.TLKeyType;
import appeng.me.storage.ExternalStorageFacade;

public abstract class HandlerStrategy<C, S> {
    private final TLKeyType keyType;

    public HandlerStrategy(TLKeyType keyType) {
        this.keyType = keyType;
    }

    public boolean isSupported(TLKey what) {
        return what.getType() == keyType;
    }

    public TLKeyType getKeyType() {
        return keyType;
    }

    public abstract ExternalStorageFacade getFacade(C handler);

    @Nullable
    public abstract S getStack(TLKey what, long amount);

    public abstract long insert(C handler, TLKey what, long amount, Actionable mode);

    public static final HandlerStrategy<IItemHandler, ItemStack> ITEMS = new HandlerStrategy<>(TLKeyType.items()) {
        @Override
        public boolean isSupported(TLKey what) {
            return TLItemKey.is(what);
        }

        @Override
        public ExternalStorageFacade getFacade(IItemHandler handler) {
            return ExternalStorageFacade.of(handler);
        }

        @Override
        public long insert(IItemHandler handler, TLKey what, long amount, Actionable mode) {
            if (what instanceof TLItemKey itemKey) {
                var stack = itemKey.toStack(Ints.saturatedCast(amount));

                var remainder = ItemHandlerHelper.insertItem(handler, stack, mode.isSimulate());
                return amount - remainder.getCount();
            }

            return 0;
        }

        @org.jetbrains.annotations.Nullable
        @Override
        public ItemStack getStack(TLKey what, long amount) {
            if (what instanceof TLItemKey itemKey) {
                return itemKey.toStack(Ints.saturatedCast(amount));
            }
            return null;
        }
    };

    public static final HandlerStrategy<IFluidHandler, FluidStack> FLUIDS = new HandlerStrategy<>(TLKeyType.fluids()) {
        @Override
        public boolean isSupported(TLKey what) {
            return TLFluidKey.is(what);
        }

        @Override
        public ExternalStorageFacade getFacade(IFluidHandler handler) {
            return ExternalStorageFacade.of(handler);
        }

        @Override
        public long insert(IFluidHandler handler, TLKey what, long amount, Actionable mode) {
            if (what instanceof TLFluidKey itemKey && amount > 0) {
                var stack = itemKey.toStack(Ints.saturatedCast(amount));
                return handler.fill(stack, mode.getFluidAction());
            }

            return 0;
        }

        @Override
        public FluidStack getStack(TLKey what, long amount) {
            if (what instanceof TLFluidKey fluidKey) {
                return fluidKey.toStack(Ints.saturatedCast(amount));
            }
            return null;
        }
    };

}
