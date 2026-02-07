---
navigation:
  parent: items-blocks-machines/items-blocks-machines-index.md
  title: Controller
  icon: controller
  position: 110
categories:
- network infrastructure
item_ids:
- tl2:controller
---

# The Controller

<BlockImage id="controller" p:state="online" scale="8" />

The controller is the routing hub of a [ME Network](../tl2-mechanics/me-network-connections.md).
Without it, a network is "ad-hoc" and can only have a max of 8 channel-using devices total.

It is not possible to have 2 controllers in one [ME Network](../tl2-mechanics/me-network-connections.md).

The controller provides 32 [Channels](../tl2-mechanics/channels.md) per face.

The controller requires 6 TL/t per controller block to
function. Each controller block can store 8000 TL, so larger networks might require additional
energy storage. See [energy](../tl2-mechanics/energy.md) for details.

Multiblock Controllers can be built in a fairly free form.

<GameScene zoom="2" background="transparent">
  <ImportStructure src="../assets/assemblies/controllers.snbt" />
  <IsometricCamera yaw="195" pitch="30" />
</GameScene>

However, there are a few rules that must be followed:

1.  All controller blocks on a [ME Network](../tl2-mechanics/me-network-connections.md) must be connected; else the blocks will turn red.
2.  The size of the controller must be within 7x7x7; else it will turn red.
3.  A controller can have 2 adjacent blocks in at most 1 axis; if a block violates this rule, it will disable and turn red.

<GameScene zoom="2" background="transparent">
  <ImportStructure src="../assets/assemblies/controller_rules.snbt" />
  <IsometricCamera yaw="195" pitch="30" />
</GameScene>

As long as all rules are followed and powered, the controller should glow and
cycle colors.

You can right-click on a controller to get the same GUI as a <ItemLink id="network_tool" />

## Recipe

<RecipeFor id="controller" />
