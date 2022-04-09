package com.vetpetmon.wyrmsofnyrus;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ItemRegistry {

    public static final List<Item> ALL_ITEMS = new ArrayList<Item>();

    //public static final Block BlockHiveCreepBlockTop = new Block(Material.CRAFTED_SNOW);

    public static void preInit(){
        for(Item item : ALL_ITEMS){
            ForgeRegistries.ITEMS.register(item);
        }

        for(Block block : BlockRegistry.ALL_BLOCKS){
            ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        }
    }
}
