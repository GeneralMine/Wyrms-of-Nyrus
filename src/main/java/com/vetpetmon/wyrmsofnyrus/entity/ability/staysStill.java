package com.vetpetmon.wyrmsofnyrus.entity.ability;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

public class staysStill {
    public static void staysStill(Map<String, Object> dependencies) {
        Entity entity = (Entity) dependencies.get("entity");
        int x = (int) dependencies.get("x");
        int y = (int) dependencies.get("y");
        int z = (int) dependencies.get("z");
        World world = (World) dependencies.get("world");
        if (!(world.isAirBlock(new BlockPos(x, (y - 1), z)))) {
            entity.setPositionAndUpdate(x, y, z);
        }
    }
}
