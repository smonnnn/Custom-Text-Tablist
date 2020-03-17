package me.simon;

import me.simon.commands.*;
import me.simon.commands.util.TickCounter;
import me.simon.config.Config;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.server.ServerTickCallback;
import net.fabricmc.fabric.api.registry.CommandRegistry;

import java.io.IOException;

public class Main implements ModInitializer {
    //TODO fix the spaghetti called TextFormatter

    public static final String MOD_ID = "colorchat";
    public static final String MOD_NAME = "colorchat";
    public static Config settings = new Config();

    @Override
    public void onInitialize() {
        try {
            settings.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommandRegistry.INSTANCE.register(false, FormatCommand::register);
        ServerTickCallback.EVENT.register(TickCounter::onTick);
    }
}