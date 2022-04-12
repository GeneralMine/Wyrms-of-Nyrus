package com.vetpetmon.wyrmsofnyrus.entity.ability;

import net.minecraft.util.DamageSource;
import net.minecraft.entity.Entity;

import java.util.Map;

//BROKEN RIGHT NOW, DO NOT USE. WILL INSTANTLY KILL ENTITIES.
public class EntityDeathTimer {

	public static void executeProcedure(Map<String, Object> dependencies) {
		Entity entity = (Entity) dependencies.get("entity");
		double timer = 0;
		while (((timer) <= 1200)) {
			timer = (timer) + 1;
		}
		entity.attackEntityFrom(DamageSource.MAGIC, (float) 100000);
	}
}
