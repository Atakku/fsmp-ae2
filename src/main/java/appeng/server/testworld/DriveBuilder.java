package appeng.server.testworld;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;

import appeng.api.config.Actionable;
import appeng.api.stacks.GenericStack;
import appeng.api.stacks.TLFluidKey;
import appeng.api.stacks.TLItemKey;
import appeng.api.stacks.TLKey;
import appeng.api.storage.MEStorage;
import appeng.core.definitions.TLItems;
import appeng.me.cells.BasicCellInventory;
import appeng.me.helpers.BaseActionSource;
import appeng.util.ConfigInventory;

/**
 * A helper class for quickly and succintly building the contents of a drive for test plots and tests.
 */
public class DriveBuilder {
    private final List<ItemStack> cells;

    DriveBuilder(List<ItemStack> cells) {
        this.cells = cells;
    }

    public CreativeCellBuilder addCreativeCell() {
        var cell = TLItems.CREATIVE_CELL.stack();
        var configInv = TLItems.CREATIVE_CELL.get().getConfigInventory(cell);
        cells.add(cell);
        return new CreativeCellBuilder(configInv);
    }

    public ItemCellBuilder addItemCell64k() {
        var cell = TLItems.CELL_ITEM_64K.stack();
        var cellInv = BasicCellInventory.createInventory(cell, null);
        cells.add(cell);
        return new ItemCellBuilder(cellInv);
    }

    public FluidCellBuilder addFluidCell64k() {
        var cell = TLItems.CELL_FLUID_64K.stack();
        var cellInv = BasicCellInventory.createInventory(cell, null);
        cells.add(cell);
        return new FluidCellBuilder(cellInv);
    }

    public class CellBuilder {
        protected final MEStorage inv;

        public CellBuilder(MEStorage inv) {
            this.inv = inv;
        }

        public void add(GenericStack stack) {
            add(stack.what(), stack.amount());
        }

        public void add(TLKey what, long amount) {
            if (inv.insert(what, amount, Actionable.MODULATE, new BaseActionSource()) != amount) {
                throw new IllegalArgumentException("Couldn't insert " + amount + " of " + what);
            }
        }

        public DriveBuilder and() {
            return DriveBuilder.this;
        }
    }

    public class FluidCellBuilder extends CellBuilder {
        public FluidCellBuilder(BasicCellInventory inv) {
            super(inv);
        }

        public void addBuckets(Fluid fluid, double buckets) {
            add(TLFluidKey.of(fluid), (long) (buckets * TLFluidKey.AMOUNT_BUCKET));
        }
    }

    public class ItemCellBuilder extends CellBuilder {
        public ItemCellBuilder(BasicCellInventory inv) {
            super(inv);
        }

        public void add(ItemLike what, long amount) {
            add(TLItemKey.of(what), amount);
        }
    }

    public class CreativeCellBuilder {
        private final ConfigInventory inv;

        public CreativeCellBuilder(ConfigInventory inv) {
            this.inv = inv;
        }

        public CreativeCellBuilder add(ItemLike item) {
            return add(TLItemKey.of(item));
        }

        public CreativeCellBuilder add(Fluid fluid) {
            return add(TLFluidKey.of(fluid));
        }

        public CreativeCellBuilder add(@Nullable GenericStack stack) {
            if (stack != null) {
                add(stack.what());
            }
            return this;
        }

        public CreativeCellBuilder add(TLKey key) {
            inv.insert(key, 1, Actionable.MODULATE, new BaseActionSource());
            return this;
        }

        public DriveBuilder and() {
            return DriveBuilder.this;
        }
    }
}
