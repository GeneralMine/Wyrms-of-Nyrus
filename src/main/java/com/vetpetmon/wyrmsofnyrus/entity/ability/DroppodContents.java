package com.vetpetmon.wyrmsofnyrus.entity.ability;

import com.vetpetmon.synapselib.util.RNG;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.config.Radiogenetics;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.entity.creeped.*;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DroppodContents {
    private static void dropPodContents(BlockPos pos, int size, int type, World world) {
        if (!world.isRemote) {
            switch (type) {
                case (4):
                    for (int i = 0; i < (RNG.getIntRangeInclu(Invasion.minWyrmsCallouspod,Invasion.maxWyrmsCallouspod + size)); i++) {spawnMob(pos, new EntityWyrmProber(world), world);}
                    break;
                case (3):
                    spawnMob(pos, new EntityCreepwyrm(world), world);
                    for (int i = 0, m = ((RNG.getIntRangeInclu(2, 2 + size))); i < m; i++) {spawnMob(pos, new EntityBiter(world), world);}
                    for (int i = 0, m = ((RNG.getIntRangeInclu(1, 1 + size))); i < m; i++) {spawnMob(pos, new EntityCreepedHumanoid(world), world);}
                    break;
                case (2):
                    for (int i = 0, m = ((RNG.getIntRangeInclu(Invasion.minWyrmsCallouspod,Invasion.maxWyrmsCallouspod + size))); i < m; i++) {spawnMob(pos, new EntityWyrmRover(world), world);}
                    for (int i = 0, m = ((RNG.getIntRangeInclu((int)Math.floor((Invasion.minWyrmsCallouspod + size)/2), (int)Math.floor((Invasion.maxWyrmsCallouspod + size)/2)))); i < m; i++) {spawnMob(pos, new EntityWyrmSoldier(world), world);}
                    break;
                default:
                    for (int i = 0, m = ((RNG.getIntRangeInclu(Invasion.minWyrmsHexepod,Invasion.maxWyrmsHexepod + size))); i < m; i++) {spawnMob(pos, new EntityWyrmling(world), world);}
                    break;
            }
        }
    }
    private static void spawnMob(BlockPos pos, EntityWyrm type, World world){
        Entity entityToSpawn = type;
        entityToSpawn.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), world.rand.nextFloat() * 360F, 0.0F);
        world.spawnEntity(entityToSpawn);
    }
    public static void DropPodEventSequence(BlockPos pos, int size, int type, World world) {
        if (!world.isRemote && Radiogenetics.explodingDropPods) {
            world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), (float) (size*0.85), true);
        }
        dropPodContents(pos,size,type,world);
    }
}
