package com.vetpetmon.wyrmsofnyrus.invasion;

import net.minecraft.world.World;

public class InvasionScheduler {
    public static int getWorldDays(World world) {
        return (int) Math.floor((int) (world.getWorldTime()/24000));
    }
}
