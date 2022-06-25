package com.vetpetmon.wyrmsofnyrus.evo;

import com.vetpetmon.wyrmsofnyrus.synapselib.RNG;
import com.vetpetmon.wyrmsofnyrus.wyrmVariables;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * A collection of functions used to mess with invasion points.
 * I made this specifically because I hated writing so much code every
 * time I wanted to add points to the invasion
 *
 * Even worse, there's no dedicated getter. I'm fixing that as we speak.
 */
public class evoPoints {
    public static double get(World w) {return wyrmVariables.WorldVariables.get(w).wyrmEvo;}

    /**
     * Adds invasion points to the world.
     * Syncs automatically. Now you can do this in one line.
     * @param w World
     * @param i Value to add
     */
    public static void add(World w, int i){
        wyrmVariables.WorldVariables.get(w).wyrmEvo += i;
        sync(w);
    }

    /**
     * Removes invasion points from the world.
     * Syncs automatically. Now you can do this in one line.
     * @param w World
     * @param i Value to add
     */
    public static void subtract(World w, int i){
        wyrmVariables.WorldVariables.get(w).wyrmEvo -= i;
        sync(w);
    }

    /**
     * Syncs the variable change to the world.
     */
    private static void sync(World w) {
        wyrmVariables.WorldVariables.get(w).syncData(w);
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END ) {
            World world = event.world;
            if (RNG.dBase(300) == 1) {
                subtract(world,1);
            }
        }
    }
}
