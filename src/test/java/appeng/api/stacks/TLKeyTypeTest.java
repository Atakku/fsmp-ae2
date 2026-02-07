package appeng.api.stacks;

import com.google.gson.JsonPrimitive;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;

import org.junit.jupiter.api.Test;

import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.StringTag;

import appeng.util.BootstrapMinecraft;
import appeng.util.CodecTestUtil;

@BootstrapMinecraft
class TLKeyTypeTest {
    @Test
    void testItemJsonRoundtrip() {
        testKeyTypeRoundtrip(TLKeyType.items(), JsonOps.INSTANCE, new JsonPrimitive("tl2:i"));
    }

    @Test
    void testFluidJsonRoundtrip() {
        testKeyTypeRoundtrip(TLKeyType.fluids(), JsonOps.INSTANCE, new JsonPrimitive("tl2:f"));
    }

    @Test
    void testItemNbtRoundtrip() {
        testKeyTypeRoundtrip(TLKeyType.items(), NbtOps.INSTANCE, StringTag.valueOf("tl2:i"));
    }

    @Test
    void testFluidNbtRoundtrip() {
        testKeyTypeRoundtrip(TLKeyType.fluids(), NbtOps.INSTANCE, StringTag.valueOf("tl2:f"));
    }

    private static <T> void testKeyTypeRoundtrip(TLKeyType type, DynamicOps<T> ops, T encodedValue) {
        CodecTestUtil.testRoundtrip(TLKeyType.CODEC, type, ops, encodedValue);
    }

    @Test
    void testItemNetworkRoundtrip() {
        CodecTestUtil.testRoundtrip(TLKeyType.STREAM_CODEC, TLKeyType.items());
    }

    @Test
    void testFluidNetworkRoundtrip() {
        CodecTestUtil.testRoundtrip(TLKeyType.STREAM_CODEC, TLKeyType.fluids());
    }
}
