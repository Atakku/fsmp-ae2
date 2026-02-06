/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2014, AlgorithmX2, All rights reserved.
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

package appeng.debug;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import appeng.api.networking.GridHelper;
import appeng.core.AEConfig;
import appeng.items.AEBaseItem;
import appeng.util.InteractionUtil;

public class ReplicatorCardItem extends AEBaseItem {

    public ReplicatorCardItem(Properties properties) {
        super(properties);
    }

    private CompoundTag getTag(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
    }

    private int getReplications(ItemStack stack) {
        return getTag(stack).getInt("r");
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, InteractionHand handIn) {
        if (!level.isClientSide()) {
            var stack = playerIn.getItemInHand(handIn);
            CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
                final int replications;
                if (tag.contains("r")) {
                    replications = (tag.getInt("r") + 1) % 4;
                } else {
                    replications = 0;
                }
                tag.putInt("r", replications);
            });

            var replications = getReplications(stack);
            playerIn.sendSystemMessage(Component.literal(replications + 1 + "³ Replications"));
        }

        return super.use(level, playerIn, handIn);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide()) {
            // Needed, otherwise client will trigger onItemRightClick also on server...
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        Direction side = context.getClickedFace();
        InteractionHand hand = context.getHand();

        if (player == null) {
            return InteractionResult.PASS;
        }

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if (InteractionUtil.isInAlternateUseMode(player)) {
            var gridHost = GridHelper.getNodeHost(level, pos);

            if (gridHost != null) {
                CustomData.update(DataComponents.CUSTOM_DATA, player.getItemInHand(hand), tag -> {
                    tag.putInt("x", x);
                    tag.putInt("y", y);
                    tag.putInt("z", z);
                    tag.putInt("side", side.ordinal());
                    tag.putString("w", level.dimension().location().toString());
                    tag.putInt("r", 0);
                });

                this.outputMsg(player, "Set replicator source");
            } else {
                this.outputMsg(player, "This does not host a grid node");
            }
        } else {
            this.outputMsg(player, "No Source Defined");
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    private void outputMsg(Entity player, String string) {
        player.sendSystemMessage(Component.literal(string));
    }

    @Override
    public void addToMainCreativeTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
        if (AEConfig.instance().isDebugToolsEnabled()) {
            output.accept(this);
        }
    }
}
