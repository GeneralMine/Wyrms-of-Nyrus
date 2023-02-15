package com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.Evo;
import com.vetpetmon.wyrmsofnyrus.config.Radiogenetics;
import com.vetpetmon.wyrmsofnyrus.evo.evoPoints;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class wyrmDeathSpecial {
    public static void wyrmDeathSpecial(Entity entityIn, BlockPos pos, World world, double power) {
        int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        if (power > 0) {
            int evoPointBonus = (power > 20) ? (int) Math.floor((power - 20) / 20) : 0;
            evoPoints.add(world, (int) ((1 + evoPointBonus) * Evo.evoFactor));
            if ((entityIn.isBurning()) && Radiogenetics.explodingWyrms) {
                float explosionPower = (float) power / 2;
                if (!world.isRemote) {
                    world.createExplosion(null, x, y, z, explosionPower, true);
                }
                if ((explosionPower >= 20)) {
                    world.addWeatherEffect(new EntityLightningBolt(world, x, y, z, false));
                    world.playSound(null, x, y, z, SoundRegistry.nukeClose, SoundCategory.AMBIENT, (float) (100.0 * explosionPower), 1.00F);
                    world.playSound(null, x, y, z, SoundRegistry.nukeFar, SoundCategory.AMBIENT, (float) (1000.0 * explosionPower), 1.00F);
                }
            }
        }
    }
}