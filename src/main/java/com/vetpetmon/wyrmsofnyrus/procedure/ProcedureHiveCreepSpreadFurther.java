package com.vetpetmon.wyrmsofnyrus.procedure;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.material.Material;

import java.util.Map;

import com.vetpetmon.wyrmsofnyrus.block.BlockHiveCreepTopInactive;
import com.vetpetmon.wyrmsofnyrus.block.BlockHiveCreepTop;
import com.vetpetmon.wyrmsofnyrus.block.BlockHiveCreepBlockInactive;
import com.vetpetmon.wyrmsofnyrus.block.BlockHiveCreepBlock;
import com.vetpetmon.wyrmsofnyrus.ElementswyrmsofnyrusMod;

@ElementswyrmsofnyrusMod.ModElement.Tag
public class ProcedureHiveCreepSpreadFurther extends ElementswyrmsofnyrusMod.ModElement {
	public ProcedureHiveCreepSpreadFurther(ElementswyrmsofnyrusMod instance) {
		super(instance, 10);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("x") == null) {
			System.err.println("Failed to load dependency x for procedure HiveCreepSpreadFurther!");
			return;
		}
		if (dependencies.get("y") == null) {
			System.err.println("Failed to load dependency y for procedure HiveCreepSpreadFurther!");
			return;
		}
		if (dependencies.get("z") == null) {
			System.err.println("Failed to load dependency z for procedure HiveCreepSpreadFurther!");
			return;
		}
		if (dependencies.get("world") == null) {
			System.err.println("Failed to load dependency world for procedure HiveCreepSpreadFurther!");
			return;
		}
		int x = (int) dependencies.get("x");
		int y = (int) dependencies.get("y");
		int z = (int) dependencies.get("z");
		World world = (World) dependencies.get("world");
		if (((Math.random() * 10) >= 9)) {
			if ((((world.getBlockState(new BlockPos((int) x, (int) y, (int) (z - 1)))).getMaterial() == Material.GROUND)
					|| ((world.getBlockState(new BlockPos((int) x, (int) y, (int) (z - 1)))).getMaterial() == Material.GRASS))) {
				{
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) (z - 1));
					IBlockState _bs = BlockHiveCreepBlock.block.getDefaultState();
					IBlockState _bso = world.getBlockState(_bp);
					for (Map.Entry<IProperty<?>, Comparable<?>> entry : _bso.getProperties().entrySet()) {
						IProperty _property = entry.getKey();
						if (_bs.getPropertyKeys().contains(_property))
							_bs = _bs.withProperty(_property, (Comparable) entry.getValue());
					}
					world.setBlockState(_bp, _bs, 3);
				}
			} else if ((((world.getBlockState(new BlockPos((int) x, (int) y, (int) (z + 1)))).getMaterial() == Material.GROUND)
					|| ((world.getBlockState(new BlockPos((int) x, (int) y, (int) (z + 1)))).getMaterial() == Material.GRASS))) {
				{
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) (z + 1));
					IBlockState _bs = BlockHiveCreepBlock.block.getDefaultState();
					IBlockState _bso = world.getBlockState(_bp);
					for (Map.Entry<IProperty<?>, Comparable<?>> entry : _bso.getProperties().entrySet()) {
						IProperty _property = entry.getKey();
						if (_bs.getPropertyKeys().contains(_property))
							_bs = _bs.withProperty(_property, (Comparable) entry.getValue());
					}
					world.setBlockState(_bp, _bs, 3);
				}
			} else if ((((world.getBlockState(new BlockPos((int) (x + 1), (int) y, (int) z))).getMaterial() == Material.GROUND)
					|| ((world.getBlockState(new BlockPos((int) (x + 1), (int) y, (int) z))).getMaterial() == Material.GRASS))) {
				{
					BlockPos _bp = new BlockPos((int) (x + 1), (int) y, (int) z);
					IBlockState _bs = BlockHiveCreepBlock.block.getDefaultState();
					IBlockState _bso = world.getBlockState(_bp);
					for (Map.Entry<IProperty<?>, Comparable<?>> entry : _bso.getProperties().entrySet()) {
						IProperty _property = entry.getKey();
						if (_bs.getPropertyKeys().contains(_property))
							_bs = _bs.withProperty(_property, (Comparable) entry.getValue());
					}
					world.setBlockState(_bp, _bs, 3);
				}
			} else if ((((world.getBlockState(new BlockPos((int) (x - 1), (int) y, (int) z))).getMaterial() == Material.GROUND)
					|| ((world.getBlockState(new BlockPos((int) (x - 1), (int) y, (int) z))).getMaterial() == Material.GRASS))) {
				{
					BlockPos _bp = new BlockPos((int) (x - 1), (int) y, (int) z);
					IBlockState _bs = BlockHiveCreepBlock.block.getDefaultState();
					IBlockState _bso = world.getBlockState(_bp);
					for (Map.Entry<IProperty<?>, Comparable<?>> entry : _bso.getProperties().entrySet()) {
						IProperty _property = entry.getKey();
						if (_bs.getPropertyKeys().contains(_property))
							_bs = _bs.withProperty(_property, (Comparable) entry.getValue());
					}
					world.setBlockState(_bp, _bs, 3);
				}
			} else if ((((world.getBlockState(new BlockPos((int) x, (int) (y - 1), (int) z))).getMaterial() == Material.GROUND)
					|| ((world.getBlockState(new BlockPos((int) x, (int) (y - 1), (int) z))).getMaterial() == Material.GRASS))) {
				{
					BlockPos _bp = new BlockPos((int) x, (int) (y - 1), (int) z);
					IBlockState _bs = BlockHiveCreepBlock.block.getDefaultState();
					IBlockState _bso = world.getBlockState(_bp);
					for (Map.Entry<IProperty<?>, Comparable<?>> entry : _bso.getProperties().entrySet()) {
						IProperty _property = entry.getKey();
						if (_bs.getPropertyKeys().contains(_property))
							_bs = _bs.withProperty(_property, (Comparable) entry.getValue());
					}
					world.setBlockState(_bp, _bs, 3);
				}
			} else if ((((world.getBlockState(new BlockPos((int) x, (int) (y + 1), (int) z))).getMaterial() == Material.GROUND)
					|| ((world.getBlockState(new BlockPos((int) x, (int) (y + 1), (int) z))).getMaterial() == Material.GRASS))) {
				{
					BlockPos _bp = new BlockPos((int) x, (int) (y + 1), (int) z);
					IBlockState _bs = BlockHiveCreepBlock.block.getDefaultState();
					IBlockState _bso = world.getBlockState(_bp);
					for (Map.Entry<IProperty<?>, Comparable<?>> entry : _bso.getProperties().entrySet()) {
						IProperty _property = entry.getKey();
						if (_bs.getPropertyKeys().contains(_property))
							_bs = _bs.withProperty(_property, (Comparable) entry.getValue());
					}
					world.setBlockState(_bp, _bs, 3);
				}
			} else {
				if (((world.getBlockState(new BlockPos((int) x, (int) y, (int) z))).getBlock() == BlockHiveCreepTop.block.getDefaultState()
						.getBlock())) {
					{
						BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
						IBlockState _bs = BlockHiveCreepTopInactive.block.getDefaultState();
						IBlockState _bso = world.getBlockState(_bp);
						for (Map.Entry<IProperty<?>, Comparable<?>> entry : _bso.getProperties().entrySet()) {
							IProperty _property = entry.getKey();
							if (_bs.getPropertyKeys().contains(_property))
								_bs = _bs.withProperty(_property, (Comparable) entry.getValue());
						}
						world.setBlockState(_bp, _bs, 3);
					}
				} else {
					{
						BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
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
