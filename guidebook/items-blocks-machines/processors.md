---
navigation:
  parent: items-blocks-machines/items-blocks-machines-index.md
  title: Processors
  icon: logic_processor
  position: 010
categories:
- misc ingredients blocks
item_ids:
- tl2:logic_processor
- tl2:calculation_processor
- tl2:engineering_processor
- tl2:printed_silicon
- tl2:printed_logic_processor
- tl2:printed_calculation_processor
- tl2:printed_engineering_processor
- tl2:silicon
---

# Processors

<Row>
  <ItemImage id="logic_processor" scale="4" />

  <ItemImage id="calculation_processor" scale="4" />

  <ItemImage id="engineering_processor" scale="4" />
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

    <RecipeFor id="printed_logic_processor" />
  </Row>

  <Row>
    <RecipeFor id="printed_calculation_processor" />

    <RecipeFor id="printed_engineering_processor" />
  </Row>

  <br />

  3.  Final assembly

  <Row>
    <RecipeFor id="logic_processor" />

    <RecipeFor id="calculation_processor" />
  </Row>

  <RecipeFor id="engineering_processor" />
</Column>
