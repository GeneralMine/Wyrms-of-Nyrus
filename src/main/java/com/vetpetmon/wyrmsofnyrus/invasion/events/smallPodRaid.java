package com.vetpetmon.wyrmsofnyrus.invasion.events;

import com.vetpetmon.wyrmsofnyrus.config.ConfigLib;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityHexePod;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.Map;

public class smallPodRaid {
    static ConfigLib.Debug debug = ConfigLib.debug;
    public static void Do(Map<String, Object> e){
        int x = (int) e.get("x");
        int z = (int) e.get("z");
        World world = (World) e.get("world");
        if ((Math.random() <= 0.75)) {
            if (!world.isRemote) {
                Entity entityToSpawn = new EntityHexePod(world);
                entityToSpawn.setLocationAndAngles((x - (Math.random() * 50)), 280, (z + (Math.random() * 50)), world.rand.nextFloat() * 360F,
                        0.0F);
                world.spawnEntity(entityToSpawn);
            }
        } else if ((Math.random() <= 0.5)) {
            if (!world.isRemote) {
                Entity entityToSpawn = new EntityHexePod(world);
                entityToSpawn.setLocationAndAngles((x + (Math.random() * 50)), 280, (z - (Math.random() * 50)), world.rand.nextFloat() * 360F,
                        0.0F);
                world.spawnEntity(entityToSpawn);
            }
        } else if ((Math.random() <= 0.25)) {
            if (!world.isRemote) {
                Entity entityToSpawn = new EntityHexePod(world);
                entityToSpawn.setLocationAndAngles((x + (Math.random() * 50)), 280, (z + (Math.random() * 50)), world.rand.nextFloat() * 360F,
                        0.0F);
                world.spawnEntity(entityToSpawn);
            }
        } else {
            if (!world.isRemote) {
                Entity entityToSpawn = new EntityHexePod(world);
                entityToSpawn.setLocationAndAngles((x - (Math.random() * 50)), 280, (z - (Math.random() * 50)), world.rand.nextFloat() * 360F,
                        0.0F);
                world.spawnEntity(entityToSpawn);
            }
        }
        if (debug.LOGGINGENABLED && debug.DEBUGLEVEL >= 2) wyrmsofnyrus.logger.info("A small hexepod was spawned.");
    }
}
