package com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.Radiogenetics;
import com.vetpetmon.wyrmsofnyrus.evo.evoPoints;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class wyrmDeathSpecial {
    public static void wyrmDeathSpecial(Entity entityIn, BlockPos pos, World world, double power) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        evoPoints.add(world,1);
        if ((entityIn.isBurning()) && Radiogenetics.explodingWyrms) {
            if (!world.isRemote) {
                world.createExplosion(null, x, y, z, (float) (power), true);
            }
            if (((power) >= 20)) {
                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z, true));
                world.playSound(null, x, y, z, SoundRegistry.nukeClose, SoundCategory.AMBIENT, (float) (100.0 * power), 1.00F);
                world.playSound(null, x, y, z, SoundRegistry.nukeFar, SoundCategory.AMBIENT, (float) (1000.0 * power), 1.00F);
            }
        }
    }
}