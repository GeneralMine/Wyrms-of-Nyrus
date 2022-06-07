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

	/**
	 * Tests a LOT of statements before ruling that the block being looked at is valid for spreading to or not.
	 * @param i The block position that is being tested ("looked at")
	 * @param world The world that block is in
	 * @param OG The original block position (from origin) (Patches issue where it looks at itself needlessly.)
	 * @return True for valid block, False for invalid block.
	 */
	public static boolean creepspreadRules(BlockPos i, World world, BlockPos OG) {
		boolean isAir = (world.isAirBlock(i));
		boolean isSoft = (world.getBlockState(i).getBlockHardness(world, i) < Invasion.creepSpreadMaxHardness);
		boolean isFullCube = (world.getBlockState(i).isFullCube());
		boolean isUnAllowedBlock = ((Invasion.invalidBlocks.contains((world.getBlockState(i).getBlock()))));
		if ((Invasion.CSBlockBLEnabled) && !(i.equals(OG))) {return (!isAir) && (isSoft) && (isFullCube) && (!isUnAllowedBlock);}
		else {return (!isAir) && (isSoft) && (isFullCube) && (!isUnAllowedBlock);}
	}
	public static int executescript(BlockPos pos, World world, int ts) {

		int timesspread = ts;
		boolean hasSpread = false;
		boolean canSpreadThisTick = ((RNG.getIntRangeInclu(0, Invasion.creepSpreadRate)) == Invasion.creepSpreadRate);

		if (Invasion.isCreepEnabled()) {
			int Range = 1;
			assert false;
			for (int i = 0; i < 5; i++) {
				int x = ((pos.getX()) + RNG.getIntRangeInclu(-Range, Range));
				int y = ((pos.getY()) + RNG.getIntRangeInclu(-Range, Range));
				int z = ((pos.getZ()) + RNG.getIntRangeInclu(-Range, Range));
				BlockPos posi = new BlockPos(x, y, z);
				if ((creepspreadRules(posi, world, pos)) && canSpreadThisTick) {
					if (((world.getBlockState(posi))).getBlock() == (Block.getBlockFromName("minecraft:glowstone"))) world.setBlockState(posi, BlockWyrmLightsYellow.block.getDefaultState(), 3);
					else if (matLookingBlock(posi, Material.ROCK, world)) world.setBlockState(posi, BlockCreepstone.block.getDefaultState(), 3);
					else if ((matLookingBlock(posi, Material.GROUND, world) || (matLookingBlock(posi, Material.GRASS, world)))) world.setBlockState(posi, BlockHiveCreepBlock.block.getDefaultState(), 3);
					hasSpread = true;
				}
			}
			if (hasSpread) {
				timesspread = ++ts;
			}
		}
		if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 5)
			System.out.println("Debugging: timespread for block at: " + (timesspread));
		return timesspread;
	}
	public static boolean matLookingBlock(BlockPos pos, Material mat, World world) {return (((world.getBlockState(pos)).getMaterial() == mat));}

}