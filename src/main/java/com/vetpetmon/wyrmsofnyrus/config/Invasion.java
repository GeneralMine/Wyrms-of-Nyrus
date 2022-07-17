package com.vetpetmon.wyrmsofnyrus.config;

import com.vetpetmon.wyrmsofnyrus.synapselib.blacklistUtil;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.block.Block;
import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;

public class Invasion {
    public static boolean invasionEnabled;
    public static boolean EXCANON;
    public static float EXCANONDIFFICULTY;

    public static boolean probingEnabled;
    public static int invasionPointsPerKill;

    public static int maxEventDistance;

    public static boolean creepEnabled;
    public static boolean creepNewInactivity;
    public static int creepSpreadRate;
    public static int creepTickRate;
    public static float creepSpreadPoints;
    public static float creepSpreadMaxHardness;
    public static String[] invalidBlocksForCreepspread;
    public static boolean CSBlockBLEnabled;
    public static ArrayList<Block> invalidBlocks;

    public static float invasionProgressionRate;

    public static String[] iBdef = {"minecraft:furnace", "minecraft:brick_block", "minecraft:bone_block", "minecraft:bedrock", "minecraft:concrete", "minecraft:concrete_powder", "minecraft:end_bricks", "minecraft:end_stone", "minecraft:glass", "minecraft:jukebox", "minecraft:nether_brick", "minecraft:red_nether_brick", "minecraft:noteblock", "minecraft:observer", "minecraft:obsidian", "minecraft:packed_ice", "minecraft:prismarine", "minecraft:purpur_block", "minecraft:purpur_pillar", "minecraft:quartz_block", "minecraft:sponge", "minecraft:stained_glass", "minecraft:wool", "minecraft:stonebrick"};

    public static void loadFromConfig(Configuration config) {

        final String CATEGORY = "Invasion";
        config.addCustomCategoryComment(CATEGORY,  "\nThe Wyrm Invasion is the main mechanic of this mod, with a fully-fledged event system with threats that keep players on edge.\n");
        config.setCategoryRequiresWorldRestart(CATEGORY, true);

        final String CATEGORYTWO = "Hive Creep";
        config.addCustomCategoryComment(CATEGORYTWO,  "\nEverything involving Hive Creep\n");
        config.setCategoryRequiresWorldRestart(CATEGORYTWO, true);

        invasionEnabled = ConfigLib.createConfigBool(config, CATEGORY, "Invasion enabled", "Enables the invasion system. Many functions of the mod will not work if this is off, including other sub-systems. Default: true", true);
        if (!invasionEnabled) wyrmsofnyrus.logger.info("Invasion module has been disabled");

        maxEventDistance= ConfigLib.createConfigInt(config, CATEGORY, "Max event distance", "All invasion events take place a certain distance away from the player. Increasing this range makes it less likely that events happen near the player, but may cause performance hitches due to potential chunkloading. Usually keep this number in increments of 16 (Chunk x/z size). Default is calculated for Minecraft's usual 12 chunk render radius. Default: 192", 192);

        EXCANON = ConfigLib.createConfigBool(config, CATEGORY, "SNAZ OS EX-tended canon", "Are we playing with the Bloodmind Nyral Wyrms, which bend their backs to take over ALL the worlds? (activates Nether and End invasions, makes wyrms immune to a LOT of damagetypes, and worsens invasion difficulty + hive creep.)\nThe EX-tended canon basically follows the Hostile Universe principle, where most, if not, all the aliens of the Stellar Networked Actuality Zone act as Great Filters to primitive or early space-age civilizations and worlds.\nEnabling this forces ALL features of this mod's invasion system with enhanced difficulty to exist.\nIf you are playing without other mods, you might want to be smart and keep this at false.\nIf you are playing with mods like SRP, HBM, TechGuns, Matter Overdrive, and/or Draconic evolution, you may want to set this to true.\nConfiguration options like EXCANON DIFFICULY will now apply. Default: false", false);
        EXCANONDIFFICULTY = ConfigLib.createConfigDouble(config, CATEGORY, "EXCANON DIFFICULTY", "The factor of difficulty you wish to put yourself here. If for some reason that the doubled stats is not enough, you can increase this from the default 2.0x difficulty. Default: 2.0", 2.0);

        invasionPointsPerKill = ConfigLib.createConfigInt(config, CATEGORY, "Invasion Points Per Kill", "Wyrms gain invasion points for every kill. Set this to 0 to disable this feature entirely. Default: 1", 1);

        probingEnabled = ConfigLib.createConfigBool(config, CATEGORY, "Probing enabled", "Probers deal heavy damage and every kill advances the invasion by 5 points, compared to only one. They also have longer aggro range and fly faster. Set to false to disable this feature and make probers less dangerous. Default: true", true);

        creepTickRate = ConfigLib.createConfigInt(config, CATEGORYTWO, "Creep tickrate", "Every n world ticks, hive creep blocks will tick and roll to see if they spread or not. 20 ticks = 1 second. Default: 400", 400);
        creepNewInactivity = ConfigLib.createConfigBool(config, CATEGORYTWO, "Experimental creep inactivity algorithm", "By default, hive creep blocks just have a chance to turn inactive. The new algorithm checks all blocks in range for a non-creepable block. However, the new algorithm can be buggy and produce weird results, such as hive creep turning inactive ASAP, or it never turning inactive. Default: false", false);
        creepEnabled = ConfigLib.createConfigBool(config, CATEGORYTWO, "Creep enabled", "If The Creep is enabled or not. This stops spread, and also renders Creepwyrms useless (Also disabling their spawning.) Default: true", true);
        if (!creepEnabled) wyrmsofnyrus.logger.info("Creep module has been disabled");
        if (creepNewInactivity) wyrmsofnyrus.logger.warn("We are using the new Creep inactivity algorithm. May the suns be on your sides. (This is EXPERIMENTAL)");
        creepSpreadRate = ConfigLib.createConfigInt(config, CATEGORYTWO, "Creep spread speed", "1 to n chance every tick that a hive creep blocks actually does something. Some blocks will tick much slower, like creepstone. Increase this number if you're seeing TPS drops. Default: 5", 5);
        creepSpreadPoints = ConfigLib.createConfigDouble(config, CATEGORYTWO, "Creep spread points", "Every time a creep block is created, the invasion points increase. If Invasion is not enabled, this won't work at all. It is recommended you should keep this number as a decimal unless if you want pain... Default: 0.015", 0.015);
        creepSpreadMaxHardness = ConfigLib.createConfigDouble(config, CATEGORYTWO, "Creep spread max hardness", "Maximum hardness of a block that can be infested. Can automatically generate a blacklist this way for other mods if you're too lazy to add to the blacklist below. Default: 2.45", 2.45);
        invalidBlocksForCreepspread = ConfigLib.createConfigStringList(config, CATEGORYTWO, "Creepable block blacklist", ("Blacklist of blocks hive creep can not spread to. Blocks that are not consider a full block by the MC engine do not need to be included here.\nWARNING: EXPERIMENTAL FEATURE. Disabled/enable in the 2nd config option. Is automatically set to true in development builds."), iBdef);
        CSBlockBLEnabled = ConfigLib.createConfigBool(config, CATEGORYTWO, "Creepable block blacklist enabled", "Enables the creepable block blacklist.", true);
        FinalizeiBdef();

        invasionProgressionRate = ConfigLib.createConfigDouble(config, CATEGORY, "Invasion Progression Rate", "Speed up or slow down invasion stage progression by multiplying the point requirements. Higher numbers slow things down. Make sure to not set this too higher, else there may be overflow errors trying to reach higher stages. Default: 1.0", 1.0);

    }

    public static boolean isEXCANON() {
        return EXCANON;
    }

    public static float getEXCANONDIFFICULTY() {
        return EXCANONDIFFICULTY;
    }

    public static boolean isCreepEnabled() {
        return creepEnabled;
    }

    /**
     * Casts the invalid block blacklist for creep spread. First casts to List, then ArrayList<String>, and finally ArrayList<Block>.
     */
    public static void FinalizeiBdef() {
        invalidBlocks = blacklistUtil.castToBlockBL(invalidBlocksForCreepspread);
    }
}
