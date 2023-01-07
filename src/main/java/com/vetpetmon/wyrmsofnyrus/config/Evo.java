package com.vetpetmon.wyrmsofnyrus.config;

import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraftforge.common.config.Configuration;

import static com.vetpetmon.synapselib.util.CFG.*;

public class Evo {
    public static boolean evoEnabled, evoReadsModpack, evoVariantsEnabled;
    public static boolean evoFromKilled;

    public static boolean evoHBMVariantsEnabled;

    public static float evoFactor, evoPowerHP, evoPowerDEF, evoPowerATK;
    public static int customEvoMinCap, evoPointsPerLevel;

    public static int minEvoCreepwyrm, minEvoWyrmling, minEvoWorker, minEvoSoldier, minEvoSoldierInf, minEvoSoldierFrost, minEvoWarrior, minEvoWarriorTainted;


    public static void loadFromConfig(Configuration config) {
        final String CATEGORY = "Evolution";
        config.addCustomCategoryComment(CATEGORY,  "\nWyrms will determine just how much of a threat the MC world is to them, and likewise, will evolve to stronger forms. Depending on the situation, wyrms may or may not \"autobalance\" depending on the mod setup or your strength. This means some wyrm mobs may not show up at all.\nWARNING: This module is a WIP, but will automatically come enabled in releases as it won't likely cause any game-breaking bugs.\n");
        config.setCategoryRequiresWorldRestart(CATEGORY, true);

        evoEnabled = createConfigBool(config, CATEGORY, "Evolution enabled", "Enables the evolution system. Default: true", true);
        if (!evoEnabled) wyrmsofnyrus.logger.info("Evolution module has been disabled");

        evoPowerHP = createConfigDouble(config,CATEGORY,"Evolution HP power","The overall health boost given to wyrms at certain stages of evolution. Default: 0.15",0.15);
        evoPowerDEF = createConfigDouble(config,CATEGORY,"Evolution DEF power","The overall armor boost given to wyrms at certain stages of evolution. Default: 0.1",0.1);
        evoPowerATK = createConfigDouble(config,CATEGORY,"Evolution ATK power","The overall damage boost given to wyrms at certain stages of evolution. Default: 0.05",0.05);
        if (evoPowerHP < 0.05) evoPowerHP = 0.05F;
        if (evoPowerDEF < 0.05) evoPowerDEF = 0.05F;
        if (evoPowerATK < 0.05) evoPowerATK = 0.05F;

        evoReadsModpack = createConfigBool(config, CATEGORY, "Read Your Modpack", "By default, Wyrms of Nyrus will do a one-time check at the initialization of the game to determine the minimum evolution based on certain mods in your modlist. If you have any privacy concerns, you can disable this feature, even though this feature doesn't send anything out into the internet as it is all localized within your MC instance. Or, if you prefer not to have this feature enabled, this option exists. Default: true", true);
        customEvoMinCap = createConfigInt(config, CATEGORY, "Custom minimum evo cap", "If you're a pack dev and disabled the Read Your Modpack feature but still want to use a starting evo cap, this is for you.", 0);
        evoPointsPerLevel = createConfigInt(config, CATEGORY, "Evo Points per Level", "How many points is a level? Default: 50", 50);

        evoFactor = createConfigDouble(config,CATEGORY,"Evolution factor","Determines how powerful evolution gets. Do not touch this if you intend on keeping closer to what would be expected in vanilla Mineccraft. Higher values speeds up the evolution system growth faster, lower values slow it down. Default: 1.0",1.0);
        evoFromKilled = createConfigBool(config, CATEGORY, "Evolution from wyrm deaths", "When wyrms die, they gain more evolutionary pressure to evolve and adapt against dying. Improvise. Overcome. Adapt. Default: true", true);
        evoVariantsEnabled = createConfigBool(config, CATEGORY, "Evolution variants", "As evolution increases, so does the chance of meeting an evolved variant of wyrm. Default: true", true);

        evoHBMVariantsEnabled = createConfigBool(config, CATEGORY, "HBM evolution variants", "Enable/disable the HBM Taint evo variations. Requires variants to be enabled, does nothing if HBM is not installed. Default: true", true);


        final String CATEGORYTWO = "Min Evolution Level";
        config.addCustomCategoryComment(CATEGORY,  "\nMinimum evo level that is required until wyrms start getting evolution boosts.");
        config.setCategoryRequiresWorldRestart(CATEGORY, false);

        minEvoCreepwyrm = createConfigInt(config, CATEGORYTWO, "Creepwyrm", "Default: 6", 6);
        minEvoWyrmling = createConfigInt(config, CATEGORYTWO, "Wyrmling", "Default: 2", 2);
        minEvoWorker = createConfigInt(config, CATEGORYTWO, "Worker", "Default: 3", 3);
        minEvoSoldier = createConfigInt(config, CATEGORYTWO, "Soldier", "Default: 4", 4);
        minEvoSoldierInf = createConfigInt(config, CATEGORYTWO, "Soldier (Infectoid)", "Default: 10", 10);
        minEvoWarrior = createConfigInt(config, CATEGORYTWO, "Warrior", "Default: 5", 5);
        minEvoWarriorTainted = createConfigInt(config, CATEGORYTWO, "Warrior (Tainted)", "Default: 12", 12);
        minEvoSoldierFrost = createConfigInt(config, CATEGORYTWO, "Soldier (Frost)", "Default: 6", 6);
    }
}
