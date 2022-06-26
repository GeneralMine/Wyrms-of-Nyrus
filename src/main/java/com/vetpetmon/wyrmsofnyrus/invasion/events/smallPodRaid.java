package com.vetpetmon.wyrmsofnyrus.invasion.events;

import com.vetpetmon.wyrmsofnyrus.config.Debug;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityHexePod;
import com.vetpetmon.wyrmsofnyrus.synapselib.RNG;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.Map;

public class smallPodRaid {
    public static void Do(Map<String, Object> e){
        int x = (int) e.get("x");
        int z = (int) e.get("z");
        World world = (World) e.get("world");
        Entity entityToSpawn = new EntityHexePod(world);
        if (!world.isRemote) {
            entityToSpawn.setLocationAndAngles((x + (RNG.getIntRangeInclu(-250,250))), 280, (z + (RNG.getIntRangeInclu(-250,250))), world.rand.nextFloat() * 360F,
                    0.0F);
            world.spawnEntity(entityToSpawn);
        }
        if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 2) wyrmsofnyrus.logger.info("A small hexepod was spawned.");
    }
}
