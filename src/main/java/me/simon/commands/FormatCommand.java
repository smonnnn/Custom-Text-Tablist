package me.simon.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import me.simon.Main;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;


import java.io.IOException;

import static com.mojang.brigadier.arguments.StringArgumentType.*;

public class FormatCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("format")
                .executes(FormatCommand::formatCommand));

        dispatcher.register(CommandManager.literal("enableformatting")
                .requires(source -> source.hasPermissionLevel(1))
                .executes(FormatCommand::enableFormatting));

        dispatcher.register(CommandManager.literal("disableformatting")
                .requires(source -> source.hasPermissionLevel(1))
                .executes(FormatCommand::disableFormatting));

        dispatcher.register(CommandManager.literal("setheader")
                .then(CommandManager.argument("text", greedyString())
                .requires(source -> source.hasPermissionLevel(1))
                .executes(FormatCommand::setHeader)
        ));

        dispatcher.register(CommandManager.literal("setfooter")
                .then(CommandManager.argument("text", greedyString())
                .requires(source -> source.hasPermissionLevel(1))
                .executes(FormatCommand::setFooter)
                ));
    }

    private static int setHeader(CommandContext<ServerCommandSource> ctx){
        Main.settings.header = getString(ctx, "text");
        try {
            Main.settings.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctx.getSource().sendFeedback(new LiteralText("&aHeader set."), false);
        return 1;
    }

    private static int setFooter(CommandContext<ServerCommandSource> ctx){
        Main.settings.footer = getString(ctx, "text");
        try {
            Main.settings.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctx.getSource().sendFeedback(new LiteralText("&aFooter set."), false);
        return 1;
    }

    private static int enableFormatting(CommandContext<ServerCommandSource> ctx) {
        Main.settings.enableColor = true;
        try {
            Main.settings.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctx.getSource().sendFeedback(new LiteralText("&aText formatting is now enabled."), true);
        return 1;
    }
    private static int disableFormatting(CommandContext<ServerCommandSource> ctx) {
        Main.settings.enableColor = false;
        try {
            Main.settings.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctx.getSource().sendFeedback(new LiteralText("&cText formatting is now disabled."), true);
        return 1;
    }

        private static int formatCommand(CommandContext<ServerCommandSource> ctx){
        ctx.getSource().sendFeedback(
                new LiteralText("")
                .append("Use the \"&\" character following one of the formatting codes for your message to be formatted.\n Use the \"\\\" character to escape format codes.\n")
                .append("0 is &0black&f, ")
                .append("1 is &1dark blue&f, ")
                .append("2 is &2dark green&f, ")
                .append("3 is &3dark aqua&f, \n")
                .append("4 is &4dark red&f, ")
                .append("5 is &5dark purple&f, ")
                .append("8 is &8dark gray&f\n")
                .append("----------------------------------------------------\n")
                .append("6 is &6gold&f, ")
                .append("7 is &7gray&f, ")
                .append("9 is &9blue&f, ")
                .append("a is &agreen&f, ")
                .append("b is &baqua&f, ")
                .append("c is &cred&f, \n")
                .append("d is &dlight purple&f, ")
                .append("e is &eyellow&f, ")
                .append("f is &fwhite&f\n")
                .append("----------------------------------------------------\n")
                .append("k is &kobfuscated&f, ")
                .append("l is &lbold&f, ")
                .append("m is &mstrikethrough&f, ")
                .append("n is &nunderline&f, \n")
                .append("o is &oitalic&f, ")
                , false);
        return 1;
    }
}
