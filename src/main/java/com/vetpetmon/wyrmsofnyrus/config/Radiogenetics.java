package com.vetpetmon.wyrmsofnyrus.config;

import net.minecraftforge.common.config.Configuration;

import static com.vetpetmon.synapselib.util.CFG.*;

// The overall properties of wyrms.
public class Radiogenetics {
    public static boolean immuneToFalling, immuneToCacti, immuneToExplosions;
    public static boolean explodingWyrms;

    public static int follyAscenSteps, workerProductivity;
    public static int creepwyrmInfestRange;

    public static double wyrmStrength, wyrmResistance, wyrmVitality, flyingWyrmProjWeakness, voidwyrmProjWeakness;
    public static double follyAscenBuffFactor;

    public static void loadFromConfig(Configuration config, int id) {

        final String CATEGORY = "Radiogenetics";
        config.addCustomCategoryComment(CATEGORY,"\nGeneral mob properties for Wyrms & The Creeped. Doesn't affect non-wyrms.\n");

        immuneToExplosions = createConfigBool(config, CATEGORY, "Immune to explosions", "Makes wyrms and Creeped immune to explosions. Default: false", ConfigBase.presetBools(false, true, true, id));
        immuneToFalling = createConfigBool(config, CATEGORY, "Immune to falling", "Makes certain wyrms immune to fall damage. Obviously doesn't apply to any kind of droppod if true, and doesn't do anything to flying wyrms if set to false. Default: true", true);
        immuneToCacti = createConfigBool(config, CATEGORY, "Immune to cacti", "Makes certain wyrms immune to cactus damage. Default: false", ConfigBase.presetBools(false, true, true, id));
        explodingWyrms = createConfigBool(config, CATEGORY, "Wyrms go supercritical", "Lore-wise, wyrms can undergo uncontrolled nuclear chain reactions when set on fire. This makes wyrms explode on death if they're on fire upon death. Default: true", true);

        workerProductivity = createConfigInt(config, CATEGORY, "Worker productivity", "As a baseline, workers make a product every x ticks. This value is the base time it will take, total time will vary based on RNG still. Default: 2500", 2500);
        creepwyrmInfestRange = createConfigInt(config, CATEGORY, "Creepwyrm Infestation Range", "Creepwyrms can check a block within a x by x by x range and infest it. For example, a range of 8 makes creepwyrms add blocks in a 8x8x8 range. Default: 16", 16);

        wyrmStrength = createConfigDouble(config, CATEGORY, "Wyrm Strength", "The attack strength of wyrms. 1.0 = 100% Default: 1.0", ConfigBase.presetFloats(1.0F, 1.1F, 1.25F, id));
        wyrmResistance = createConfigDouble(config, CATEGORY, "Wyrm Resistance", "The armor of wyrms. 1.0 = 100% Default: 1.0", ConfigBase.presetFloats(1.0F, 1.05F, 1.1F, id));
        wyrmVitality = createConfigDouble(config, CATEGORY, "Wyrm Vitality", "The health of wyrms. 1.0 = 100% Default: 1.0", ConfigBase.presetFloats(1.0F, 1.25F, 1.5F, id));
        flyingWyrmProjWeakness = createConfigDouble(config, CATEGORY, "Flying Wyrm Projectile Weakness", "The damage multiplier flying wyrms (including probers) will take from projectiles. Will not always be applicable to some flying wyrms. Does not apply to voidwyrms. 2.0 = doubled damage Default: 2.5", ConfigBase.presetFloats(2.5F, 2.0F, 1.75F, id));
        voidwyrmProjWeakness = createConfigDouble(config, CATEGORY, "Voidyrm Projectile Weakness", "The damage multiplier voidwyrms (like the Visitor) will take from projectiles. Will not always be applicable to some voidwyrms. Does not apply to flying wyrms. 2.0 = doubled damage Default: 3.0", ConfigBase.presetFloats(3.0F, 2.0F, 1.75F, id));

        follyAscenBuffFactor = createConfigDouble(config, CATEGORY, "Wyrmfolly buff factor", "The factor of which wyrmfolly stats increase. Default: 1.05", ConfigBase.presetFloats(1.05F, 1.15F, 1.3F, id));
        follyAscenSteps= createConfigInt(config, CATEGORY, "Wyrmfolly level steps", "The number of kills before a folly levels up. Default: 5", ConfigBase.presetInts(5, 4, 6, id));
    }
}
