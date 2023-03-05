package com.vetpetmon.wyrmsofnyrus.invasion.events;

import com.vetpetmon.synapselib.util.RNG;
import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import com.vetpetmon.wyrmsofnyrus.config.Debug;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityCallousPod;
import net.minecraft.world.World;

public class ScoutingPodRaid {
    public static void callEvent(double x, double z, World world){
        EntityCallousPod entityToSpawn = new EntityCallousPod(world);
        if (!world.isRemote) {
            entityToSpawn.setLocationAndAngles((x + (RNG.PMRange(Invasion.maxEventDistance))), 280, (z + (RNG.PMRange(Invasion.maxEventDistance))), world.rand.nextFloat() * 360F,
                    0.0F);
            world.spawnEntity(entityToSpawn);
        }
        if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 2) WyrmsOfNyrus.logger.info("A callouspod was spawned.");
    }

    public static void callEvent(double x, double z, World world, int podID, int size){
        EntityCallousPod entityToSpawn = new EntityCallousPod(world);
        entityToSpawn.setPodType(podID, size);
        if (!world.isRemote) {
            entityToSpawn.setLocationAndAngles((x + (RNG.PMRange(Invasion.maxEventDistance))), 280, (z + (RNG.PMRange(Invasion.maxEventDistance))), world.rand.nextFloat() * 360F,
                    0.0F);
            world.spawnEntity(entityToSpawn);
        }
        if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 2) WyrmsOfNyrus.logger.info("A callouspod was spawned.");
    }
}
