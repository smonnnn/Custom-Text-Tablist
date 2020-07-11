package me.simon.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;

public class Config {
    public static Config INSTANCE = new Config();
    public int configVersion = 1;
    public String header = "&cTest &6Header";
    public String footer = "&6Test &cFooter";
    public String motd = "";
    public boolean enableColor = true;
    public boolean enableTablistFormatting = true;

    public void save() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File config = new File(FabricLoader.getInstance().getConfigDirectory(), "color.json");
        try (FileWriter file = new FileWriter(config)) {
            file.write(gson.toJson(this));
        }
    }

    public void load() throws IOException{
        File config1 = new File(FabricLoader.getInstance().getConfigDirectory(), "color.json");
        if(!config1.exists()){
            this.save();
            return;
        }
        if (config1.isDirectory()) {
            if (config1.delete()) {
                this.save();
                return;
            }
        }
        try (FileReader reader = new FileReader(config1)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Config config = gson.fromJson(reader, Config.class);
            this.enableColor = config.enableColor;
            this.header = config.header;
            this.footer = config.footer;
            this.motd = config.motd;
            this.enableTablistFormatting = config.enableTablistFormatting;
            if(config.configVersion != this.configVersion){
                this.save();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
