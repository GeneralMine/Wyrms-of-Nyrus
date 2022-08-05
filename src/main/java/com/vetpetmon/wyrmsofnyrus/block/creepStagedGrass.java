package com.vetpetmon.wyrmsofnyrus.block;

import net.minecraft.block.BlockGrass;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class creepStagedGrass extends BlockGrass {
    public static PropertyInteger STAGE = PropertyInteger.create("stage", 0, 9);

    public creepStagedGrass() {
        this.setTickRandomly(true);
        this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, 0));
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

    /**
     * Converts a block into another block. OVERRIDE THIS IN EXTENDED CLASSES!
     * @param world World
     * @param pos BlockPos
     * @param convertTo BlockState
     */
    public void convert(World world, BlockPos pos, IBlockState convertTo) {
        world.setBlockState(pos, convertTo, 3);
    }
}
