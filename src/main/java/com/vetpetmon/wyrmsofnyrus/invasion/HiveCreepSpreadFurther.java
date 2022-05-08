package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.block.*;
import com.vetpetmon.wyrmsofnyrus.config.Debug;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.Map;

import com.vetpetmon.wyrmsofnyrus.AutoReg;

@AutoReg.ModElement.Tag
public class HiveCreepSpreadFurther extends AutoReg.ModElement {
	public HiveCreepSpreadFurther(AutoReg instance) {
		super(instance, 10);
	}

	public static boolean hasTicked;
	public static int timesspread;

	public static int executescript(Map<String, Object> e, int ts) {

		timesspread = ts;
		boolean canSpreadThisTick = ((Math.random() < ((float)(1.0/ Invasion.creepSpreadRate))));

		if (Invasion.isCreepEnabled() && canSpreadThisTick) {
			hasTicked = false;

			ArrayList<BlockPos> BlockPosList = new ArrayList<>();

			int x = (int) e.get("x");
			int y = (int) e.get("y");
			int z = (int) e.get("z");

			BlockPosList.add(new BlockPos(x, y, z));        // 0
			BlockPosList.add(new BlockPos(x, y - 1, z));    // 1
			BlockPosList.add(new BlockPos(x, y + 1, z));    // 2
			BlockPosList.add(new BlockPos(x - 1, y, z));    // 3
			BlockPosList.add(new BlockPos(x + 1, y, z));    // 4
			BlockPosList.add(new BlockPos(x, y, z - 1));    // 5
			BlockPosList.add(new BlockPos(x, y, z + 1));    // 6

			World world = (World) e.get("world");
			if (world.isAirBlock(new BlockPos(x, y + 1, z))) {
				if (((world.getBlockState(BlockPosList.get(0))).getBlock() == Block.getBlockFromName("wyrmsofnyrus:hivecreepblock"))) {
					world.setBlockState((BlockPosList.get(0)), BlockHiveCreepTop.block.getDefaultState(), 3);
				}
			}
			for (BlockPos i : BlockPosList) {
				if (creepspreadRules(i, world)) {
					if (((world.getBlockState(i))).getBlock() == (Block.getBlockFromName("minecraft:glowstone"))) {
						world.setBlockState((i), BlockWyrmLightsYellow.block.getDefaultState(), 3);
						break;
					} else if ((((world.getBlockState(i)).getMaterial() == Material.ROCK))) {
						world.setBlockState((i), BlockCreepstone.block.getDefaultState(), 3);
						break;
					} else if ((((world.getBlockState(i)).getMaterial() == Material.GROUND) || ((world.getBlockState(i)).getMaterial() == Material.GRASS))) {
						world.setBlockState((i), BlockHiveCreepBlock.block.getDefaultState(), 3);
						break;
					}
				}
			}
			BlockPosList.clear();
			if (!hasTicked){
				timesspread = ts + 1;
				hasTicked = true;
			}

		}
		if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 5) System.out.println("Debugging: timespread for block at: " + (timesspread));
		return timesspread;
	}

	private static boolean creepspreadRules(BlockPos i, World world) {
		boolean isSoft = (world.getBlockState(i).getBlockHardness(world, i) < Invasion.creepSpreadMaxHardness);
		return (!world.isAirBlock(i)) && (isSoft) && (world.getBlockState(i).isFullCube()) && !(Invasion.iBdefFinal.contains((world.getBlockState(i)).getBlock()));
	}

}