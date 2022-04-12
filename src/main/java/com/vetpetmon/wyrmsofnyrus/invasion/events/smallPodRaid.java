package com.vetpetmon.wyrmsofnyrus.invasion.events;

import com.vetpetmon.wyrmsofnyrus.AutoReg;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityHexePod;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Map;

@AutoReg.ModElement.Tag
public class smallPodRaid extends AutoReg.ModElement{
    public smallPodRaid(AutoReg elements, int sortid) {
        super(elements, sortid);
    }
    public static void Do(Map<String, Object> e){
        int x = (int) e.get("x");
        int z = (int) e.get("z");
        World world = (World) e.get("world");
        if ((Math.random() <= 0.75)) {
            if (!world.isRemote) {
                Entity entityToSpawn = new EntityHexePod(world);
                entityToSpawn.setLocationAndAngles((x - (Math.random() * 50)), 280, (z + (Math.random() * 50)), world.rand.nextFloat() * 360F,
                        0.0F);
                world.spawnEntity(entityToSpawn);
            }
        } else if ((Math.random() <= 0.5)) {
            if (!world.isRemote) {
                Entity entityToSpawn = new EntityHexePod(world);
                entityToSpawn.setLocationAndAngles((x + (Math.random() * 50)), 280, (z - (Math.random() * 50)), world.rand.nextFloat() * 360F,
                        0.0F);
                world.spawnEntity(entityToSpawn);
            }
        } else if ((Math.random() <= 0.25)) {
            if (!world.isRemote) {
                Entity entityToSpawn = new EntityHexePod(world);
                entityToSpawn.setLocationAndAngles((x + (Math.random() * 50)), 280, (z + (Math.random() * 50)), world.rand.nextFloat() * 360F,
                        0.0F);
                world.spawnEntity(entityToSpawn);
            }
        } else {
            if (!world.isRemote) {
                Entity entityToSpawn = new EntityHexePod(world);
                entityToSpawn.setLocationAndAngles((x - (Math.random() * 50)), 280, (z - (Math.random() * 50)), world.rand.nextFloat() * 360F,
                        0.0F);
                world.spawnEntity(entityToSpawn);
            }
        }
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }
}
