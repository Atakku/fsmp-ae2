package appeng.init;

import java.util.function.Function;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModLoader;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import appeng.api.TLCapabilities;
import appeng.api.behaviors.GenericInternalInventory;
import appeng.api.networking.IInWorldGridNodeHost;
import appeng.api.parts.RegisterPartCapabilitiesEvent;
import appeng.api.parts.RegisterPartCapabilitiesEventInternal;
import appeng.blockentity.TLBaseInvBlockEntity;
import appeng.blockentity.storage.MEChestBlockEntity;
import appeng.core.definitions.TLBlockEntities;
import appeng.helpers.externalstorage.GenericStackFluidStorage;
import appeng.helpers.externalstorage.GenericStackItemStorage;

public final class InitCapabilityProviders {

    private InitCapabilityProviders() {
    }

    /**
     * Called with high priority to mark which capabilities are proxyable.
     */
    public static void markProxyableCapabilities(RegisterCapabilitiesEvent event) {
        // Definitely proxyable - this is a storage capability.
        event.setProxyable(TLCapabilities.ME_STORAGE);
        // Why not - this is a storage capability, albeit in principle not exposed directly.
        event.setProxyable(TLCapabilities.GENERIC_INTERNAL_INV);
        // Definitely not proxyable, we don't want to connect nodes through a capability tunnel.
        event.setNonProxyable(TLCapabilities.IN_WORLD_GRID_NODE_HOST);
    }

    public static void register(RegisterCapabilitiesEvent event) {

        var partEvent = new RegisterPartCapabilitiesEvent();
        partEvent.addHostType(TLBlockEntities.CABLE_BUS.get());
        ModLoader.postEvent(partEvent);
        RegisterPartCapabilitiesEventInternal.register(partEvent, event);

        initMEChest(event);
        initMisc(event);

        for (var type : TLBlockEntities.getSubclassesOf(TLBaseInvBlockEntity.class)) {
            event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, type,
                    TLBaseInvBlockEntity::getExposedItemHandler);
        }
        for (var type : TLBlockEntities.getImplementorsOf(IInWorldGridNodeHost.class)) {
            event.registerBlockEntity(TLCapabilities.IN_WORLD_GRID_NODE_HOST, type,
                    (object, context) -> (IInWorldGridNodeHost) object);
        }
    }

    /**
     * This registration is called with the lowest possible priority to register adapters.
     */
    public static void registerGenericAdapters(RegisterCapabilitiesEvent event) {

        for (var block : BuiltInRegistries.BLOCK) {
            if (event.isBlockRegistered(TLCapabilities.GENERIC_INTERNAL_INV, block)) {
                registerGenericInvAdapter(event, block, Capabilities.ItemHandler.BLOCK, GenericStackItemStorage::new);
                registerGenericInvAdapter(event, block, Capabilities.FluidHandler.BLOCK, GenericStackFluidStorage::new);
            }
        }

    }

    private static <T> void registerGenericInvAdapter(RegisterCapabilitiesEvent event,
            Block block,
            BlockCapability<T, Direction> capability,
            Function<GenericInternalInventory, T> adapter) {
        event.registerBlock(
                capability,
                (level, pos, state, blockEntity, context) -> {
                    var genericInv = level.getCapability(TLCapabilities.GENERIC_INTERNAL_INV, pos, state,
                            blockEntity, context);
                    if (genericInv != null) {
                        return adapter.apply(genericInv);
                    }
                    return null;
                },
                block);
    }

    private static void initMEChest(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, TLBlockEntities.ME_CHEST.get(),
                MEChestBlockEntity::getFluidHandler);
        event.registerBlockEntity(TLCapabilities.ME_STORAGE, TLBlockEntities.ME_CHEST.get(),
                MEChestBlockEntity::getMEStorage);
    }

    private static void initMisc(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                TLBlockEntities.DEBUG_ITEM_GEN.get(),
                (object, context) -> object.getItemHandler());
    }
}
