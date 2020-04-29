package me.simon.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.simon.Main;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.PlayerListHeaderS2CPacket;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;

import java.io.IOException;

import static com.mojang.brigadier.arguments.StringArgumentType.*;

public class FormatCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("format")
                .executes(FormatCommand::formatCommand));

        dispatcher.register(CommandManager.literal("toggleformatting")
                .requires(source -> source.hasPermissionLevel(1))
                .executes(FormatCommand::toggleformatting));

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

        dispatcher.register(CommandManager.literal("displayitem")
                .executes(FormatCommand::displayItem)
        );

        dispatcher.register(CommandManager.literal("toggletablist")
                .requires(source->source.hasPermissionLevel(1))
                .executes(FormatCommand::toggleTablist)
        );

        dispatcher.register(CommandManager.literal("setmotd")
                .then(CommandManager.argument("text", greedyString())
                        .requires(source->source.hasPermissionLevel(1))
                        .executes(FormatCommand::setMotd)
                ));
    }

    private static int setMotd(CommandContext<ServerCommandSource> ctx) {
        Main.settings.motd = getString(ctx, "text");
        try {
            Main.settings.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctx.getSource().sendFeedback(new LiteralText("set motd"), false);
        return 1;
    }

        private static int toggleTablist(CommandContext<ServerCommandSource> ctx) {
        clearTablist(ctx);
        ctx.getSource().sendFeedback(new LiteralText("Toggled tablist updates").formatted(Formatting.GREEN), true);
        Main.settings.enableTablistFormatting = !Main.settings.enableTablistFormatting;
        return 1;
    }

    private static void clearTablist(CommandContext<ServerCommandSource> ctx) {
        Main.settings.header = "";
        Main.settings.footer = "";
        try {
            Main.settings.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctx.getSource().getMinecraftServer().getPlayerManager().sendToAll(new PlayerListHeaderS2CPacket());
        }
        private static int displayItem(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ItemStack itemStack = ctx.getSource().getPlayer().getStackInHand(Hand.MAIN_HAND);
        if(itemStack == ItemStack.EMPTY){
            ctx.getSource().sendFeedback(new LiteralText("You're currently not holding anything!").formatted(Formatting.RED), false);
        }
        else{
            BaseText nametxt = (BaseText) ctx.getSource().getDisplayName();
            MutableText hoverText =nametxt.append(" wants to show you their ").append(itemStack.toHoverableText());
            //
            ctx.getSource().getMinecraftServer().getPlayerManager().sendToAll(hoverText);
        }
        return 1;
    }

    private static int setHeader(CommandContext<ServerCommandSource> ctx){
        Main.settings.header = getString(ctx, "text");
        try {
            Main.settings.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctx.getSource().sendFeedback(new LiteralText("Header set.").formatted(Formatting.GREEN), false);
        return 1;
    }

    private static int setFooter(CommandContext<ServerCommandSource> ctx){
        Main.settings.footer = getString(ctx, "text");
        try {
            Main.settings.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctx.getSource().sendFeedback(new LiteralText("Footer set.").formatted(Formatting.GREEN), false);
        return 1;
    }

    private static int toggleformatting(CommandContext<ServerCommandSource> ctx) {
        Main.settings.enableColor = !Main.settings.enableColor;
        try {
            Main.settings.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctx.getSource().sendFeedback(new LiteralText("Toggled text formatting").formatted(Formatting.GREEN), true);
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
