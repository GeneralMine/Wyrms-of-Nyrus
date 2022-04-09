
package com.vetpetmon.wyrmsofnyrus.creativetab;

import com.vetpetmon.wyrmsofnyrus.blocks.BlockHiveCreepBlockTop;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;

public class TabWyrms {
	public void initElements() {
		tab = new CreativeTabs("tabwyrms") {
			@SideOnly(Side.CLIENT)
			@Override
			public ItemStack getTabIconItem() {
				return new ItemStack(BlockHiveCreepBlockTop.block, (int) (1));
			}

			@SideOnly(Side.CLIENT)
			public boolean hasSearchBar() {
				return false;
			}
		};
	}
	public static CreativeTabs tab;
}
