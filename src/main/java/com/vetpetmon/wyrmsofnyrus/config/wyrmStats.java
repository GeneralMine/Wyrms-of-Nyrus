package com.vetpetmon.wyrmsofnyrus.config;

import net.minecraftforge.common.config.Configuration;

import static com.vetpetmon.wyrmsofnyrus.synapselib.CFG.*;

//Giant list of wyrm stats for fine-tuning
public class wyrmStats {

    private static final String advNotice = "\n\n WIP section! This configuration file isn't fully fleshed out and is subject to change in future versions. \n\n These values are part of advanced configuration for more precise user tuning. Good for modpack developers.";

    public static float warriorHP, taintedWarriorHP;
    public static float warriorDEF, taintedWarriorDEF;
    public static float warriorATK, taintedWarriorATK;
    public static float warriorKBR, taintedWarriorKBR;
    public static float warriorSPD, taintedWarriorSPD;

    public static float soldierHP, infectoidSoldierHP, frostSoldierHP;
    public static float soldierDEF, infectoidSoldierDEF, frostSoldierDEF;
    public static float soldierATK, infectoidSoldierATK, frostSoldierATK;
    public static float soldierSPD, infectoidSoldierSPD, frostSoldierSPD;
    public static float soldierKBR, infectoidSoldierKBR, frostSoldierKBR;
    public static float roverHP, roverDEF, roverATK, roverSPD, roverKBR;
    public static float roverUraniumHP, roverUraniumDEF, roverUraniumATK, roverUraniumSPD, roverUraniumKBR;
    public static float myrmurHP, myrmurDEF, myrmurATK, myrmurSPD, myrmurSprintSPD, myrmurKBR;
    public static float biterHP, biterDEF, biterATK, biterSPD, biterRollSPD, biterRollDMG, biterKBR;
    public static float proberHP, proberDEF, proberATK, proberSPD;
    public static float dobberHP, dobberDEF, dobberATK, dobberSPD;
    public static float workerHP, workerDEF, workerATK, workerSPD, workerKBR;
    public static float wyrmlingHP, wyrmlingDEF, wyrmlingSPD;
    public static float creepwyrmHP, creepwyrmDEF, creepwyrmATK;
    public static float visitorHP, visitorDEF, visitorSPD, visitorKBR;
    public static float creepedhumanoidHP, creepedhumanoidDEF, creepedhumanoidATK, creepedhumanoidSPD, creepedhumanoidKBR, creepedhumanoidSprintSPD;
    public static float strykerfollyHP, strykerfollyATK, strykerfollyDEF, strykerfollySPD, strykerfollyKBR, strykerfollyPointsTillAscension;
    public static float strykerfollyAscendedHP, strykerfollyAscendedATK, strykerfollyAscendedDEF, strykerfollyAscendedSPD, strykerfollyAscendedKBR;

    public static void loadFromConfig(Configuration c) {
        startFile(c);
        roverStats(c);
        CreepwyrmStats(c);
        biterStats(c);
        creepedhumanoidStats(c);
        myrmurStats(c);
        warriorStats(c);
        soldierStats(c);
        proberStats(c);
        workerStats(c);
        dobberStats(c);
        wyrmlingStats(c);
        visitorStats(c);
        strykerfollyStats(c);

    }

    public static void startFile(Configuration c) {
        final String CAT = "!Wyrm stats";
        c.addCustomCategoryComment(CAT,"\nThis file contains mostly every adjustable base stat value present on the wyrms. Keep in mind that in-game, these values may CHANGE due to the Invasion and/or Evolution system.\n\n STAT KEYS:\n\nSPD: Speed\nATK: Damage\nHP: Health\nDEF: Armor\nKBR: Knockback Reduction\n" + advNotice);
    }

    public static void roverStats(Configuration c) {
        final String CAT = "Rover Base stats";
        c.addCustomCategoryComment(CAT,"\nStats for the Rovers, including variants.\n");
        roverHP = createConfigDouble(c, CAT, "Normal HP", "Default: 5", 5);
        roverUraniumHP = createConfigDouble(c, CAT, "Uranium Variant HP", "Default: 8", 8);

        roverDEF = createConfigDouble(c, CAT, "Normal DEF", "Default: 3.5", 3.5);
        roverUraniumDEF = createConfigDouble(c, CAT, "Uranium Variant DEF", "Default: 6", 6);

        roverATK = createConfigDouble(c, CAT, "Normal ATK", "Default: 4", 4);
        roverUraniumATK = createConfigDouble(c, CAT, "Uranium Variant ATK", "Default: 6", 6);

        roverSPD = createConfigDouble(c, CAT, "Normal SPD", "Default: 0.75", 0.75);
        roverUraniumSPD = createConfigDouble(c, CAT, "Uranium Variant SPD", "Default: 0.75", 0.75);

        roverKBR = createConfigDouble(c, CAT, "Normal KBR", "Default: 0.05", 0.05);
        roverUraniumKBR = createConfigDouble(c, CAT, "Uranium Variant KBR", "Default: 0.5", 0.5);
    }

    public static void CreepwyrmStats(Configuration c) {
        final String CAT = "Creepwyrm Base stats";
        c.addCustomCategoryComment(CAT,"\nStats for the Creepwyrms.\n");
        creepwyrmHP = createConfigDouble(c, CAT, "Normal HP", "Default: 55", 55);
        creepwyrmDEF = createConfigDouble(c, CAT, "Normal DEF", "Default: 10", 10);
        creepwyrmATK = createConfigDouble(c, CAT, "Normal ATK", "Default: 2.5", 2.5);
    }

    public static void biterStats(Configuration c) {
        final String CAT = "Biter Base stats";
        c.addCustomCategoryComment(CAT,"\nStats for the Biters.\n");
        biterHP = createConfigDouble(c, CAT, "Normal HP", "Default: 11", 11);
        biterDEF = createConfigDouble(c, CAT, "Normal DEF", "Default: 3", 3);
        biterATK = createConfigDouble(c, CAT, "Normal ATK", "Default: 3", 3);
        biterSPD = createConfigDouble(c, CAT, "Normal SPD", "Default: 0.6", 0.6);
        biterKBR = createConfigDouble(c, CAT, "Normal KBR", "Default: 0.05", 0.05);
        biterRollSPD = createConfigDouble(c, CAT, "Normal Roll SPD", "Default: 2.0", 2.0);
        biterRollDMG = createConfigDouble(c, CAT, "Normal Roll DMG", "Default: 10", 10);
    }

    public static void creepedhumanoidStats(Configuration c) {
        final String CAT = "Vanitas Base stats";
        c.addCustomCategoryComment(CAT,"\nStats for the Vanitas (Creeped Humanoids).\n");
        creepedhumanoidHP = createConfigDouble(c, CAT, "Normal HP", "Default: 18", 18);
        creepedhumanoidDEF = createConfigDouble(c, CAT, "Normal DEF", "Default: 4", 4);
        creepedhumanoidATK = createConfigDouble(c, CAT, "Normal ATK", "Default: 6", 6);
        creepedhumanoidSPD = createConfigDouble(c, CAT, "Normal SPD", "Default: 0.25", 0.25);
        creepedhumanoidKBR = createConfigDouble(c, CAT, "Normal KBR", "Default: 0.4", 0.4);
        creepedhumanoidSprintSPD = createConfigDouble(c, CAT, "Normal Sprint SPD", "Default:1.5", 1.5);
    }
    public static void myrmurStats(Configuration c) {
        final String CAT = "Myrmur Base stats";
        c.addCustomCategoryComment(CAT,"\nStats for the Myrmurs.\n");
        myrmurHP = createConfigDouble(c, CAT, "Normal HP", "Default: 9", 9);
        myrmurDEF = createConfigDouble(c, CAT, "Normal DEF", "Default: 3.5", 3.5);
        myrmurATK = createConfigDouble(c, CAT, "Normal ATK", "Default: 4", 4);
        myrmurSPD = createConfigDouble(c, CAT, "Normal SPD", "Default: 0.35", 0.35);
        myrmurKBR = createConfigDouble(c, CAT, "Normal KBR", "Default: 0.1", 0.1);
        myrmurSprintSPD = createConfigDouble(c, CAT, "Normal Sprint SPD", "Default: 0.2", 0.2);
    }

    public static void warriorStats(Configuration c) {
        final String CAT = "Warrior Base stats";
        c.addCustomCategoryComment(CAT,"\nStats for the Warriors, including variants.\n");
        warriorHP = createConfigDouble(c, CAT, "Normal HP", "Default: 26", 26);
        taintedWarriorHP = createConfigDouble(c, CAT, "Tainted Variant HP", "Default: 66", 66);

        warriorDEF = createConfigDouble(c, CAT, "Normal DEF", "Default: 2", 2);
        taintedWarriorDEF = createConfigDouble(c, CAT, "Tainted Variant DEF", "Default: 16", 16);

        warriorATK = createConfigDouble(c, CAT, "Normal ATK", "Default: 4", 4);
        taintedWarriorATK = createConfigDouble(c, CAT, "Tainted Variant ATK", "Default: 12", 12);

        warriorSPD = createConfigDouble(c, CAT, "Normal SPD", "Default: 0.45", 0.45);
        taintedWarriorSPD = createConfigDouble(c, CAT, "Tainted Variant SPD", "Default: 0.45", 0.45);

        warriorKBR = createConfigDouble(c, CAT, "Normal KBR", "Default: 0.05", 0.05);
        taintedWarriorKBR = createConfigDouble(c, CAT, "Tainted Variant KBR", "Default: 0.5", 0.5);
    }

    public static void soldierStats(Configuration c) {
        final String CAT = "Soldier Base stats";
        c.addCustomCategoryComment(CAT,"\nStats for the Soldiers, including variants.\n");
        soldierHP = createConfigDouble(c, CAT, "Normal HP", "Default: 26", 26);
        infectoidSoldierHP = createConfigDouble(c, CAT, "Infectoid Variant HP", "Default: 20", 20);
        frostSoldierHP = createConfigDouble(c, CAT, "Frost Variant HP", "Default: 6", 6);

        soldierDEF = createConfigDouble(c, CAT, "Normal DEF", "Default: 7", 7);
        infectoidSoldierDEF = createConfigDouble(c, CAT, "Infectoid Variant DEF", "Default: 5", 5);
        frostSoldierDEF = createConfigDouble(c, CAT, "Frost Variant DEF", "Default: 15", 15);

        soldierATK = createConfigDouble(c, CAT, "Normal ATK", "Default: 3", 3);
        infectoidSoldierATK = createConfigDouble(c, CAT, "Infectoid Variant ATK", "Default: 9", 9);
        frostSoldierATK = createConfigDouble(c, CAT, "Frost Variant ATK", "Default: 6", 6);

        soldierSPD = createConfigDouble(c, CAT, "Normal SPD", "Default: 0.5", 0.5);
        infectoidSoldierSPD = createConfigDouble(c, CAT, "Infectoid Variant SPD", "Default: 0.52", 0.52);
        frostSoldierSPD = createConfigDouble(c, CAT, "Frost Variant SPD", "Default: 0.42", 0.42);

        soldierKBR = createConfigDouble(c, CAT, "Normal KBR", "Default: 0.7", 0.7);
        infectoidSoldierKBR = createConfigDouble(c, CAT, "Infectoid Variant KBR", "Default: 0.52", 0.52);
        frostSoldierKBR = createConfigDouble(c, CAT, "Frost Variant KBR", "Default: 1.0", 1.0);
    }

    public static void proberStats(Configuration c) {
        final String CAT = "Prober Base stats";
        c.addCustomCategoryComment(CAT,"\nStats for the Probers.\n");
        proberHP = createConfigDouble(c, CAT, "Normal HP", "Default: 5", 5);

        proberDEF = createConfigDouble(c, CAT, "Normal DEF", "Default: 3.25", 3.25);

        proberSPD = createConfigDouble(c, CAT, "Normal SPD", "Default: 0.55", 0.55);

        proberATK = createConfigDouble(c, CAT, "Normal ATK", "Only used if probing is disabled. Default: 4", 4);
    }

    public static void workerStats(Configuration c) {
        final String CAT = "Worker Base stats";
        c.addCustomCategoryComment(CAT,"\nStats for the Workers.\n");
        workerHP = createConfigDouble(c, CAT, "Normal HP", "Default: 14", 14);

        workerDEF = createConfigDouble(c, CAT, "Normal DEF", "Default: 4.5", 4.5);

        workerSPD = createConfigDouble(c, CAT, "Normal SPD", "Default: 0.35", 0.35);

        workerATK = createConfigDouble(c, CAT, "Normal ATK", "Default: 2", 2);

        workerKBR = createConfigDouble(c, CAT, "Normal KBR", "Default: 0.05", 0.05);
    }

    public static void dobberStats(Configuration c) {
        final String CAT = "Dobber Base stats";
        c.addCustomCategoryComment(CAT,"\nStats for the dobbers.\n");
        dobberHP = createConfigDouble(c, CAT, "Normal HP", "Default: 1", 1);

        dobberDEF = createConfigDouble(c, CAT, "Normal DEF", "Default: 1", 1);

        dobberSPD = createConfigDouble(c, CAT, "Normal SPD", "Default: 0.9", 0.9);

        dobberATK = createConfigDouble(c, CAT, "Normal ATK", "Default: 0.5", 0.5);
    }

    public static void wyrmlingStats(Configuration c) {
        final String CAT = "Wyrmling Base stats";
        c.addCustomCategoryComment(CAT,"\nStats for the wyrmlings.\n");
        wyrmlingHP = createConfigDouble(c, CAT, "Normal HP", "Default: 4", 4);

        wyrmlingDEF = createConfigDouble(c, CAT, "Normal DEF", "Default: 8", 8);

        wyrmlingSPD = createConfigDouble(c, CAT, "Normal SPD", "Default: 0.55", 0.55);
    }
    public static void visitorStats(Configuration c) {
        final String CAT = "Visitor Base stats";
        c.addCustomCategoryComment(CAT,"\nStats for the Visitor.\n");
        visitorHP = createConfigDouble(c, CAT, "Normal HP", "Default: 600", 600);

        visitorDEF = createConfigDouble(c, CAT, "Normal DEF", "Default: 20", 20);

        visitorSPD = createConfigDouble(c, CAT, "Normal SPD", "Default: 0.15", 0.15);

        visitorKBR = createConfigDouble(c, CAT, "Normal KBR", "Default: 1.0", 1.0);
    }

    public static void strykerfollyStats(Configuration c) {
        final String CAT = "Strykerfolly Base stats";
        c.addCustomCategoryComment(CAT,"\nStats for the Strykerfolly.\n");
        strykerfollyHP = createConfigDouble(c, CAT, "Normal HP", "Default: 90", 90);
        strykerfollyATK = createConfigDouble(c, CAT, "Normal ATK", "Default: 10", 10);
        strykerfollyDEF = createConfigDouble(c, CAT, "Normal DEF", "Default: 10", 10);
        strykerfollySPD = createConfigDouble(c, CAT, "Normal SPD", "Default: 0.85", 0.85);
        strykerfollyKBR = createConfigDouble(c, CAT, "Normal KBR", "Default: 0.5", 0.5);

        strykerfollyPointsTillAscension = createConfigInt(c, CAT, "Kills until Ascension", "Default: 400", 400);

        strykerfollyAscendedHP = createConfigDouble(c, CAT, "Ascended HP", "Default: 500", 500);
        strykerfollyAscendedATK = createConfigDouble(c, CAT, "Ascended ATK", "Default: 40", 40);
        strykerfollyAscendedDEF = createConfigDouble(c, CAT, "Ascended DEF", "Default: 30", 30);
        strykerfollyAscendedSPD = createConfigDouble(c, CAT, "Ascended SPD", "Default: 0.95", 0.95);
        strykerfollyAscendedKBR = createConfigDouble(c, CAT, "Ascended KBR", "Default: 1.0", 1.0);
    }
}
