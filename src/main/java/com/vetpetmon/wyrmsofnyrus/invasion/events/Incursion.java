package com.vetpetmon.wyrmsofnyrus.invasion.events;

import com.vetpetmon.synapselib.util.RNG;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.world.World;

public class Incursion {
    public static void callEvent(double x, double z, World world, int level) {
        int raidSize = (RNG.getIntRangeInclu((4 + level),(6 + (level*2))));
        world.addWeatherEffect(new EntityLightningBolt(world, x, 200, z, false));
        world.addWeatherEffect(new EntityLightningBolt(world, x - 6, 200, z - 10, true));
        for (int i = 0; i < raidSize; i++) {
            ScoutingPodRaid.callEvent(x,z,world);
        }
        world.addWeatherEffect(new EntityLightningBolt(world, x + 20, 200, z + 30, true));
        for (int i = 0; i < raidSize; i = i+2) {
            SmallPodRaid.callEvent(x,z,world);
        }
        world.addWeatherEffect(new EntityLightningBolt(world, x - 26, 200, z + 3, true));
    }
}
