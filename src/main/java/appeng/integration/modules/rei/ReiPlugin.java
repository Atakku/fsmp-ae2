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

package appeng.integration.modules.rei;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.client.renderer.Rect2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;

import dev.architectury.event.CompoundEventResult;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.client.registry.screen.ExclusionZones;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandlerRegistry;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCustomShapelessDisplay;

import appeng.api.integrations.rei.IngredientConverters;
import appeng.client.gui.TLBaseScreen;
import appeng.core.AppEng;
import appeng.core.definitions.TLItems;
import appeng.core.definitions.TLParts;
import appeng.integration.abstraction.ItemListMod;
import appeng.integration.modules.itemlists.CompatLayerHelper;
import appeng.integration.modules.itemlists.ItemPredicates;
import appeng.integration.modules.rei.transfer.UseCraftingRecipeTransfer;
import appeng.menu.me.items.CraftingTermMenu;
import appeng.recipes.game.StorageCellUpgradeRecipe;

@REIPluginClient
public class ReiPlugin implements REIClientPlugin {
    static final ResourceLocation TEXTURE = AppEng.makeId("textures/guis/jei.png");

    public ReiPlugin() {
        if (CompatLayerHelper.IS_LOADED) {
            return;
        }

        IngredientConverters.register(new ItemIngredientConverter());
        IngredientConverters.register(new FluidIngredientConverter());

        ItemListMod.setAdapter(new ReiItemListModAdapter());
    }

    @Override
    public String getPluginProviderName() {
        return "TL2";
    }

    @Override
    public void registerCategories(CategoryRegistry registry) {
        if (CompatLayerHelper.IS_LOADED) {
            return;
        }

        registerWorkingStations(registry);
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        if (CompatLayerHelper.IS_LOADED) {
            return;
        }

        registry.registerRecipeFiller(StorageCellUpgradeRecipe.class, RecipeType.CRAFTING,
                this::convertStorageCellUpgradeRecipe);
    }

    private Display convertStorageCellUpgradeRecipe(RecipeHolder<StorageCellUpgradeRecipe> holder) {
        var recipe = holder.value();

        return new DefaultCustomShapelessDisplay(
                holder,
                List.of(EntryIngredients.of(recipe.getInputCell()), EntryIngredients.of(recipe.getInputComponent())),
                List.of(EntryIngredients.of(recipe.getResultCell()), EntryIngredients.of(recipe.getResultComponent())));
    }

    @Override
    public void registerTransferHandlers(TransferHandlerRegistry registry) {
        if (CompatLayerHelper.IS_LOADED) {
            return;
        }

        // Allow recipe transfer from JEI to crafting and pattern terminal
        registry.register(new UseCraftingRecipeTransfer<>(CraftingTermMenu.class));
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        if (CompatLayerHelper.IS_LOADED) {
            return;
        }

        registry.registerDraggableStackVisitor(new GhostIngredientHandler());
        registry.registerFocusedStack((screen, mouse) -> {
            if (screen instanceof TLBaseScreen<?> aeScreen) {
                var stack = aeScreen.getStackUnderMouse(mouse.x, mouse.y);
                if (stack != null) {
                    for (var converter : IngredientConverters.getConverters()) {
                        var entryStack = converter.getIngredientFromStack(stack.stack());
                        if (entryStack != null) {
                            return CompoundEventResult.interruptTrue(entryStack);
                        }
                    }
                }
            }

            return CompoundEventResult.pass();
        });
    }

    @Override
    public void registerEntries(EntryRegistry registry) {
        registry.removeEntryIf(this::shouldEntryBeHidden);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void registerExclusionZones(ExclusionZones zones) {
        if (CompatLayerHelper.IS_LOADED) {
            return;
        }

        zones.register(TLBaseScreen.class, screen -> {
            return screen != null ? mapRects(screen.getExclusionZones()) : Collections.emptyList();
        });

    }

    private static List<Rectangle> mapRects(List<Rect2i> exclusionZones) {
        return exclusionZones.stream()
                .map(ez -> new Rectangle(ez.getX(), ez.getY(), ez.getWidth(), ez.getHeight()))
                .collect(Collectors.toList());
    }

    private void registerWorkingStations(CategoryRegistry registry) {
        var craftingTerminal = TLParts.CRAFTING_TERMINAL.stack();
        registry.addWorkstations(BuiltinPlugin.CRAFTING, EntryStacks.of(craftingTerminal));

        var wirelessCraftingTerminal = TLItems.WIRELESS_CRAFTING_TERMINAL.stack();
        registry.addWorkstations(BuiltinPlugin.CRAFTING, EntryStacks.of(wirelessCraftingTerminal));
    }

    private boolean shouldEntryBeHidden(EntryStack<?> entryStack) {
        if (entryStack.getType() != VanillaEntryTypes.ITEM) {
            return false;
        }
        return ItemPredicates.shouldBeHidden(entryStack.castValue());
    }

}
