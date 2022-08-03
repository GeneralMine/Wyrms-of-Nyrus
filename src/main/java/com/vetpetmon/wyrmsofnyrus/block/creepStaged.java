package com.vetpetmon.wyrmsofnyrus.block;

import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

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

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        super.onBlockAdded(world, pos, state);
        world.setBlockState(pos, state.withProperty(STAGE, 0), 2);
        world.scheduleUpdate(pos, this, this.tickRate(world));
    }

    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        super.updateTick(world, pos, state, random);
        int stage = state.getValue(STAGE);
        if (stage <= 3) world.setBlockState(pos, state.withProperty(STAGE, stage++), 2);
        if (stage >= 3) convert(world, pos);
    }

    /**
     * Converts a block into another block. OVERRIDE THIS IN EXTENDED CLASSES!
     * @param world World
     * @param pos BlockPos
     */
    public void convert(World world, BlockPos pos) {
        world.setBlockState(pos, BlockHiveCreepBlock.block.getDefaultState(), 3);
    }
}
