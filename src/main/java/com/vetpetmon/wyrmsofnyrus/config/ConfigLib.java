package com.vetpetmon.wyrmsofnyrus.config;

/*
    This handy file is the configuration library that WoN uses. I may make it a separate library in the future
    for other mod developers to use to quickly create configuration files, but it is easiest for me to make this
    embedded in WoN itself for full customization.
*/

import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.File;

import static com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus.proxy;

public class ConfigLib {

    public static String CFG_DIV = "================================================";

    public static boolean createConfigBool(Configuration config, String category, String name, String comment, boolean def) {
        Property prop = config.get(category, name, def);
        prop.setComment(comment);
        return prop.getBoolean();
    }

    public static String createConfigString(Configuration config, String category, String name, String comment, String def) {

        Property prop = config.get(category, name, def);
        prop.setComment(comment);
        return prop.getString();
    }

    public static int createConfigInt(Configuration config, String category, String name, String comment, int def) {

        Property prop = config.get(category, name, def);
        prop.setComment(comment);
        return prop.getInt();
    }

    public static float createConfigDouble(Configuration config, String category, String name, String comment, double def) {
        Property prop = config.get(category, name, def);
        prop.setComment(comment);
        return (float) prop.getDouble();
    }

    public static int catchZero(int value, int def) {

        if(value < 0) {
            wyrmsofnyrus.logger.error("User error in config: You set a configurable value to 0 or below.");
            wyrmsofnyrus.logger.error(String.format("Error will be catched and set back to %d, but please, read configuration instructions more closely.", def));
            return def;
        }

        return value;
    }

    public static void reloadConfig() {
        Configuration cfg = new Configuration(new File(proxy.getDataDir().getPath() + "/config/wyrms.cfg"));
        cfg.load();
        Debug.loadFromConfig(cfg);
        Invasion.loadFromConfig(cfg);
        AI.loadFromConfig(cfg);
        Radiogenetics.loadFromConfig(cfg);
        cfg.save();
    }

    @Mod.EventBusSubscriber(modid = wyrmsofnyrus.MODID)
    private static class EventHandler
    {
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(wyrmsofnyrus.MODID))
            {
                ConfigManager.sync(wyrmsofnyrus.MODID, Config.Type.INSTANCE);
            }
        }
    }

}
