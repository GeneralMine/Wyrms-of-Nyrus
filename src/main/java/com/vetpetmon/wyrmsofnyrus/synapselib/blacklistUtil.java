package com.vetpetmon.wyrmsofnyrus.synapselib;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class blacklistUtil {

    /**
     * Creates a block blacklist through a given set of namespaces.
     *
     * @param nameSpaces A raw string array of namespaces
     * @return an ArrayList of blocks found by namespace.
     */
    public static ArrayList<Block> castToBlockBL(String[] nameSpaces) {
        ArrayList<Block> blockBlackList = new ArrayList<>();
        List<String> listTemp = Arrays.asList(nameSpaces);
        ArrayList<String> AL =  new ArrayList<>(listTemp);
        for (String i:AL) {
            blockBlackList.add(Block.getBlockFromName(i));
        }
        return blockBlackList;


    }
}
