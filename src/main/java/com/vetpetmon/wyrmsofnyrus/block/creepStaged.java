package com.vetpetmon.wyrmsofnyrus.block;

import net.minecraft.block.Block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public abstract class creepStaged extends Block {

    public static PropertyInteger STAGE = PropertyInteger.create("stage", 1, 3);

    public creepStaged() {
        super(BlockMaterials.CREEP);
        this.setTickRandomly(true);
    }

    @Override
    public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag) {
        super.addInformation(itemstack, world, list, flag);
        list.add("Spreads!");
    }

    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(STAGE);
    }
    public BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {STAGE});
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        super.onBlockAdded(world, pos, state);
        world.setBlockState(pos, state.withProperty(STAGE, 1), 2);
        world.scheduleUpdate(pos, this, this.tickRate(world));
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
