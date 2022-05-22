package com.vetpetmon.wyrmsofnyrus.config;

import net.minecraftforge.common.config.Configuration;

import static com.vetpetmon.wyrmsofnyrus.config.ConfigLib.CFG_DIV;

// The overall properties of wyrms.
public class Radiogenetics {
    public static boolean immuneToFalling;
    public static boolean immuneToCacti;

    public static int workerProductivity;

    public static double wyrmStrength;
    public static double wyrmResistance;
    public static double wyrmVitality;

    public static void loadFromConfig(Configuration config) {

        final String CATEGORY = "Radiogenetics";
        config.addCustomCategoryComment(CATEGORY,CFG_DIV +"\nGeneral mob properties for Wyrms & The Creeped. Doesn't affect non-wyrms.\n" + CFG_DIV);

        immuneToFalling = ConfigLib.createConfigBool(config, CATEGORY, "Immune to falling", "Makes certain wyrms immune to fall damage. Obviously doesn't apply to any kind of droppod if true, and doesn't do anything to flying wyrms if set to false.", true);
        immuneToCacti = ConfigLib.createConfigBool(config, CATEGORY, "Immune to cacti", "Makes certain wyrms immune to cactus damage. Set this to true if you want a closer to canon defense strategy.", false);

        workerProductivity = ConfigLib.createConfigInt(config, CATEGORY, "Worker productivity", "As a baseline, workers make a product every x ticks. This value is the base time it will take, total time will vary based on RNG still.", 2500);

        wyrmStrength = ConfigLib.createConfigDouble(config, CATEGORY, "Wyrm Strength", "The attack strength of wyrms. Default: 1.0", 1.0);
        wyrmResistance = ConfigLib.createConfigDouble(config, CATEGORY, "Wyrm Resistance", "The armor of wyrms. Default: 1.0", 1.0);
        wyrmVitality = ConfigLib.createConfigDouble(config, CATEGORY, "Wyrm Vitality", "The health of wyrms. Default: 1.0", 1.0);
    }
}
