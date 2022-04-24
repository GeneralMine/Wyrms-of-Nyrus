package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.wyrmVariables;

import net.minecraft.world.World;

import java.util.Map;

public class InvasionBlockSpread {
    public static void run(Map<String, Object> dependencies) {
        World world = (World) dependencies.get("world");
        wyrmVariables.WorldVariables
                .get(world).wyrmInvasionPoints = (wyrmVariables.WorldVariables.get(world).wyrmInvasionPoints) + 0.015;
        wyrmVariables.WorldVariables.get(world).syncData(world);
    }
}
