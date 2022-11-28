package com.vetpetmon.wyrmsofnyrus.item;

import com.vetpetmon.wyrmsofnyrus.creativetab.TabWyrms;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.client.resources.I18n;

import java.util.List;

public class ItemBase extends Item implements IHasModel {
    private final boolean hastooltip;

    public ItemBase(String name, boolean hastooltip) {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(TabWyrms.tab);
        AllItems.ALL_ITEMS.add(this);
        this.hastooltip = hastooltip;
    }

    @Override
    public void registerModels() {
        wyrmsofnyrus.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (hastooltip) tooltip.add(I18n.format("tooltip.won." + this.getUnlocalizedName(), new Object[0]));
    }
}