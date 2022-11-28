
package com.vetpetmon.wyrmsofnyrus.creativetab;

import com.vetpetmon.wyrmsofnyrus.item.AllItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;


public class TabWyrms extends CreativeTabs {

	public TabWyrms(int index, String label) {
		super(index, label);
	}

	@Override
	@Nonnull
	public ItemStack getTabIconItem() {
		return new ItemStack(AllItems.wyrmico, 1);
	}
}
