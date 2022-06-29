package com.vetpetmon.wyrmsofnyrus.entity.ability;

import com.vetpetmon.wyrmsofnyrus.config.Evo;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.*;
import com.vetpetmon.wyrmsofnyrus.evo.evoPoints;
import com.vetpetmon.wyrmsofnyrus.synapselib.RNG;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.Map;

public class WyrmlingGrowUp {

    public static void growUp(Map<String, Object> e, final EntityWyrm wyrmIn) {
        int x = (int) e.get("x");
        boolean hasSpawned = false;
        int y = (int) e.get("y");
        int z = (int) e.get("z");
        Entity entityToSpawn;
        int entityToGrowTo = RNG.dBase(13);
        World world = (World) e.get("world");
        switch(entityToGrowTo) {
            case(11):
                if (Evo.evoEnabled && evoPoints.get(world) >= 100) {
                    entityToSpawn = new EntityMyrmur(world);
                }
                else {
                    entityToSpawn = new EntityWyrmWorker(world);
                }
                if(!Evo.evoEnabled) entityToSpawn = new EntityMyrmur(world);

                break;
            case(8):
                if (Evo.evoEnabled && evoPoints.get(world) >= 400) {
                    entityToSpawn = new EntityWyrmRoverUranium(world);
                }
                else {
                    entityToSpawn = new EntityWyrmRover(world);
                }
            case(0):
            case(1):
            case(2):
            case(3):
                if (Evo.evoVariantsEnabled && (evoPoints.get(world) >= 50 && RNG.dBase(3) == 2)) {
                    entityToSpawn = new EntityWyrmSoldierInfectoid(world);
                }
                else {
                    entityToSpawn = new EntityWyrmSoldier(world);
                }
                break;
            default:
                entityToSpawn = new EntityWyrmWorker(world);
                break;
        }
        if (!world.isRemote && !hasSpawned) {
            entityToSpawn.setLocationAndAngles((x), (y), (z), world.rand.nextFloat() * 360F, 0.0F);
            world.spawnEntity(entityToSpawn);
            hasSpawned = true;
        }
        if (hasSpawned) {wyrmIn.setDead();}
    }

}
