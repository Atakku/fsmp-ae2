# PonderJS NBT Interface

TL2 normally uses server-side only state and synchronizes it using a binary blob to the client. This
obviously will not work with PonderJS's `modifyTileNBT` calls.

## Cable Bus

To place TL2 cables with attached parts in PonderJS, the `tl2:cable_bus` block must be placed first.
Then it's NBT data can be modified to actually set the cable and attach parts.

The cable bus supports one NBT property for each side and one for the center where the cable is placed.

The property names are `cable` for the cable and `down`, `up`, `north`, `south`, `west`, `east` for the part on the
respective side of the cable.

A part tag requires at least an `id` property, which is the Item ID of the part that should be placed at that location
of the cable bus. Any additional properties are part-type specific.

You can find all TL2 part ids in [TLPartIds.java](./src/main/java/appeng/api/ids/TLPartIds.java).

### Cables

The following code places a simple uncolored glass cable at 0,0,0:

```javascript
scene.world.setBlocks([0, 0, 0], "tl2:cable_bus", false);
scene.world.modifyTileNBT([0, 0, 0], nbt => {
    nbt.cable = {
        id: "tl2:fluix_glass_cable"
    };
});
```

Cables also support the following properties to configure their visual appearance:

* `visual.connections` a list of Directions configures which sides the cable has a connection to.
* `visual.powered` a boolean that toggles whether the cable has power. This is only used by smart cables to enable the
  channel display.
* `visual.channels` an integer that sets the number of channels carried by the cable on all sides at once. Used by smart
  cables.
* `visual.channelsWest`, `channelsUp`, etc. set the number of channels carried by the cable to that particular side.
  Used by
  smart cables.

Full example:

```javascript
scene.world.setBlocks([0, 0, 0, 4, 0, 0], "tl2:cable_bus", false);
scene.world.modifyTileNBT([0, 0, 0, 4, 0, 0], nbt => {
    nbt.cable = {
        id: "tl2:fluix_smart_cable",
        visual: {
            channels: 5, // Show 5 channels on the cable
            powered: true, // actually show the channels
            connections: ['west', 'east'] // show a connection from west to east
        }
    };
});
```

### Parts

All parts support the boolean visual properties `powered` to enable the status indicators on the part,
and `missingChannel` to enable the missing channel indicator light.

The following example places a cable and attaches a storage bus facing downward. The cable is green to
make the indicator light more visible.

```javascript
// Place green cable with storage bus facing downwards
scene.world.setBlocks([2, 1, 2], "tl2:cable_bus", false);
scene.world.modifyTileNBT([2, 1, 2], nbt => {
    nbt.cable = {
        id: "tl2:green_covered_cable"
    };
    nbt.down = {
        id: 'tl2:storage_bus'
    };
});

// Toggle the power light on
scene.idle(20);
scene.world.modifyTileNBT([2, 1, 2], nbt => {
    nbt.down.visual.powered = true;
}, true);

// Switch to missing channel state
scene.idle(20);
scene.world.modifyTileNBT([2, 1, 2], nbt => {
    nbt.down.visual.missingChannel = true;
}, true);
```

### Part-Specific Properties

#### Storage Monitor & Conversion Monitor

NBT Example:

```javascript
nbt.north = {
    id: 'tl2:storage_monitor',
    isLocked: true, // Shows the lock indicator (default: false)
    configuredItem: {
        "#c": "tl2:i", // tl2:i for items, tl2:f for fluids (no default)
        "id": "minecraft:stick", // ID of shown item or fluid
        "tag": {}, // NBT of shown item or fluid (default is null -> no NBT)
    },
    visual: {
        powered: true,
        amount: 1234567 // Amount shown on the monitor
    }
};
```

#### Level Emitters

NBT Example:

```javascript
nbt.north = {
    id: 'tl2:level_emitter',
    visual: {
        on: true // Toggles the level emitter on or off
    }
};
```

#### Annihilation Plane / Formation Plane

The planes will automatically visually connect to adjacent planes. No NBT is needed for this.

NBT Example:

```javascript
nbt.up = {
    id: 'tl2:annihilation_plane',
    visual: {
        powered: true // Enables the texture animation on the plane (default: false)
    }
};
```

#### Rotating All Monitors / Terminals

All "reporting parts", which includes all terminals and the storage and conversion monitors, can be rotated
further around the axis they're attached to. This is controlled using the `visual.spin` property.

NBT Example:

```javascript
nbt.north = {
    id: "tl2:terminal",
    spin: 1 // Rotation. Default is 0. 0=0°, 1=90°, 2=180° 3=270°.
};
```

### Block Entities

Some block entities also support specific NBT to manipulate their visual state.

In general, TL block entities support the `forward` and `up` NBT properties to set their orientation.
The default is `forward` = `north` and `up` = `up`.

#### ME Drive

Example:

```javascript
scene.world.setBlock([2, 1, 2], 'tl2:drive', false);
scene.world.modifyTileNBT([2, 1, 2], nbt => {
    nbt.visual = {
        online: true, // Controls whether the LEDs are on or off (default: false)
        // Set the cells using cell0 to cell9
        cell2: {
            id: 'tl2:item_storage_cell_64k', // Item ID of cell
            state: 'empty' // Status of cell LED
        },
        cell5: {
            id: 'tl2:fluid_storage_cell_64k',
            state: 'full'
        }
    };
}, true);
```

Supported cell states are:

* `empty` The cell is completely empty (green)
* `not_empty` The cell is neither empty, nor full (blue)
* `types_full` The types are full (orange)
* `full` Full (red)

#### Paint Splotches

These are very hard to do with NBT since they use binary encoding. Use a mater cannon to place the paint
and structure blocks to export them.

#### Sky Stone Tank

Uses standard Forge Tank NBT, which means:

* `FluidName` = ID of fluid stored
* `Amount` = How much
* `Tag` = Optional NBT of fluid

#### Wireless Access Point

Uses a combination of block states and the standard NBT for rotation of TL2 block entities.

Example:

```javascript
scene.world.setBlock([2, 1, 2], 'tl2:wireless_access_point', false);
// States: off, on, has_channel
scene.world.modifyBlock([2, 1, 2], state => state.with("state", "has_channel"), false);
scene.world.modifyTileNBT([2, 1, 2], nbt => {
    nbt.forward = "up"; // Forward is where the antenna is pointing
    nbt.up = "north";
});
```

#### Inventories

Some TL2 machines have internal inventories. These save their slots under the `inv` tag. Each slot is named
`item<x>` with x starting from 0 and counting up. Each slot uses the standard Vanilla ItemStack NBT format.

Example:

```javascript
scene.world.modifyTileNBT([2, 1, 2], nbt => {
    nbt.inv = {
        item0: {
            id: 'minecraft:stick', // Item ID
            Count: 1, // Amount
            tag: null // Optional NBT for the item
        }
    };
});
```

#### Charger

NBT Example:

```javascript
scene.world.setBlock([2, 1, 2], 'tl2:charger', false);
scene.world.modifyTileNBT([2, 1, 2], nbt => {
    // Put a crystal in
    nbt.inv = {
        item0: {
            id: 'tl2:certus_quartz_crystal',
            Count: 1
        }
    };
}, true);

scene.idle(20); // wait a bit

scene.world.modifyTileNBT([2, 1, 2], nbt => {
    // Put a *charged* crystal in
    nbt.inv = {
        item0: {
            id: 'tl2:charged_certus_quartz_crystal',
            Count: 1
        }
    };
}, true);
```
