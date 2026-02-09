/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2015, AlgorithmX2, All rights reserved.
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

package appeng.blockentity.networking;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import appeng.api.inventories.InternalInventory;
import appeng.api.networking.GridFlags;
import appeng.api.networking.GridHelper;
import appeng.api.networking.IGridNodeListener;
import appeng.api.networking.events.GridControllerChange;
import appeng.api.networking.pathing.ControllerState;
import appeng.api.util.TLCableType;
import appeng.block.networking.ControllerBlock;
import appeng.block.networking.ControllerBlock.ControllerBlockState;
import appeng.blockentity.grid.TLNetworkedInvBlockEntity;
import appeng.util.Platform;

public class ControllerBlockEntity extends TLNetworkedInvBlockEntity {

    static {
        GridHelper.addNodeOwnerEventHandler(
                GridControllerChange.class,
                ControllerBlockEntity.class,
                ControllerBlockEntity::updateState);
    }

    public ControllerBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState blockState) {
        super(blockEntityType, pos, blockState);
        this.getMainNode().setFlags(GridFlags.CANNOT_CARRY, GridFlags.DENSE_CAPACITY);
    }

    @Override
    public TLCableType getCableConnectionType(Direction dir) {
        return TLCableType.DENSE;
    }

    @Override
    public void onReady() {
        super.onReady();
        updateState();
    }

    @Override
    public void onMainNodeStateChanged(IGridNodeListener.State reason) {
        this.updateState();
    }

    public void updateState() {
        if (!this.getMainNode().isReady()) {
            return;
        }

        ControllerBlockState metaState = ControllerBlockState.offline;

        var grid = getMainNode().getGrid();
        if (grid != null) {
            metaState = ControllerBlockState.online;

            if (grid.getPathingService().getControllerState() == ControllerState.CONTROLLER_CONFLICT) {
                metaState = ControllerBlockState.conflicted;
            }
        } else {
            metaState = ControllerBlockState.offline;
        }

        if (this.checkController(this.worldPosition)
                && this.level.getBlockState(this.worldPosition)
                        .getValue(ControllerBlock.CONTROLLER_STATE) != metaState) {
            // We don't want to be sending neighbor updates when a controller is being moved by spatial IO.
            // So we never send one, and only notify the clients.
            this.level.setBlock(this.worldPosition,
                    this.level.getBlockState(this.worldPosition).setValue(ControllerBlock.CONTROLLER_STATE, metaState),
                    Block.UPDATE_CLIENTS);
        }

    }

    @Override
    public InternalInventory getInternalInventory() {
        return InternalInventory.empty();
    }

    /**
     * Check for a controller at this coordinates as well as is it loaded.
     *
     * @return true if there is a loaded controller
     */
    private boolean checkController(BlockPos pos) {
        return Platform.getTickingBlockEntity(getLevel(), pos) instanceof ControllerBlockEntity;
    }
}
