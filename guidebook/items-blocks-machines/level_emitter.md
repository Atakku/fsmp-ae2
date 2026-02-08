---
navigation:
  parent: items-blocks-machines/items-blocks-machines-index.md
  title: Level Emitter
  icon: level_emitter
  position: 220
categories:
- devices
item_ids:
- tl2:level_emitter
- tl2:energy_level_emitter
---

# The Level Emitter

<GameScene zoom="8" background="transparent">
  <ImportStructure src="../assets/blocks/level_emitter.snbt" />
</GameScene>

The Level Emitter emits a redstone signal depending on the quantity of an item in
[network storage](../tl2-mechanics/import-export-storage.md).

There is also a version that emits a redstone signal depending on the [energy](../tl2-mechanics/energy.md) stored
in your network.

Items and fluids can be dragged into the slot from JEI/REI even if you don't actually have any of that item.

Right-click with a fluid container (like a bucket or fluid tank) to set that fluid as a filter instead of the bucket or tank item.

They are [cable subparts](../tl2-mechanics/cable-subparts.md).

Unlike other devices, level emitters *do not* require a [channel](../tl2-mechanics/channels.md).

## Settings

*   The Level Emitter can be set to either "greater than/equal to" or "less than" mode
*   When a <ItemLink id="crafting_card" /> is inserted, it can be set to "emit redstone while item is crafting" or
    "emit redstone to craft item"

## Upgrades

The level emitter supports the following [upgrades](upgrade_cards.md):

*   <ItemLink id="fuzzy_card" /> lets the emitter filter by damage level and/or ignore item NBT
*   <ItemLink id="crafting_card" /> enables the crafting functionality

## Recipe

<RecipeFor id="level_emitter" />

<RecipeFor id="energy_level_emitter" />
