package com.vetpetmon.wyrmsofnyrus.config;

import net.minecraftforge.common.config.Configuration;

public class Invasion {
    public static boolean invasionEnabled;

    public static boolean probingEnabled;

    public static boolean creepEnabled;
    public static int creepSpreadRate;
    public static float creepSpreadPoints;

    public static void loadFromConfig(Configuration config) {

        final String CATEGORY = "Invasion";

        invasionEnabled = ConfigLib.createConfigBool(config, CATEGORY, "Invasion enabled", "Enables the invasion system. Many functions of the mod will not work if this is off, including other sub-systems.", true);

        probingEnabled = ConfigLib.createConfigBool(config, CATEGORY, "Probing enabled", "Probers deal heavy damage and every kill advances the invasion by 2 points, compared to only one. They also have longer aggro range and fly faster. Set to false to disable this feature and make probers less dangerous.", true);

        creepEnabled = ConfigLib.createConfigBool(config, CATEGORY, "Creep enabled", "If hive creep is enabled or not. This stops spread, and also renders Creepwyrms useless (as they will not naturally spawn.)", true);
        creepSpreadRate = ConfigLib.createConfigInt(config, CATEGORY, "Creep spread speed", "1 in every n ticks, a creep block will spread. Some blocks will tick much slower, like creepstone.", 240);
        creepSpreadPoints = ConfigLib.createConfigDouble(config, CATEGORY, "Creep spread points", "Every time a creep block is created, the invasion points increase. If Invasion is not enabled, this won't work at all. It is recommended you should keep this number as a decimal unless if you want pain...", 0.015);
    }
}
