package com.vetpetmon.wyrmsofnyrus.entity.ability;

import com.vetpetmon.wyrmsofnyrus.block.*;
import com.vetpetmon.wyrmsofnyrus.config.Radiogenetics;
import com.vetpetmon.wyrmsofnyrus.synapselib.RNG;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.vetpetmon.wyrmsofnyrus.block.creepStaged.STAGE;
import static com.vetpetmon.wyrmsofnyrus.invasion.HiveCreepSpreadFurther.*;

public class creepTheLands {
    public static void creepTheLands(BlockPos pos, World world, int Range){
        int x = (int) ((pos.getX()) + RNG.PMRange(Range));
        int y = (int) ((pos.getY()) + RNG.PMRange(Range));
        int z = (int) ((pos.getZ()) + RNG.PMRange(Range));
        BlockPos lookingBlock = new BlockPos(x,y,z);
        if (creepspreadRules(lookingBlock, world, pos)) {
            assert false;
            if (((world.getBlockState(lookingBlock))).getBlock() == (Block.getBlockFromName("minecraft:glowstone"))) {
                world.setBlockState(lookingBlock, AllBlocks.wyrm_lights_yellow.getDefaultState(), 3);
                addPoints(world);
            } else if (matLookingBlock(lookingBlock, Material.ROCK, world)) {
                world.setBlockState(lookingBlock, AllBlocks.creepedstone.getDefaultState().withProperty(STAGE,6), 3);
                addPoints(world);
            } else if ((matLookingBlock(lookingBlock, Material.GROUND, world))) {
                world.setBlockState(lookingBlock, AllBlocks.creepeddirt.getDefaultState().withProperty(STAGE,6), 3);
                addPoints(world);
            } else if ((matLookingBlock(lookingBlock, Material.SAND, world))) {
                world.setBlockState(lookingBlock, AllBlocks.creepedsand.getDefaultState().withProperty(STAGE, 6), 3);
                addPoints(world);
            } else if ((matLookingBlock(lookingBlock, Material.GRASS, world))) {
                world.setBlockState(lookingBlock, AllBlocks.creepedgrass.getDefaultState().withProperty(STAGE,6), 3);
                addPoints(world);
            }
        }
    }
}
