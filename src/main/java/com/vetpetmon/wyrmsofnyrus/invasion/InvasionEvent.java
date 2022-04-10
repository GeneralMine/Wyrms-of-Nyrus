package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityHexePod;
import com.vetpetmon.wyrmsofnyrus.wyrmVariables;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.World;
import net.minecraft.entity.Entity;

import java.util.Map;

import com.vetpetmon.wyrmsofnyrus.AutoReg;

@AutoReg.ModElement.Tag
public class InvasionEvent extends AutoReg.ModElement {
	public InvasionEvent(AutoReg instance) {
		super(instance, 25);
	}

	public static void executescript(Map<String, Object> dependencies) {
		int x = (int) dependencies.get("x");
		int z = (int) dependencies.get("z");
		World world = (World) dependencies.get("world");
		if ((Math.random() > (0.99999 - ((wyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty)/5000)))) {
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
			this.executescript(dependencies);
		}
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
	}
}
