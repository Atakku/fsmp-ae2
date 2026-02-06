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

package appeng.menu.me.networktool;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import org.jetbrains.annotations.Nullable;

import net.minecraft.network.RegistryFriendlyByteBuf;

import appeng.api.networking.IGrid;
import appeng.api.networking.IGridNode;
import appeng.client.gui.me.networktool.NetworkStatusScreen;

/**
 * Contains statistics about an ME network and the machines that form it.
 *
 * @see NetworkStatusScreen
 */
public class NetworkStatus {

    private int channelsUsed;

    private List<MachineGroup> groupedMachines = Collections.emptyList();

    public static NetworkStatus fromGrid(IGrid grid) {
        NetworkStatus status = new NetworkStatus();

        status.channelsUsed = grid.getPathingService().getUsedChannels();

        // This is essentially a groupBy machineRepresentation + count, sum(idlePowerUsage)
        Map<MachineGroupKey, MachineGroup> groupedMachines = new HashMap<>();
        for (var machineClass : grid.getMachineClasses()) {
            for (IGridNode machine : grid.getMachineNodes(machineClass)) {
                var key = getKey(machine);
                if (key != null) {
                    var group = groupedMachines.computeIfAbsent(key, MachineGroup::new);
                    group.setCount(group.getCount() + 1);
                }
            }
        }
        status.groupedMachines = ImmutableList.copyOf(groupedMachines.values());

        return status;
    }

    @Nullable
    private static MachineGroupKey getKey(IGridNode machine) {
        var visualRepresentation = machine.getVisualRepresentation();
        if (visualRepresentation == null) {
            return null;
        }

        return new MachineGroupKey(visualRepresentation, !machine.meetsChannelRequirements());
    }

    public int getChannelsUsed() {
        return channelsUsed;
    }

    /**
     * @return Machines grouped by their UI representation.
     */
    public List<MachineGroup> getGroupedMachines() {
        return groupedMachines;
    }

    /**
     * Reads a network status previously written using {@link #write(RegistryFriendlyByteBuf)}.
     */
    public static NetworkStatus read(RegistryFriendlyByteBuf data) {
        NetworkStatus status = new NetworkStatus();
        status.channelsUsed = data.readVarInt();

        int count = data.readVarInt();
        ImmutableList.Builder<MachineGroup> machines = ImmutableList.builder();
        for (int i = 0; i < count; i++) {
            machines.add(MachineGroup.read(data));
        }
        status.groupedMachines = machines.build();

        return status;
    }

    /**
     * Writes the contents of this object to a packet buffer. Use {@link #read(RegistryFriendlyByteBuf)} to restore.
     */
    public void write(RegistryFriendlyByteBuf data) {
        data.writeVarInt(channelsUsed);
        data.writeVarInt(groupedMachines.size());
        for (MachineGroup machine : groupedMachines) {
            machine.write(data);
        }
    }
}
