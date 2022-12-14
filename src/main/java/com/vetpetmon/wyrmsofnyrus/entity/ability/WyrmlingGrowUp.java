package com.vetpetmon.wyrmsofnyrus.entity.ability;

import com.vetpetmon.wyrmsofnyrus.compat.hbm;
import com.vetpetmon.wyrmsofnyrus.config.Evo;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.*;
import com.vetpetmon.wyrmsofnyrus.evo.evoPoints;
import com.vetpetmon.wyrmsofnyrus.synapselib.util.RNG;
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
        int entityToGrowTo = RNG.dBase(18);
        World world = (World) e.get("world");
        int evoPointsInWorld = evoPoints.get(world);
        switch(entityToGrowTo) {
            case(11):
                if (Evo.evoEnabled && evoPointsInWorld >= 100) {
                    entityToSpawn = new EntityMyrmur(world);
                }
                else {
                    entityToSpawn = new EntityWyrmWorker(world);
                }
                if(!Evo.evoEnabled) entityToSpawn = new EntityMyrmur(world);

                break;
            case(8):
            case(9):
                if (Evo.evoVariantsEnabled && (evoPointsInWorld >= 400 && RNG.dBase(3) == 2)) {
                    entityToSpawn = new EntityWyrmRoverUranium(world);
                }
                else {
                    entityToSpawn = new EntityWyrmRover(world);
                }
                break;
            case(15):
            case(16):
                if (hbm.isEnabled() && Evo.evoVariantsEnabled)
                {
                    if (evoPointsInWorld >= 1800){
                        entityToSpawn = HBMwyrms.getWyrm("Warrior",world);
                    }
                    else if (evoPointsInWorld >= 800 && RNG.dBase(3) == 2) {
                        entityToSpawn = HBMwyrms.getWyrm("Warrior",world);
                    }
                    else entityToSpawn = new EntityWyrmWarrior(world);
                }
                else {
                    entityToSpawn = new EntityWyrmWarrior(world);
                }
                break;
            case(0):
            case(1):
            case(2):
            case(3):
                if (Evo.evoVariantsEnabled && (evoPointsInWorld >= 50 && RNG.dBase(3) == 2)) {
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
