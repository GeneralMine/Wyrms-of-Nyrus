package com.vetpetmon.wyrmsofnyrus.block;

import com.vetpetmon.wyrmsofnyrus.creativetab.TabWyrms;
import com.vetpetmon.wyrmsofnyrus.item.AllItems;
import com.vetpetmon.wyrmsofnyrus.item.IHasModel;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block implements IHasModel {
    public BlockBase(Material m, String s, SoundType st, float hardness, float blastresist){
        super(m);
        this.setUnlocalizedName(s);
        this.setRegistryName(s);
        this.setSoundType(st);
        this.setHardness(hardness);
        this.setResistance(blastresist);
        setCreativeTab(TabWyrms.tab);
        AllBlocks.ALL_BLOCKS.add(this);
        AllItems.ALL_ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    public BlockBase(Material m, String s, SoundType st, float hardness, float blastresist, int lightOpacity, float lightLevel){
        super(m);
        this.setUnlocalizedName(s);
        this.setRegistryName(s);
        this.setSoundType(st);
        this.setHardness(hardness);
        this.setResistance(blastresist);
        this.setLightLevel(lightLevel);
        this.setLightOpacity(lightOpacity);
        setCreativeTab(TabWyrms.tab);
        AllBlocks.ALL_BLOCKS.add(this);
        AllItems.ALL_ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public void registerModels() {
        wyrmsofnyrus.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}
