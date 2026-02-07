---
navigation:
  parent: example-setups/example-setups-index.md
  title: Storage Network Cleanliness
  icon: drive
---

# Keeping Your Network Organized

Using filters, [partitions](../items-blocks-machines/cell_workbench.md), and storage priority,
you can set up several tiers of storage for various kinds of things.

The kinds of storage tend to be:
* General storage, for all the random stuff you have a few to a few thousand of. This uses small [cells](../items-blocks-machines/storage_cells.md),
like 1k or 4k.
* Bulk storage, for all the stuff you have more than a few thousand of, like cobble or iron. This uses big cells like 16k or 64k.

The priorities are set up so that when items are dumped into the main network, it first tries to store them in the specialized
bulk or local storage, and if that can't be done (due to filters and partitions), it then puts the items in general storage.
This means that items WILL NOT ACTIVELY MOVE from one storage ot the other, but will "migrate" as they enter and leave the network.
To actively move items, use an <ItemLink id="io_port" />.

<GameScene zoom="3" interactive={true}>
  <ImportStructure src="../assets/assemblies/network_storage_types.snbt" />

    <BoxAnnotation color="#33dd33" min="11 0 3" max="12 1 4" thickness="0.05">
        Bulk Storage. In this case a partitioned 64k cell in a drive with high priority. This cell is partitioned to
        cobblestone and iron. It has an Equal Distribution Card, so it won't be completely filled with cobblestone, leaving
        no space for iron. The drive has a high priority so whenever cobble or iron enters the network, it goes to this storage bus,
        and whenever cobble or iron is pulled from the network, it is pulled from *evere except here*, so cobble and iron "migrate" to this cell.
    </BoxAnnotation>

    <BoxAnnotation color="#33dddd" min="11 0 5" max="12 1 6" thickness="0.05">
        General Storage. In this case a drive full of 16k cells. These cells are not partitioned. The drive has a neutral priority
        (in this case 0) so whenever something enters the network, it goes to the specialized bulk or local storage first,
        and whenever something is pulled from the network, it is pulled from here first, so items that have specialized storage naturally
        "migrate" out of general storage.
    </BoxAnnotation>

    <BoxAnnotation color="#88ff88" min="11 0 8" max="12 1 9" thickness="0.05">
        This IO Port plays an important role in keeping the network organized. Because storage priority does not *actively*
        move items, cells used in General Gtorage should be periodically "shuffled" through an IO port to move items that have a
        place in specialized storage into that specialized storage. This "defragments" the storage, making sure things aren't
        being stored in multiple places.
    </BoxAnnotation>

  <IsometricCamera yaw="270" pitch="30" />
</GameScene>
