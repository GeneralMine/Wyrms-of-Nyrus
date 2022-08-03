
package com.vetpetmon.wyrmsofnyrus.block;

import com.vetpetmon.wyrmsofnyrus.AutoReg;
import com.vetpetmon.wyrmsofnyrus.creativetab.TabWyrms;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

@AutoReg.ModElement.Tag
public class BlockHiveCreepedDirt extends AutoReg.ModElement {
	@GameRegistry.ObjectHolder("wyrmsofnyrus:creepeddirt")
	public static final Block block = null;
	public BlockHiveCreepedDirt(AutoReg instance) {
		super(instance, 204);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new creepedDirt().setRegistryName("creepeddirt"));
		elements.items.add(() -> new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
				new ModelResourceLocation("wyrmsofnyrus:creepeddirt", "inventory"));
	}
	public static class creepedDirt extends creepStaged {

		public creepedDirt() {
			setUnlocalizedName("creepeddirt");
			setSoundType(SoundType.GROUND);
			setHarvestLevel("shovel", 1);
			setHardness(0.5F);
			setResistance(0.5F);
			setLightLevel(0F);
			setLightOpacity(255);
			setCreativeTab(TabWyrms.tab);
		}
		@Override
		public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
			super.updateTick(world, pos, state, random);
			int stage = state.getValue(STAGE) + 1;
			if (stage < 3) world.setBlockState(pos, state.withProperty(STAGE, stage), 2);
			else if (stage >= 3) {
				assert false;
				convert(world, pos, BlockHiveCreepBlockInactive.block.getDefaultState());
			}
		}
	}
}
