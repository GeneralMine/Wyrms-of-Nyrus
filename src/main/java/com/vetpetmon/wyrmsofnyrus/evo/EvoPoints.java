package com.vetpetmon.wyrmsofnyrus.evo;

import com.vetpetmon.synapselib.util.RNG;
import com.vetpetmon.wyrmsofnyrus.WyrmVariables;
import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import com.vetpetmon.wyrmsofnyrus.config.Debug;
import com.vetpetmon.wyrmsofnyrus.config.Evo;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

/**
 * A collection of functions used to mess with evolution points.
 * I made this specifically because I hated writing so much code every
 * time I wanted to add points to the wyrms' evolution
 * Even worse, there's no dedicated getter. I'm fixing that as we speak.
 */
public class EvoPoints {

    public static int minEvoCap = 0, evoLevel = 0;
    public static int get(World w) {return WyrmVariables.WorldVariables.get(w).wyrmEvo;}
    public static void set(World w, int i) {
        WyrmVariables.WorldVariables.get(w).wyrmEvo = i;}
    public static int getLevel() {return evoLevel;}
    public static void evoMilestone(World w){
        evoLevel = (int) Math.floor((get(w) / Evo.evoPointsPerLevel));
    }

    /**
     * Adds evolution points to the world.
     * Syncs automatically. Now you can do this in one line.
     * @param w World
     * @param i Value to add
     */
    public static void add(World w, int i){
        if (get(w) < minEvoCap) set(w,minEvoCap);
        WyrmVariables.WorldVariables.get(w).wyrmEvo += i;
        evoMilestone(w);
        sync(w);
        if(Debug.LOGGINGENABLED && Debug.DEBUGLEVEL > 4)WyrmsOfNyrus.logger.info("Added " + i + " evolution points into world data.");
    }

    /**
     * Looks at your modpack and sees what mods are currently loaded. Does not throw
     * an error if a mod isn't detected, and instead just doesn't add to the minimum.
     *
     * MUST BE RUN AT POST-INT. Minimum evo points cap is remembered until the game
     * is unloaded from RAM. minEvoCap is always reset to 0 when first loaded, so
     * no need to make another setter for that.
     */
    public static void minimum(){
        if (Evo.customEvoMinCap > 0) minEvoCap += Evo.customEvoMinCap;
        if (Evo.evoReadsModpack) {
            String[] splitStr;String modName;int evoAddition;
            WyrmsOfNyrus.logger.info("uwu");
            for (String base : Evo.modEvo) {
                splitStr = base.split(";");
                modName = ((splitStr[0].length() > 0) ? splitStr[0] : "null");
                evoAddition = ((splitStr[0].length() > 0) ? Integer.parseInt(splitStr[1]) : 0);
                if (Loader.isModLoaded(modName)) {
                    minEvoCap += evoAddition;
                    WyrmsOfNyrus.logger.info("Detected mod with name: " + modName + ", now adding " + evoAddition + " points to minimum evolution");
                }
            }
        }
        WyrmsOfNyrus.logger.info("Min evo finalization: " + minEvoCap);
    }

    /**
     * Removes evolution points from the world.
     * Syncs automatically. Now you can do this in one line.
     * @param w World
     * @param i Value to add
     */
    public static void subtract(World w, int i){
        WyrmVariables.WorldVariables.get(w).wyrmEvo -= i;
        if (get(w) < minEvoCap) {
            set(w,minEvoCap);
        }
        sync(w);
    }

    public static void decay(World w) {
        if (RNG.dBase(10000) == 5) {
            subtract(w, (int) (1 * Evo.evoFactor));
            evoMilestone(w);
            if(Debug.LOGGINGENABLED && Debug.DEBUGLEVEL > 4)WyrmsOfNyrus.logger.info("World's wyrm evolution was decreased by " + (1*Evo.evoFactor) + " from natural decay.s");
        }
    }

    /**
     * Syncs the variable change to the world.
     */
    private static void sync(World w) {
        WyrmVariables.WorldVariables.get(w).syncData(w);
    }
}
