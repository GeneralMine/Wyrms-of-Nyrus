package com.vetpetmon.wyrmsofnyrus.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.*;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IWyrmBlocks {
    Class<? extends ItemBlock> getItemClass();

    IProperty[] getPresetProperties();

    IProperty[] getNonRenderingProperties();

    String getStateName(final IBlockState p0);

    @SideOnly(Side.CLIENT)
    IBlockColor getBlockColor();

    @SideOnly(Side.CLIENT)
    IItemColor getItemColor();
}
