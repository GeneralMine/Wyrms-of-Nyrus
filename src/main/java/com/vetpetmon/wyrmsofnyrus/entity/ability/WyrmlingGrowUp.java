package com.vetpetmon.wyrmsofnyrus.entity.ability;

import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmSoldier;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmWorker;
import com.vetpetmon.wyrmsofnyrus.synapselib.RNG;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.Map;

public class WyrmlingGrowUp {

    public static void growUp(Map<String, Object> e, final EntityWyrm wyrmIn, int timeUntilGrowth) {
        int x = (int) e.get("x");
        boolean hasSpawned = false;
        int y = (int) e.get("y");
        int z = (int) e.get("z");
        Entity entityToSpawn;
        int GrowthTime = timeUntilGrowth;
        int entityToGrowTo = RNG.dBase(1);
        World world = (World) e.get("world");
        switch(entityToGrowTo) {
            case(0):
                entityToSpawn = new EntityWyrmSoldier(world);
                break;
            default:
                entityToSpawn = new EntityWyrmWorker(world);
                break;
        }
        if (!world.isRemote && GrowthTime <= 0 && !hasSpawned) {
            entityToSpawn.setLocationAndAngles((x), (y), (z), world.rand.nextFloat() * 360F, 0.0F);
            world.spawnEntity(entityToSpawn);
            hasSpawned = true;
        }
        if (hasSpawned) {wyrmIn.setDead();}
    }

}
