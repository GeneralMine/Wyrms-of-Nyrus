package com.vetpetmon.wyrmsofnyrus.synapselib.util;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class blockUtils {
    public static Material getLookingBlockMat(BlockPos pos, World world) {return (world.getBlockState(pos)).getMaterial();}
}
