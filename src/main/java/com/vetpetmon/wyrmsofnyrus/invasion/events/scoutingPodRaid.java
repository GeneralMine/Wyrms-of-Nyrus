package com.vetpetmon.wyrmsofnyrus.invasion.events;

import com.vetpetmon.wyrmsofnyrus.config.Debug;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityCallousPod;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityHexePod;
import com.vetpetmon.wyrmsofnyrus.synapselib.RNG;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.Map;

public class scoutingPodRaid {
    public static void call(Map<String, Object> e){
        int x = (int) e.get("x");
        int z = (int) e.get("z");
        World world = (World) e.get("world");
        Entity entityToSpawn = new EntityCallousPod(world);
        if (!world.isRemote) {
            entityToSpawn.setLocationAndAngles((x + (RNG.PMRange(Invasion.maxEventDistance))), 280, (z + (RNG.PMRange(Invasion.maxEventDistance))), world.rand.nextFloat() * 360F,
                    0.0F);
            world.spawnEntity(entityToSpawn);
        }
        if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 2) wyrmsofnyrus.logger.info("A callouspod was spawned.");
    }
}
