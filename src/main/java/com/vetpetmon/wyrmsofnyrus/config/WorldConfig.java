package com.vetpetmon.wyrmsofnyrus.config;

import com.vetpetmon.synapselib.util.CFG;
import net.minecraftforge.common.config.Configuration;

public class WorldConfig {

    // Creep variables

    public static boolean creepBlocksStopSpawns;

    // Vile Creep
    public static boolean vileEnabled;
    public static int coriumDecayRate;

    // Folly variables
    public static boolean follyBlocksStopSpawns;

    public static void loadFromConfig(Configuration config) {

        final String CATEGORY = "Creep Biome";
        config.addCustomCategoryComment(CATEGORY, "\n[CURRENTLY UNUSED] After creep blocks infect a certain number of blocks in the chunk, they turn the chunk they're in to a special biome.\n");
        config.setCategoryRequiresWorldRestart(CATEGORY, true);

        creepBlocksStopSpawns = CFG.createConfigBool(config, CATEGORY, "Blocks don't prevent mob spawns", "Creep blocks will stop mobs from spawning normally on them. The Creeped will still be spawned on these blocks, should the rules check out. Default: false", false);
        vileEnabled = CFG.createConfigBool(config, CATEGORY, "Vile creep enabled", "Only activated if Scape and Run: Parasites is present. Enables corium, which eats up the world once hive creep touches parastic infestation. Developer in-joke, disable if the destruction is too much. Default: true", true);
        coriumDecayRate = CFG.createConfigInt(config, CATEGORY, "Vile creep decay", "Age until Corium decays into Inert Corium. Leave this number small so that corium can't spread too far and cause lag. Maximum: 10 ; Default: 6", 6);
        coriumDecayRate = Math.min(coriumDecayRate, 10);

        final String CATEGORYTWO = "Follies";
        config.addCustomCategoryComment(CATEGORYTWO, "\n[CURRENTLY UNUSED] Wyrmfolly will pollute the chunk they are residing within with every kill they make. When enough follyflesh exists in the area, new follies can be made, and follies in this area get extra bonuses. This is not a biome, but rather a world mechanic.\n");
        config.setCategoryRequiresWorldRestart(CATEGORYTWO, true);

        follyBlocksStopSpawns = CFG.createConfigBool(config, CATEGORYTWO, "Blocks don't prevent mob spawns", "Folly blocks don't stop normal mobs from spawning on them by default. Disable this if you think that wyrmfollies are getting extremely powerful due to them killing mobs spawning in their territory. Default: true", true);
    }
}
