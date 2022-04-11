package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.World;
import net.minecraft.util.SoundCategory;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.Entity;

import java.util.Map;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityTheVisitor;
import com.vetpetmon.wyrmsofnyrus.wyrmVariables;

public class VisitorEvent {

	public static void executeProcedure(Map<String, Object> dependencies) {
		int x = (int) dependencies.get("x");
		int y = (int) dependencies.get("y");
		int z = (int) dependencies.get("z");
		World world = (World) dependencies.get("world");
		if ((!(wyrmVariables.MapVariables.get(world).invasionStarted))) {
			if ((Math.random() < 0.001)) {
				if (!world.isRemote) {
					Entity entityToSpawn = new EntityTheVisitor(world);
					entityToSpawn.setLocationAndAngles(x, (y + 50), z, world.rand.nextFloat() * 360F, 0.0F);
					world.spawnEntity(entityToSpawn);
				}
				if (!world.isRemote) {
					Entity entityToSpawn = new EntityTheVisitor(world);
					entityToSpawn.setLocationAndAngles((x + 50), (y + 50), (z + 50), world.rand.nextFloat() * 360F, 0.0F);
					world.spawnEntity(entityToSpawn);
				}
				if (!world.isRemote) {
					Entity entityToSpawn = new EntityTheVisitor(world);
					entityToSpawn.setLocationAndAngles((x - 50), (y + 50), (z - 50), world.rand.nextFloat() * 360F, 0.0F);
					world.spawnEntity(entityToSpawn);
				}
				world.playSound(null, x, y, z, SoundRegistry.theVisitor, SoundCategory.NEUTRAL, (float) 200, (float) 1);
				world.addWeatherEffect(new EntityLightningBolt(world, x, 170, z, false));
				world.addWeatherEffect(new EntityLightningBolt(world, x, 170, z, false));
				world.addWeatherEffect(new EntityLightningBolt(world, x, 170, z, false));
				wyrmVariables.MapVariables.get(world).invasionStarted = true;
				wyrmVariables.MapVariables.get(world).syncData(world);
			}
		}
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			Entity entity = event.player;
			World world = entity.world;
			int i = (int) entity.posX;
			int j = (int) entity.posY;
			int k = (int) entity.posZ;
			java.util.HashMap<String, Object> dependencies = new java.util.HashMap<>();
			dependencies.put("x", i);
			dependencies.put("y", j);
			dependencies.put("z", k);
			dependencies.put("world", world);
			dependencies.put("entity", entity);
			dependencies.put("event", event);
			this.executeProcedure(dependencies);
		}
	}

	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
	}
}
