
package com.vetpetmon.wyrmsofnyrus.block;

import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.invasion.HiveCreepSpreadFurther;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.event.ModelRegistryEvent;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.Item;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block;

import java.util.Random;
import java.util.Map;
import java.util.List;
import java.util.HashMap;

import com.vetpetmon.wyrmsofnyrus.invasion.InvasionBlockSpread;
import com.vetpetmon.wyrmsofnyrus.creativetab.TabWyrms;
import com.vetpetmon.wyrmsofnyrus.AutoReg;

@AutoReg.ModElement.Tag
public class BlockHiveCreepBlock extends AutoReg.ModElement {
	@GameRegistry.ObjectHolder("wyrmsofnyrus:hivecreepblock")
	public static final Block block = null;
	public BlockHiveCreepBlock(AutoReg instance) {
		super(instance, 4);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new BlockCustom().setRegistryName("hivecreepblock"));
		elements.items.add(() -> new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
				new ModelResourceLocation("wyrmsofnyrus:hivecreepblock", "inventory"));
	}
	public static class BlockCustom extends Block {
		int timesSpread;
		public BlockCustom() {
			super(BlockMaterials.CREEP);
			setUnlocalizedName("hivecreepblock");
			setSoundType(SoundType.SLIME);
			setHarvestLevel("shovel", 1);
			setHardness(1.5F);
			setResistance(5F);
			setLightLevel(0F);
			setLightOpacity(255);
			setCreativeTab(TabWyrms.tab);
			this.timesSpread = 0;
		}

		@Override
		public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag) {
			super.addInformation(itemstack, world, list, flag);
			list.add("Spreads!");
		}

		@Override
		public int tickRate(World world) {
			return Invasion.creepTickRate;
		}

		@Override
		public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
			super.onBlockAdded(world, pos, state);
			world.scheduleUpdate(pos, this, this.tickRate(world));
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("world", world);
				InvasionBlockSpread.run($_dependencies);
			}
		}

		public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
			super.updateTick(world, pos, state, random);
			if (world.isAirBlock(new BlockPos(pos.getX(), pos.getY()+1,pos.getZ()))){
				world.setBlockState((pos), BlockHiveCreepTop.block.getDefaultState(), 3);
			}
			this.timesSpread = HiveCreepSpreadFurther.executescript(pos, world, this.timesSpread);
			ActiveCreepBlock.CreepSpread(pos, world, this.timesSpread, BlockHiveCreepBlockInactive.block.getDefaultState());
			world.scheduleUpdate(pos, this, this.tickRate(world));
		}
	}
}
