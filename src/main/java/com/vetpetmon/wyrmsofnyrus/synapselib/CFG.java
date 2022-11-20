package com.vetpetmon.wyrmsofnyrus.synapselib;

import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

public class CFG {
    /**
     * Automatically generates directories to a configuration.
     * Automatically appends .cfg to the name, so no need to add that.
     *
     * It is recommended to make Directory an actual variable within your mod.
     *
     * Use proxy.getDataDir().getPath() + "/config/" to start in the
     * standardized configuration folders used by most mods.
     * @param name Name of the file, excluding ".cfg"
     * @param Directory The file's destination.
     * @return Configuration file and it's directory.
     */
    public static Configuration createDirectory(String name, String Directory) {
        return new Configuration(new File(Directory + name + ".cfg"));
    }

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

    public static String[] createConfigStringList(Configuration config, String category, String name, String comment, String[] def) {

        Property prop = config.get(category, name, def);
        prop.setComment(comment);
        return prop.getStringList();
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

        if(value <= 0) {
            wyrmsofnyrus.logger.error("User error in config: You set a configurable value to 0 or below.");
            wyrmsofnyrus.logger.error(String.format("Error will be caught and set back to %d, but please, read configuration instructions more closely.", def));
            return def;
        }

        return value;
    }
}
