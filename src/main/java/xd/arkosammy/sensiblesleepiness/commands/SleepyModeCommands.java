package xd.arkosammy.sensiblesleepiness.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import xd.arkosammy.sensiblesleepiness.mode.SleepyMode;
import xd.arkosammy.sensiblesleepiness.mode.ISleepyModeAccess;

public abstract class SleepyModeCommands {

    private SleepyModeCommands(){}

     static void register(LiteralCommandNode<ServerCommandSource> sleepyModeNode){

        //Insomnia node
        LiteralCommandNode<ServerCommandSource> insomniaNode = CommandManager
                .literal("insomnia")
                .executes(SleepyModeCommands::setInsomniaCommand)
                .build();

        //Hypersomnia node
        LiteralCommandNode<ServerCommandSource> hypersomniaNode = CommandManager
                .literal("hypersomnia")
                .executes(SleepyModeCommands::setHypersomniaCommand)
                .build();

        //Parasomnia node
        LiteralCommandNode<ServerCommandSource> parasomniaNode = CommandManager
                .literal("parasomnia")
                .executes(SleepyModeCommands::setParasomniaCommand)
                .build();

        //Sleepy mode nodes
        sleepyModeNode.addChild(insomniaNode);
        sleepyModeNode.addChild(hypersomniaNode);
        sleepyModeNode.addChild(parasomniaNode);

    }

    private static int setInsomniaCommand(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ((ISleepyModeAccess)ctx.getSource().getPlayerOrThrow()).sensible_sleepiness$setSleepyMode(SleepyMode.Insomnia);
        ctx.getSource().getPlayerOrThrow().sendMessage(Text.literal("Sleepy mode has been set to: " + SleepyMode.Insomnia.getDisplayName()));
        return Command.SINGLE_SUCCESS;

    }

    private static int setHypersomniaCommand(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ((ISleepyModeAccess)ctx.getSource().getPlayerOrThrow()).sensible_sleepiness$setSleepyMode(SleepyMode.Hypersomnia);
        ctx.getSource().getPlayerOrThrow().sendMessage(Text.literal("Sleepy mode has been set to: " + SleepyMode.Hypersomnia.getDisplayName()));
        return Command.SINGLE_SUCCESS;

    }

    private static int setParasomniaCommand(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ((ISleepyModeAccess)ctx.getSource().getPlayerOrThrow()).sensible_sleepiness$setSleepyMode(SleepyMode.Parasomnia);
        ctx.getSource().getPlayerOrThrow().sendMessage(Text.literal("Sleepy mode has been set to: " + SleepyMode.Parasomnia.getDisplayName()));
        return Command.SINGLE_SUCCESS;
    }

}
