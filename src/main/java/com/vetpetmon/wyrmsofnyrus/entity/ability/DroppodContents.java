package com.vetpetmon.wyrmsofnyrus.entity.ability;

import com.vetpetmon.wyrmsofnyrus.entity.creeped.EntityBiter;
import com.vetpetmon.wyrmsofnyrus.entity.creeped.EntityCreepedHumanoid;
import com.vetpetmon.wyrmsofnyrus.entity.creeped.EntityCreepwyrm;
import com.vetpetmon.synapselib.util.RNG;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DroppodContents {
    public static void droppodContents(BlockPos pos, int size, int type, World world) {
        Entity entityToSpawn;
        if (!world.isRemote) {
            switch (type) {
                case (3):
                    entityToSpawn = new EntityCreepwyrm(world);
                    entityToSpawn.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), world.rand.nextFloat() * 360F, 0.0F);
                    world.spawnEntity(entityToSpawn);
                    for (int i = 0, m = ((RNG.getIntRangeInclu(2, 2 + size))); i < m; i++) {
                        entityToSpawn = new EntityBiter(world);
                        entityToSpawn.setLocationAndAngles(pos.getX(), pos.getY() + 1, pos.getZ(), world.rand.nextFloat() * 360F, 0.0F);
                        world.spawnEntity(entityToSpawn);
                    }
                    for (int i = 0, m = ((RNG.getIntRangeInclu(1, 1 + size))); i < m; i++) {
                        entityToSpawn = new EntityCreepedHumanoid(world);
                        entityToSpawn.setLocationAndAngles(pos.getX(), pos.getY() + 1, pos.getZ(), world.rand.nextFloat() * 360F, 0.0F);
                        world.spawnEntity(entityToSpawn);
                    }
                    break;
            }
        }
    }
}
