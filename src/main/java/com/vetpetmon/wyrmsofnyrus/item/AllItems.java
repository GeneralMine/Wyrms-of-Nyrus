package com.vetpetmon.wyrmsofnyrus.item;

import com.vetpetmon.wyrmsofnyrus.block.AllBlocks;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AllItems {
    public static final List<Item> ALL_ITEMS = new ArrayList<Item>();

    public static final Item creepshard = new ItemBase("creepshard", true);
    public static final Item metalcomb_array = new ItemBase("metalcomb_array", true);
    public static final Item wyrmarmorfrag = new ItemBase("wyrmarmorfrag", true);
    public static final Item wyrmico = new ItemBase("wyrmico", false);

    public static void preInit(){
        for(Item item : ALL_ITEMS){
            ForgeRegistries.ITEMS.register(item);
            wyrmsofnyrus.logger.info("Item registered:" + item);
        }

        for(Block block : AllBlocks.ALL_BLOCKS){
            ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(Objects.requireNonNull(block.getRegistryName())));
            wyrmsofnyrus.logger.info("ItemBlock registered:" + block);
        }
    }

    public static void init(){
    }

    public static void postInit(){
    }
}
