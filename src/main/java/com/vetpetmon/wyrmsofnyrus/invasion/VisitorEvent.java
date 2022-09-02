package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.AutoReg;
import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.invasion.events.smallPodRaid;
import com.vetpetmon.wyrmsofnyrus.synapselib.RNG;
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

@AutoReg.ModElement.Tag
public class VisitorEvent extends AutoReg.ModElement {
	public VisitorEvent(AutoReg instance) {
		super(instance, 205);
	}

	public static void executeProcedure(Map<String, Object> e, Boolean forced, World world) {
		int x = (int) e.get("x");
		int y = (int) e.get("y");
		int z = (int) e.get("z");
		boolean isForced = forced;
		if (((!wyrmVariables.WorldVariables.get(world).invasionStarted) && ((RNG.getIntRangeInclu(0, 20000000)) == 1)) || (isForced)) {
				if (!world.isRemote) {
					Entity entityToSpawn = new EntityTheVisitor(world);
					entityToSpawn.setLocationAndAngles(x, (y + 40), z, world.rand.nextFloat() * 360F, 0.0F);
					world.spawnEntity(entityToSpawn);
				}
				world.playSound(null, x, y, z, SoundRegistry.theVisitor, SoundCategory.MASTER, (float) 200, (float) 1);
				world.addWeatherEffect(new EntityLightningBolt(world, x, 170, z, false));
				world.addWeatherEffect(new EntityLightningBolt(world, x, 170, z, false));
				world.addWeatherEffect(new EntityLightningBolt(world, x, 170, z, false));
				if (!isForced) {
					for (int index0 = 0; index0 < (2 + (RNG.getIntRangeInclu(1, 3))); index0++) {
						smallPodRaid.Do(e);
					}
				}
				wyrmVariables.WorldVariables.get(world).invasionStarted = true;
				wyrmVariables.WorldVariables.get(world).syncData(world);
		}
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		Entity entity = event.player;
		World world = entity.world;
		if (!wyrmVariables.WorldVariables.get(world).invasionStarted && Invasion.invasionStartsNaturally) {
			java.util.HashMap<String, Object> dependencies = new java.util.HashMap<>();
			dependencies.put("x", (int) entity.posX);
			dependencies.put("y", (int) entity.posY);
			dependencies.put("z", (int) entity.posZ);
			dependencies.put("world", world);
			dependencies.put("entity", entity);
			executeProcedure(dependencies, false, world);
			wyrmVariables.WorldVariables.get(world).invasionStarted = true;
			wyrmVariables.WorldVariables.get(world).syncData(world);
		}
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
	}
}
