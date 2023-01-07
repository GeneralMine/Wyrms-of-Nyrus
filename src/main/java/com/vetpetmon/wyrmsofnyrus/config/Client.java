package com.vetpetmon.wyrmsofnyrus.config;

import net.minecraftforge.common.config.Configuration;

import static com.vetpetmon.synapselib.util.CFG.createConfigBool;

public class Client {
    public static boolean fancyAnimations;
    public static boolean compactConfig;

    public static void loadFromConfig(Configuration config) {

        final String CATEGORY = "Clientside";

        config.addCustomCategoryComment(CATEGORY, "\nUser-end settings. Can be reloaded at any point in time.\n");

        fancyAnimations = createConfigBool(config, CATEGORY, "Enable fancy animations", "Allows for extra animations to be played for certain wyrms that have more than 2-3 animations, reduces the amount of conditionals being checked and might help with FPS on lower end PCs. Default: true", true);
        compactConfig = createConfigBool(config, CATEGORY, "Compact Config", "If you want to use a simple, single-file configuration file, this will switch your configs from the wyrms folder to the general configs folder. Client settings not included.", false);

    }
}
