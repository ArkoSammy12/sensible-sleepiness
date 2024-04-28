package xd.arkosammy.sensiblesleepiness.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import xd.arkosammy.sensiblesleepiness.mode.ISleepyModeAccess;

public abstract class SensibleSleepinessCommandManager {

    private SensibleSleepinessCommandManager(){}
    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment){

        LiteralCommandNode<ServerCommandSource> sensibleSleepinessNode = CommandManager
                .literal("sleepy-mode")
                .executes(SensibleSleepinessCommandManager::getSleepyModeCommand)
                .build();

        dispatcher.getRoot().addChild(sensibleSleepinessNode);
        SleepyModeCommands.register(sensibleSleepinessNode);

    }

    private static int getSleepyModeCommand(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ctx.getSource().getPlayerOrThrow().sendMessage(Text.literal("Sleepy mode currently set to: " + ((ISleepyModeAccess)ctx.getSource().getPlayerOrThrow()).sensible_sleepiness$getSleepyMode().getDisplayName()));
        return Command.SINGLE_SUCCESS;

    }

}
