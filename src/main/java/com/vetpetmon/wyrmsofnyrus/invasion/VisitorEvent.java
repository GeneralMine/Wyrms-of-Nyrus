package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityTheVisitor;
import com.vetpetmon.wyrmsofnyrus.invasion.events.smallPodRaid;
import com.vetpetmon.wyrmsofnyrus.synapselib.RNG;
import com.vetpetmon.wyrmsofnyrus.wyrmVariables;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Map;

public class VisitorEvent {
	private static int nextDay = 0;

	public static void visitorEvent(Map<String, Object> e, Boolean forced, World world) {
		int x = (int) e.get("x"),y = (int) e.get("y")+40,z = (int) e.get("z");
		if (((!wyrmVariables.WorldVariables.get(world).invasionStarted) && invasionStartCondition(world)) || (forced)) {
			if (!world.isRemote) {
				Entity entityToSpawn = new EntityTheVisitor(world);
				entityToSpawn.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360F, 0.0F);
				world.spawnEntity(entityToSpawn);
			}
			world.playSound(null, x, y, z, SoundRegistry.theVisitor, SoundCategory.MASTER, (float) 200, (float) 0.5);
			world.addWeatherEffect(new EntityLightningBolt(world, x, 170, z, false));
			if (!forced) {
				for (int index0 = 0; index0 < (2 + (RNG.getIntRangeInclu(1, 3))); index0++) {
					smallPodRaid.Do(e);
				}
			}
			wyrmVariables.WorldVariables.get(world).invasionStarted = true;
			wyrmVariables.WorldVariables.get(world).syncData(world);
		}
	}

	public static boolean invasionStartCondition(World world) {
		int currentDay = InvasionScheduler.getWorldDays(world);
		if (nextDay > currentDay + 1) nextDay = currentDay;
		else if (currentDay >= nextDay) {
			nextDay = currentDay + 1;
			wyrmsofnyrus.logger.info("Day changed detected, current day is now " + currentDay + ".");
			wyrmsofnyrus.logger.info("invasionStartTime is " + Invasion.invasionStartTime + ".");
			wyrmsofnyrus.logger.info("invasionStartChance is " + Invasion.invasionStartChance + ".");
			if (Invasion.invasionStartMode > 1) {
				return currentDay > Invasion.invasionStartTime && (RNG.getIntRangeInclu(0, Invasion.invasionStartChance) == 1);
			} else if (Invasion.invasionStartMode == 1) {
				return currentDay > Invasion.invasionStartTime;
			} else {
				return RNG.getIntRangeInclu(0, Invasion.invasionStartChance) == 1;
			}
		}
		//wyrmsofnyrus.logger.info("Current day is " + currentDay + ", next day is "+nextDay+".");
		return false;
	}


	@Deprecated
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
			visitorEvent(dependencies, false, world);
		}
	}
}
