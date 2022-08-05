
package com.vetpetmon.wyrmsofnyrus.block;

import com.vetpetmon.wyrmsofnyrus.AutoReg;
import com.vetpetmon.wyrmsofnyrus.creativetab.TabWyrms;
import com.vetpetmon.wyrmsofnyrus.synapselib.RegHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
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
public class BlockHiveCreepedSand extends AutoReg.ModElement {

	private static final String name = "creepedsand";

	@GameRegistry.ObjectHolder("wyrmsofnyrus:creepedsand")
	public static final Block block = null;
	public BlockHiveCreepedSand(AutoReg instance) {
		super(instance, 207);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new creepedSand().setRegistryName(name));
		elements.items.add(() -> new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
				new ModelResourceLocation(RegHelper.resName(name), "inventory"));
	}
	public static class creepedSand extends creepStaged {

		public creepedSand() {
			setUnlocalizedName(name);
			setSoundType(SoundType.SAND);
			setHarvestLevel("shovel", 0);
			setHardness(0.15F);
			setResistance(0.15F);
			setLightLevel(0F);
			setLightOpacity(255);
			setCreativeTab(TabWyrms.tab);
		}
		@Override
		public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
			super.updateTick(world, pos, state, random);
			int stage = state.getValue(STAGE) + 1;
			switch (stage) {
				case(4):
				case(5):
					assert false;
					convert(world, pos, BlockHiveCreepBlock.block.getDefaultState());
					break;
				case(10):
					assert false;
					convert(world, pos, BlockHiveCreepBlockInactive.block.getDefaultState());
					break;
				default:
					world.setBlockState(pos, state.withProperty(STAGE, stage), 2);
			}
		}
	}
}
