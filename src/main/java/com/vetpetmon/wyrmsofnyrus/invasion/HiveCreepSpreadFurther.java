package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.block.*;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.material.Material;

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
}
//why.