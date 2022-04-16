package com.vetpetmon.wyrmsofnyrus.entity.ability;

import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmWorker;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.Map;

public class WyrmlingGrowUp {

    public static void growUp(Map<String, Object> e, final EntityWyrm wyrmIn, int timeUntilGrowth) {
        int x = (int) e.get("x");
        boolean hasSpawned = false;
        int y = (int) e.get("y");
        int z = (int) e.get("z");
        int GrowthTime = timeUntilGrowth;
        World world = (World) e.get("world");
        if (!world.isRemote && GrowthTime <= 0 && !hasSpawned) {
            Entity entityToSpawn = new EntityWyrmWorker(world);
            entityToSpawn.setLocationAndAngles((x), (y), (z), world.rand.nextFloat() * 360F, 0.0F);
            world.spawnEntity(entityToSpawn);
            hasSpawned = true;
        }
        if (hasSpawned) {wyrmIn.attackEntityFrom(DamageSource.MAGIC, (float) 100000);}
    }

}
