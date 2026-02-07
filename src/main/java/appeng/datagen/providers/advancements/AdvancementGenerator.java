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

package appeng.datagen.providers.advancements;

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

import java.util.function.Consumer;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import appeng.api.util.TLColor;
import appeng.core.definitions.TLBlocks;
import appeng.core.definitions.TLItems;
import appeng.core.definitions.TLParts;
import appeng.core.stats.AdvancementTriggers;
import appeng.datagen.providers.localization.LocalizationProvider;
import appeng.datagen.providers.tags.ConventionTags;

public class AdvancementGenerator implements AdvancementProvider.AdvancementGenerator {
    private final LocalizationProvider localization;

    public AdvancementGenerator(LocalizationProvider localization) {
        this.localization = localization;
    }

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> consumer,
            ExistingFileHelper existingFileHelper) {
        var root = Advancement.Builder.advancement()
                .display(
                        TLItems.FLUIX_CRYSTAL,
                        localization.component("achievement.tl2.Fluix", "Unnatural"),
                        localization.component("achievement.tl2.Fluix.desc", "Create Fluix Crystals"),
                        null /* background */,
                        AdvancementType.TASK,
                        true /* showToast */,
                        true /* announceChat */,
                        false /* hidden */
                )
                .addCriterion("certus", InventoryChangeTrigger.TriggerInstance.hasItems(TLItems.FLUIX_CRYSTAL))
                .save(consumer, "tl2:main/fluix");

        var controller = Advancement.Builder.advancement()
                .display(
                        TLBlocks.CONTROLLER,
                        localization.component("achievement.tl2.Controller", "Networking Switchboard"),
                        localization.component("achievement.tl2.Controller.desc", "Craft a Controller"),
                        null /* background */,
                        AdvancementType.TASK,
                        true /* showToast */,
                        true /* announceChat */,
                        false /* hidden */
                )
                .parent(root)
                .addCriterion("certus", InventoryChangeTrigger.TriggerInstance.hasItems(TLBlocks.CONTROLLER))
                .save(consumer, "tl2:main/controller");

        var storageCell = Advancement.Builder.advancement()
                .display(
                        TLItems.ITEM_CELL_64K,
                        localization.component("achievement.tl2.StorageCell", "Better Than Chests"),
                        localization.component("achievement.tl2.StorageCell.desc", "Craft a Storage Cell"),
                        null /* background */,
                        AdvancementType.TASK,
                        false,
                        false,
                        false)
                .parent(controller)
                .addCriterion("c1k", InventoryChangeTrigger.TriggerInstance.hasItems(TLItems.ITEM_CELL_1K))
                .addCriterion("c4k", InventoryChangeTrigger.TriggerInstance.hasItems(TLItems.ITEM_CELL_4K))
                .addCriterion("c16k", InventoryChangeTrigger.TriggerInstance.hasItems(TLItems.ITEM_CELL_16K))
                .addCriterion("c64k", InventoryChangeTrigger.TriggerInstance.hasItems(TLItems.ITEM_CELL_64K))
                .addCriterion("c256k", InventoryChangeTrigger.TriggerInstance.hasItems(TLItems.ITEM_CELL_256K))
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(consumer, "tl2:main/storage_cell");

        var ioport = Advancement.Builder.advancement()
                .display(
                        TLBlocks.IO_PORT,
                        localization.component("achievement.tl2.IOPort", "Storage Cell Shuffle"),
                        localization.component("achievement.tl2.IOPort.desc", "Craft an IO Port"),
                        null /* background */,
                        AdvancementType.TASK,
                        true /* showToast */,
                        true /* announceChat */,
                        false /* hidden */
                )
                .parent(storageCell)
                .addCriterion("certus", InventoryChangeTrigger.TriggerInstance.hasItems(TLBlocks.IO_PORT))
                .save(consumer, "tl2:main/ioport");

        var craftingTerminal = Advancement.Builder.advancement()
                .display(
                        TLParts.CRAFTING_TERMINAL,
                        localization.component("achievement.tl2.CraftingTerminal", "A (Much) Bigger Table"),
                        localization.component("achievement.tl2.CraftingTerminal.desc", "Craft a Crafting Terminal"),
                        null /* background */,
                        AdvancementType.TASK,
                        true /* showToast */,
                        true /* announceChat */,
                        false /* hidden */
                )
                .parent(controller)
                .addCriterion("certus", InventoryChangeTrigger.TriggerInstance.hasItems(TLParts.CRAFTING_TERMINAL))
                .save(consumer, "tl2:main/crafting_terminal");

        var glassCable = Advancement.Builder.advancement()
                .display(
                        TLParts.GLASS_CABLE.item(TLColor.TRANSPARENT),
                        localization.component("achievement.tl2.GlassCable", "Fluix Energy Connection"),
                        localization.component("achievement.tl2.GlassCable.desc", "Craft ME Glass Cable"),
                        null /* background */,
                        AdvancementType.TASK,
                        true /* showToast */,
                        true /* announceChat */,
                        false /* hidden */
                )
                .parent(root)
                .addCriterion("certus",
                        InventoryChangeTrigger.TriggerInstance
                                .hasItems(ItemPredicate.Builder.item().of(ConventionTags.GLASS_CABLE).build()))
                .save(consumer, "tl2:main/glass_cable");

        var network1 = Advancement.Builder.advancement()
                .display(
                        TLParts.COVERED_CABLE.item(TLColor.TRANSPARENT),
                        localization.component("achievement.tl2.Networking1", "Network Apprentice"),
                        localization.component("achievement.tl2.Networking1.desc",
                                "Reach 8 channels using devices on a network."),
                        null /* background */,
                        AdvancementType.TASK,
                        true /* showToast */,
                        true /* announceChat */,
                        false /* hidden */
                )
                .parent(glassCable)
                .addCriterion("cable", AdvancementTriggers.networkApprenticeCriterion())
                .save(consumer, "tl2:main/network1");

        var network2 = Advancement.Builder.advancement()
                .display(
                        TLParts.SMART_CABLE.item(TLColor.TRANSPARENT),
                        localization.component("achievement.tl2.Networking2", "Network Engineer"),
                        localization.component("achievement.tl2.Networking2.desc",
                                "Reach 128 channels using devices on a network."),
                        null /* background */,
                        AdvancementType.TASK,
                        true /* showToast */,
                        true /* announceChat */,
                        false /* hidden */
                )
                .parent(network1)
                .addCriterion("cable", AdvancementTriggers.networkEngineerCriterion())
                .save(consumer, "tl2:main/network2");

        var network3 = Advancement.Builder.advancement()
                .display(
                        TLParts.SMART_DENSE_CABLE.item(TLColor.TRANSPARENT),
                        localization.component("achievement.tl2.Networking3", "Network Administrator"),
                        localization.component("achievement.tl2.Networking3.desc",
                                "Reach 2048 channels using devices on a network."),
                        null /* background */,
                        AdvancementType.TASK,
                        true /* showToast */,
                        true /* announceChat */,
                        false /* hidden */
                )
                .parent(network2)
                .addCriterion("cable", AdvancementTriggers.networkAdminCriterion())
                .save(consumer, "tl2:main/network3");

        var portableCell = Advancement.Builder.advancement()
                .display(
                        TLItems.PORTABLE_ITEM_CELL1K,
                        localization.component("achievement.tl2.PortableCell", "Storage Nomad"),
                        localization.component("achievement.tl2.PortableCell.desc", "Craft a Portable Cell"),
                        null /* background */,
                        AdvancementType.TASK,
                        false,
                        false,
                        false)
                .parent(storageCell)
                .addCriterion("pc_1k",
                        InventoryChangeTrigger.TriggerInstance.hasItems(TLItems.PORTABLE_ITEM_CELL1K))
                .addCriterion("pc_4k",
                        InventoryChangeTrigger.TriggerInstance.hasItems(TLItems.PORTABLE_ITEM_CELL4K))
                .addCriterion("pc_16k",
                        InventoryChangeTrigger.TriggerInstance.hasItems(TLItems.PORTABLE_ITEM_CELL16K))
                .addCriterion("pc_64k",
                        InventoryChangeTrigger.TriggerInstance.hasItems(TLItems.PORTABLE_ITEM_CELL64K))
                .addCriterion("pc_256k",
                        InventoryChangeTrigger.TriggerInstance.hasItems(TLItems.PORTABLE_ITEM_CELL256K))
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(consumer, "tl2:main/portable_cell");
    }
}
