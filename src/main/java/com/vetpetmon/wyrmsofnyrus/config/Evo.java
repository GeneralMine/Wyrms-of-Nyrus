package com.vetpetmon.wyrmsofnyrus.config;

import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraftforge.common.config.Configuration;

public class Evo {
    public static boolean evoEnabled;
    public static boolean evoReadsModpack;
    public static boolean evoVariantsEnabled;
    public static boolean evoFromKilled;

    public static boolean evoHBMVariantsEnabled;

    public static float evoFactor;
    public static int customEvoMinCap;

    public static void loadFromConfig(Configuration config) {
        final String CATEGORY = "Evolution";
        config.addCustomCategoryComment(CATEGORY,  "\nWyrms will determine just how much of a threat the MC world is to them, and likewise, will evolve to stronger forms. Depending on the situation, wyrms may or may not \"autobalance\" depending on the mod setup or your strength. This means some wyrm mobs may not show up at all.\nWARNING: This module is a WIP, but will automatically come enabled in releases as it won't likely cause any game-breaking bugs.\n");
        config.setCategoryRequiresWorldRestart(CATEGORY, true);

        evoEnabled = ConfigLib.createConfigBool(config, CATEGORY, "Evolution enabled", "Enables the evolution system. Default: true", true);
        if (!evoEnabled) wyrmsofnyrus.logger.info("Evolution module has been disabled");

        evoReadsModpack = ConfigLib.createConfigBool(config, CATEGORY, "Read Your Modpack", "By default, Wyrms of Nyrus will do a one-time check at the initialization of the game to determine the minimum evolution based on certain mods in your modlist. If you have any privacy concerns, you can disable this feature, even though this feature doesn't send anything out into the internet as it is all localized within your MC instance. Or, if you prefer not to have this feature enabled, this option exists. Default: true", true);
        customEvoMinCap = ConfigLib.createConfigInt(config, CATEGORY, "Custom minimum evo cap", "If you're a pack dev and disabled the Read Your Modpack feature but still want to use a starting evo cap, this is for you.", 0);

        evoFactor = ConfigLib.createConfigDouble(config,CATEGORY,"Evolution factor"," Determines how powerful evolution gets. Do not touch this if you intend on keeping closer to what would be expected in vanilla Mineccraft. Higher values speeds up the evolution system growth faster, lower values slow it down. Default: 1.0",1.0);
        evoFromKilled = ConfigLib.createConfigBool(config, CATEGORY, "Evolution from wyrm deaths", "When wyrms die, they gain more evolutionary pressure to evolve and adapt against dying. Improvise. Overcome. Adapt. Default: true", true);
        evoVariantsEnabled = ConfigLib.createConfigBool(config, CATEGORY, "Evolution variants", "As evolution increases, so does the chance of meeting an evolved variant of wyrm. Default: true", true);

        evoHBMVariantsEnabled = ConfigLib.createConfigBool(config, CATEGORY, "HBM evolution variants", "Enable/disable the HBM Taint evo variations. Requires variants to be enabled, does nothing if HBM is not installed. Default: true", true);

    }
}
