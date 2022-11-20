package com.vetpetmon.wyrmsofnyrus.invasion.events;

import com.vetpetmon.wyrmsofnyrus.synapselib.RNG;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.world.World;

import java.util.Map;

public class massIncursion {
    public static void call(Map<String, Object> e, int level) {
        World world = (World) e.get("world");
        int raidSize = (RNG.getIntRangeInclu((4 + level),(6 + (level*2))));
        world.addWeatherEffect(new EntityLightningBolt(world, (Double) e.get("x"), 200, (Double) e.get("z"), false));
        world.addWeatherEffect(new EntityLightningBolt(world, (Double) e.get("x") - 6, 200, (Double) e.get("z") - 10, true));
        for (int i = 0; i < raidSize; i++) {
            scoutingPodRaid.call(e);
        }
        world.addWeatherEffect(new EntityLightningBolt(world, (Double) e.get("x") + 20, 200, (Double) e.get("z") + 30, true));
        for (int i = 0; i < raidSize; i = i+2) {
            smallPodRaid.Do(e);
        }
        world.addWeatherEffect(new EntityLightningBolt(world, (Double) e.get("x") - 26, 200, (Double) e.get("z") + 3, true));
    }
}
