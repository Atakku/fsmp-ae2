package appeng.api.stacks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.testframework.junit.EphemeralTestServerProvider;

import appeng.api.config.FuzzyMode;
import appeng.api.ids.TLComponents;
import appeng.core.definitions.TLItems;
import appeng.util.BootstrapMinecraft;
import appeng.util.Platform;

@BootstrapMinecraft
@ExtendWith(EphemeralTestServerProvider.class)
class TLItemKeyTest {
    private RegistryAccess registries = RegistryAccess.EMPTY;

    private static int getMaxDamage(ItemLike item) {
        return item.asItem().getMaxDamage(item.asItem().getDefaultInstance());
    }

    @Test
    void testFuzzySearchValues() {
        var undamaged = TLItemKey.of(Items.DIAMOND_PICKAXE);
        var damagedStack = undamaged.toStack();
        damagedStack.setDamageValue(getMaxDamage(undamaged.getItem()));
        var damaged = TLItemKey.of(damagedStack);

        assertEquals(damaged.getFuzzySearchMaxValue(), getMaxDamage(Items.DIAMOND_PICKAXE));
        assertEquals(damaged.getFuzzySearchValue(), getMaxDamage(Items.DIAMOND_PICKAXE));
        assertEquals(undamaged.getFuzzySearchValue(), 0);
    }

    @ParameterizedTest
    @CsvSource({
            "100,100,IGNORE_ALL,true",
            "100,100,PERCENT_99,true",
            "100,100,PERCENT_75,true",
            "100,100,PERCENT_50,true",
            "100,100,PERCENT_25,true",
            "100,99,PERCENT_99,false",
            "99,0,PERCENT_99,true",
            "100,99,PERCENT_99,false",
            "99,0,PERCENT_99,true",
            "100,75,PERCENT_75,true",
            "100,74,PERCENT_75,false",
            "75,74,PERCENT_75,false",
            "74,0,PERCENT_75,true",
            "100,50,PERCENT_50,true",
            "100,49,PERCENT_50,false",
            "50,49,PERCENT_50,false",
            "49,0,PERCENT_50,true",
            "100,25,PERCENT_25,true",
            "100,24,PERCENT_25,false",
            "25,24,PERCENT_25,false",
            "24,0,PERCENT_25,true",
    })
    void testFuzzyEquals(int durabilityPercentA, int durabilityPercentB, FuzzyMode mode, boolean equals) {
        var stackA = new ItemStack(Items.DIAMOND_PICKAXE);
        setDamageFromPercentage(stackA, durabilityPercentA);
        var keyA = TLItemKey.of(stackA);
        var stackB = new ItemStack(Items.DIAMOND_PICKAXE);
        setDamageFromPercentage(stackB, durabilityPercentB);
        var keyB = TLItemKey.of(stackB);

        assertEquals(equals, keyA.fuzzyEquals(keyB, mode));
        assertEquals(equals, keyB.fuzzyEquals(keyA, mode));
    }

    /**
     * This tests that the fuzzy search works the same for {@link FuzzySearch} and {@link TLKey#fuzzyEquals}.
     */
    @ParameterizedTest
    @EnumSource(value = FuzzyMode.class, mode = EnumSource.Mode.EXCLUDE, names = "IGNORE_ALL")
    void testConsistencyWithFuzzySearch(FuzzyMode mode) {
        var keys = new KeyCounter();
        for (var i = 0; i <= getMaxDamage(Items.IRON_PICKAXE); i++) {
            var stack = new ItemStack(Items.IRON_PICKAXE);
            stack.setDamageValue(i);
            keys.set(TLItemKey.of(stack), 1);
        }

        var undamaged = new ItemStack(Items.IRON_PICKAXE);
        var fullyDamaged = new ItemStack(Items.IRON_PICKAXE);
        fullyDamaged.setDamageValue(fullyDamaged.getMaxDamage());

        // Get the upper partition
        var upperKeys = keys.findFuzzy(TLItemKey.of(undamaged), mode)
                .stream().map(Map.Entry::getKey).toList();
        var lowerKeys = keys.findFuzzy(TLItemKey.of(fullyDamaged), mode)
                .stream().map(Map.Entry::getKey).toList();

        // Given TLKey.fuzzyEquals, all upperKeys should be equal to each other, and unequal to all lower keys
        for (int i = 0; i < upperKeys.size(); i++) {
            var key = upperKeys.get(i);
            var unequals = upperKeys.stream().filter(ok -> !ok.fuzzyEquals(key, mode)).toList();
            assertThat(unequals).isEmpty();

            var equals = lowerKeys.stream().filter(ok -> ok.fuzzyEquals(key, mode)).toList();
            assertThat(equals).isEmpty();
        }
        for (int i = 0; i < lowerKeys.size(); i++) {
            var key = lowerKeys.get(i);
            var unequals = lowerKeys.stream().filter(ok -> !ok.fuzzyEquals(key, mode)).toList();
            assertThat(unequals).isEmpty();

            var equals = upperKeys.stream().filter(ok -> ok.fuzzyEquals(key, mode)).toList();
            assertThat(equals).isEmpty();
        }
    }

    private void setDamageFromPercentage(ItemStack stack, int percentage) {
        stack.setDamageValue((int) ((1.0f - (percentage / 100.0f)) * (float) stack.getMaxDamage()));
    }

    @Test
    void testFuzzyEqualsDifferentItems() {
        // Different items are not fuzzy equals
        assertFalse(TLItemKey.of(Items.DIAMOND).fuzzyEquals(TLItemKey.of(Items.DIAMOND_PICKAXE), FuzzyMode.IGNORE_ALL));
    }

    @Test
    void testFuzzyEqualsDifferentNbt(MinecraftServer server) {
        var pick1 = new ItemStack(Items.DIAMOND_PICKAXE);
        var pick2 = new ItemStack(Items.DIAMOND_PICKAXE);
        pick2.enchant(Platform.getEnchantment(server, Enchantments.FORTUNE), 2);
        assertNotEquals(pick1.getComponents(), pick2.getComponents());

        assertTrue(TLItemKey.of(pick1).fuzzyEquals(TLItemKey.of(pick2), FuzzyMode.IGNORE_ALL));
    }

    @Nested
    class GenericTagSerialization {

        @Test
        void deserializeFromTagWithoutChannel() {
            var tag = new CompoundTag();

            assertMissingContent(tag, "Input does not contain a key [#t]: MapLike[{}]");
        }

        @Test
        void deserializeFromTagWithUnknownChannelId() {
            var tag = new CompoundTag();
            tag.putString("#t", "modid:doesnt_exist");

            assertMissingContent(tag,
                    "Unknown registry key in ResourceKey[minecraft:root / tl2:keytypes]: modid:doesnt_exist");
        }

        @Test
        void deserializeFromTagWithMalformedChannelId() {
            var tag = new CompoundTag();
            tag.putString("#t", "modid!!!!!doesnt_exist");

            assertMissingContent(tag,
                    "Not a valid resource location: modid!!!!!doesnt_exist Non [a-z0-9/._-] character in path of location: minecraft:modid!!!!!doesnt_exist");
        }

        private void assertMissingContent(CompoundTag tag, String error) {
            var decodedKey = TLKey.fromTagGeneric(registries, tag);
            assertNotNull(decodedKey);
            assertEquals(decodedKey.dropSecondary(), TLItemKey.of(TLItems.MISSING_CONTENT));
            assertEquals(error, decodedKey.get(TLComponents.MISSING_CONTENT_ERROR));
            assertNotNull(decodedKey.get(TLComponents.MISSING_CONTENT_TLKEY_DATA));
            assertEquals(tag, decodedKey.get(TLComponents.MISSING_CONTENT_TLKEY_DATA).copyTag());
        }
    }

    @Test
    void testIsDamaged() {
        assertFalse(TLItemKey.of(Blocks.BEDROCK).isDamaged());
        assertFalse(TLItemKey.of(Items.DIAMOND_PICKAXE).isDamaged());

        var stack = new ItemStack(Items.DIAMOND_PICKAXE);
        stack.setDamageValue(0);
        assertFalse(TLItemKey.of(stack).isDamaged());

        stack.setDamageValue(1);
        assertTrue(TLItemKey.of(stack).isDamaged());
    }

    /**
     * Regression test for {@link FuzzySearch#COMPARATOR} wrongly using TLKey identity comparison as a last resort.
     */
    @Test
    void testDifferentInstances(MinecraftServer server) {
        int testCount = 100;
        while (testCount-- > 0) {

            final int COUNT = 5;
            TLKey[] keys = new TLKey[COUNT];
            TLKey[] keyCopies = new TLKey[COUNT];

            for (int i = 0; i < COUNT; i++) {
                var stack = new ItemStack(Items.DIAMOND_SWORD);
                stack.enchant(Platform.getEnchantment(server, Enchantments.SHARPNESS), i + 1);
                keys[i] = TLItemKey.of(stack);
                keyCopies[i] = TLItemKey.of(stack);

                // If we ever intern TLKeys, remember to update this test...
                assertThat(keys[i]).isNotSameAs(keyCopies[i]);
            }

            var counter = new KeyCounter();
            for (int i = 0; i < COUNT; i++) {
                counter.set(keys[i], 1);
            }

            for (int i = 0; i < COUNT; i++) {
                assertThat(counter.get(keyCopies[i])).isEqualTo(1);
            }

        }
    }
}
