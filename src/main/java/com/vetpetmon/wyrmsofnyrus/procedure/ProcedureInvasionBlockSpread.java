package com.vetpetmon.wyrmsofnyrus.procedure;

import net.minecraft.world.World;

import java.util.Map;

import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrusModVariables;
import com.vetpetmon.wyrmsofnyrus.ElementswyrmsofnyrusMod;

@ElementswyrmsofnyrusMod.ModElement.Tag
public class ProcedureInvasionBlockSpread extends ElementswyrmsofnyrusMod.ModElement {
	public ProcedureInvasionBlockSpread(ElementswyrmsofnyrusMod instance) {
		super(instance, 13);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			System.err.println("Failed to load dependency world for procedure InvasionBlockSpread!");
			return;
		}
		World world = (World) dependencies.get("world");
		wyrmsofnyrusModVariables.WorldVariables
				.get(world).wyrmInvasionPoints = (double) ((wyrmsofnyrusModVariables.WorldVariables.get(world).wyrmInvasionPoints) + 0.05);
		wyrmsofnyrusModVariables.WorldVariables.get(world).syncData(world);
	}
}
