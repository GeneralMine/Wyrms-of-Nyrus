package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.block.AllBlocks;
import com.vetpetmon.wyrmsofnyrus.block.BlockMaterials;
import com.vetpetmon.wyrmsofnyrus.block.hivecreep.BlockHivecreepPillar;
import com.vetpetmon.wyrmsofnyrus.block.hivecreep.creepStaged;
import com.vetpetmon.wyrmsofnyrus.compat.SRP;
import com.vetpetmon.wyrmsofnyrus.config.Debug;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.config.WorldConfig;
import com.vetpetmon.synapselib.util.RNG;
import com.vetpetmon.synapselib.util.blockUtils;
import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.minecraft.block.BlockRotatedPillar.AXIS;

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
		boolean isUnAllowedBlock = (
				(Invasion.invalidBlocks.contains((world.getBlockState(i).getBlock())))
				|| matLookingBlock(i,BlockMaterials.CREEP,world)
				|| (world.getBlockState(i).getBlock() == (Block.getBlockFromName("wyrmsofnyrus:creepedgrass")))
				|| (world.getBlockState(i).getBlock() == (Block.getBlockFromName("wyrmsofnyrus:corium")))
		);
		if ((Invasion.CSBlockBLEnabled) && !(i.equals(OG))) return (!isAir) && (isSoft) && (isFullCube) && (!isUnAllowedBlock);
		else return (!isAir) && (isSoft) && (isFullCube) && (!isUnAllowedBlock);
	}

	public static void executescript(BlockPos pos, World world) {
		boolean canSpreadThisTick = ((RNG.getIntRangeInclu(0, Invasion.creepSpreadRate)) == Invasion.creepSpreadRate);

		if (Invasion.isCreepEnabled()) {
			int Range = 1;
			assert false;
			for (int i = 0; i < 3; i++) {
				int x = (int) ((pos.getX()) + RNG.PMRange(Range));
				int y = (int) ((pos.getY()) + RNG.PMRange(Range));
				int z = (int) ((pos.getZ()) + RNG.PMRange(Range));
				BlockPos posi = new BlockPos(x, y, z);
				Block blockLooking = (world.getBlockState(posi)).getBlock();
				if ((creepspreadRules(posi, world, pos)) && canSpreadThisTick) {
					if (SRP.isEnabled() && WorldConfig.vileEnabled) {
						if (SRP.srpBlocks.contains(blockLooking)) world.setBlockState(posi, AllBlocks.corium.getDefaultState(), 3);addPoints(world);
					}
					if (blockLooking == (Block.getBlockFromName("minecraft:glowstone"))) {world.setBlockState(posi, AllBlocks.wyrm_lights_yellow.getDefaultState(), 3);addPoints(world);}
					else if ((blockLooking instanceof BlockLog) || (blockLooking instanceof BlockOldLog)) {world.setBlockState(posi, AllBlocks.creeplog.getDefaultState().withProperty(BlockHivecreepPillar.ACTIVE,1).withProperty(AXIS, EnumFacing.Axis.Y), 3);addPoints(world);} //Apparently Minecraft has two different BlockLog classes and I don't know what mods like to use so there you go, cover both.
					else if ((matLookingBlock(posi, Material.SAND, world))) {world.setBlockState(posi, AllBlocks.creepedsand.getDefaultState().withProperty(creepStaged.STAGE, 0), 3);addPoints(world);}
					else if (matLookingBlock(posi, Material.ROCK, world)) {world.setBlockState(posi, AllBlocks.creepedstone.getDefaultState().withProperty(creepStaged.STAGE, 0), 3);addPoints(world);}
					else if ((matLookingBlock(posi, Material.GROUND, world) || (matLookingBlock(posi, Material.GRASS, world)))) {world.setBlockState(posi, AllBlocks.creepeddirt.getDefaultState().withProperty(creepStaged.STAGE, 0), 3);addPoints(world);}
				}
			}
		}
	}

	public static void addPoints(World world){
		InvasionPoints.add(world, Invasion.creepSpreadPoints);
		if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 8) WyrmsOfNyrus.logger.info("Invasion points increased by " + Invasion.creepSpreadPoints + " from creep spread");
	}

	public static boolean matLookingBlock(BlockPos pos, Material mat, World world) {return (blockUtils.getLookingBlockMat(pos,world) == mat);}

}