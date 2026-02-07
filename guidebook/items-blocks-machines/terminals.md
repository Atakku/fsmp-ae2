---
navigation:
  parent: items-blocks-machines/items-blocks-machines-index.md
  title: Terminals
  icon: crafting_terminal
  position: 210
categories:
- devices
item_ids:
- tl2:terminal
- tl2:crafting_terminal
- tl2:pattern_encoding_terminal
- tl2:pattern_access_terminal
---

# Terminals

<GameScene zoom="6" background="transparent">
  <ImportStructure src="../assets/assemblies/terminals.snbt" />
  <IsometricCamera yaw="195" pitch="30" />
</GameScene>

Terminals are the primary method by which an TL2 network interacts with *you*. There are several variants with differing functions.

Terminals will inherit the color of the [cable](cables.md) they are mounted on.

They are [cable subparts](../tl2-mechanics/cable-subparts.md).

## Terminal Placement

As a terminal is often the first [subpart](../tl2-mechanics/cable-subparts.md) someone might place,
it is common to get it wrong and place the terminal backwards. Here is an example of what to do and what not to do:

<GameScene zoom="6" background="transparent">
  <ImportStructure src="../assets/assemblies/terminal_placement.snbt" />
  <IsometricCamera yaw="195" pitch="30" />

  <LineAnnotation color="#ff3333" from="2.5 .5 .5" to="4.5 2.5 .5" alwaysOnTop={true} thickness="0.05"/>
  <LineAnnotation color="#ff3333" from="2.5 2.5 .5" to="4.5 .5 .5" alwaysOnTop={true} thickness="0.05"/>

  <LineAnnotation color="#33ff33" from="-.5 2.5 .5" to="1 .5 .5" alwaysOnTop={true} thickness="0.05"/>
  <LineAnnotation color="#33ff33" from="1 .5 .5" to="1.5 1 .5" alwaysOnTop={true} thickness="0.05"/>
</GameScene>

You still have a terminal and an energy acceptor, except now the terminal is the right way around and actually
connected to the network, and it all fits in a smaller space too.

<a name="terminal-ui"></a>

# Terminal Search

The searchbox accepts Regex terms, so you can, for example, write "gtceu:.*ore" to get all ores from Gregtech. Learning
Regex is left as an exercise for the reader.

# Terminal

<GameScene zoom="6" background="transparent">
  <ImportStructure src="../assets/blocks/terminal.snbt" />
  <IsometricCamera yaw="180" />
</GameScene>

Your basic terminal, allowing you to view and access the contents of your [network's storage](../tl2-mechanics/import-export-storage.md).

## The UI

There are several sections of a basic terminal's UI

The center section gives access to your network's storage. You can put things in and take things out. There are several
mouse/key shortcuts:

*   Left-click grabs a stack, right-click grabs half a stack.
*   Holding shift will freeze the displayed items in-place, stopping them from re-organizing themselves when quantities change or new items enter the system.
*   Right-clicking with a bucket or other fluid container will deposit the fluid, left-clicking a fluid in the terminal with
    an empty fluid container will withdraw the fluid.

The left section has settings buttons to:

*   Sort by different attributes like name, mod, and quantity
*   View stored, craftable, or both
*   View items, fluids, or both
*   Change the sort order
*   Open the detailed terminal settings window
*   Change the height of the terminal UI

On the right there are slots for <ItemLink id="view_cell" />s

## Recipe

<RecipeFor id="terminal" />

<a name="crafting-terminal-ui"></a>

# Crafting Terminal

<GameScene zoom="6" background="transparent">
  <ImportStructure src="../assets/blocks/crafting_terminal.snbt" />
  <IsometricCamera yaw="180" />
</GameScene>

The Crafting Terminal is similar to a regular terminal, with all the same settings and sections, but with an added crafting grid that will be automatically
refilled from [network storage](../tl2-mechanics/import-export-storage.md). Be careful when shift-clicking the output!

You should upgrade your terminal into a crafting terminal ASAP.

## The UI

The crafting terminal has the same UI as the regular terminal, but with an added crafting grid in the middle.

There are 2 additional buttons, to empty the crafting grid into network storage or your inventory.

## Recipe

<RecipeFor id="crafting_terminal" />
