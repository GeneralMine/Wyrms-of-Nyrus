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

		if (Invasion.creepEnabled && canSpreadThisTick) {
			hasTicked = false;

			ArrayList<Block> invalidBlocks = new ArrayList<>();
			invalidBlocks.add(Block.getBlockFromName("minecraft:furnace"));
			invalidBlocks.add(Block.getBlockFromName("minecraft:brick_block"));
			invalidBlocks.add(Block.getBlockFromName("minecraft:bone_block"));
			invalidBlocks.add(Block.getBlockFromName("minecraft:bedrock")); //I HAVEN'T TESTED TO SEE IF IT CONVERTS BEDROCK BEFORE BUT THAT'D BE HILARIOUS IF IT DID.
			invalidBlocks.add(Block.getBlockFromName("minecraft:concrete"));
			invalidBlocks.add(Block.getBlockFromName("minecraft:concrete_powder"));
			invalidBlocks.add(Block.getBlockFromName("minecraft:dropper"));
			invalidBlocks.add(Block.getBlockFromName("minecraft:end_bricks"));
			invalidBlocks.add(Block.getBlockFromName("minecraft:end_stone"));
			invalidBlocks.add(Block.getBlockFromName("minecraft:glass"));
			invalidBlocks.add(Block.getBlockFromName("minecraft:jukebox"));
			invalidBlocks.add(Block.getBlockFromName("minecraft:nether_brick"));
			invalidBlocks.add(Block.getBlockFromName("minecraft:red_nether_brick"));
			invalidBlocks.add(Block.getBlockFromName("minecraft:noteblock"));
			invalidBlocks.add(Block.getBlockFromName("minecraft:observer"));
			invalidBlocks.add(Block.getBlockFromName("minecraft:obsidian"));
			invalidBlocks.add(Block.getBlockFromName("minecraft:packed_ice"));
			invalidBlocks.add(Block.getBlockFromName("minecraft:prismarine"));
			invalidBlocks.add(Block.getBlockFromName("minecraft:purpur_block"));
			invalidBlocks.add(Block.getBlockFromName("minecraft:purpur_pillar"));
			invalidBlocks.add(Block.getBlockFromName("minecraft:quartz_block"));
			invalidBlocks.add(Block.getBlockFromName("minecraft:sponge"));
			invalidBlocks.add(Block.getBlockFromName("minecraft:stained_glass"));
			invalidBlocks.add(Block.getBlockFromName("minecraft:stone_button"));
			invalidBlocks.add(Block.getBlockFromName("minecraft:wool"));
			invalidBlocks.add(Block.getBlockFromName("minecraft:stonebrick"));

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
				if ((!world.isAirBlock(i)) && (world.getBlockState(i).getBlockHardness(world, i) < 2.45) && (world.getBlockState(i).isFullCube()) && !(invalidBlocks.contains((world.getBlockState(i)).getBlock()))) {
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
			invalidBlocks.clear();
			BlockPosList.clear();
			if (!hasTicked){
				timesspread = ts + 1;
				hasTicked = true;
			}

		}
		if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 5) System.out.println("Debugging: timespread for block at: " + (timesspread));
		return timesspread;
	}
}