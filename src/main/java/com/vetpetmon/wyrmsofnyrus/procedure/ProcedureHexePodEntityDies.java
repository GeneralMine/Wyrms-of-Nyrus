package com.vetpetmon.wyrmsofnyrus.procedure;

import net.minecraft.world.World;
import net.minecraft.entity.Entity;

import java.util.Map;

import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrmling;
import com.vetpetmon.wyrmsofnyrus.entity.EntityHexePod;
import com.vetpetmon.wyrmsofnyrus.ElementswyrmsofnyrusMod;

@ElementswyrmsofnyrusMod.ModElement.Tag
public class ProcedureHexePodEntityDies extends ElementswyrmsofnyrusMod.ModElement {
	public ProcedureHexePodEntityDies(ElementswyrmsofnyrusMod instance) {
		super(instance, 24);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			System.err.println("Failed to load dependency entity for procedure HexePodEntityDies!");
			return;
		}
		if (dependencies.get("x") == null) {
			System.err.println("Failed to load dependency x for procedure HexePodEntityDies!");
			return;
		}
		if (dependencies.get("y") == null) {
			System.err.println("Failed to load dependency y for procedure HexePodEntityDies!");
			return;
		}
		if (dependencies.get("z") == null) {
			System.err.println("Failed to load dependency z for procedure HexePodEntityDies!");
			return;
		}
		if (dependencies.get("world") == null) {
			System.err.println("Failed to load dependency world for procedure HexePodEntityDies!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		int x = (int) dependencies.get("x");
		int y = (int) dependencies.get("y");
		int z = (int) dependencies.get("z");
		World world = (World) dependencies.get("world");
		for (int index0 = 0; index0 < (int) (Math.ceil((Math.random() * 3))); index0++) {
			if ((entity instanceof EntityHexePod.EntityCustom)) {
				if (!world.isRemote) {
					Entity entityToSpawn = new EntityWyrmling.EntityCustom(world);
					if (entityToSpawn != null) {
						entityToSpawn.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360F, 0.0F);
						world.spawnEntity(entityToSpawn);
					}
				}
			}
		}
	}
}
