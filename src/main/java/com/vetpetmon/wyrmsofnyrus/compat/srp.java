package com.vetpetmon.wyrmsofnyrus.compat;

import com.vetpetmon.wyrmsofnyrus.synapselib.util.blockUtils;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;

public class srp {
    private static boolean isEnabled = false;
    public static ArrayList<Block> srpBlocks;
    public static void compatInit()
    {
        if (Loader.isModLoaded("srparasites")) {
            isEnabled = true;
        }
    }

    public static void compatPostInt() {
        srpBlocks = blockUtils.getModBlocks("srparasites");
        //wyrmsofnyrus.logger.info("srpBlocks is now defined as:" + srpBlocks);
    }


    public static boolean isEnabled() {
        return isEnabled;
    }
}
