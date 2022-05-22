package com.vetpetmon.wyrmsofnyrus.synapselib;

import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.config.Radiogenetics;

/**
 * This component was specifically designed for Wyrms of Nyrus's mobs and creatures.
 * This acts as a shortcut formula for determining mob stats.
 */
public class difficultyStats {
    public static double exdif;
    /**
     * Calculates the HP of the entity in the fastest way possible.
     * @param baseHP The number of hearts this creature has (not including half-hearts). This automatically gets doubled in the formula.
     * @param difficulty The current difficulty of the wyrm invasion.
     * @return A double value of the entity's resulting max HP.
     */
    public static double health(double baseHP, double difficulty)
    {
        if (Invasion.isEXCANON()) exdif = Invasion.getEXCANONDIFFICULTY();
        else exdif = 1;

        return ( ((baseHP * 2) + ((difficulty * exdif)/2)) * Radiogenetics.wyrmVitality);
    }

    /**
     * Calculates the DMG of the entity in the fastest way possible.
     * @param baseDMG the amount of damage a mob does in FULL hearts.
     * @param difficulty The current difficulty of the wyrm invasion.
     * @return a double value of the entity's damage capabilities.
     */
    public static double damage(double baseDMG, double difficulty)
    {
        if (Invasion.isEXCANON()) exdif = Invasion.getEXCANONDIFFICULTY();
        else exdif = 1;

        return ( ((baseDMG) + ((difficulty * exdif)/3)) * Radiogenetics.wyrmStrength);
    }

    /**
     * Calculates the AMR of the entity in the fastest way possible.
     * @param baseAMR the amount of armor the mob has.
     * @param difficulty The current difficulty of the wyrm invasion.
     * @return a double value of the entity's total armor.
     */
    public static double armor(double baseAMR, double difficulty)
    {
        if (Invasion.isEXCANON()) exdif = Invasion.getEXCANONDIFFICULTY();
        else exdif = 1;

        return ( ((baseAMR) + ((difficulty * exdif)/4)) * Radiogenetics.wyrmStrength);
    }



}
