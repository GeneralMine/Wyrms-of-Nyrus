package com.vetpetmon.wyrmsofnyrus.config;

import net.minecraftforge.common.config.Configuration;

import static com.vetpetmon.synapselib.util.CFG.*;

// The overall properties of wyrms.
public class Radiogenetics {
    public static boolean immuneToFalling, immuneToCacti, immuneToExplosions, creepedImmuneToFalling, creepedImmuneToCacti, creepedImmuneToExplosions;
    public static boolean explodingWyrms, explodingDropPods;

    public static int follyAscenSteps, workerProductivity;
    public static int creepwyrmInfestRange, creepwyrmBlocksNeededToSpawn, creepwyrmSpawnTiers, creepwyrmSpawnThreshhold, creepwyrmPodCallThreshhold, creepwyrmPodCallAmount, creepwyrmDropPodCallRadius;

    public static double wyrmStrength, wyrmResistance, wyrmVitality, flyingWyrmProjWeakness, voidwyrmProjWeakness;
    public static double follyAscenBuffFactor;
    public static boolean mobEasterEggs;

    public static void loadFromConfig(Configuration config, int id) {

        final String CATEGORY = "Wyrms";
        config.addCustomCategoryComment(CATEGORY,"\nGeneral mob properties for Wyrms. Doesn't affect non-wyrms.\n");
        final String CATEGORYTWO = "Creeped";
        config.addCustomCategoryComment(CATEGORY,"\nGeneral mob properties for The Creeped. Doesn't affect non-creeped.\n");
        final String CATEGORYTHREE = "Wyrmfollies";
        config.addCustomCategoryComment(CATEGORY,"\nGeneral mob properties for Wyrmfollies. Doesn't affect non-follies.\n");
        final String CATEGORYFOUR = "Global";
        config.addCustomCategoryComment(CATEGORY,"\nUniversal mob properties for every mob in the mod.\n");


        // Wyrms
        immuneToExplosions = createConfigBool(config, CATEGORY, "Immune to explosions", "Makes wyrms immune to explosions. Default: false", ConfigBase.presetBools(false, true, true, id));
        immuneToFalling = createConfigBool(config, CATEGORY, "Immune to falling", "Makes wyrms immune to fall damage. Obviously doesn't apply to any kind of droppod if true, and doesn't do anything to flying wyrms if set to false. Default: true", true);
        immuneToCacti = createConfigBool(config, CATEGORY, "Immune to cacti", "Makes wyrms immune to cactus damage. Default: false", ConfigBase.presetBools(false, true, true, id));

        explodingWyrms = createConfigBool(config, CATEGORY, "Wyrms go supercritical", "Lore-wise, wyrms can undergo uncontrolled nuclear chain reactions when set on fire. This makes wyrms explode on death if they're on fire upon death. Default: true", true);
        explodingDropPods= createConfigBool(config, CATEGORY, "Drop pods cause block damage", "Drop pods can damage blocks around them with an actual explosion. SHOULD BE PAIRED WITH WYRMS AND CREEPED BEING IMMUNE TO EXPLOSIONS! Default: false", ConfigBase.presetBools(false, true, true, id));

        workerProductivity = createConfigInt(config, CATEGORY, "Worker productivity", "As a baseline, workers make a product every x ticks. This value is the base time it will take, total time will vary based on RNG still. Default: 2500", 2500);

        wyrmStrength = createConfigDouble(config, CATEGORY, "Wyrm Strength", "The attack strength of wyrms. 1.0 = 100% Default: 1.0", ConfigBase.presetFloats(1.0F, 1.1F, 1.25F, id));
        wyrmResistance = createConfigDouble(config, CATEGORY, "Wyrm Resistance", "The armor of wyrms. 1.0 = 100% Default: 1.0", ConfigBase.presetFloats(1.0F, 1.05F, 1.1F, id));
        wyrmVitality = createConfigDouble(config, CATEGORY, "Wyrm Vitality", "The health of wyrms. 1.0 = 100% Default: 1.0", ConfigBase.presetFloats(1.0F, 1.25F, 1.5F, id));
        flyingWyrmProjWeakness = createConfigDouble(config, CATEGORY, "Flying Wyrm Projectile Weakness", "The damage multiplier flying wyrms (including probers) will take from projectiles. Will not always be applicable to some flying wyrms. Does not apply to voidwyrms. 2.0 = doubled damage Default: 2.5", ConfigBase.presetFloats(2.5F, 2.0F, 1.75F, id));
        voidwyrmProjWeakness = createConfigDouble(config, CATEGORY, "Voidyrm Projectile Weakness", "The damage multiplier voidwyrms (like the Visitor) will take from projectiles. Will not always be applicable to some voidwyrms. Does not apply to flying wyrms. 2.0 = doubled damage Default: 3.0", ConfigBase.presetFloats(3.0F, 2.0F, 1.75F, id));

        // Creeped
        creepwyrmInfestRange = createConfigInt(config, CATEGORYTWO, "Creepwyrm Infestation Range", "Creepwyrms can check a block within a x by x by x range and infest it. For example, a range of 8 makes creepwyrms add blocks in a 8x8x8 range. Default: 16", 16);
        creepedImmuneToExplosions = createConfigBool(config, CATEGORYTWO, "Immune to explosions", "Makes the Creeped immune to explosions. Default: false", ConfigBase.presetBools(false, true, true, id));
        creepedImmuneToFalling = createConfigBool(config, CATEGORYTWO, "Immune to falling", "Makes the Creeped immune to fall damage. Flying Creeped always ignore fall damage. Default: false", ConfigBase.presetBools(false, false, true, id));
        creepedImmuneToCacti = createConfigBool(config, CATEGORYTWO, "Immune to cacti", "Makes the Creeped immune to cactus damage. Default: false", ConfigBase.presetBools(false, false, false, id));

        creepwyrmSpawnTiers = createConfigInt(config, CATEGORYTWO, "Creepwyrm Maximum Spawn Tier", "Maximum tier of Creeped that Creepwyrms can spawn. Default: 0", ConfigBase.presetInts(0, 1, 5, id));
        creepwyrmSpawnThreshhold = createConfigInt(config, CATEGORYTWO, "Creepwyrm Spawn Threshold", "Amount of blocks Creepwyrms need to infest in order to spawn something. Default: 20", 20);
        creepwyrmPodCallThreshhold = createConfigInt(config, CATEGORYTWO, "Creepwyrm Pod Call Threshold Spawn", "Creepwyrms must summon x amount of times before they call a creeped pod to spread their influence. Default: 10", ConfigBase.presetInts(10, 8, 7, id));
        creepwyrmPodCallAmount =createConfigInt(config, CATEGORYTWO, "Creepwyrm Pod Call Threshold", "The number of Creeped Pods Creepwyrms will summon. Default: 1", ConfigBase.presetInts(1, 2, 3, id));
        creepwyrmDropPodCallRadius = createConfigInt(config, CATEGORYTWO, "Creepwyrm Pod Call Radius", "How far up Creepwyrms can call in Creeped Pods. Default: 30", ConfigBase.presetInts(30, 45, 100, id));

        // Follies
        follyAscenBuffFactor = createConfigDouble(config, CATEGORYTHREE, "Wyrmfolly buff factor", "The factor of which wyrmfolly stats increase. Default: 1.05", ConfigBase.presetFloats(1.15F, 1.25F, 1.4F, id));
        follyAscenSteps= createConfigInt(config, CATEGORYTHREE, "Wyrmfolly level steps", "The number of kills before a folly levels up. Default: 5", ConfigBase.presetInts(5, 4, 6, id));

        // Global
        mobEasterEggs = createConfigBool(config, CATEGORYFOUR, "Mob Easter Eggs", "Wyrms, Creeped, and Follies can hold some secrets... Default: true", true);
    }
}
