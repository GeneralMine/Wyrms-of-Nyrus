package com.vetpetmon.wyrmsofnyrus.config;

import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraftforge.common.config.Configuration;

import static com.vetpetmon.synapselib.util.CFG.*;

public class Evo {
    public static boolean evoEnabled, evoReadsModpack, evoVariantsEnabled;
    public static boolean evoFromKilled, bonusEvofromFireKill;
    public static boolean evoHBMVariantsEnabled;
    public static float evoFactor, evoPowerHP, evoPowerDEF, evoPowerATK, bonusFromFireKill;
    public static int customEvoMinCap, evoPointsPerLevel;
    public static int minEvoCreepwyrm, minEvoWyrmling, minEvoWorker, minEvoSoldier, minEvoSoldierInf, minEvoSoldierFrost, minEvoWarrior, minEvoWarriorTainted;
    public static String[] modEvo, modEvoDef = {"draconicevolution;400","srparasites;300","hbm;250","icbmclassic;150","lycanitesmobs;125","securitycraft;80","techguns;75","roughmobsrevamped;75","immersiveintelligence;65","roughmobs;60","ic2;50"};


    public static void loadFromConfig(Configuration config, int id) {
        final String CATEGORY = "Evolution";
        config.addCustomCategoryComment(CATEGORY,  "\nWyrms will determine just how much of a threat the MC world is to them, and likewise, will evolve to stronger forms. Depending on the situation, wyrms may or may not \"autobalance\" depending on the mod setup or your strength. This means some wyrm mobs may not show up at all.\nWARNING: This module is a WIP, but will automatically come enabled in releases as it won't likely cause any game-breaking bugs.\n");
        config.setCategoryRequiresWorldRestart(CATEGORY, true);

        evoEnabled = createConfigBool(config, CATEGORY, "Evolution enabled", "Enables the evolution system. Default: true", true);
        if (!evoEnabled) wyrmsofnyrus.logger.info("Evolution module has been disabled");

        modEvo = createConfigStringList(config,CATEGORY, "Modlist detector strings", "uwu", modEvoDef);

        evoPowerHP = createConfigDouble(config,CATEGORY,"Evolution HP power","The overall health boost given to wyrms at certain stages of evolution. Default: 0.15",ConfigBase.presetFloats(0.15F, 0.19F, 0.25F, id));
        evoPowerDEF = createConfigDouble(config,CATEGORY,"Evolution DEF power","The overall armor boost given to wyrms at certain stages of evolution. Default: 0.1",ConfigBase.presetFloats(0.1F, 0.11F, 0.13F, id));
        evoPowerATK = createConfigDouble(config,CATEGORY,"Evolution ATK power","The overall damage boost given to wyrms at certain stages of evolution. Default: 0.05",ConfigBase.presetFloats(0.05F, 0.135F, 0.25F, id));
        if (evoPowerHP < 0.05) evoPowerHP = 0.05F;
        if (evoPowerDEF < 0.05) evoPowerDEF = 0.05F;
        if (evoPowerATK < 0.05) evoPowerATK = 0.05F;

        evoReadsModpack = createConfigBool(config, CATEGORY, "Read Your Modpack", "By default, Wyrms of Nyrus will do a one-time check at the initialization of the game to determine the minimum evolution based on certain mods in your modlist. If you have any privacy concerns, you can disable this feature, even though this feature doesn't send anything out into the internet as it is all localized within your MC instance. Or, if you prefer not to have this feature enabled, this option exists. Default: true", true);
        customEvoMinCap = createConfigInt(config, CATEGORY, "Custom minimum evo cap", "If you're a pack dev and disabled the Read Your Modpack feature but still want to use a starting evo cap, this is for you.", 0);
        evoPointsPerLevel = createConfigInt(config, CATEGORY, "Evo Points per Level", "How many points is a level? Default: 50", ConfigBase.presetInts(50,40,30, id));

        evoFactor = createConfigDouble(config,CATEGORY,"Evolution factor","Determines how powerful evolution gets. Do not touch this if you intend on keeping closer to what would be expected in vanilla Mineccraft. Higher values speeds up the evolution system growth faster, lower values slow it down. Default: 1.0",1.0);
        evoFromKilled = createConfigBool(config, CATEGORY, "Evolution from wyrm deaths", "When wyrms die, they gain more evolutionary pressure to evolve and adapt against dying. Improvise. Overcome. Adapt. Default: true", true);
        bonusEvofromFireKill =  createConfigBool(config, CATEGORY, "Bonus from wyrm deaths by fire", "Wyrms add more evo points if killed by fire. Default: false", ConfigBase.presetBools(false,false,true, id));
        bonusFromFireKill =  createConfigDouble(config, CATEGORY, "Multiplier from wyrm deaths by fire", "Multiplier for the bonus from wyrm deaths by fire. Default: 2.0", 2.0F);
        evoVariantsEnabled = createConfigBool(config, CATEGORY, "Evolution variants", "As evolution increases, so does the chance of meeting an evolved variant of wyrm. Default: true", true);

        evoHBMVariantsEnabled = createConfigBool(config, CATEGORY, "HBM evolution variants", "Enable/disable the HBM Taint evo variations. Requires variants to be enabled, does nothing if HBM is not installed. Default: true", true);


        final String CATEGORYTWO = "Min Evolution Level";
        config.addCustomCategoryComment(CATEGORY,  "\nMinimum evo level that is required until wyrms start getting evolution boosts.");
        config.setCategoryRequiresWorldRestart(CATEGORY, false);

        minEvoCreepwyrm = createConfigInt(config, CATEGORYTWO, "Creepwyrm", "Default: 6", ConfigBase.presetInts(6,1,1, id));
        minEvoWyrmling = createConfigInt(config, CATEGORYTWO, "Wyrmling", "Default: 2", ConfigBase.presetInts(2,1,1, id));
        minEvoWorker = createConfigInt(config, CATEGORYTWO, "Worker", "Default: 3", ConfigBase.presetInts(3,1,1, id));
        minEvoSoldier = createConfigInt(config, CATEGORYTWO, "Soldier", "Default: 4", ConfigBase.presetInts(4,1,1, id));
        minEvoSoldierInf = createConfigInt(config, CATEGORYTWO, "Soldier (Infectoid)", "Default: 10", ConfigBase.presetInts(10,1,1, id));
        minEvoWarrior = createConfigInt(config, CATEGORYTWO, "Warrior", "Default: 5", ConfigBase.presetInts(5,1,1, id));
        minEvoWarriorTainted = createConfigInt(config, CATEGORYTWO, "Warrior (Tainted)", "Default: 12", ConfigBase.presetInts(12,1,1, id));
        minEvoSoldierFrost = createConfigInt(config, CATEGORYTWO, "Soldier (Frost)", "Default: 6", ConfigBase.presetInts(6,1,1, id));
    }
}
