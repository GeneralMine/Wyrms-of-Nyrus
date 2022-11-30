package com.vetpetmon.wyrmsofnyrus.synapselib;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

/**
 * This component was originally designed for Wyrms of Nyrus's mobs and creatures.
 * Since 0.4, it is now its own component.
 * This acts as a shortcut formula for determining mob stats.
 */
public class difficultyStats {
    /**
     * Calculates the HP of the entity in the fastest way possible.
     * @param baseHP The number of hearts this creature has (not including half-hearts). This automatically gets doubled in the formula.
     * @param difficulty Factor of which to multiply stats by.
     * @return A double value of the entity's resulting max HP.
     */
    public static double health(double baseHP, double difficulty)
    {
        return ((baseHP * 2) + difficulty) ;
    }

    /**
     * Calculates the DMG of the entity in the fastest way possible.
     * @param baseDMG the amount of damage a mob does in FULL hearts.
     * @param difficulty Factor of which to multiply stats by.
     * @return a double value of the entity's damage capabilities.
     */
    public static double damage(double baseDMG, double difficulty)
    {

        return (baseDMG + (difficulty*1.2));
    }

    /**
     * Applies a potion effect to an attacked entity.
     * Duration is scaled/increased by difficulty.
     * Supports modded & vanilla potions.
     * @param inputE Entity input, the entity that gets attacked
     * @param effect Class of the effect, like HbmPotion.radiation
     * @param duration Base duration of effect in ticks
     * @param lvl Effect level
     */
    public static void applyPotionEffect(Entity inputE, Potion effect, int duration, int lvl) {

        ((EntityLivingBase) inputE).addPotionEffect(new PotionEffect(effect, duration + (inputE.world.getDifficulty().ordinal() * 30), lvl));
    }

    /**
     * Calculates the AMR of the entity in the fastest way possible.
     * @param baseAMR the amount of armor the mob has.
     * @param difficulty Factor of which to multiply stats by.
     * @return a double value of the entity's total armor.
     */
    public static double armor(double baseAMR, double difficulty)
    {
        return (baseAMR + (difficulty*1.5));
    }



}
