package com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

public class wyrmDeathSpecial {
    public static void wyrmDeathSpecial(Entity entityIn, BlockPos pos, World world, double power) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if ((entityIn.isBurning())) {
            if (!world.isRemote) {
                world.createExplosion(null, x, y, z, (float) (power), true);
            }
            if (((power) >= 20)) {
                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z, false));
            }
        }
    }
}
