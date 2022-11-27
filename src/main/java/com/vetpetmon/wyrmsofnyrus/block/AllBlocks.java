package com.vetpetmon.wyrmsofnyrus.block;

import com.vetpetmon.wyrmsofnyrus.creativetab.TabWyrms;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class AllBlocks {
    public static List<Block> ALL_BLOCKS = new ArrayList<Block>();

    public static final Block metalcombpanelmimic = new BlockBase(Material.IRON, "metalcombpanelmimic", SoundType.METAL, 10, 6).setCreativeTab(TabWyrms.tab);


    public static void preInit(){
        for(Block block : ALL_BLOCKS){
            ForgeRegistries.BLOCKS.register(block);
            wyrmsofnyrus.logger.info("Block registered:" + block);
        }
    }

    public static void init(){

    }

    public static void postInit(){

    }
}
