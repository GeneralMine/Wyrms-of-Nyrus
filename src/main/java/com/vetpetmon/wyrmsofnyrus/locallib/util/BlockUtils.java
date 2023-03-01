package com.vetpetmon.wyrmsofnyrus.locallib.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import java.util.ArrayList;

public class BlockUtils {
    public static Material getLookingBlockMat(BlockPos pos, World world) {return (world.getBlockState(pos)).getMaterial();}

    public static ArrayList<Block> getModBlocks(String modname) {
        ArrayList<Block> modBlocks = new ArrayList<>();

        for(ModContainer modcontainer : Loader.instance().getModList()) {
            if(modcontainer.getModId().equals(modname)) {

                for(Block block : Block.REGISTRY) {
                    if(block.getRegistryName().toString().contains(modname) ) {

                        modBlocks.add(block);
                    }
                }
            }
        }
        return modBlocks;
    }
}
