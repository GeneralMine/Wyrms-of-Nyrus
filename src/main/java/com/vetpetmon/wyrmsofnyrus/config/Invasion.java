package com.vetpetmon.wyrmsofnyrus.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;

import static com.vetpetmon.wyrmsofnyrus.config.ConfigLib.CFG_DIV;

public class Invasion {
    @Config.Comment("Enable or disable the wyrm invasion")
    @Config.Name("Enable invasion")
    public static boolean invasionEnabled;
    public static boolean EXCANON;
    public static float EXCANONDIFFICULTY;
    @Config.Comment("Makes probers mean and scary.")
    @Config.Name("Enable probing")
    public static boolean probingEnabled;

    public static boolean creepEnabled;
    public static int creepSpreadRate;
    public static float creepSpreadPoints;

    public static void loadFromConfig(Configuration config) {

        final String CATEGORY = "Invasion";
        config.addCustomCategoryComment(CATEGORY,CFG_DIV + "\nThe Wyrm Invasion is the main mechanic of this mod, with a fully-fledged event system with threats that keep players on edge.\n" + CFG_DIV);
        config.setCategoryRequiresWorldRestart(CATEGORY,true);

        invasionEnabled = ConfigLib.createConfigBool(config, CATEGORY, "Invasion enabled", "Enables the invasion system. Many functions of the mod will not work if this is off, including other sub-systems.", true);

        EXCANON = ConfigLib.createConfigBool(config, CATEGORY, "SNAZ OS EX-tended canon", "Are we playing with the Bloodmind Nyral Wyrms, which bend their backs to take over ALL the worlds? (activates Nether and End invasions, makes wyrms immune to a LOT of damagetypes, and worsens invasion difficulty + hive creep.)\nThe EX-tended canon basically follows the Hostile Universe principle, where most, if not, all the aliens of the Stellar Networked Actuality Zone act as Great Filters to primitive or early space-age civilizations and worlds.\nEnabling this forces ALL features of this mod's invasion system with enhanced difficulty to exist.\nIf you are playing without other mods, you might want to be smart and keep this at false.\nIf you are playing with mods like SRP, HBM, TechGuns, Matter Overdrive, and/or Draconic evolution, you may want to set this to true.\nConfiguration options like EXCANON DIFFICULY will now apply.", true);
        EXCANONDIFFICULTY = ConfigLib.createConfigDouble(config, CATEGORY, "EXCANON DIFFICULTY", "The factor of difficulty you wish to put yourself here. If for some reason that the doubled stats is not enough, you can increase this from the default 2.0x difficulty.", 2.0);

        probingEnabled = ConfigLib.createConfigBool(config, CATEGORY, "Probing enabled", "Probers deal heavy damage and every kill advances the invasion by 2 points, compared to only one. They also have longer aggro range and fly faster. Set to false to disable this feature and make probers less dangerous.", true);

        creepEnabled = ConfigLib.createConfigBool(config, CATEGORY, "Creep enabled", "If hive creep is enabled or not. This stops spread, and also renders Creepwyrms useless (as they will not naturally spawn.)", true);
        creepSpreadRate = ConfigLib.createConfigInt(config, CATEGORY, "Creep spread speed", "1 to n chance every tick that a hive creep blocks actually does something. Some blocks will tick much slower, like creepstone. Increase this number if you're seeing TPS drops", 10);
        creepSpreadPoints = ConfigLib.createConfigDouble(config, CATEGORY, "Creep spread points", "Every time a creep block is created, the invasion points increase. If Invasion is not enabled, this won't work at all. It is recommended you should keep this number as a decimal unless if you want pain...", 0.015);
    }

    public static boolean isEXCANON() {
        return EXCANON;
    }
    public static float getEXCANONDIFFICULTY() {
        return EXCANONDIFFICULTY;
    }
}
