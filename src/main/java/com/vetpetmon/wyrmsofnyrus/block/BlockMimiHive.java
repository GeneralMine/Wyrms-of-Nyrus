package com.vetpetmon.wyrmsofnyrus.block;

import com.vetpetmon.wyrmsofnyrus.AutoReg;
import com.vetpetmon.wyrmsofnyrus.creativetab.TabWyrms;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockMimiHive extends AutoReg.ModElement {
    @GameRegistry.ObjectHolder("wyrmsofnyrus:metalcombpanelmimic")
    public static final Block block = null;

    public BlockMimiHive(AutoReg instance) {
        super(instance, 400);
    }

    @Override
    public void initElements() {
        elements.blocks.add(() -> new BlockCustom().setRegistryName("metalcombpanelmimic"));
        elements.items.add(() -> new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
                new ModelResourceLocation("wyrmsofnyrus:metalcombpanelmimic", "inventory"));
    }
    public static class BlockCustom extends Block {

        public BlockCustom() {
            super(BlockMaterials.CREEP);
            setUnlocalizedName("metalcombpanelmimic");
            setSoundType(SoundType.METAL);
            setHarvestLevel("pickaxe", 3);
            setHardness(10.5F);
            setResistance(10F);
            setLightLevel(0F);
            setLightOpacity(255);
            setCreativeTab(TabWyrms.tab);
        }

        @Override
        public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag) {
            super.addInformation(itemstack, world, list, flag);
            list.add("Mimics the appearance of real Nyral Wyrm hivecomb panels, wyrms will not attack these walls unless if they suspect you're behind them.");
        }

        @Override
        public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
            super.onBlockAdded(world, pos, state);
        }
    }
}
