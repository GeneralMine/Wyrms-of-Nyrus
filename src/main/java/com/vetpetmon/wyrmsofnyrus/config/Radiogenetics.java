package com.vetpetmon.wyrmsofnyrus.config;

import net.minecraftforge.common.config.Configuration;

// The overall properties of wyrms.
public class Radiogenetics {
    public static boolean immuneToFalling;
    public static boolean immuneToCacti;

    public static int workerProductivity;
    public static int creepwyrmInfestRange;

    public static double wyrmStrength;
    public static double wyrmResistance;
    public static double wyrmVitality;

    public static void loadFromConfig(Configuration config) {

        final String CATEGORY = "Radiogenetics";
        config.addCustomCategoryComment(CATEGORY,"\nGeneral mob properties for Wyrms & The Creeped. Doesn't affect non-wyrms.\n");

        immuneToFalling = ConfigLib.createConfigBool(config, CATEGORY, "Immune to falling", "Makes certain wyrms immune to fall damage. Obviously doesn't apply to any kind of droppod if true, and doesn't do anything to flying wyrms if set to false. Default: true", true);
        immuneToCacti = ConfigLib.createConfigBool(config, CATEGORY, "Immune to cacti", "Makes certain wyrms immune to cactus damage. Set this to true if you want a closer to canon defense strategy. Default: false", false);

        workerProductivity = ConfigLib.createConfigInt(config, CATEGORY, "Worker productivity", "As a baseline, workers make a product every x ticks. This value is the base time it will take, total time will vary based on RNG still. Default: 2500", 2500);
        creepwyrmInfestRange= ConfigLib.createConfigInt(config, CATEGORY, "Creepwyrm Infestation Range", "Creepwyrms can check a block within a x by x by x range and infest it. For example, a range of 8 makes creepwyrms add blocks in a 8x8x8 range. Default: 16", 16);

        wyrmStrength = ConfigLib.createConfigDouble(config, CATEGORY, "Wyrm Strength", "The attack strength of wyrms. 1.0 = 100% Default: 1.0", 1.0);
        wyrmResistance = ConfigLib.createConfigDouble(config, CATEGORY, "Wyrm Resistance", "The armor of wyrms. 1.0 = 100% Default: 1.0", 1.0);
        wyrmVitality = ConfigLib.createConfigDouble(config, CATEGORY, "Wyrm Vitality", "The health of wyrms. 1.0 = 100% Default: 1.0", 1.0);
    }
}
