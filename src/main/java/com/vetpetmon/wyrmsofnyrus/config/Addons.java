package com.vetpetmon.wyrmsofnyrus.config;

import net.minecraftforge.common.config.Configuration;

public class Addons {
    public static void loadFromConfig(Configuration config, int id) {

        final String CATEGORY = "ADDONS";
        config.addCustomCategoryComment(CATEGORY,  "\nAddon developers, place your configs here! This folder is for configuration not added by Wyrms of Nyrus itself.\n");
        config.setCategoryRequiresWorldRestart(CATEGORY, true);

    }
}
