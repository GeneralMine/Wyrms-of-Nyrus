
package com.vetpetmon.wyrmsofnyrus.block;

import com.vetpetmon.wyrmsofnyrus.invasion.HiveCreepSpreadFurther;
import com.vetpetmon.wyrmsofnyrus.invasion.InvasionBlockSpread;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.event.ModelRegistryEvent;

import net.minecraft.world.IBlockAccess;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block;

import com.vetpetmon.wyrmsofnyrus.creativetab.TabWyrms;
import com.vetpetmon.wyrmsofnyrus.AutoReg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@AutoReg.ModElement.Tag
public class BlockCreepstone extends AutoReg.ModElement {
	@GameRegistry.ObjectHolder("wyrmsofnyrus:creepstone")
	public static final Block block = null;
	public BlockCreepstone(AutoReg instance) {
		super(instance, 30);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new BlockCustom().setRegistryName("creepstone"));
		elements.items.add(() -> new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
				new ModelResourceLocation("wyrmsofnyrus:creepstone", "inventory"));
	}
	public static class BlockCustom extends Block {
		int timesSpread = 0;
		public BlockCustom() {
			super(Material.CRAFTED_SNOW);
			setUnlocalizedName("creepstone");
			setSoundType(SoundType.SLIME);
			setHardness(2F);
			setResistance(7F);
			setLightLevel(0F);
			setLightOpacity(255);
			setCreativeTab(TabWyrms.tab);
		}
		@Override
		public int tickRate(World world) {
			return 600;
		}

		@Override
		public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
			super.onBlockAdded(world, pos, state);
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			world.scheduleUpdate(new BlockPos(x, y, z), this, this.tickRate(world));
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("world", world);
				InvasionBlockSpread.run($_dependencies);
			}
		}

		@Override
		public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
			super.updateTick(world, pos, state, random);
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				timesSpread = HiveCreepSpreadFurther.executescript($_dependencies, timesSpread);
			}
			world.scheduleUpdate(new BlockPos(x, y, z), this, this.tickRate(world));
			if (timesSpread > 50) {
				System.out.println("Debugging: Creepstone block at " + (new BlockPos(x, y, z)) + " was turned inactive after " + (timesSpread) + " operations.");
				world.setBlockState((new BlockPos(x, y, z)), BlockCreepstoneInactive.block.getDefaultState(), 3);
			}
		}

		@Override
		public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag) {
			super.addInformation(itemstack, world, list, flag);
			list.add("Spreads!");
		}
	}
}
