package xd.arkosammy.commands.categories;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import xd.arkosammy.InsomniaFieldMixinInterface;

public abstract class InsomniaToggle {

    private InsomniaToggle(){}

    public static void register(LiteralCommandNode<ServerCommandSource> hypersomniaNode){

        //Do insomnia node
        LiteralCommandNode<ServerCommandSource> doInsomniaNode = CommandManager
                .literal("do_insomnia")
                .executes(InsomniaToggle::getIsInsomniaEnabledCommand)
                .build();

        //Do insomnia argument node
        ArgumentCommandNode<ServerCommandSource, Boolean> doInsomniaArgumentNode = CommandManager
                .argument("value", BoolArgumentType.bool())
                .executes(InsomniaToggle::setInsomniaCommand)
                .build();

        //Root node connection
        hypersomniaNode.addChild(doInsomniaNode);

        //Argument nodes
        doInsomniaNode.addChild(doInsomniaArgumentNode);

    }

    private static int setInsomniaCommand(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {

        ((InsomniaFieldMixinInterface) ctx.getSource().getPlayerOrThrow()).hypersomnia$setInsomnia(BoolArgumentType.getBool(ctx, "value"));
        ctx.getSource().getPlayerOrThrow().sendMessage(Text.literal("Insomnia has been: " + (BoolArgumentType.getBool(ctx, "value") ? "enabled" : "disabled" )));
        return Command.SINGLE_SUCCESS;

    }

    private static int getIsInsomniaEnabledCommand(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {

        ctx.getSource().getPlayerOrThrow().sendMessage(Text.literal("Insomnia is currently " + (((InsomniaFieldMixinInterface)ctx.getSource().getPlayerOrThrow()).hypersomnia$isInsomniaEnabled() ? "enabled" : "disabled")      ));
        return Command.SINGLE_SUCCESS;

    }

}
