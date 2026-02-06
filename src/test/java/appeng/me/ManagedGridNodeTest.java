package appeng.me;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.assertj.core.data.Offset;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

class ManagedGridNodeTest extends AbstractGridNodeTest {
    public static final Offset<Double> TOLERANCE = Offset.offset(0.1);
    private final Object owner = new Object();

    @Test
    void testGridStorageRetrieval() {

        // Create the NBT tag that contains a fully loaded energy service
        var precursorNode = createAndInitNode();
        var savedNode = new CompoundTag();
        precursorNode.saveToNBT(savedNode);
        precursorNode.destroy();

        // We create 2 nodes and re-initialize both with the same storage
        // We should have twice the storage as before.
        var mgn1 = new ManagedGridNode(owner, listener);
        mgn1.loadFromNBT(savedNode);
        var mgn2 = new ManagedGridNode(owner, listener);
        mgn2.loadFromNBT(savedNode);

        mgn1.create(level, BlockPos.ZERO);
        mgn2.create(level, BlockPos.ZERO);
        GridConnection.create(
                mgn1.getNode(),
                mgn2.getNode(),
                null);

        assertSame(mgn1.getGrid(), mgn2.getGrid());
    }

    @NotNull
    private ManagedGridNode createAndInitNode() {
        var mgn = new ManagedGridNode(owner, listener);
        mgn.create(level, null);
        return mgn;
    }

}
