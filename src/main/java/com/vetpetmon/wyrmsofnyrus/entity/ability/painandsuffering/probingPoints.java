package com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering;

import com.vetpetmon.wyrmsofnyrus.wyrmVariables;
import net.minecraft.world.World;

public class probingPoints {
    public static void probingPoints(World world) {
        wyrmVariables.WorldVariables.get(world).wyrmInvasionPoints += 5;
        wyrmVariables.WorldVariables.get(world).syncData(world);
    }
}
