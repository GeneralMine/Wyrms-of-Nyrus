package com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering;

import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.wyrmVariables;
import net.minecraft.world.World;

public class wyrmKillBonuses {
    public static void pointIncrease(World world) {
        wyrmVariables.WorldVariables.get(world).wyrmInvasionPoints += Invasion.invasionPointsPerKill;
        wyrmVariables.WorldVariables.get(world).syncData(world);
    }
}
