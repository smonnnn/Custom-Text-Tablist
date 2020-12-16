package me.simon.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.simon.Main;
import me.simon.commands.util.SuggestionsProvider;
import me.simon.config.Config;
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
    static Config config = Config.INSTANCE;
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,  boolean b) {
        dispatcher.register(CommandManager.literal("format")
                .executes(FormatCommand::formatCommand));

        dispatcher.register(CommandManager.literal("toggleformatting")
                .requires(source -> source.hasPermissionLevel(1))
                .executes(FormatCommand::toggleformatting));

        dispatcher.register(CommandManager.literal("setheader")
                .then(CommandManager.argument("text", greedyString())
                        .suggests(SuggestionsProvider.headerFooterSuggestions())
                        .requires(source -> source.hasPermissionLevel(1))
                        .executes(FormatCommand::setHeader)
                ));

        dispatcher.register(CommandManager.literal("setfooter")
                .then(CommandManager.argument("text", greedyString())
                        .suggests(SuggestionsProvider.headerFooterSuggestions())
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
        config.motd = getString(ctx, "text");
        try {
            config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctx.getSource().sendFeedback(new LiteralText("set motd"), false);
        return 1;
    }

        private static int toggleTablist(CommandContext<ServerCommandSource> ctx) {
        clearTablist(ctx);
        ctx.getSource().sendFeedback(new LiteralText("Toggled tablist updates").formatted(Formatting.GREEN), true);
        config.enableTablistFormatting = !config.enableTablistFormatting;
        return 1;
    }

    private static void clearTablist(CommandContext<ServerCommandSource> ctx) {
        config.header = "";
        config.footer = "";
        try {
            config.save();
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
            MutableText nametxt = ctx.getSource().getDisplayName().shallowCopy();
            Text hoverText = nametxt.append(new LiteralText(" wants to show you their ").formatted(Formatting.YELLOW)).append(itemStack.toHoverableText());

            ctx.getSource().getMinecraftServer().getPlayerManager().getPlayerList().forEach(player->player.sendMessage(hoverText, false));
        }
        return 1;
    }

    private static int setHeader(CommandContext<ServerCommandSource> ctx){
        config.header = getString(ctx, "text");
        try {
            config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctx.getSource().sendFeedback(new LiteralText("Header set.").formatted(Formatting.GREEN), false);
        return 1;
    }

    private static int setFooter(CommandContext<ServerCommandSource> ctx){
        config.footer = getString(ctx, "text");
        try {
            config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctx.getSource().sendFeedback(new LiteralText("Footer set.").formatted(Formatting.GREEN), false);
        return 1;
    }

    private static int toggleformatting(CommandContext<ServerCommandSource> ctx) {
        config.enableColor = !config.enableColor;
        try {
            config.save();
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
                        .append("&00 &11 &22 &33 &44 &55 &66 &77 &88 &99\n")
                        .append("&aa &bb &cc &dd &ee &ff &gg &hh &ii &jj\n")
                        .append("----------------------------------------------------\n")
                        .append("&kk &ll &mm &nn &oo\n")
                        .append("----------------------------------------------------\n")
                        .append("n is #N next line\n")
                        .append("n is #UPTIME show uptime\n")
                        .append("n is #TPS show ticks per second\n")
                        .append("n is #MSPT next line\n")
                        .append("n is #PLAYERCOUNT show how many players are online\n")
                , false);
        return 1;
    }


}
