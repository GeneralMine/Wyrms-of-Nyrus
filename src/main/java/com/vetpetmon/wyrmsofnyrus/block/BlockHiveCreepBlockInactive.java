
package com.vetpetmon.wyrmsofnyrus.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.event.ModelRegistryEvent;

import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.Item;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block;

import java.util.List;
import java.util.Random;

import com.vetpetmon.wyrmsofnyrus.creativetab.TabWyrms;
import com.vetpetmon.wyrmsofnyrus.AutoReg;

@AutoReg.ModElement.Tag
public class BlockHiveCreepBlockInactive extends AutoReg.ModElement {
	@GameRegistry.ObjectHolder("wyrmsofnyrus:hivecreepblockinactive")
	public static final Block block = null;
	public BlockHiveCreepBlockInactive(AutoReg instance) {
		super(instance, 17);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new BlockCustom().setRegistryName("hivecreepblockinactive"));
		elements.items.add(() -> new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
				new ModelResourceLocation("wyrmsofnyrus:hivecreepblockinactive", "inventory"));
	}
	public static class BlockCustom extends Block {
		public BlockCustom() {
			super(BlockMaterials.CREEP);
			setUnlocalizedName("hivecreepblockinactive");
			setSoundType(SoundType.SLIME);
			setHarvestLevel("shovel", 1);
			setHardness(1.05F);
			setResistance(5F);
			setLightLevel(0F);
			setLightOpacity(255);
			setCreativeTab(TabWyrms.tab);
		}

		@Override
		public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag) {
			super.addInformation(itemstack, world, list, flag);
			list.add("Stale - Does not spread");
		}

		@Override
		public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
			super.onBlockAdded(world, pos, state);
			world.scheduleUpdate(pos, this, this.tickRate(world));
		}
		@Override
		public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
			super.updateTick(world, pos, state, random);
			if (world.isAirBlock(new BlockPos(pos.getX(), pos.getY()+1,pos.getZ()))){
				world.setBlockState((pos), BlockHiveCreepTopInactive.block.getDefaultState(), 3);
			}
		}

		@Override
		public int tickRate(World world) {
			return 500;
		}
	}
}
