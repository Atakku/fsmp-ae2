package appeng.api.behaviors;

import net.minecraft.network.chat.Component;

import appeng.api.stacks.TLKey;

/**
 * Describes the action of emptying an item into the storage network.
 */
public record EmptyingAction(Component description, TLKey what, long maxAmount) {
}
