package com.vetpetmon.wyrmsofnyrus.entity.ability;

import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityTheVisitor;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmWorker;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.Map;

public class WyrmlingGrowUp {

    public static void growUp(Map<String, Object> e, final EntityWyrm wyrmIn) {
        int x = (int) e.get("x");
        int y = (int) e.get("y");
        int z = (int) e.get("z");
        World world = (World) e.get("world");
        if (!world.isRemote) {
            Entity entityToSpawn = new EntityWyrmWorker(world);
            entityToSpawn.setLocationAndAngles((x - 50), (y + 40), (z - 50), world.rand.nextFloat() * 360F, 0.0F);
            world.spawnEntity(entityToSpawn);
            wyrmIn.attackEntityFrom(DamageSource.MAGIC, (float) 100000);
        }
    }

}
