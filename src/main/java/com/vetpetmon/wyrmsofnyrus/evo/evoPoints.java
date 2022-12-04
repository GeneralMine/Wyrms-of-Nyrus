package com.vetpetmon.wyrmsofnyrus.evo;

import com.vetpetmon.wyrmsofnyrus.compat.hbm;
import com.vetpetmon.wyrmsofnyrus.config.Evo;
import com.vetpetmon.wyrmsofnyrus.synapselib.RNG;
import com.vetpetmon.wyrmsofnyrus.wyrmVariables;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

/**
 * A collection of functions used to mess with invasion points.
 * I made this specifically because I hated writing so much code every
 * time I wanted to add points to the invasion
 *
 * Even worse, there's no dedicated getter. I'm fixing that as we speak.
 */
public class evoPoints{

    public static int minEvoCap = 0, evoLevel = 0;
    public static int get(World w) {return wyrmVariables.WorldVariables.get(w).wyrmEvo;}
    public static void set(World w, int i) {wyrmVariables.WorldVariables.get(w).wyrmEvo = i;}
    public static int getLevel() {return evoLevel;}
    public static void evoMilestone(World w){
        evoLevel = (int) Math.floor(get(w) % Evo.evoPointsPerLevel);
    }

    /**
     * Adds invasion points to the world.
     * Syncs automatically. Now you can do this in one line.
     * @param w World
     * @param i Value to add
     */
    public static void add(World w, int i){
        if (get(w) < minEvoCap) set(w,minEvoCap);
        wyrmVariables.WorldVariables.get(w).wyrmEvo += i;
        evoMilestone(w);
        sync(w);
    }

    /**
     * Looks at your modpack and sees what mods are currently loaded. Does not throw
     * an error if a mod isn't detected, and instead just doesn't add to the minimum.
     *
     * MUST BE RUN AT PRE-INT. Minimum evo points cap is remembered until the game
     * is unloaded from RAM. minEvoCap is always reset to 0 when first loaded, so
     * no need to make another setter for that.
     */
    public static void minimum(){
        if (Evo.customEvoMinCap > 0) minEvoCap += Evo.customEvoMinCap;
        if (Evo.evoReadsModpack) {
            if (Loader.isModLoaded("draconicevolution")) minEvoCap += 100;
            if (Loader.isModLoaded("srparasites")) minEvoCap += 100;
            if (hbm.isEnabled()) minEvoCap += 80;
            if (Loader.isModLoaded("techguns")) minEvoCap += 50;
            if (Loader.isModLoaded("immersiveintelligence")) minEvoCap += 50;
            if (Loader.isModLoaded("securitycraft")) minEvoCap += 45;
            if (Loader.isModLoaded("roughmobs")) minEvoCap += 30;
            if (Loader.isModLoaded("roughmobsrevamped")) minEvoCap += 30;
            if (Loader.isModLoaded("ic2")) minEvoCap += 30;
        }
    }

    /**
     * Removes invasion points from the world.
     * Syncs automatically. Now you can do this in one line.
     * @param w World
     * @param i Value to add
     */
    public static void subtract(World w, int i){
        wyrmVariables.WorldVariables.get(w).wyrmEvo -= i;
        if (get(w) < minEvoCap) {
            set(w,minEvoCap);
        }
        sync(w);
    }

    public static void decay(World w) {
        if (RNG.dBase(3000) == 5) {
            subtract(w, (int) (1 * Evo.evoFactor));
            evoMilestone(w);
        }
    }

    /**
     * Syncs the variable change to the world.
     */
    private static void sync(World w) {
        wyrmVariables.WorldVariables.get(w).syncData(w);
    }
}
