package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.block.*;
import com.vetpetmon.wyrmsofnyrus.config.ConfigLib;
import com.vetpetmon.wyrmsofnyrus.config.Debug;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
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

	private static boolean creepspreadRules(BlockPos i, World world) {
		boolean isAir = (world.isAirBlock(i));
		boolean isSoft = (world.getBlockState(i).getBlockHardness(world, i) < Invasion.creepSpreadMaxHardness);
		boolean isFullCube = (world.getBlockState(i).isFullCube());
		boolean isUnAllowedBlock = (ConfigLib.iBdefFinal.contains((world.getBlockState(i)).getBlock()));
		return (!isAir) && (isSoft) && (isFullCube) && (!isUnAllowedBlock);
	}

	public static int executescript(BlockPos pos, World world, int ts) {

		int timesspread = ts;
		boolean canSpreadThisTick = ((Math.random() < ((float)(1.0/ Invasion.creepSpreadRate))));

		if (Invasion.isCreepEnabled() && canSpreadThisTick) {
			int x = (int) pos.getX();
			int y = (int) pos.getX();
			int z = (int) pos.getX();
			BlockPosList = getCSPos(x,y,z);
			assert false;
			if (world.isAirBlock(BlockPosList.get(2))) {
				if (((world.getBlockState(BlockPosList.get(0))).getBlock() == Block.getBlockFromName("wyrmsofnyrus:hivecreepblock"))) {
					world.setBlockState((BlockPosList.get(0)), BlockHiveCreepTop.block.getDefaultState(), 3);
				}
			}
			for (BlockPos i : BlockPosList) {
				if (creepspreadRules(i, world)) {
					if (((world.getBlockState(i))).getBlock() == (Block.getBlockFromName("minecraft:glowstone"))) {
						world.setBlockState((i), (IBlockState) Block.getBlockFromName("wyrmsofnyrus:wyrm_lights_yellow"), 3);
						break;
					} else if ((((world.getBlockState(i)).getMaterial() == Material.ROCK))) {
						world.setBlockState((i), (IBlockState) Block.getBlockFromName("wyrmsofnyrus:creepstone"), 3);
						break;
					} else if ((((world.getBlockState(i)).getMaterial() == Material.GROUND) || ((world.getBlockState(i)).getMaterial() == Material.GRASS))) {
						world.setBlockState((i), (IBlockState) Block.getBlockFromName("wyrmsofnyrus:hivecreepblock"), 3);
						break;
					}
				}
			}
			BlockPosList.clear();
			timesspread = ts + 1;
		}
		if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 5) System.out.println("Debugging: timespread for block at: " + (timesspread));
		return timesspread;
	}

}