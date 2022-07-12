package com.vetpetmon.wyrmsofnyrus.config;

import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraftforge.common.config.Configuration;

public class Evo {
    public static boolean evoEnabled;
    public static boolean evoVariantsEnabled;
    public static boolean evoFromKilled;

    public static float evoFactor;

    public static void loadFromConfig(Configuration config) {
        final String CATEGORY = "Evolution";
        config.addCustomCategoryComment(CATEGORY,  "\nWyrms will determine just how much of a threat the MC world is to them, and likewise, will evolve to stronger forms. Depending on the situation, wyrms may or may not \"autobalance\" depending on the mod setup or your strength. This means some wyrm mobs may not show up at all.\nWARNING: This module is a WIP, but will automatically come enabled in releases as it won't likely cause any game-breaking bugs.\n");
        config.setCategoryRequiresWorldRestart(CATEGORY, true);

        evoEnabled = ConfigLib.createConfigBool(config, CATEGORY, "Evolution enabled", "Enables the evolution system. Default: true", true);
        if (!evoEnabled) wyrmsofnyrus.logger.info("Evolution module has been disabled");

        evoFactor = ConfigLib.createConfigDouble(config,CATEGORY,"Evolution factor"," Determines how powerful evolution gets. Do not touch this if you intend on keeping closer to what would be expected in vanilla Mineccraft. Higher values speeds up the evolution system growth faster, lower values slow it down. Default: 1.0",1.0);
        evoFromKilled = ConfigLib.createConfigBool(config, CATEGORY, "Evolution from wyrm deaths", "When wyrms die, they gain more evolutionary pressure to evolve and adapt against dying. Improvise. Overcome. Adapt. Default: true", true);
        evoVariantsEnabled = ConfigLib.createConfigBool(config, CATEGORY, "Evolution variants", "As evolution increases, so does the chance of meeting an evolved variant of wyrm. Default: true", true);

    }
}
