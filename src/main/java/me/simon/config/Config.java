package me.simon.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.logging.Log;


import java.io.*;

public class Config {

    public static File configdir = FabricLoader.getInstance().getConfigDirectory();//repurposed code from PVP opt-in - Ill pretty this up later. My main focus is functionality
    public static File colorFile = new File(configdir.getPath(), "color.json");//Idk why but at least in the IDE it would not generate the config directory unless this code was present

    public short configVersion = 1;

    public String header = "&cTest &6Header";
    public String footer = "&6Test &cFooter";
    public String motd = "";
    public boolean enableColor = true;
    public boolean enableTablistFormatting = true;



    public void save() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File config = new File("config/color.json");
        if(!config.exists()){
            config.createNewFile();
        }
        try (FileWriter file = new FileWriter("config/color.json")) {
            file.write(gson.toJson(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() throws IOException{
        File config1 = new File("config/color.json");
        if(!config1.exists()){
            this.save();
        }
        FileReader reader;
        try {
            reader = new FileReader("config/color.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Config config = gson.fromJson(reader, Config.class);
            this.enableColor = config.enableColor;
            this.header = config.header;
            this.footer = config.footer;
            this.motd = config.motd;
            this.enableTablistFormatting = config.enableTablistFormatting;
            if(config.configVersion != this.configVersion){
                config1.delete();
                this.load();
            }
            this.save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
