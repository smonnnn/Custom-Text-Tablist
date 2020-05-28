package me.simon.commands.util;

import net.minecraft.server.MinecraftServer;

import java.lang.management.ManagementFactory;

public class TablistVariables {
    private static int mspt = 1;
    private static int tps = 1;
    private static int playerCount = 0;


    public static void onTick(MinecraftServer minecraftServer){
        playerCount = minecraftServer.getCurrentPlayerCount();
        mspt = (int) minecraftServer.getTickTime();
        if(mspt != 0) {
            tps = 1000 / mspt;
        }
    }

    public static int getMspt(){
        return mspt;
    }
    public static int getTps(){
        return Math.min(tps, 20);
    }
    public static int getPlayerCount(){
        return playerCount;
    }
    public static String getUptime() {
        String s = "";
        int time = (int) ManagementFactory.getRuntimeMXBean().getUptime() / 1000;
        int days = (int) Math.floor(time / 86400f);
        int hours = (int) Math.floor(time / 3600f) - days * 24;
        int minutes = (int) Math.floor(time / 60f) - hours * 60 - days * 1440;
        int seconds = time - minutes * 60 - hours * 3600 - days * 86400;
        if (days > 0) {
            s = s + days + " day" + (days == 1 ? "" : "s") + ", ";
        }

        if (hours > 0 || days > 0) {
            s = s + (hours < 10 ? "0" : "") + hours + ":";
        }

        if (minutes > 0 || hours > 0 || days > 0) {
            s = s + (minutes < 10 ? "0" : "") + minutes + ":";
        }

        s = s + (seconds < 10 ? "0" : "") + seconds;
        return s;
    }


}
