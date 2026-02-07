package appeng.client.commands;

import java.util.List;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import appeng.core.TLConfig;

public final class ClientCommands {

    public static final List<CommandBuilder> DEBUG_COMMANDS = List.of(
            ClientCommands::highlightGuiAreas);

    private ClientCommands() {
    }

    @FunctionalInterface
    public interface CommandBuilder {
        void build(LiteralArgumentBuilder<CommandSourceStack> builder);
    }

    private static void highlightGuiAreas(LiteralArgumentBuilder<CommandSourceStack> builder) {
        builder.then(Commands.literal("highlight_gui_areas").executes(context -> {
            var src = context.getSource();
            var toggle = !TLConfig.instance().isShowDebugGuiOverlays();
            TLConfig.instance().setShowDebugGuiOverlays(toggle);
            TLConfig.instance().save();
            src.sendSystemMessage(Component.literal("GUI Overlays: " + toggle));
            return 0;
        }));
    }
}
