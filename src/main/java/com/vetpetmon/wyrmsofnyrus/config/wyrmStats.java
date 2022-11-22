package com.vetpetmon.wyrmsofnyrus.config;

import net.minecraftforge.common.config.Configuration;

import static com.vetpetmon.wyrmsofnyrus.synapselib.CFG.*;

//Giant list of wyrm stats for fine-tuning
public class wyrmStats {

    private static final String advNotice = "\n\n WIP section! This configuration file isn't fully fleshed out and is subject to change in future versions. \n\n These values are part of advanced configuration for more precise user tuning. Good for modpack developers.";

    public static float warriorHP, taintedWarriorHP;
    public static float warriorDEF, taintedWarriorDEF;
    public static float warriorATK, taintedWarriorATK;

    public static float soldierHP, infectoidSoldierHP;
    public static float soldierDEF, infectoidSoldierDEF;
    public static float soldierATK, infectoidSoldierATK;

    public static float proberHP;
    public static float proberDEF;
    public static float proberATK;
    public static float proberSPD;

    public static float dobberHP;
    public static float dobberDEF;
    public static float dobberATK;
    public static float dobberSPD;

    public static float wyrmlingHP;
    public static float wyrmlingDEF;
    public static float wyrmlingSPD;

    public static float visitorHP;
    public static float visitorDEF;
    public static float visitorSPD;

    public static void loadFromConfig(Configuration c) {
        startFile(c);
        warriorStats(c);
        soldierStats(c);
        proberStats(c);
        dobberStats(c);
        wyrmlingStats(c);

    }

    public static void startFile(Configuration c) {
        final String CAT = "!Wyrm stats";
        c.addCustomCategoryComment(CAT,"\nThis file contains mostly every adjustable base stat value present on the wyrms. Keep in mind that in-game, these values may CHANGE due to the Invasion and/or Evolution system.\n\n STAT KEYS:\n\nSPD: Speed\nATK: Damage\nHP: Health\nDEF: Armor\n" + advNotice);
    }

    public static void warriorStats(Configuration c) {
        final String CAT = "Warrior Base stats";
        c.addCustomCategoryComment(CAT,"\nStats for the Warriors, including variants.\n");
        warriorHP = createConfigDouble(c, CAT, "Normal HP", "Default: 22", 22);
        taintedWarriorHP = createConfigDouble(c, CAT, "Tainted Variant HP", "Default: 66", 66);

        warriorDEF = createConfigDouble(c, CAT, "Normal DEF", "Default: 6", 6);
        taintedWarriorDEF = createConfigDouble(c, CAT, "Tainted Variant DEF", "Default: 16", 16);

        warriorATK = createConfigDouble(c, CAT, "Normal ATK", "Default: 4", 4);
        taintedWarriorATK = createConfigDouble(c, CAT, "Tainted Variant ATK", "Default: 12", 12);
    }

    public static void soldierStats(Configuration c) {
        final String CAT = "Soldier Base stats";
        c.addCustomCategoryComment(CAT,"\nStats for the Soldiers, including variants.\n");
        soldierHP = createConfigDouble(c, CAT, "Normal HP", "Default: 6", 6);
        infectoidSoldierHP = createConfigDouble(c, CAT, "Infectoid Variant HP", "Default: 10", 10);

        soldierDEF = createConfigDouble(c, CAT, "Normal DEF", "Default: 4", 4);
        infectoidSoldierDEF = createConfigDouble(c, CAT, "Infectoid Variant DEF", "Default: 5", 5);

        soldierATK = createConfigDouble(c, CAT, "Normal ATK", "Default: 3", 3);
        infectoidSoldierATK = createConfigDouble(c, CAT, "Infectoid Variant ATK", "Default: 5", 5);
    }

    public static void proberStats(Configuration c) {
        final String CAT = "Prober Base stats";
        c.addCustomCategoryComment(CAT,"\nStats for the Probers.\n");
        proberHP = createConfigDouble(c, CAT, "Normal HP", "Default: 5", 5);

        proberDEF = createConfigDouble(c, CAT, "Normal DEF", "Default: 3.25", 3.25);

        proberSPD = createConfigDouble(c, CAT, "Normal SPD", "Default: 0.55", 0.55);

        proberATK = createConfigDouble(c, CAT, "Normal ATK", "Only used if probing is disabled. Default: 4", 4);
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
        wyrmlingHP = createConfigDouble(c, CAT, "Normal HP", "Default: 3", 3);

        wyrmlingDEF = createConfigDouble(c, CAT, "Normal DEF", "Default: 2", 2);

        wyrmlingSPD = createConfigDouble(c, CAT, "Normal SPD", "Default: 0.55", 0.55);
    }
    public static void visitorStats(Configuration c) {
        final String CAT = "Visitor Base stats";
        c.addCustomCategoryComment(CAT,"\nStats for the wyrmlings.\n");
        visitorHP = createConfigDouble(c, CAT, "Normal HP", "Default: 60", 60);

        visitorDEF = createConfigDouble(c, CAT, "Normal DEF", "Default: 8", 8);

        visitorSPD = createConfigDouble(c, CAT, "Normal SPD", "Default: 0.05", 0.05);
    }
}
