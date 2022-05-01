package com.vetpetmon.wyrmsofnyrus.config;

import net.minecraftforge.common.config.Configuration;

// The overall properties of wyrms.
public class Radiogenetics {
    public static boolean immuneToFalling;
    public static boolean immuneToCacti;

    public static int workerProductivity;

    public static void loadFromConfig(Configuration config) {

        final String CATEGORY = "Radiogenetics";

        immuneToFalling = ConfigLib.createConfigBool(config, CATEGORY, "Immune to falling", "Makes certain wyrms immune to fall damage. Obviously doesn't apply to any kind of droppod if true, and doesn't do anything to flying wyrms if set to false.", true);
        immuneToCacti = ConfigLib.createConfigBool(config, CATEGORY, "Immune to cacti", "Makes certain wyrms immune to cactus damage. Set this to true if you want a closer to canon defense strategy.", false);

        workerProductivity = ConfigLib.createConfigInt(config, CATEGORY, "Worker productivity", "As a baseline, workers make a product every x ticks. This value is the base time it will take, total time will vary based on RNG still.", 2500);

    }
}
