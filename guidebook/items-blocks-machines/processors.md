---
navigation:
  parent: items-blocks-machines/items-blocks-machines-index.md
  title: Processors
  icon: processor_logic
  position: 010
categories:
- misc ingredients blocks
item_ids:
- tl2:processor_logic
- tl2:processor_calculation
- tl2:processor_engineering
- tl2:printed_silicon
- tl2:circuit_logic
- tl2:circuit_calculation
- tl2:circuit_engineering
- tl2:silicon
---

# Processors

<Row>
  <ItemImage id="processor_logic" scale="4" />

  <ItemImage id="processor_calculation" scale="4" />

  <ItemImage id="processor_engineering" scale="4" />
</Row>

Processors are one of the primary ingredients in TL2 devices and machines. They are also one of your first
big automation challenges. There are three types of processors, made with <ItemLink id="minecraft:gold_ingot" />, <ItemLink id="minecraft:amethyst_shard" />,
and <ItemLink id="minecraft:diamond" /> respectively. They are in a multi-step
process (usually achieved via a series of AKUTODO).

## Production Steps

<Column gap="5">
  1.  Gather/make the required ingredients: <ItemLink id="silicon" />, <ItemLink id="minecraft:redstone" />, <ItemLink id="minecraft:gold_ingot" />, <ItemLink id="minecraft:amethyst_shard" />, <ItemLink id="minecraft:diamond" />.

  <RecipeFor id="silicon" />

  <br />

  2.  Press the prerequisite printed circuit components

  <Row>
    <RecipeFor id="printed_silicon" />

    <RecipeFor id="circuit_logic" />
  </Row>

  <Row>
    <RecipeFor id="circuit_calculation" />

    <RecipeFor id="circuit_engineering" />
  </Row>

  <br />

  3.  Final assembly

  <Row>
    <RecipeFor id="processor_logic" />

    <RecipeFor id="processor_calculation" />
  </Row>

  <RecipeFor id="processor_engineering" />
</Column>
