package com.vetpetmon.wyrmsofnyrus.synapselib;

import com.vetpetmon.wyrmsofnyrus.config.Invasion;

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

        return ((baseHP * 2) + ((difficulty * exdif)/2));
    }

}
