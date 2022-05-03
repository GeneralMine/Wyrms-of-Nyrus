package com.vetpetmon.wyrmsofnyrus.config;

import net.minecraftforge.common.config.Configuration;

public class AI {
    public static boolean suicidalWyrms = false;
    public static boolean attackMobs = true;
    public static boolean attackAnimals = true;

    public static boolean savageAIMode = true;

    public static void loadFromConfig(Configuration config) {

        final String CATEGORY = "AI";

        suicidalWyrms = ConfigLib.createConfigBool(config, CATEGORY, "Suidical wyrms", "Makes wyrms also attack creepers like idiots, if they use hostile AI targetting more than just players. Leave this as false if you want wyrms to have more braincells than you.", false);
        attackMobs = ConfigLib.createConfigBool(config, CATEGORY, "Attack mobs", "Wyrms that can target mobs (zombies, SRP parasites, etc) will target and attack mobs.", true);
        attackAnimals = ConfigLib.createConfigBool(config, CATEGORY, "Attack animals", "Wyrms that can target animals (Sheep, animania cows, etc) will target and attack animals.", true);

        savageAIMode = ConfigLib.createConfigBool(config, CATEGORY, "Enable Savage AI", "The savage AI may not be for everyone. By setting this to false, you disable AI features like Workers attacking players at later invasion stages, Rovers from attacking players, etc.", true);

    }
}
