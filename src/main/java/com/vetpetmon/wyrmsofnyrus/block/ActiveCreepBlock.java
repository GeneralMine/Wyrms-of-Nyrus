package com.vetpetmon.wyrmsofnyrus.block;


import com.vetpetmon.wyrmsofnyrus.config.Debug;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.invasion.HiveCreepSpreadFurther;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ActiveCreepBlock{

    public static void CreepSpread(BlockPos pos, World world, int ts, String inactiveBlockName) {
        HiveCreepSpreadFurther.executescript(pos, world, ts);
        if (ts > (Invasion.creepSpreadRate * 20)) {
            if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 4)
                System.out.println("Debugging: creep block at " + (pos + " was turned inactive after " + (ts) + " operations."));
            world.setBlockState(pos, (IBlockState) Block.getBlockFromName(inactiveBlockName), 3);
        }

    }


}
