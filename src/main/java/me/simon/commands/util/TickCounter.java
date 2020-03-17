package me.simon.commands.util;

import net.minecraft.server.MinecraftServer;

public class TickCounter {
    private static long lastTickTime = 0;
    private static long thisTickTime = 0;
    private static long tps = 0;

    public static void onTick(MinecraftServer minecraftServer){
        lastTickTime = thisTickTime;
        thisTickTime = System.currentTimeMillis();
        tps = thisTickTime - lastTickTime;
        System.out.println(tps);
    }

    public static long getTps(){
        return tps;
    }
}
