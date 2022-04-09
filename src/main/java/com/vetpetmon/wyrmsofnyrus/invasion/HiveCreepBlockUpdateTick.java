package com.vetpetmon.wyrmsofnyrus.invasion;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.properties.IProperty;

import java.util.Map;
import java.util.HashMap;

import com.vetpetmon.wyrmsofnyrus.blocks.BlockHiveCreepBlockTop;

public class HiveCreepBlockUpdateTick {
    public static void executescript(Map<String, Object> dependencies) {
        int x = (int) dependencies.get("x");
        int y = (int) dependencies.get("y");
        int z = (int) dependencies.get("z");
        World world = (World) dependencies.get("world");
        if (((Math.random() * 10) > 9)) {
            if ((world.isAirBlock(new BlockPos((int) x, (int) (y + 1), (int) z)))) {
                {
                    BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
                    IBlockState _bs = BlockHiveCreepBlockTop.block.getDefaultState();
                    IBlockState _bso = world.getBlockState(_bp);
                    for (Map.Entry<IProperty<?>, Comparable<?>> entry : _bso.getProperties().entrySet()) {
                        IProperty _property = entry.getKey();
                        if (_bs.getPropertyKeys().contains(_property))
                            _bs = _bs.withProperty(_property, (Comparable) entry.getValue());
                    }
                    world.setBlockState(_bp, _bs, 3);
                }
            } else {
                {
                    Map<String, Object> $_dependencies = new HashMap<>();
                    $_dependencies.put("x", x);
                    $_dependencies.put("y", y);
                    $_dependencies.put("z", z);
                    $_dependencies.put("world", world);
                    //HiveCreepSpreadFurther.executescript($_dependencies);
                }
            }
        }
    }
}
