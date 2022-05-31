package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.block.*;
import com.vetpetmon.wyrmsofnyrus.config.Debug;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.synapselib.RNG;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.material.Material;

import java.util.ArrayList;

public class HiveCreepSpreadFurther{

	private static ArrayList<BlockPos> BlockPosList = new ArrayList<>();

	private static ArrayList<BlockPos> getCSPos(int x, int y, int z)
	{
		BlockPosList.add(new BlockPos(x, y, z));               // 0
		BlockPosList.add(new BlockPos(x, y - 1, z));        // 1
		BlockPosList.add(new BlockPos(x, y + 1, z));        // 2
		BlockPosList.add(new BlockPos(x - 1, y, z));        // 3
		BlockPosList.add(new BlockPos(x + 1, y, z));        // 4
		BlockPosList.add(new BlockPos(x, y, z - 1));        // 5
		BlockPosList.add(new BlockPos(x, y, z + 1));        // 6
		if (Invasion.creepSpreadsDiagonally) {
			BlockPosList.add(new BlockPos(x, y + 1, z + 1));           // 7
			BlockPosList.add(new BlockPos(x, y - 1, z + 1));           // 8
			BlockPosList.add(new BlockPos(x, y + 1, z - 1));           // 9
			BlockPosList.add(new BlockPos(x, y - 1, z - 1));           // 10
			BlockPosList.add(new BlockPos(x + 1, y + 1, z + 1));    // 11
			BlockPosList.add(new BlockPos(x + 1, y - 1, z + 1));    // 12
			BlockPosList.add(new BlockPos(x - 1, y + 1, z + 1));    // 13
			BlockPosList.add(new BlockPos(x - 1, y - 1, z + 1));    // 13
			BlockPosList.add(new BlockPos(x + 1, y + 1, z - 1));    // 14
			BlockPosList.add(new BlockPos(x + 1, y - 1, z - 1));    // 15
			BlockPosList.add(new BlockPos(x - 1, y + 1, z - 1));    // 16
			BlockPosList.add(new BlockPos(x - 1, y - 1, z - 1));    // 17

		}
		return BlockPosList;
	}

	private static boolean creepspreadRules(BlockPos i, World world, int x, int y, int z) {
		boolean isAir = (world.isAirBlock(i));
		boolean isSoft = (world.getBlockState(i).getBlockHardness(world, i) < Invasion.creepSpreadMaxHardness);
		boolean isFullCube = (world.getBlockState(i).isFullCube());
		boolean isUnAllowedBlock = ((Invasion.invalidBlocks.contains((world.getBlockState(i).getBlock()))));
		if ((Invasion.CSBlockBLEnabled) && !(i.equals(new BlockPos(x, y, z)))) {
			return (!isAir) && (isSoft) && (isFullCube) && (!isUnAllowedBlock);
		}
		else {
			return (!isAir) && (isSoft) && (isFullCube) && (!isUnAllowedBlock);
		}
	}

	public static int executescript(BlockPos pos, World world, int ts) {

		int timesspread = ts;
		boolean hasSpread = false;
		boolean canSpreadThisTick = ((RNG.getIntRangeInclu(0,Invasion.creepSpreadRate)) == Invasion.creepSpreadRate);

		if (Invasion.isCreepEnabled()) {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			BlockPosList = getCSPos(x,y,z);
			assert false;
			if (canSpreadThisTick){
				hasSpread = true;
				for (int i = 0; i < BlockPosList.size(); i++) {
					BlockPos posi = BlockPosList.get(i);

					if (creepspreadRules(posi, world, x, y, z)) {
						if (((world.getBlockState(posi))).getBlock() == (Block.getBlockFromName("minecraft:glowstone"))) {
							world.setBlockState(posi, BlockWyrmLightsYellow.block.getDefaultState(), 3);
						} else if (matLookingBlock(posi, Material.ROCK, world)) {
							world.setBlockState(posi, BlockCreepstone.block.getDefaultState(), 3);
						} else if ((matLookingBlock(posi, Material.GROUND, world) || (matLookingBlock(posi, Material.GRASS, world)))) {
							world.setBlockState(posi, BlockHiveCreepBlock.block.getDefaultState(), 3);
						}
					}
				}
			}
			//failsafe(world, x, y, z); //Old code left for reference
			BlockPosList.clear();
			if (hasSpread){
				timesspread = ts + 1;
				hasSpread = true;
			}
		}
		if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 5) System.out.println("Debugging: timespread for block at: " + (timesspread));
		return timesspread;
	}

	public static void failsafe(World world, int x, int y, int z) {
		for (BlockPos i : BlockPosList) {

			if (creepspreadRules(i, world, x,y,z)) {
				if (((world.getBlockState(i))).getBlock() == (Block.getBlockFromName("minecraft:glowstone"))) {
					world.setBlockState(i, BlockWyrmLightsYellow.block.getDefaultState(), 3);
					break;
				} else if (matLookingBlock(i, Material.ROCK, world)) {
					world.setBlockState(i, BlockCreepstone.block.getDefaultState(), 3);
					break;
				} else if ((matLookingBlock(i, Material.GROUND, world) || (matLookingBlock(i, Material.GRASS, world)))) {
					world.setBlockState(i, BlockHiveCreepBlock.block.getDefaultState(), 3);
					break;
				}
			}
		}
	}

	public static boolean matLookingBlock(BlockPos pos, Material mat, World world) {return (((world.getBlockState(pos)).getMaterial() == mat));}

}