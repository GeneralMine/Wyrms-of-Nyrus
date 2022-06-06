package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.block.*;
import com.vetpetmon.wyrmsofnyrus.config.Debug;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.synapselib.RNG;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.material.Material;

public class HiveCreepSpreadFurther{

	public static boolean creepspreadRules(BlockPos i, World world, int x, int y, int z) {
		boolean isAir = (world.isAirBlock(i));
		boolean isSoft = (world.getBlockState(i).getBlockHardness(world, i) < Invasion.creepSpreadMaxHardness);
		boolean isFullCube = (world.getBlockState(i).isFullCube());
		boolean isUnAllowedBlock = ((Invasion.invalidBlocks.contains((world.getBlockState(i).getBlock()))));
		if ((Invasion.CSBlockBLEnabled) && !(i.equals(new BlockPos(x, y, z)))) {return (!isAir) && (isSoft) && (isFullCube) && (!isUnAllowedBlock);}
		else {return (!isAir) && (isSoft) && (isFullCube) && (!isUnAllowedBlock);}
	}
	public static int executescript(BlockPos pos, World world, int ts) {

		int timesspread = ts;
		boolean hasSpread = false;
		boolean canSpreadThisTick = ((RNG.getIntRangeInclu(0, Invasion.creepSpreadRate)) == Invasion.creepSpreadRate);

		if (Invasion.isCreepEnabled()) {
			int Range = 1;
			int x = ((pos.getX()) + RNG.getIntRangeInclu(-Range, Range));
			int y = ((pos.getY()) + RNG.getIntRangeInclu(-Range, Range));
			int z = ((pos.getZ()) + RNG.getIntRangeInclu(-Range, Range));
			BlockPos posi = new BlockPos(x, y, z);
			assert false;
			for (int i = 0; i < 5; i++) {
				if ((creepspreadRules(posi, world, x, y, z)) && canSpreadThisTick) {
					if (((world.getBlockState(posi))).getBlock() == (Block.getBlockFromName("minecraft:glowstone"))) world.setBlockState(posi, BlockWyrmLightsYellow.block.getDefaultState(), 3);
					else if (matLookingBlock(posi, Material.ROCK, world)) world.setBlockState(posi, BlockCreepstone.block.getDefaultState(), 3);
					else if ((matLookingBlock(posi, Material.GROUND, world) || (matLookingBlock(posi, Material.GRASS, world)))) world.setBlockState(posi, BlockHiveCreepBlock.block.getDefaultState(), 3);
					hasSpread = true;
				}
			}
			if (hasSpread) {
				timesspread = ts + 1;
			}
		}
		if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 5)
			System.out.println("Debugging: timespread for block at: " + (timesspread));
		return timesspread;
	}
	public static boolean matLookingBlock(BlockPos pos, Material mat, World world) {return (((world.getBlockState(pos)).getMaterial() == mat));}

}