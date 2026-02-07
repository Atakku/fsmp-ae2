/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2021, TeamAppliedEnergistics, All rights reserved.
 *
 * Applied Energistics 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Applied Energistics 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Applied Energistics 2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package appeng.menu.me.common;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.item.ItemStack;

import appeng.api.stacks.TLKey;

/**
 * This utility class helps menus that need to send a list of information that is grouped by {@link TLKey} to the client
 * and keep it updated, without having to resend the {@link TLKey} everytime. This can be especially important if the
 * item stack is serialized using it's {@link ItemStack#getShareTag() share tag}, which would not match the server-side
 * stack if it's sent back, or that would group distinct server-side entries together on the client-side if their share
 * tag was equal.
 */
public class IncrementalUpdateHelper implements Iterable<TLKey> {

    /**
     * Maps stacks to serial numbers. This relies on the fact that these stacks are equal iff their type is equal, and
     * two stacks with different counts are still equal.
     */
    private final BiMap<TLKey, Long> mapping;

    private final Set<TLKey> changes = new HashSet<>();

    private long serial;

    /**
     * Indicates that a full update should be sent.
     */
    private boolean fullUpdate = true;

    public IncrementalUpdateHelper() {
        this.mapping = HashBiMap.create();
    }

    @Nullable
    public Long getSerial(TLKey stack) {
        return mapping.get(stack);
    }

    public long getOrAssignSerial(TLKey key) {
        return mapping.computeIfAbsent(key, k -> ++this.serial);
    }

    public TLKey getBySerial(long serial) {
        return mapping.inverse().get(serial);
    }

    /**
     * Clear pending changes and prepare for a full update.
     * <p/>
     * Mappings are kept because even in case of a full update, many items are usually still present and should keep
     * their serial.
     */
    public void clear() {
        this.changes.clear();
        fullUpdate = true;
    }

    /**
     * Fully resets this helper into its initial state. This will also clear any serial mapping.
     */
    public void reset() {
        clear();
        this.serial = 0;
        this.mapping.clear();
    }

    public void addChange(TLKey entry) {
        if (!changes.add(entry)) {
            changes.remove(entry);
            changes.add(entry);
        }
    }

    /**
     * Removes the serial mapping for the given key. Will lead to a new serial being generated the next time this
     * particular key is used.
     */
    public void removeSerial(TLKey what) {
        mapping.remove(what);
    }

    public void commitChanges() {
        changes.clear();
        fullUpdate = false;
    }

    public boolean hasChanges() {
        return fullUpdate || !changes.isEmpty();
    }

    public boolean isFullUpdate() {
        return fullUpdate;
    }

    @Override
    public Iterator<TLKey> iterator() {
        return changes.iterator();
    }

    @Override
    public void forEach(Consumer<? super TLKey> action) {
        changes.forEach(action);
    }

    @Override
    public Spliterator<TLKey> spliterator() {
        return changes.spliterator();
    }
}
