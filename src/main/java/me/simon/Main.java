package me.simon;

import me.simon.commands.*;

import me.simon.commands.util.TextFormatter;
import me.simon.commands.util.TablistVariables;
import me.simon.config.Config;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.server.ServerTickCallback;

import java.io.IOException;

public class Main implements ModInitializer {
    //TODO fix the spaghetti called TextFormatter

    public static final String MOD_ID = "colorchat";
    public static final String MOD_NAME = "colorchat";
    public static Config settings = new Config();
    public static TextFormatter TF = new TextFormatter();//Referencing a public class in main and then calling it in mixins prevents crashing

    @Override
    public void onInitialize() {
        try {
            settings.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CommandRegistrationCallback.EVENT.register(FormatCommand::register);
        ServerTickCallback.EVENT.register(TablistVariables::onTick);
    }
}