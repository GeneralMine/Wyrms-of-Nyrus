package com.vetpetmon.wyrmsofnyrus.entity.ability;

import com.vetpetmon.synapselib.util.RNG;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityHexePod;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmling;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.Map;

public class hexepodContents {
    public static void doThis(Map<String, Object> dpnds) {
        Entity entity = (Entity) dpnds.get("entity");
        int x = (int) dpnds.get("x");
        int y = (int) dpnds.get("y");
        int z = (int) dpnds.get("z");
        World world = (World) dpnds.get("world");
        for (int index0 = 0; index0 < (RNG.getIntRangeInclu(Invasion.minWyrmsHexepod,Invasion.maxWyrmsHexepod)); index0++) {
            if ((entity instanceof EntityHexePod)) {
                if (!world.isRemote) {
                    Entity entityToSpawn = new EntityWyrmling(world);
                    entityToSpawn.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360F, 0.0F);
                    world.spawnEntity(entityToSpawn);
                }
            }
        }
    }
}
