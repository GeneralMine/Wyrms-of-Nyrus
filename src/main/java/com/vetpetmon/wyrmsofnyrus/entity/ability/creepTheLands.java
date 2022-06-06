package com.vetpetmon.wyrmsofnyrus.entity.ability;

import com.vetpetmon.wyrmsofnyrus.block.*;
import com.vetpetmon.wyrmsofnyrus.config.Radiogenetics;
import com.vetpetmon.wyrmsofnyrus.synapselib.RNG;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.vetpetmon.wyrmsofnyrus.invasion.HiveCreepSpreadFurther.creepspreadRules;
import static com.vetpetmon.wyrmsofnyrus.invasion.HiveCreepSpreadFurther.matLookingBlock;

public class creepTheLands {
    public static void creepTheLands(BlockPos pos, World world){
        int Range = Radiogenetics.creepwyrmInfestRange;
        int x = ((pos.getX()) + RNG.getIntRangeInclu(-Range,Range));
        int y = ((pos.getY()) + RNG.getIntRangeInclu(-Range,Range));
        int z = ((pos.getZ()) + RNG.getIntRangeInclu(-Range,Range));
        BlockPos lookingBlock = new BlockPos(x,y,z);
        if (creepspreadRules(lookingBlock, world, x, y, z)) {
            assert false;
            if (((world.getBlockState(lookingBlock))).getBlock() == (Block.getBlockFromName("minecraft:glowstone"))) {
                world.setBlockState(lookingBlock, BlockWyrmLightsYellow.block.getDefaultState(), 3);
            } else if (matLookingBlock(lookingBlock, Material.ROCK, world)) {
                world.setBlockState(lookingBlock, BlockCreepstoneInactive.block.getDefaultState(), 3);
            } else if ((matLookingBlock(lookingBlock, Material.GROUND, world))) {
                world.setBlockState(lookingBlock, BlockHiveCreepBlockInactive.block.getDefaultState(), 3);
            } else if ((matLookingBlock(lookingBlock, Material.GRASS, world))) {
                world.setBlockState(lookingBlock, BlockHiveCreepTopInactive.block.getDefaultState(), 3);
            }
        }
    }
}
