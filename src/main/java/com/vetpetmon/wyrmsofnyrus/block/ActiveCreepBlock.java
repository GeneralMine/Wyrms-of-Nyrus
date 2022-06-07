package com.vetpetmon.wyrmsofnyrus.block;


import com.vetpetmon.wyrmsofnyrus.config.Debug;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.synapselib.RNG;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

import static com.vetpetmon.wyrmsofnyrus.invasion.HiveCreepSpreadFurther.creepspreadRules;

public class ActiveCreepBlock {

    private static BlockPos posLooking;

    public static void CreepSpread(BlockPos pos, World world, IBlockState inactiveBlockName) {
        boolean doCheck = ((RNG.getIntRangeInclu(0,50)) <= 49);
        if (doCheck) {
            if (checkValidRange(pos, world)) {
                if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 4)
                    System.out.println("Debugging: creep block at " + pos + " was turned inactive");
                world.setBlockState(pos, inactiveBlockName, 3);
            }
        }
    }

    private static boolean checkValidRange(BlockPos pos, World world) {
        int Range = 1;
        int fails = 0;
        for (int i = 0; i < (20); i++) {
            int x = ((pos.getX()) + RNG.getIntRangeInclu(-Range, Range));
            int y = ((pos.getY()) + RNG.getIntRangeInclu(-Range, Range));
            int z = ((pos.getZ()) + RNG.getIntRangeInclu(-Range, Range));
            posLooking = new BlockPos(x,y,z);
            if (!creepspreadRules(posLooking,world,pos))++fails;
        }
        if (fails>=20) return true;
        return false;
    }


}
