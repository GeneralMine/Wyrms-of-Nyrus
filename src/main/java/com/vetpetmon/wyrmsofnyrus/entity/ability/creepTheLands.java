package com.vetpetmon.wyrmsofnyrus.entity.ability;

import com.vetpetmon.wyrmsofnyrus.block.AllBlocks;
import com.vetpetmon.wyrmsofnyrus.block.hivecreep.BlockHivecreepPillar;
import com.vetpetmon.wyrmsofnyrus.block.hivecreep.creepStaged;
import com.vetpetmon.wyrmsofnyrus.block.hivecreep.creepStagedGrass;
import com.vetpetmon.wyrmsofnyrus.synapselib.RNG;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.vetpetmon.wyrmsofnyrus.invasion.HiveCreepSpreadFurther.*;
import static net.minecraft.block.BlockRotatedPillar.AXIS;

public class creepTheLands {
    public static void creepTheLands(BlockPos pos, World world, int Range){
        int x = (int) ((pos.getX()) + RNG.PMRange(Range));
        int y = (int) ((pos.getY()) + RNG.PMRange(Range));
        int z = (int) ((pos.getZ()) + RNG.PMRange(Range));
        BlockPos lookingBlock = new BlockPos(x,y,z);
        Block blockLooking = (world.getBlockState(lookingBlock)).getBlock();
        if (creepspreadRules(lookingBlock, world, pos)) {
            assert false;
            if (blockLooking == (Block.getBlockFromName("minecraft:glowstone"))) {
                world.setBlockState(lookingBlock, AllBlocks.wyrm_lights_yellow.getDefaultState(), 3);
                addPoints(world);
            }
            else if ((blockLooking instanceof BlockLog)|| (blockLooking == Block.getBlockFromName("minecraft:log")) || (blockLooking instanceof BlockOldLog)) {world.setBlockState(lookingBlock, AllBlocks.creeplog.getDefaultState().withProperty(BlockHivecreepPillar.ACTIVE,0).withProperty(AXIS, EnumFacing.Axis.Y), 3);addPoints(world);} //MMD very helpful, thank you.
            else if (matLookingBlock(lookingBlock, Material.ROCK, world)) {world.setBlockState(lookingBlock, AllBlocks.creepedstone.getDefaultState().withProperty(creepStaged.STAGE,6), 3);addPoints(world);}
            else if ((matLookingBlock(lookingBlock, Material.GROUND, world))) {
                world.setBlockState(lookingBlock, AllBlocks.creepeddirt.getDefaultState().withProperty(creepStaged.STAGE,6), 3);
                addPoints(world);
            } else if ((matLookingBlock(lookingBlock, Material.SAND, world))) {
                world.setBlockState(lookingBlock, AllBlocks.creepedsand.getDefaultState().withProperty(creepStaged.STAGE, 6), 3);
                addPoints(world);
            } else if ((matLookingBlock(lookingBlock, Material.GRASS, world))) {
                world.setBlockState(lookingBlock, AllBlocks.creepedgrass.getDefaultState().withProperty(creepStagedGrass.STAGE,6), 3);
                addPoints(world);
            }
        }
    }
}
