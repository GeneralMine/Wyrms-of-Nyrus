package com.vetpetmon.wyrmsofnyrus.item;

import com.vetpetmon.synapselib.rendering.IHasModel;
import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.List;

public class ItemBase extends Item implements IHasModel {
    private final boolean hastooltip;

    public ItemBase(String name, boolean hastooltip) {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(WyrmsOfNyrus.wyrmTabs);
        AllItems.ALL_ITEMS.add(this);
        this.hastooltip = hastooltip;
    }
    public ItemBase(String name, boolean hastooltip, boolean hidden) {
        setUnlocalizedName(name);
        setRegistryName(name);
        if (!hidden) setCreativeTab(WyrmsOfNyrus.wyrmTabs);
        AllItems.ALL_ITEMS.add(this);
        this.hastooltip = hastooltip;
    }

    //Shortcut used for usable items that need to be removed upon use.
    public static void onUseShrink(EntityPlayer player, EnumHand hand) {
        ItemStack is = player.getHeldItem(hand);
        if (!player.capabilities.isCreativeMode) {
            is.shrink(1);
            if (is.getCount() <= 0) player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
        }
    }

    @Override
    public void registerModels() {
        WyrmsOfNyrus.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (hastooltip) tooltip.add(I18n.format("tooltip.won." + this.getUnlocalizedName(), new Object[0]));
    }
}
