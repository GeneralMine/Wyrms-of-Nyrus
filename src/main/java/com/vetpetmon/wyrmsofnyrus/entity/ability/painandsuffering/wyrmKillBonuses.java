package com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering;

import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.invasion.invasionPoints;
import net.minecraft.world.World;

public class wyrmKillBonuses {
    public static void pointIncrease(World world) {
        invasionPoints.add(world, Invasion.invasionPointsPerKill);
    }
}
