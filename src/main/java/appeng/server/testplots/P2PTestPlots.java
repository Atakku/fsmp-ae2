package appeng.server.testplots;

import static appeng.server.testplots.P2PPlotHelper.linkTunnels;
import static appeng.server.testplots.P2PPlotHelper.placeTunnel;

import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.commons.lang3.mutable.MutableShort;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;

import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEParts;
import appeng.parts.p2p.MEP2PTunnelPart;
import appeng.parts.reporting.AbstractPanelPart;
import appeng.parts.reporting.PanelPart;
import appeng.server.testworld.PlotBuilder;

@TestPlotClass
public class P2PTestPlots {
    @TestPlot("p2p_light")
    public static void light(PlotBuilder plot) {
        var origin = BlockPos.ZERO;
        placeTunnel(plot, AEParts.LIGHT_P2P_TUNNEL);
        plot.block(origin.west().west(), Blocks.REDSTONE_LAMP);
        var leverPos = origin.west().west().above();
        plot.block(leverPos, Blocks.LEVER);
        var outputPos = origin.east().east();
        plot.test(helper -> {
            MutableInt lightLevel = new MutableInt(0);
            helper.startSequence()
                    .thenIdle(20)
                    .thenExecute(() -> {
                        lightLevel.setValue(
                                helper.getLevel().getBrightness(LightLayer.BLOCK, helper.absolutePos(outputPos)));
                        helper.pullLever(leverPos);
                    })
                    .thenWaitUntil(() -> {
                        var newLightLevel = helper.getLevel().getBrightness(LightLayer.BLOCK,
                                helper.absolutePos(outputPos));
                        helper.check(
                                newLightLevel > lightLevel.getValue(),
                                "Light-Level didn't increase");
                    })
                    .thenExecute(() -> helper.pullLever(leverPos))
                    .thenWaitUntil(() -> {
                        var newLightLevel = helper.getLevel().getBrightness(LightLayer.BLOCK,
                                helper.absolutePos(outputPos));
                        helper.check(
                                newLightLevel <= lightLevel.getValue(),
                                "Light-Level didn't reset");
                    })
                    .thenSucceed();
        });
    }

    /**
     * Tests that when a P2P tunnel is allocated/unallocated a channel, it connects/disconnects correctly.
     */
    @TestPlot("p2p_channel_reconnect_behavior")
    public static void testOutOfChannelReconnectBehavior(PlotBuilder plot) {
        var origin = BlockPos.ZERO;

        plot.creativeEnergyCell(origin.below());
        plot.block(origin, AEBlocks.CONTROLLER);

        // Build the west loop with a power connection into the P2P grid
        plot.cable(origin.west()).part(Direction.WEST, AEParts.ME_P2P_TUNNEL);
        plot.cable(origin.west().north());
        plot.cable(origin.west().north().west());
        plot.cable(origin.west().north().west().south())
                .part(Direction.NORTH, AEParts.QUARTZ_FIBER);

        // Build the east loop with the toggleable channel-sink
        // first block uses 4 channels
        plot.cable(origin.east())
                .part(Direction.NORTH, AEParts.TERMINAL)
                .part(Direction.SOUTH, AEParts.TERMINAL)
                .part(Direction.UP, AEParts.TERMINAL)
                .part(Direction.DOWN, AEParts.TERMINAL);
        // next block uses 3 to leave 1 for the P2P
        plot.cable(origin.east().east())
                .part(Direction.NORTH, AEParts.TERMINAL)
                .part(Direction.UP, AEParts.TERMINAL)
                .part(Direction.DOWN, AEParts.TERMINAL)
                .part(Direction.SOUTH, AEParts.TOGGLE_BUS);
        // lever to toggle the toggle bus
        var leverPos = origin.east().east().north();
        plot.block(leverPos, Blocks.REDSTONE_LAMP);
        plot.leverOn(leverPos.above().above(), Direction.DOWN);
        // the toggle-bus-branch with another terminal on it to consume the full 8 channels
        plot.cable(origin.east().east().south())
                .part(Direction.UP, AEParts.TERMINAL);

        // Make a somewhat winded cable to be long enough, so it's longer than the path via the toggle-bus node
        // to the terminal
        plot.cable(origin.east().east().east())
                .part(Direction.EAST, AEParts.CABLE_ANCHOR);
        plot.cable(origin.east().east().east().north());
        plot.cable(origin.east().east().east().north().east())
                .part(Direction.EAST, AEParts.CABLE_ANCHOR);
        plot.cable(origin.east().east().east().north().east().south());
        plot.cable(origin.east().east().east().north().east().south().east());
        var p2pOutputPos = origin.east().east().east().north().east().south().east().north();
        plot.cable(p2pOutputPos).part(Direction.EAST, AEParts.ME_P2P_TUNNEL);
        // This monitor will only be lit if the connection exists since it would have no power otherwise
        plot.cable(p2pOutputPos.east()).part(Direction.UP, AEParts.MONITOR);

        plot.test(helper -> {
            var freq = new MutableShort();
            var lightPanel = new MutableObject<AbstractPanelPart>();
            helper.startSequence()
                    .thenWaitUntil(() -> helper.getGrid(origin))
                    .thenExecute(() -> {
                        lightPanel.setValue(helper.getPart(p2pOutputPos.east(), Direction.UP, PanelPart.class));

                        var grid = helper.getGrid(origin);
                        var inputPos = helper.absolutePos(origin.west());
                        var outputPos = helper.absolutePos(p2pOutputPos);
                        freq.setValue(linkTunnels(grid, MEP2PTunnelPart.class, inputPos, outputPos));
                    })
                    .thenWaitUntil(() -> {
                        helper.check(
                                lightPanel.getValue().getMainNode().isOnline(),
                                "The panel should initially be on");
                    })
                    // This toggles the toggle bus and will make the P2P run out of channels
                    .thenExecute(() -> helper.pullLever(leverPos.above()))
                    .thenWaitUntil(() -> {
                        var inputTunnel = helper.getPart(p2pOutputPos, Direction.EAST, MEP2PTunnelPart.class);
                        if (inputTunnel.getMainNode().isOnline()) {
                            helper.fail("should be offline", p2pOutputPos);
                        }
                    })
                    .thenWaitUntil(() -> {
                        if (lightPanel.getValue().getMainNode().isOnline()) {
                            helper.fail("should be offline", p2pOutputPos.east());
                        }
                    })
                    // This toggles the toggle bus and will make the P2P get its channel back
                    .thenExecute(() -> helper.pullLever(leverPos.above()))
                    .thenWaitUntil(() -> {
                        var inputTunnel = helper.getPart(p2pOutputPos, Direction.EAST, MEP2PTunnelPart.class);
                        if (!inputTunnel.getMainNode().isOnline()) {
                            helper.fail("should be online", p2pOutputPos);
                        }
                    })
                    .thenWaitUntil(() -> {
                        if (!lightPanel.getValue().getMainNode().isOnline()) {
                            helper.fail("should be online", p2pOutputPos.east());
                        }
                    })
                    .thenSucceed();
        });
    }

}
