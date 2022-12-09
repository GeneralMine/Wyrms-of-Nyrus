package com.vetpetmon.wyrmsofnyrus.block;

import com.vetpetmon.wyrmsofnyrus.block.generic.*;
import com.vetpetmon.wyrmsofnyrus.block.hivecreep.*;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class AllBlocks {
    public static List<Block> ALL_BLOCKS = new ArrayList<>();

    public static final Block metalcombpanelmimic = new BlockBase(Material.IRON, "metalcombpanelmimic", SoundType.METAL, 4, 6, true);
    public static final Block metal_comb_panel_hive_creep = new BlockBase(Material.IRON, "metal_comb_panel_hive_creep", SoundType.METAL, 6, 7);
    public static final Block follyflesh = new BlockBase(Material.CLAY, "follyflesh", SoundType.SLIME, 1, 1.5F);
    public static final Block follybone = new BlockPillar(Material.ROCK, "follybone", SoundType.STONE, 4, 8);

    public static final Block wyrm_lights_orange = new BlockBase(BlockMaterials.CREEP, "wyrm_lights_orange", SoundType.SLIME, 6, 7,true, 0, 1F);
    public static final Block wyrm_lights_yellow = new BlockBase(BlockMaterials.CREEP, "wyrm_lights_yellow", SoundType.SLIME, 6, 7,true, 0, 1F);

    public static final Block hivecreepblock = new BlockHivecreepBase("hivecreepblock",1,1);
    public static final Block hivecreeptop = new BlockHivecreepBase("hivecreeptop",1,1.25F);
    public static final Block creepstone = new BlockHivecreepBase("creepstone",3,4);
    public static final Block creepsludge = new BlockHivecreepBase("creepsludge",0.5F,1.25F);
    public static final Block creeplog = new BlockHivecreepPillar("creeplog",2,2);


    public static final Block creepedgrass = new creepStagedGrass();
    public static final Block creepeddirt = new creepStaged("creepeddirt",0.7F,0.9F, hivecreepblock);
    public static final Block creepedsand = new creepStaged("creepedsand",0.5F,0.5F, creepsludge);
    public static final Block creepedstone = new creepStaged("creepedstone",1.5F,7.5F, hivecreepblock);

}
