package com.vetpetmon.wyrmsofnyrus.procedure;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.properties.IProperty;

import java.util.Map;
import java.util.HashMap;

import com.vetpetmon.wyrmsofnyrus.block.BlockHiveCreepTop;
import com.vetpetmon.wyrmsofnyrus.ElementswyrmsofnyrusMod;

@ElementswyrmsofnyrusMod.ModElement.Tag
public class ProcedureHiveCreepBlockUpdateTick extends ElementswyrmsofnyrusMod.ModElement {
	public ProcedureHiveCreepBlockUpdateTick(ElementswyrmsofnyrusMod instance) {
		super(instance, 9);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("x") == null) {
			System.err.println("Failed to load dependency x for procedure HiveCreepBlockUpdateTick!");
			return;
		}
		if (dependencies.get("y") == null) {
			System.err.println("Failed to load dependency y for procedure HiveCreepBlockUpdateTick!");
			return;
		}
		if (dependencies.get("z") == null) {
			System.err.println("Failed to load dependency z for procedure HiveCreepBlockUpdateTick!");
			return;
		}
		if (dependencies.get("world") == null) {
			System.err.println("Failed to load dependency world for procedure HiveCreepBlockUpdateTick!");
			return;
		}
		int x = (int) dependencies.get("x");
		int y = (int) dependencies.get("y");
		int z = (int) dependencies.get("z");
		World world = (World) dependencies.get("world");
		if (((Math.random() * 10) > 9)) {
			if ((world.isAirBlock(new BlockPos((int) x, (int) (y + 1), (int) z)))) {
				{
					BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
					IBlockState _bs = BlockHiveCreepTop.block.getDefaultState();
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
					Map<String, Object> $_dependencies = new HashMap<>();
					$_dependencies.put("x", x);
					$_dependencies.put("y", y);
					$_dependencies.put("z", z);
					$_dependencies.put("world", world);
					ProcedureHiveCreepSpreadFurther.executeProcedure($_dependencies);
				}
			}
		}
	}
}
