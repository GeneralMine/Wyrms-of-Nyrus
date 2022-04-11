package com.vetpetmon.wyrmsofnyrus.invasion;

import net.minecraft.util.DamageSource;
import net.minecraft.entity.Entity;

import java.util.Map;


public class EntityDeathTimer {

	public static void executeProcedure(Map<String, Object> dependencies) {
		Entity entity = (Entity) dependencies.get("entity");
		double timer = 0;
		while (((timer) <= 120)) {
			timer = (double) ((timer) + 1);
			entity.attackEntityFrom(DamageSource.MAGIC, (float) 100000);
		}
	}
}
