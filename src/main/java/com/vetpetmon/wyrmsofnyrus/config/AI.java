package com.vetpetmon.wyrmsofnyrus.config;

import net.minecraftforge.common.config.Configuration;

import static com.vetpetmon.synapselib.util.CFG.*;

public class AI {
    public static boolean suicidalWyrms, attackMobs, attackAnimals, attackVillagers;

    public static boolean niceToPlayers;

    public static boolean savageAIMode;
    public static boolean destroyBlocks;

    public static boolean performanceAIMode;

    public static void loadFromConfig(Configuration config) {

        final String CATEGORY = "AI";

        config.addCustomCategoryComment(CATEGORY,"\nAI configuration\n");
        config.setCategoryRequiresMcRestart(CATEGORY,true);

        destroyBlocks= createConfigBool(config, CATEGORY, "Block destruction", "Some wyrms (Those marked as diggers or sapients) will try to break blocks. If this is disabled, they won't do that. Default: true", true);
        suicidalWyrms = createConfigBool(config, CATEGORY, "Suidical wyrms", "Makes wyrms also attack creepers like idiots, if they use hostile AI targeting more than just players. Leave this as false if you want wyrms to have more braincells than you. Default: false", false);
        niceToPlayers = createConfigBool(config, CATEGORY, "Friendly to players", "Makes wyrms not attack players (unless if attacked already). Default: false", false);

        attackMobs = createConfigBool(config, CATEGORY, "Attack mobs", "Wyrms that can target mobs (zombies, SRP parasites, etc) will target and attack mobs. Default: true", true);
        attackAnimals = createConfigBool(config, CATEGORY, "Attack animals", "Wyrms that can target animals (Sheep, animania cows, etc) will target and attack animals. Default: true", true);
        attackVillagers = createConfigBool(config, CATEGORY, "Attack villagers", "Wyrms that can target villagers will target and attack villagers. Default: true", true);
        savageAIMode = createConfigBool(config, CATEGORY, "Enable Savage AI", "The savage AI may not be for everyone. By setting this to false, you disable AI features like Workers attacking players at later invasion stages, Rovers from attacking players, etc. Default: true", true);
        performanceAIMode = createConfigBool(config, CATEGORY, "Enable Performance AI", "Reduces the amount of AI tasks given to wyrms. Enable this if the AI lags you, but keep in mind this may cause a few glitches or oddities. Mainly disables EntityAILookIdle. Default: false", false);

    }
}
