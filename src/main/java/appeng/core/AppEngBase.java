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

package appeng.core;

import java.util.Collection;
import java.util.Collections;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import appeng.api.ids.TLComponents;
import appeng.api.stacks.TLKeyType;
import appeng.api.stacks.TLKeyTypesInternal;
import appeng.core.definitions.TLAttachmentTypes;
import appeng.core.definitions.TLBlockEntities;
import appeng.core.definitions.TLBlocks;
import appeng.core.definitions.TLItems;
import appeng.core.definitions.TLParts;
import appeng.core.network.ClientboundPacket;
import appeng.core.network.InitNetwork;
import appeng.hooks.WrenchHook;
import appeng.hooks.ticking.TickHandler;
import appeng.hotkeys.HotkeyActions;
import appeng.init.InitAdvancementTriggers;
import appeng.init.InitCapabilityProviders;
import appeng.init.InitDispenserBehavior;
import appeng.init.InitMenuTypes;
import appeng.init.InitStats;
import appeng.init.client.InitParticleTypes;
import appeng.init.internal.InitGridLinkables;
import appeng.init.internal.InitGridServices;
import appeng.init.internal.InitStorageCells;
import appeng.init.internal.InitUpgrades;
import appeng.integration.Integrations;
import appeng.recipes.TLRecipeSerializers;
import appeng.recipes.TLRecipeTypes;
import appeng.server.TLCommand;
import appeng.server.testworld.GameTestPlotAdapter;
import appeng.sounds.AppEngSounds;

/**
 * Mod functionality that is common to both dedicated server and client.
 * <p>
 * Note that a client will still have zero or more embedded servers (although only one at a time).
 */
public abstract class AppEngBase implements AppEng {

    /**
     * While we process a player-specific part placement/cable interaction packet, we need to use that player's
     * transparent-facade mode to understand whether the player can see through facades or not.
     * <p>
     * We need to use this method since the collision shape methods do not know about the player that the shape is being
     * requested for, so they will call {@link #getCableRenderMode()} below, which then will use this field to figure
     * out which player it's for.
     */
    private final ThreadLocal<Player> partInteractionPlayer = new ThreadLocal<>();

    static AppEngBase INSTANCE;

    public AppEngBase(IEventBus modEventBus, ModContainer container) {
        if (INSTANCE != null) {
            throw new IllegalStateException();
        }
        INSTANCE = this;

        TLConfig.register(container);

        InitGridServices.init();

        TLParts.init();
        TLBlocks.DR.register(modEventBus);
        TLItems.DR.register(modEventBus);
        TLBlockEntities.DR.register(modEventBus);
        TLComponents.DR.register(modEventBus);
        TLRecipeTypes.DR.register(modEventBus);
        TLRecipeSerializers.DR.register(modEventBus);
        TLAttachmentTypes.register(modEventBus);

        modEventBus.addListener(this::registerRegistries);
        modEventBus.addListener(MainCreativeTab::initExternal);
        modEventBus.addListener(InitNetwork::init);
        modEventBus.addListener(EventPriority.HIGH, InitCapabilityProviders::markProxyableCapabilities);
        modEventBus.addListener(InitCapabilityProviders::register);
        modEventBus.addListener(EventPriority.LOWEST, InitCapabilityProviders::registerGenericAdapters);
        modEventBus.addListener((RegisterEvent event) -> {
            if (event.getRegistryKey() == Registries.SOUND_EVENT) {
                registerSounds(BuiltInRegistries.SOUND_EVENT);
            } else if (event.getRegistryKey() == Registries.CREATIVE_MODE_TAB) {
                registerCreativeTabs(BuiltInRegistries.CREATIVE_MODE_TAB);
            } else if (event.getRegistryKey() == Registries.CUSTOM_STAT) {
                InitStats.init(event.getRegistry(Registries.CUSTOM_STAT));
            } else if (event.getRegistryKey() == Registries.TRIGGER_TYPE) {
                InitAdvancementTriggers.init(event.getRegistry(Registries.TRIGGER_TYPE));
            } else if (event.getRegistryKey() == Registries.PARTICLE_TYPE) {
                InitParticleTypes.init(event.getRegistry(Registries.PARTICLE_TYPE));
            } else if (event.getRegistryKey() == Registries.MENU) {
                InitMenuTypes.init(event.getRegistry(Registries.MENU));
            } else if (event.getRegistryKey() == TLKeyType.REGISTRY_KEY) {
                registerKeyTypes(event.getRegistry(TLKeyType.REGISTRY_KEY));
            }
        });

        modEventBus.addListener(Integrations::enqueueIMC);
        modEventBus.addListener(this::commonSetup);

        modEventBus.addListener(this::registerTests);

        TickHandler.instance().init();

        NeoForge.EVENT_BUS.addListener(this::serverStopped);
        NeoForge.EVENT_BUS.addListener(this::registerCommands);

        NeoForge.EVENT_BUS.addListener(WrenchHook::onPlayerUseBlockEvent);

        HotkeyActions.init();
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(this::postRegistrationInitialization).whenComplete((res, err) -> {
            if (err != null) {
                TLLog.warn(err);
            }
        });
    }

    /**
     * Runs after all mods have had time to run their registrations into registries.
     */
    public void postRegistrationInitialization() {
        // Now that item instances are available, we can initialize registries that need item instances
        InitGridLinkables.init();
        InitStorageCells.init();

        InitDispenserBehavior.init();

        InitUpgrades.init();
    }

    public void registerKeyTypes(Registry<TLKeyType> registry) {
        Registry.register(registry, TLKeyType.items().getId(), TLKeyType.items());
        Registry.register(registry, TLKeyType.fluids().getId(), TLKeyType.fluids());
    }

    public void registerCommands(RegisterCommandsEvent evt) {
        new TLCommand().register(evt.getDispatcher());
    }

    public void registerSounds(Registry<SoundEvent> registry) {
        AppEngSounds.register(registry);
    }

    public void registerRegistries(NewRegistryEvent e) {
        var registry = e.create(new RegistryBuilder<>(TLKeyType.REGISTRY_KEY)
                .sync(true)
                .maxId(127));
        TLKeyTypesInternal.setRegistry(registry);
    }

    private void serverStopped(final ServerStoppedEvent event) {
        TickHandler.instance().shutdown();
    }

    public void registerCreativeTabs(Registry<CreativeModeTab> registry) {
        MainCreativeTab.init(registry);
    }

    @Override
    public Collection<ServerPlayer> getPlayers() {
        var server = getCurrentServer();

        if (server != null) {
            return server.getPlayerList().getPlayers();
        }

        return Collections.emptyList();
    }

    @Override
    public void sendToAllNearExcept(Player p, double x, double y, double z,
            double dist, Level level, ClientboundPacket packet) {
        if (level instanceof ServerLevel serverLevel) {
            ServerPlayer except = null;
            if (p instanceof ServerPlayer) {
                except = (ServerPlayer) p;
            }
            PacketDistributor.sendToPlayersNear(serverLevel, except, x, y, z, dist, packet);
        }
    }

    @Override
    public void setPartInteractionPlayer(Player player) {
        this.partInteractionPlayer.set(player);
    }

    @Nullable
    @Override
    public MinecraftServer getCurrentServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

    private void registerTests(RegisterGameTestsEvent e) {
        if ("true".equals(System.getProperty("appeng.tests"))) {
            e.register(GameTestPlotAdapter.class);
        }
    }
}
