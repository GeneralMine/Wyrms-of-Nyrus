package com.vetpetmon.wyrmsofnyrus.entity.ability;

import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityCallousPod;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmProber;
import com.vetpetmon.synapselib.util.RNG;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.Map;

public class callouspodContents {
    public static void onDeath(Map<String, Object> dpnds) {
        Entity entity = (Entity) dpnds.get("entity");
        int x = (int) dpnds.get("x");
        int y = (int) dpnds.get("y");
        int z = (int) dpnds.get("z");
        World world = (World) dpnds.get("world");
        for (int index0 = 0; index0 < (RNG.getIntRangeInclu(Invasion.minWyrmsCallouspod,Invasion.maxWyrmsCallouspod)); index0++) {
            if ((entity instanceof EntityCallousPod)) {
                if (!world.isRemote) {
                    Entity entityToSpawn = new EntityWyrmProber(world);
                    entityToSpawn.setLocationAndAngles(x, y+2, z, world.rand.nextFloat() * 360F, 0.0F);
                    world.spawnEntity(entityToSpawn);
                }
            }
        }
    }
}
