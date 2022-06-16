package com.vetpetmon.wyrmsofnyrus.synapselib;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class rangeCheck {
    /**
     * Checks a range of blocks for a specific block in a cube area. Returns true if it's found the block, false if it didn't.
     * Do not make this range huge. I'll actually cry if you do. Any big operation will cause TPS lag.
     * @param world World
     * @param pos Origin of the cube
     * @param range Range of the cube
     * @param blockToLookFor Block to test for
     * @return Boolean value
     */
    public static boolean blocks(World world, BlockPos pos, double range, String blockToLookFor) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        boolean found;
        double sx;
        double sy;
        double sz;
        sx = (-range/2);
        found =(false);
        for (int index0 = 0; index0 < (range); index0++) {
            sy = (-range/2);
            for (int index1 = 0; index1 < range; index1++) {
                sz =(-range/2);
                for (int index2 = 0; index2 < range; index2++) {
                    if ((world.getBlockState(new BlockPos((int) (x + (sx)), (int) (y + (sy)), (int) (z + (sz)))))
                            .getBlock() == (Block.getBlockFromName(blockToLookFor))
                    ) {
                        found = true;
                    }
                    sz = ((sz) + 1);
                }
                sy = ((sy) + 1);
            }
            sx = ((sx) + 1);
        }
        if (found) return true;
        else return false;
    }
    public static boolean material(World world, BlockPos pos, double range, Material lookForMat) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        boolean found;
        double sx;
        double sy;
        double sz;
        Material lookFor = lookForMat;
        sx = (-range/2);
        found =(false);
        for (int index0 = 0; index0 < (range); index0++) {
            sy = (-range/2);
            for (int index1 = 0; index1 < range; index1++) {
                sz =(-range/2);
                for (int index2 = 0; index2 < range; index2++) {
                    if ((world.getBlockState(new BlockPos((int) (x + (sx)), (int) (y + (sy)), (int) (z + (sz)))))
                            .getMaterial() == lookFor
                    ) {
                        found = true;
                    }
                    sz = ((sz) + 1);
                }
                sy = ((sy) + 1);
            }
            sx = ((sx) + 1);
        }
        if (found) return true;
        else return false;
    }
}
