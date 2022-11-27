package com.vetpetmon.wyrmsofnyrus.block;

import com.vetpetmon.wyrmsofnyrus.creativetab.TabWyrms;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class AllBlocks {
    public static List<Block> ALL_BLOCKS = new ArrayList<Block>();

    public static final Block metalcombpanelmimic = new BlockBase(Material.IRON, "metalcombpanelmimic", SoundType.METAL, 10, 6).setCreativeTab(TabWyrms.tab);

}
