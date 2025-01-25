---
navigation:
  parent: ae2-mechanics/ae2-mechanics-index.md
  title: Energy
  icon: energy_cell
---

# Energy

Your network will require energy to run. Networks have a pool of energy that [devices](../ae2-mechanics/devices.md) directly pull from, and
<ItemLink id="energy_cell" />s, (and <ItemLink id="controller" />s) add to. You can
see the energy statistics for a network by right-clicking anywhere on it with a <ItemLink id="network_tool" /> or by
right-clicking the network's controller, if it has one. This network-wide storage and distribution  means that
there are no energy transfer rate limits, so devices can pull arbitrarily high amounts of energy and
are only limited by your energy storage.

## Energy Storage

<Row>
  <BlockImage id="energy_cell" scale="4" p:fullness="4" />

  <BlockImage id="dense_energy_cell" scale="4" p:fullness="4" />

  <BlockImage id="creative_energy_cell" scale="4" />
</Row>

For relatively obvious reasons, a network cannot intake or consume more energy in a gametick than it can store. If a network
can only store 800 AE, when its [devices](../ae2-mechanics/devices.md) request energy, they will only be able to use up to 800 AE (assuming the storage is full).

This is a common cause for odd behavior, where one makes a small network with just an energy cell, drive, terminal, and
some devices and tries to dump an inventory full of cobblestone from their inventory into the network. Inserting that cobble all at once in a
single gametick requires more energy than the network has in storage, so not all of the cobble is inserted, the network
runs out of energy, and thus reboots.

**This can be solved by the addition of energy cells.**

Networks have a built-in energy buffer of 25 AE per cable, machine or part.

<ItemLink id="controller" />s have a small amount of internal energy storage, 8,000 AE

The <ItemLink id="energy_cell" /> can store 200k AE, and just one should be sufficient for most use cases, handling the power surges
of normal network use with ease.

The <ItemLink id="dense_energy_cell" /> can store 1.6M AE and is for when you want to run a network off of stored power, or
handle the massive instantaneous energy draw of large [spatial storage](spatial-io.md) setups.

The <ItemLink id="creative_energy_cell" /> is a creative item for testing, providing UNLIMITED POWAHHHH or whatever.
