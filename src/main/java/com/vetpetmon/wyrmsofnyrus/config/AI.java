package com.vetpetmon.wyrmsofnyrus.config;

import net.minecraftforge.common.config.Configuration;

import static com.vetpetmon.wyrmsofnyrus.config.ConfigLib.CFG_DIV;

public class AI {
    public static boolean suicidalWyrms;
    public static boolean attackMobs;
    public static boolean attackAnimals;

    public static boolean savageAIMode;


    public static boolean performanceAIMode;

    public static void loadFromConfig(Configuration config) {

        final String CATEGORY = "AI";

        config.addCustomCategoryComment(CATEGORY,CFG_DIV + "\nAI configuration\n" + CFG_DIV);
        config.setCategoryRequiresMcRestart(CATEGORY,true);

        suicidalWyrms = ConfigLib.createConfigBool(config, CATEGORY, "Suidical wyrms", "Makes wyrms also attack creepers like idiots, if they use hostile AI targetting more than just players. Leave this as false if you want wyrms to have more braincells than you.", false);
        attackMobs = ConfigLib.createConfigBool(config, CATEGORY, "Attack mobs", "Wyrms that can target mobs (zombies, SRP parasites, etc) will target and attack mobs.", true);
        attackAnimals = ConfigLib.createConfigBool(config, CATEGORY, "Attack animals", "Wyrms that can target animals (Sheep, animania cows, etc) will target and attack animals.", true);

        savageAIMode = ConfigLib.createConfigBool(config, CATEGORY, "Enable Savage AI", "The savage AI may not be for everyone. By setting this to false, you disable AI features like Workers attacking players at later invasion stages, Rovers from attacking players, etc.", true);
        performanceAIMode = ConfigLib.createConfigBool(config, CATEGORY, "Enable Performance AI", "Reduces the amount of AI tasks given to wyrms. Enable this if the AI lags you, but keep in mind this may cause a few glitches or oddities. Mainly disables EntityAILookIdle.", false);

    }
}
