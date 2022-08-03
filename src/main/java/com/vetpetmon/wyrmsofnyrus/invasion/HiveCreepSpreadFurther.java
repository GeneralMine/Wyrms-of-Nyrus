package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.block.*;
import com.vetpetmon.wyrmsofnyrus.config.Debug;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.synapselib.RNG;
import com.vetpetmon.wyrmsofnyrus.synapselib.blockUtils;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
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
		boolean isUnAllowedBlock = ((Invasion.invalidBlocks.contains((world.getBlockState(i).getBlock())))
				|| matLookingBlock(i,BlockMaterials.CREEP,world));
		if ((Invasion.CSBlockBLEnabled) && !(i.equals(OG))) return (!isAir) && (isSoft) && (isFullCube) && (!isUnAllowedBlock);
		else return (!isAir) && (isSoft) && (isFullCube) && (!isUnAllowedBlock);
	}

	public static void executescript(BlockPos pos, World world) {
		boolean canSpreadThisTick = ((RNG.getIntRangeInclu(0, Invasion.creepSpreadRate)) == Invasion.creepSpreadRate);

		if (Invasion.isCreepEnabled()) {
			int Range = 1;
			assert false;
			for (int i = 0; i < 5; i++) {
				int x = (int) ((pos.getX()) + RNG.PMRange(Range));
				int y = (int) ((pos.getY()) + RNG.PMRange(Range));
				int z = (int) ((pos.getZ()) + RNG.PMRange(Range));
				BlockPos posi = new BlockPos(x, y, z);
				if ((creepspreadRules(posi, world, pos)) && canSpreadThisTick) {
					if (((world.getBlockState(posi))).getBlock() == (Block.getBlockFromName("minecraft:glowstone"))) {world.setBlockState(posi, BlockWyrmLightsYellow.block.getDefaultState(), 3);addPoints(world);}
					else if (matLookingBlock(posi, Material.ROCK, world)) {world.setBlockState(posi, BlockCreepstone.block.getDefaultState(), 3);addPoints(world);}
					else if ((matLookingBlock(posi, Material.GROUND, world) || (matLookingBlock(posi, Material.GRASS, world)))) {world.setBlockState(posi, BlockHiveCreepedDirt.block.getDefaultState(), 3);addPoints(world);}
				}
			}
		}
	}

	public static void addPoints(World world){
		invasionPoints.add(world, Invasion.creepSpreadPoints);
		if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 8) wyrmsofnyrus.logger.info("Invasion points increased by " + Invasion.creepSpreadPoints + " from creep spread");
	}

	public static boolean matLookingBlock(BlockPos pos, Material mat, World world) {return (blockUtils.getLookingBlockMat(pos,world) == mat);}

}