package com.vetpetmon.wyrmsofnyrus.config;

import net.minecraftforge.common.config.Configuration;

import static com.vetpetmon.synapselib.util.CFG.*;

public class AI {
    public static boolean suicidalWyrms, attackMobs, attackAnimals, attackVillagers, niceToPlayers;

    public static boolean savageAIMode, rageEnabled;
    public static boolean destroyBlocks;
    public static boolean performanceAIMode;

    public static boolean gestaltTotalAwareness, gestaltUseInfamy, maxInfamyDoublesCreepedSpawns, maxInfamySummonsPods, maxInfamyTotalWar;
    public static double gestaltTotalAwarenessRange;
    public static int gestaltInfamyDecayChance, rageCooldownMax;

    public static void loadFromConfig(Configuration config, int id) {

        final String CATEGORY = "AI", CATEGORYTWO = "Gestalt";

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

        rageEnabled = createConfigBool(config, CATEGORY, "Rage Enabled", "Rage is applied when wyrms had a target, but had to switch priorities. This causes wyrms to get annoyed and switch targets, causing them to knock their original target away, gain short buffs, and go after the being that interrupted them. Default: true", true);
        rageCooldownMax = createConfigInt(config, CATEGORY, "Rage Cooldown", "The time in seconds between when wyrms can enrage. Default: 20", ConfigBase.presetInts(20,20,10, id));

        config.addCustomCategoryComment(CATEGORYTWO,"\nGESTALT\n");
        config.setCategoryRequiresMcRestart(CATEGORYTWO,true);

        gestaltTotalAwareness = createConfigBool(config, CATEGORYTWO, "Total Awareness", "Wyrms that get close enough to a player become aggressive, even if they otherwise could not. Default: true", true);
        gestaltTotalAwarenessRange = createConfigDouble(config, CATEGORYTWO, "Total Awareness Range", "Control how close a member of the hive must be to the player. Default: 8", ConfigBase.presetFloats(8,16,20,id));
        gestaltUseInfamy = createConfigBool(config, CATEGORYTWO, "Infamy Enabled", "Players that pick fights with wyrms will build up infamy within the Hive, and attract other wyrms to the player's general area faster, if this is disabled, wyrms will only slowly congregate around players. Default: true", true);
        gestaltInfamyDecayChance = createConfigInt(config, CATEGORYTWO, "Infamy Decay Chance", "1 in X chance to decay infamy every tick. Goes down exponentially faster if infamy is high. Default: 20000", ConfigBase.presetInts(20000,25000,30000,id));
        maxInfamyDoublesCreepedSpawns = createConfigBool(config, CATEGORYTWO, "Max infamy Creeped Spawns", "Max infamy causes Creeped kills to spawn double the entities. Default: true", true);
        maxInfamySummonsPods = createConfigBool(config, CATEGORYTWO, "Max infamy Causes Invasion Events", "Max infamy causes invasion events to take place around the player. Unlike the normal scheduler, this system creates an event for ALL players in the server, not just one random player. Default: true", true);
        maxInfamyTotalWar = createConfigBool(config, CATEGORYTWO, "Total War", "While infamy is maxed out, wyrms and creeped know almost EXACTLY where the player is at all times, so long as they're within proximity. This total knowledge proximity is far longer than the Total Awareness Range. Default: false", ConfigBase.presetBools(false, false, true, id));
    }
}
