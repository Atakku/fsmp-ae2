package appeng.api.stacks;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongMaps;
import it.unimi.dsi.fastutil.objects.Object2LongSortedMap;

import appeng.api.config.FuzzyMode;

/**
 * Tallies a negative or positive amount for sub-variants of a {@link TLKey}.
 */
abstract class VariantCounter implements Iterable<Object2LongMap.Entry<TLKey>> {
    /**
     * Enable to skip and remove keys that are mapped to zero.
     */
    private boolean dropZeros;

    public boolean isDropZeros() {
        return dropZeros;
    }

    public void setDropZeros(boolean dropZeros) {
        this.dropZeros = dropZeros;
    }

    public long get(TLKey key) {
        return this.getRecords().getOrDefault(key, 0);
    }

    public void add(TLKey key, long amount) {
        this.getRecords().addTo(key, amount);
    }

    public void set(TLKey key, long amount) {
        if (dropZeros && amount == 0) {
            getRecords().removeLong(key);
        } else {
            getRecords().put(key, amount);
        }
    }

    public long remove(TLKey key) {
        return getRecords().removeLong(key);
    }

    public void addAll(VariantCounter other) {
        for (var entry : other.getRecords().object2LongEntrySet()) {
            add(entry.getKey(), entry.getLongValue());
        }
    }

    public void removeAll(VariantCounter other) {
        for (var entry : other.getRecords().object2LongEntrySet()) {
            add(entry.getKey(), -entry.getLongValue());
        }
    }

    public abstract Collection<Object2LongMap.Entry<TLKey>> findFuzzy(TLKey filter, FuzzyMode fuzzy);

    public int size() {
        if (!dropZeros) {
            return getRecords().size();
        }

        var size = 0;
        for (var value : getRecords().values()) {
            if (value != 0) {
                size++;
            }
        }

        return size;
    }

    public boolean isEmpty() {
        if (!dropZeros) {
            return getRecords().isEmpty();
        }

        for (var value : getRecords().values()) {
            if (value != 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Iterator<Object2LongMap.Entry<TLKey>> iterator() {
        if (!dropZeros) {
            return Object2LongMaps.fastIterator(getRecords());
        }

        return new NonDefaultIterator();
    }

    abstract TLKey2LongMap getRecords();

    /**
     * Sets all amounts to zero.
     */
    public void reset() {
        if (dropZeros) {
            getRecords().clear();
        } else {
            getRecords().replaceAll((key, value) -> 0L);
        }
    }

    public void clear() {
        getRecords().clear();
    }

    public abstract VariantCounter copy();

    public void invert() {
        for (var entry : getRecords().object2LongEntrySet()) {
            entry.setValue(-entry.getLongValue());
        }
    }

    public void removeZeros() {
        var it = getRecords().values().iterator();
        while (it.hasNext()) {
            var entry = it.nextLong();
            if (entry == 0) {
                it.remove();
            }
        }
    }

    /**
     * Only returns entries that do not have amount 0.
     */
    private class NonDefaultIterator implements Iterator<Object2LongMap.Entry<TLKey>> {
        private final Iterator<Object2LongMap.Entry<TLKey>> parent;
        private Object2LongMap.Entry<TLKey> next;

        public NonDefaultIterator() {
            this.parent = Object2LongMaps.fastIterator(getRecords());
            this.next = seekNext();
        }

        @Override
        public boolean hasNext() {
            return this.next != null;
        }

        @Override
        public Object2LongMap.Entry<TLKey> next() {
            if (this.next == null) {
                throw new NoSuchElementException();
            }

            var result = this.next;
            this.next = this.seekNext();
            return result;
        }

        private Object2LongMap.Entry<TLKey> seekNext() {
            while (this.parent.hasNext()) {
                var entry = this.parent.next();

                if (entry.getLongValue() == 0) {
                    this.parent.remove();
                } else {
                    return entry;
                }
            }

            return null;
        }
    }

    /**
     * This variant list is optimized for items that cannot be damaged and thus do not support querying durability
     * ranges via {@link #findFuzzy}.
     */
    static class UnorderedVariantMap extends VariantCounter {
        private final TLKey2LongMap records = new TLKey2LongMap.OpenHashMap();

        /**
         * For keys whose primary key does not support fuzzy range lookups, we simply return all records, which amounts
         * to ignoring NBT.
         */
        @Override
        public Collection<Object2LongMap.Entry<TLKey>> findFuzzy(TLKey filter, FuzzyMode fuzzy) {
            return records.object2LongEntrySet();
        }

        @Override
        TLKey2LongMap getRecords() {
            return records;
        }

        @Override
        public VariantCounter copy() {
            var result = new UnorderedVariantMap();
            result.records.putAll(records);
            return result;
        }
    }

    /**
     * This variant list is optimized for damageable items, and supports selecting durability ranges with
     * {@link #findFuzzy}.
     */
    static class FuzzyVariantMap extends VariantCounter {
        private final TLKey2LongMap.AVLTreeMap records = FuzzySearch.createMap2Long();

        @Override
        public Collection<Object2LongMap.Entry<TLKey>> findFuzzy(TLKey key, FuzzyMode fuzzy) {
            // The cast is necessary because the subMap in the call is not an instance of TLKey2LongMap.AVLTreeMap!
            return FuzzySearch.findFuzzy((Object2LongSortedMap<TLKey>) records, key, fuzzy).object2LongEntrySet();
        }

        @Override
        TLKey2LongMap getRecords() {
            return this.records;
        }

        @Override
        public VariantCounter copy() {
            var result = new FuzzyVariantMap();
            result.records.putAll(records);
            return result;
        }
    }
}
