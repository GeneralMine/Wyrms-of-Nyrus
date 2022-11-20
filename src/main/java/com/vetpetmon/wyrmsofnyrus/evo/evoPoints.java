package com.vetpetmon.wyrmsofnyrus.evo;

import com.vetpetmon.wyrmsofnyrus.AutoReg;
import com.vetpetmon.wyrmsofnyrus.compat.hbm;
import com.vetpetmon.wyrmsofnyrus.config.Evo;
import com.vetpetmon.wyrmsofnyrus.synapselib.RNG;
import com.vetpetmon.wyrmsofnyrus.wyrmVariables;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * A collection of functions used to mess with invasion points.
 * I made this specifically because I hated writing so much code every
 * time I wanted to add points to the invasion
 *
 * Even worse, there's no dedicated getter. I'm fixing that as we speak.
 */
@AutoReg.ModElement.Tag
public class evoPoints extends AutoReg.ModElement {
    public evoPoints(AutoReg instance) {
        super(instance, 206);
    }

    public static int minEvoCap = 0;
    public static int get(World w) {return wyrmVariables.WorldVariables.get(w).wyrmEvo;}
    public static void set(World w, int i) {wyrmVariables.WorldVariables.get(w).wyrmEvo = i;}
    public static double evoMilestone(World w){
        double evoBoost = 1.0;
        if (get(w) >= 2000) {
            evoBoost = 4.0;
        }
        else if (get(w) >= 800) {
            evoBoost = 2.0;
        }
        else if (get(w) >= 400) {
            evoBoost = 1.75;
        }
        else if (get(w) >= 250) {
            evoBoost = 1.5;
        }
        else if (get(w) >= 150) {
            evoBoost = 1.25;
        }
        return evoBoost;
    }

    /**
     * Adds invasion points to the world.
     * Syncs automatically. Now you can do this in one line.
     * @param w World
     * @param i Value to add
     */
    public static void add(World w, int i){
        if (get(w) < minEvoCap) {
            set(w,minEvoCap);
        }
        wyrmVariables.WorldVariables.get(w).wyrmEvo += i;
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
        }
    }

    /**
     * Syncs the variable change to the world.
     */
    private static void sync(World w) {
        wyrmVariables.WorldVariables.get(w).syncData(w);
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        World world = event.world;
        decay(world);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }
}
