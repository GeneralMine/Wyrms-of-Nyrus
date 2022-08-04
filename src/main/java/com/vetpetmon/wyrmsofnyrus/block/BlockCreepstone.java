
package com.vetpetmon.wyrmsofnyrus.block;

import com.vetpetmon.wyrmsofnyrus.config.Debug;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.invasion.HiveCreepSpreadFurther;
import com.vetpetmon.wyrmsofnyrus.invasion.invasionPoints;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.event.ModelRegistryEvent;

import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block;

import com.vetpetmon.wyrmsofnyrus.creativetab.TabWyrms;
import com.vetpetmon.wyrmsofnyrus.AutoReg;

import java.util.List;
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
		public BlockCustom() {
			super(BlockMaterials.CREEP);
			setUnlocalizedName("creepstone");
			setSoundType(SoundType.SLIME);
			setHarvestLevel("pickaxe", 2);
			setHardness(2F);
			setResistance(10F);
			setLightLevel(0F);
			setLightOpacity(255);
			setCreativeTab(TabWyrms.tab);
		}

		public int tickRate(World world) {
			return Invasion.creepTickRate;
		}

		public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
			super.updateTick(world, pos, state, random);
			HiveCreepSpreadFurther.executescript(pos, world);
			ActiveCreepBlock.CreepSpread(pos, world, BlockCreepstoneInactive.block.getDefaultState());
			world.scheduleUpdate(pos, this, this.tickRate(world));
		}

		@Override
		public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
			super.onBlockAdded(world, pos, state);
			world.scheduleUpdate(pos, this, this.tickRate(world));
		}

		@Override
		public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag) {
			super.addInformation(itemstack, world, list, flag);
			list.add("Spreads!");
		}
	}
}
