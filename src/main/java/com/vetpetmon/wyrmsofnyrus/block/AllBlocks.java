package com.vetpetmon.wyrmsofnyrus.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class AllBlocks {
    public static List<Block> ALL_BLOCKS = new ArrayList<Block>();

    public static final Block metalcombpanelmimic = new BlockBase(Material.IRON, "metalcombpanelmimic", SoundType.METAL, 4, 6);
    public static final Block metal_comb_panel_hive_creep = new BlockBase(Material.IRON, "metal_comb_panel_hive_creep", SoundType.METAL, 6, 7);

    public static final Block wyrm_lights_orange = new BlockBase(Material.IRON, "wyrm_lights_orange", SoundType.METAL, 6, 7,0,1F);
    public static final Block wyrm_lights_yellow = new BlockBase(Material.IRON, "wyrm_lights_yellow", SoundType.METAL, 6, 7,0,1F);

    public static final Block hivecreepblock = new BlockHivecreepBase("hivecreepblock",1,2);
    public static final Block hivecreeptop = new BlockHivecreepBase("hivecreeptop",1,2.25F);
    public static final Block creepstone = new BlockHivecreepBase("creepstone",3,4);


    public static final Block creepedgrass = new creepStagedGrass();
    public static final Block creepeddirt = new creepStaged("creepeddirt",0.7F,0.9F, hivecreepblock);
    public static final Block creepedsand = new creepStaged("creepedsand",0.5F,0.5F, hivecreepblock);
    public static final Block creepedstone = new creepStaged("creepedstone",1.5F,7.5F, hivecreepblock);

}
