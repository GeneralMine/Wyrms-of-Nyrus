package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.block.*;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.Map;

import com.vetpetmon.wyrmsofnyrus.AutoReg;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@AutoReg.ModElement.Tag
public class HiveCreepSpreadFurther extends AutoReg.ModElement {
	public HiveCreepSpreadFurther(AutoReg instance) {
		super(instance, 10);
	}

	public static void executescript(Map<String, Object> dependencies) {
		int x = (int) dependencies.get("x");
		int y = (int) dependencies.get("y");
		int z = (int) dependencies.get("z");
		World world = (World) dependencies.get("world");
		if ((Math.random() >= 0.9)) {
			if ((((world.getBlockState(new BlockPos(x, y, z - 1))).getMaterial() == Material.GROUND)
					|| ((world.getBlockState(new BlockPos(x, y, z - 1))).getMaterial() == Material.GRASS))) {
				{
					BlockPos _bp = new BlockPos(x, y, z - 1);
					IBlockState _bs = BlockHiveCreepBlock.block.getDefaultState();
					IBlockState _bso = world.getBlockState(_bp);
					for (Map.Entry<IProperty<?>, Comparable<?>> entry : _bso.getProperties().entrySet()) {
						IProperty _property = entry.getKey();
						if (_bs.getPropertyKeys().contains(_property))
							_bs = _bs.withProperty(_property, (Comparable) entry.getValue());
					}
					world.setBlockState(_bp, _bs, 3);
				}
			}
			else if ((((world.getBlockState(new BlockPos( x,  y,  (z + 1)))).getMaterial() == Material.GROUND)
					|| ((world.getBlockState(new BlockPos( x,  y,  (z + 1)))).getMaterial() == Material.GRASS))) {
				{
					BlockPos _bp = new BlockPos( x,  y,  (z + 1));
					IBlockState _bs = BlockHiveCreepBlock.block.getDefaultState();
					IBlockState _bso = world.getBlockState(_bp);
					for (Map.Entry<IProperty<?>, Comparable<?>> entry : _bso.getProperties().entrySet()) {
						IProperty _property = entry.getKey();
						if (_bs.getPropertyKeys().contains(_property))
							_bs = _bs.withProperty(_property, (Comparable) entry.getValue());
					}
					world.setBlockState(_bp, _bs, 3);
				}
			}
			else if ((((world.getBlockState(new BlockPos( (x + 1),  y,  z))).getMaterial() == Material.GROUND)
					|| ((world.getBlockState(new BlockPos( (x + 1),  y,  z))).getMaterial() == Material.GRASS))) {
				{
					BlockPos _bp = new BlockPos( (x + 1),  y,  z);
					IBlockState _bs = BlockHiveCreepBlock.block.getDefaultState();
					IBlockState _bso = world.getBlockState(_bp);
					for (Map.Entry<IProperty<?>, Comparable<?>> entry : _bso.getProperties().entrySet()) {
						IProperty _property = entry.getKey();
						if (_bs.getPropertyKeys().contains(_property))
							_bs = _bs.withProperty(_property, (Comparable) entry.getValue());
					}
					world.setBlockState(_bp, _bs, 3);
				}
			}
			else if ((((world.getBlockState(new BlockPos( (x - 1),  y,  z))).getMaterial() == Material.GROUND)
					|| ((world.getBlockState(new BlockPos( (x - 1),  y,  z))).getMaterial() == Material.GRASS))) {
				{
					BlockPos _bp = new BlockPos( (x - 1),  y,  z);
					IBlockState _bs = BlockHiveCreepBlock.block.getDefaultState();
					IBlockState _bso = world.getBlockState(_bp);
					for (Map.Entry<IProperty<?>, Comparable<?>> entry : _bso.getProperties().entrySet()) {
						IProperty _property = entry.getKey();
						if (_bs.getPropertyKeys().contains(_property))
							_bs = _bs.withProperty(_property, (Comparable) entry.getValue());
					}
					world.setBlockState(_bp, _bs, 3);
				}
			}
			else if ((((world.getBlockState(new BlockPos( x,  (y - 1),  z))).getMaterial() == Material.GROUND)
					|| ((world.getBlockState(new BlockPos( x,  (y - 1),  z))).getMaterial() == Material.GRASS))) {
				{
					BlockPos _bp = new BlockPos( x,  (y - 1),  z);
					IBlockState _bs = BlockHiveCreepBlock.block.getDefaultState();
					IBlockState _bso = world.getBlockState(_bp);
					for (Map.Entry<IProperty<?>, Comparable<?>> entry : _bso.getProperties().entrySet()) {
						IProperty _property = entry.getKey();
						if (_bs.getPropertyKeys().contains(_property))
							_bs = _bs.withProperty(_property, (Comparable) entry.getValue());
					}
					world.setBlockState(_bp, _bs, 3);
				}
			}
			else if ((((world.getBlockState(new BlockPos( x,  (y + 1),  z))).getMaterial() == Material.GROUND)
					|| ((world.getBlockState(new BlockPos( x,  (y + 1),  z))).getMaterial() == Material.GRASS))) {
				{
					BlockPos _bp = new BlockPos( x,  (y + 1),  z);
					IBlockState _bs = BlockHiveCreepBlock.block.getDefaultState();
					IBlockState _bso = world.getBlockState(_bp);
					for (Map.Entry<IProperty<?>, Comparable<?>> entry : _bso.getProperties().entrySet()) {
						IProperty _property = entry.getKey();
						if (_bs.getPropertyKeys().contains(_property))
							_bs = _bs.withProperty(_property, (Comparable) entry.getValue());
					}
					world.setBlockState(_bp, _bs, 3);
				}
			}

			//Rocks
			else if ((world.getBlockState(new BlockPos( x,  (y + 1),  z))).getMaterial() == Material.ROCK) {
				BlockPos _bp = new BlockPos( x,  (y + 1),  z);
				IBlockState _bs = BlockCreepstone.block.getDefaultState();
				IBlockState _bso = world.getBlockState(_bp);
				for (Map.Entry<IProperty<?>, Comparable<?>> entry : _bso.getProperties().entrySet()) {
					IProperty _property = entry.getKey();
					if (_bs.getPropertyKeys().contains(_property))
						_bs = _bs.withProperty(_property, (Comparable) entry.getValue());
				}
				world.setBlockState(_bp, _bs, 3);
			}
			else if ((world.getBlockState(new BlockPos( x,  (y - 1),  z))).getMaterial() == Material.ROCK) {
				BlockPos _bp = new BlockPos( x,  (y - 1),  z);
				IBlockState _bs = BlockCreepstone.block.getDefaultState();
				IBlockState _bso = world.getBlockState(_bp);
				for (Map.Entry<IProperty<?>, Comparable<?>> entry : _bso.getProperties().entrySet()) {
					IProperty _property = entry.getKey();
					if (_bs.getPropertyKeys().contains(_property))
						_bs = _bs.withProperty(_property, (Comparable) entry.getValue());
				}
				world.setBlockState(_bp, _bs, 3);
			}
			else if ((world.getBlockState(new BlockPos( (x-1),  y,  z))).getMaterial() == Material.ROCK) {
				BlockPos _bp = new BlockPos( (x-1),  y,  z);
				IBlockState _bs = BlockCreepstone.block.getDefaultState();
				IBlockState _bso = world.getBlockState(_bp);
				for (Map.Entry<IProperty<?>, Comparable<?>> entry : _bso.getProperties().entrySet()) {
					IProperty _property = entry.getKey();
					if (_bs.getPropertyKeys().contains(_property))
						_bs = _bs.withProperty(_property, (Comparable) entry.getValue());
				}
				world.setBlockState(_bp, _bs, 3);
			}
			else if ((world.getBlockState(new BlockPos( (x+1),  y,  z))).getMaterial() == Material.ROCK) {
				BlockPos _bp = new BlockPos( (x+1),  y,  z);
				IBlockState _bs = BlockCreepstone.block.getDefaultState();
				IBlockState _bso = world.getBlockState(_bp);
				for (Map.Entry<IProperty<?>, Comparable<?>> entry : _bso.getProperties().entrySet()) {
					IProperty _property = entry.getKey();
					if (_bs.getPropertyKeys().contains(_property))
						_bs = _bs.withProperty(_property, (Comparable) entry.getValue());
				}
				world.setBlockState(_bp, _bs, 3);
			}
			else if ((world.getBlockState(new BlockPos( x,  y,  (z+1)))).getMaterial() == Material.ROCK) {
				BlockPos _bp = new BlockPos( x,  y,  (z+1));
				IBlockState _bs = BlockCreepstone.block.getDefaultState();
				IBlockState _bso = world.getBlockState(_bp);
				for (Map.Entry<IProperty<?>, Comparable<?>> entry : _bso.getProperties().entrySet()) {
					IProperty _property = entry.getKey();
					if (_bs.getPropertyKeys().contains(_property))
						_bs = _bs.withProperty(_property, (Comparable) entry.getValue());
				}
				world.setBlockState(_bp, _bs, 3);
			}
			else if ((world.getBlockState(new BlockPos( x,  y,  (z-1)))).getMaterial() == Material.ROCK) {
				BlockPos _bp = new BlockPos( x,  y,  (z-1));
				IBlockState _bs = BlockCreepstone.block.getDefaultState();
				IBlockState _bso = world.getBlockState(_bp);
				for (Map.Entry<IProperty<?>, Comparable<?>> entry : _bso.getProperties().entrySet()) {
					IProperty _property = entry.getKey();
					if (_bs.getPropertyKeys().contains(_property))
						_bs = _bs.withProperty(_property, (Comparable) entry.getValue());
				}
				world.setBlockState(_bp, _bs, 3);
			}

			//De-activate
			else {
				if (((world.getBlockState(new BlockPos( x,  y,  z))).getBlock() == BlockHiveCreepTop.block.getDefaultState()
						.getBlock())) {
					{
						BlockPos _bp = new BlockPos( x,  y,  z);
						IBlockState _bs = BlockHiveCreepTopInactive.block.getDefaultState();
						IBlockState _bso = world.getBlockState(_bp);
						for (Map.Entry<IProperty<?>, Comparable<?>> entry : _bso.getProperties().entrySet()) {
							IProperty _property = entry.getKey();
							if (_bs.getPropertyKeys().contains(_property))
								_bs = _bs.withProperty(_property, (Comparable) entry.getValue());
						}
						world.setBlockState(_bp, _bs, 3);
					}
				}
				else if (((world.getBlockState(new BlockPos( x,  y,  z))).getBlock() == BlockCreepstone.block.getDefaultState()
						.getBlock())) {
					BlockPos _bp = new BlockPos( x,  y,  z);
					IBlockState _bs = BlockCreepstoneInactive.block.getDefaultState();
					IBlockState _bso = world.getBlockState(_bp);
					for (Map.Entry<IProperty<?>, Comparable<?>> entry : _bso.getProperties().entrySet()) {
						IProperty _property = entry.getKey();
						if (_bs.getPropertyKeys().contains(_property))
							_bs = _bs.withProperty(_property, (Comparable) entry.getValue());
					}
					world.setBlockState(_bp, _bs, 3);
				}
				else {
					{
						BlockPos _bp = new BlockPos( x,  y,  z);
						IBlockState _bs = BlockHiveCreepBlockInactive.block.getDefaultState();
						IBlockState _bso = world.getBlockState(_bp);
						for (Map.Entry<IProperty<?>, Comparable<?>> entry : _bso.getProperties().entrySet()) {
							IProperty _property = entry.getKey();
							if (_bs.getPropertyKeys().contains(_property))
								_bs = _bs.withProperty(_property, (Comparable) entry.getValue());
						}
						world.setBlockState(_bp, _bs, 3);
					}
				}
			}
		}
	}

	public static void optimumRVs(Map<String, Object> e) {

		ArrayList<Block> invalidBlocks = new ArrayList<>();
		invalidBlocks.add(Block.getBlockFromName("minecraft:furnace"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:brick_block"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:brick_stairs"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:bone_block"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:bedrock")); //I HAVEN'T TESTED TO SEE IF IT CONVERTS BEDROCK BEFORE BUT THAT'D BE HILARIOUS IF IT DID.
		invalidBlocks.add(Block.getBlockFromName("minecraft:concrete"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:concrete_powder"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:dropper"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:end_bricks"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:end_stone"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:glass"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:glowstone"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:jukebox"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:nether_brick"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:red_nether_brick"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:nether_brick_fence"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:nether_brick_stairs"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:noteblock"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:observer"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:obsidian"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:packed_ice"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:prismarine"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:purpur_block"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:purpur_pillar"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:purpur_slab"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:purpur_stairs"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:quartz_block"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:quartz_stairs"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:quartz_slab"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:sponge"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:stained_glass"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:stone_brick_stairs"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:stone_stairs"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:stone_slabs"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:stone_pressure_plate"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:stone_button"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:wool"));
		invalidBlocks.add(Block.getBlockFromName("minecraft:stonebrick"));

		// Wyrm blocks
		invalidBlocks.add(Block.getBlockFromName("wyrmsofnyrus:creepstone"));
		invalidBlocks.add(Block.getBlockFromName("wyrmsofnyrus:creepstone_inactive"));
		invalidBlocks.add(Block.getBlockFromName("wyrmsofnyrus:hivecreepblockinactive"));
		invalidBlocks.add(Block.getBlockFromName("wyrmsofnyrus:hivecreepblock"));
		invalidBlocks.add(Block.getBlockFromName("wyrmsofnyrus:hivecreeptop"));
		invalidBlocks.add(Block.getBlockFromName("wyrmsofnyrus:hivecreeptopinactive"));
		invalidBlocks.add(Block.getBlockFromName("wyrmsofnyrus:metal_comb_panel_hive_creep"));
		invalidBlocks.add(Block.getBlockFromName("wyrmsofnyrus:wyrm_lights_orange"));
		invalidBlocks.add(Block.getBlockFromName("wyrmsofnyrus:wyrm_lights_yellow"));


		ArrayList<BlockPos> BlockPosList = new ArrayList<>();

		int x = (int) e.get("x");
		int y = (int) e.get("y");
		int z = (int) e.get("z");

		BlockPosList.add(new BlockPos(x, y, z));		// 0
		BlockPosList.add(new BlockPos(x, y-1, z));	// 1
		BlockPosList.add(new BlockPos(x, y+1, z));	// 2
		BlockPosList.add(new BlockPos(x-1, y, z));	// 3
		BlockPosList.add(new BlockPos(x+1, y, z));	// 4
		BlockPosList.add(new BlockPos(x, y, z-1));	// 5
		BlockPosList.add(new BlockPos(x, y, z+1));	// 6


		World world = (World) e.get("world");
		boolean canSpreadThisTick = ((Math.random() >= 0.9));
		if (canSpreadThisTick) {
			if (world.isAirBlock(new BlockPos(x, y + 1, z)))
			{
				if (((world.getBlockState(BlockPosList.get(0))).getBlock() == Block.getBlockFromName("wyrmsofnyrus:hivecreepblock"))) {
					world.setBlockState((BlockPosList.get(0)), (IBlockState) Block.getBlockFromName("wyrmsofnyrus:hivecreeptop"), 3);
				}
			}
			if (!world.isAirBlock(BlockPosList.get(1))) {
				if (
						(((world.getBlockState(BlockPosList.get(0))).getMaterial() == Material.ROCK))
						&& !((invalidBlocks.contains((world.getBlockState(BlockPosList.get(0))).getBlock())))
				)
				{

				}
			}
		}

	}
}
//why.
// look ok we can do better than that, MCreator. Eat my shiny metal ArrayLists.