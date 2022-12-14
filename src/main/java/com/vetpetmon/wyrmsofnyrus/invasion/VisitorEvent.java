package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityTheVisitor;
import com.vetpetmon.wyrmsofnyrus.invasion.events.smallPodRaid;
import com.vetpetmon.wyrmsofnyrus.synapselib.util.RNG;
import com.vetpetmon.wyrmsofnyrus.wyrmVariables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class VisitorEvent {
	public static void visitorEvent(Boolean forced, World world, int x, int y, int z) {
		if (((!wyrmVariables.WorldVariables.get(world).invasionStarted) && InvasionScheduler.invasionStartCondition()) || (forced)) {
			if (!world.isRemote) {
				Entity entityToSpawn = new EntityTheVisitor(world);
				entityToSpawn.setLocationAndAngles(x, y+120, z, world.rand.nextFloat() * 360F, 0.0F);
				world.spawnEntity(entityToSpawn);
			}
			world.playSound(null, x, y, z, SoundRegistry.theVisitor, SoundCategory.MASTER, (float) 200, (float) 0.5);
			world.addWeatherEffect(new EntityLightningBolt(world, x, 170, z, false));
			if (!forced) {
				for (int index0 = 0; index0 < (2 + (RNG.getIntRangeInclu(1, 3))); index0++) {
					smallPodRaid.callEvent(x,z,world);
				}
			}
			wyrmVariables.WorldVariables.get(world).invasionStarted = true;
			wyrmVariables.WorldVariables.get(world).syncData(world);
		}
	}
}
