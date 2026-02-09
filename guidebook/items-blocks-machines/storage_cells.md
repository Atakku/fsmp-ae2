---
navigation:
  parent: items-blocks-machines/items-blocks-machines-index.md
  title: Storage Cells
  icon: cell_item_1k
  position: 410
categories:
- tools
item_ids:
- tl2:housing_item
- tl2:housing_fluid
- tl2:component_1k
- tl2:component_4k
- tl2:component_16k
- tl2:component_64k
- tl2:cell_item_1k
- tl2:cell_item_4k
- tl2:cell_item_16k
- tl2:cell_item_64k
- tl2:cell_fluid_1k
- tl2:cell_fluid_4k
- tl2:cell_fluid_16k
- tl2:cell_fluid_64k
---

# Storage Cells

<Column>
  <Row>
    <ItemImage id="cell_item_1k" scale="4" />

    <ItemImage id="cell_item_4k" scale="4" />

    <ItemImage id="cell_item_16k" scale="4" />

    <ItemImage id="cell_item_64k" scale="4" />
  </Row>

  <Row>
    <ItemImage id="cell_fluid_1k" scale="4" />

    <ItemImage id="cell_fluid_4k" scale="4" />

    <ItemImage id="cell_fluid_16k" scale="4" />

    <ItemImage id="cell_fluid_64k" scale="4" />
  </Row>
</Column>

Storage Cells are one of the primary methods of storage in Applied Energistics. They go in <ItemLink id="drive" />s
or <ItemLink id="chest" />s.

See [Bytes and Types](../tl2-mechanics/bytes-and-types.md) for an explanation of their capacities in bytes and types.

Storage components can be removed from the housing if the cell is empty by shift-right clicking with the cell in your hand.

<Row>
    <Recipe id="upgrade/cell_item_1k_to_4k" />

    You can upgrade storage cells to higher tiers by combining them with higher-tier storage components in a crafting grid. Their content will be retained, and the lower-tier component is  returned.
</Row>

## Storage Capacity with Varying Type Count

The [upfront cost of types](../tl2-mechanics/bytes-and-types.md) is such that a cell holding 1 type can hold 2x as much as a cell with all 63 types in use.

| Cell                                     | Total Capacity of Cell With 1 Type In Use | Total Capacity of Cell With 63 Types In Use |
| ---------------------------------------- | ----------------------------------------: | ------------------------------------------: |
| <ItemLink id="cell_item_1k" />   |                                     8,128 |                                       4,160 |
| <ItemLink id="cell_item_4k" />   |                                    32,512 |                                      16,640 |
| <ItemLink id="cell_item_16k" />  |                                   130,048 |                                      66,560 |
| <ItemLink id="cell_item_64k" />  |                                   520,192 |                                     266,240 |


## Partitioning

Cells can be filtered to only accept certain items, similar to how <ItemLink id="storage_bus" />ses can be filtered. This is
done in a <ItemLink id="cell_workbench" />.

Items can be dragged into the slots from JEI/REI even if you don't actually have any of that item.

## Upgrades

Storage cells support the following [upgrades](upgrade_cards.md), inserted via a <ItemLink id="cell_workbench" />:

*   <ItemLink id="fuzzy_card" /> (not available on fluid cells) lets the cell be partitioned by damage level and/or ignore item NBT
*   <ItemLink id="inverter_card" /> switches the filter from a whitelist to a blacklist
*   <ItemLink id="equal_distribution_card" /> allocates the same amount of cell byte space to each type, so one type cannot fill up the entire cell
*   <ItemLink id="void_card" /> voids items inserted if the cell is full (or that specific type's allocated space in the
    case of an equal distribution card), useful for stopping farms from backing up. Be careful to partition this!

# Housings

Cells can be made with a storage component and a housing or with the housing recipe around a storage component:

<Row>
  <Recipe id="network/cells/cell_item_1k" />

  <Recipe id="network/cells/cell_item_1k_storage" />
</Row>

Housings by themselves are crafted like so:

<Row>
  <RecipeFor id="housing_item" />

  <RecipeFor id="housing_fluid" />
</Row>

# Storage Components

Storage Components are the core of all TL2 cells, determining the capacity of the cells. Each tier increases the capacity
by 4x and costs 3 of the previous tier.

<Column>
  <Row>
    <RecipeFor id="component_1k" />

    <RecipeFor id="component_4k" />
  </Row>
  <Row>
    <RecipeFor id="component_16k" />

    <RecipeFor id="component_64k" />
  </Row>
</Column>

# Item Storage Cells

Item storage cells can hold up to 63 distinct types of items, and are available in all the standard capacities.

<Column>
  <Row>
    <Recipe id="network/cells/cell_item_1k_storage" />

    <Recipe id="network/cells/cell_item_4k_storage" />
  </Row>

  <Row>
    <Recipe id="network/cells/cell_item_16k_storage" />

    <Recipe id="network/cells/cell_item_64k_storage" />
  </Row>
</Column>

# Fluid Storage Cells

Fluid storage cells can hold up to 5 distinct types of fluids, and are available in all the standard capacities.

<Column>
  <Row>
    <Recipe id="network/cells/cell_fluid_1k_storage" />

    <Recipe id="network/cells/cell_fluid_4k_storage" />
  </Row>

  <Row>
    <Recipe id="network/cells/cell_fluid_16k_storage" />

    <Recipe id="network/cells/cell_fluid_64k_storage" />
  </Row>
</Column>

# Creative Storage Cell

<Row>
  <ItemImage id="cell_creative" scale="2" />
</Row>

Creative cells **do not provide infinite storage**. Instead, they act as infinite sources and sinks of whatever
item or fluid you [partition](cell_workbench.md) them to.
