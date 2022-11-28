package com.vetpetmon.wyrmsofnyrus.block;

import com.vetpetmon.wyrmsofnyrus.item.IHasModel;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class creepStagedGrass extends BlockGrass implements IHasModel {
    public static PropertyInteger STAGE = PropertyInteger.create("stage", 0, 9);

    public creepStagedGrass() {
        setUnlocalizedName("creepedgrass");
        setSoundType(SoundType.GROUND);
        setHarvestLevel("shovel", 1);
        setHardness(0.75F);
        setResistance(0.75F);
        setLightLevel(0F);
        setLightOpacity(255);
        setCreativeTab(wyrmsofnyrus.wyrmTabs);
    }
    @Override
    public void registerModels() {
        wyrmsofnyrus.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
    @Override
    public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag) {
        super.addInformation(itemstack, world, list, flag);
        list.add("Gradually takes over blocks before converting them. Can not spread until the block is fully converted.");
    }

    @Nonnull
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(STAGE, meta);
    }
    public int getMetaFromState(IBlockState state){return state.getValue(STAGE);}
    public BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {STAGE,SNOWY});
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        super.onBlockAdded(world, pos, state);
        world.scheduleUpdate(pos, this, this.tickRate(world));
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    public void convert(World world, BlockPos pos, Block convertTo, int activeFlag) {
        world.setBlockState(pos, convertTo.getDefaultState().withProperty(BlockHivecreepBase.ACTIVE, activeFlag), 3);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        super.updateTick(world, pos, state, random);
        int stage = state.getValue(STAGE) + 1;
        switch (stage) {
            case(4):
            case(5):
                assert false;
                convert(world, pos, AllBlocks.hivecreeptop, 1);
                break;
            case(10):
                assert false;
                convert(world, pos, AllBlocks.hivecreeptop, 0);
                break;
            default:
                world.setBlockState(pos, state.withProperty(STAGE, stage), 2);
        }
    }
}
