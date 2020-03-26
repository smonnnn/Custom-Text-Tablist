package me.simon.commands.util;

import net.minecraft.server.MinecraftServer;

public class TickCounter {
    private static int mspt = 1;
    private static int tps = 1;


    public static void onTick(MinecraftServer minecraftServer){
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
}
