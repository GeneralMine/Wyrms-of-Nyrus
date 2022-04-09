package com.vetpetmon.wyrmsofnyrus;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import com.vetpetmon.wyrmsofnyrus.blocks.BlockHiveCreepBlockTop;
import com.vetpetmon.wyrmsofnyrus.blocks.BlockHiveCreepBlock;

import java.util.ArrayList;
import java.util.List;

public class BlockRegistry {

    public static List<Block> ALL_BLOCKS = new ArrayList<Block>();

    public static final Block BlockHiveCreepBlockTop = new Block(Material.CRAFTED_SNOW);

    public static void preInit(){
        for(Block block : ALL_BLOCKS){
            ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        }
    }
}
