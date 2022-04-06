
package com.vetpetmon.wyrmsofnyrus.creativetab;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;

import com.vetpetmon.wyrmsofnyrus.item.ItemWyrmico;
import com.vetpetmon.wyrmsofnyrus.ElementswyrmsofnyrusMod;

@ElementswyrmsofnyrusMod.ModElement.Tag
public class TabWyrms extends ElementswyrmsofnyrusMod.ModElement {
	public TabWyrms(ElementswyrmsofnyrusMod instance) {
		super(instance, 5);
	}

	@Override
	public void initElements() {
		tab = new CreativeTabs("tabwyrms") {
			@SideOnly(Side.CLIENT)
			@Override
			public ItemStack getTabIconItem() {
				return new ItemStack(ItemWyrmico.block, (int) (1));
			}

			@SideOnly(Side.CLIENT)
			public boolean hasSearchBar() {
				return false;
			}
		};
	}
	public static CreativeTabs tab;
}
