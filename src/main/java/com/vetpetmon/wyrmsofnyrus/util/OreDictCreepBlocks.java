
package com.vetpetmon.wyrmsofnyrus.util;

import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import net.minecraft.item.ItemStack;

import com.vetpetmon.wyrmsofnyrus.block.BlockHiveCreepTopInactive;
import com.vetpetmon.wyrmsofnyrus.block.BlockHiveCreepTop;
import com.vetpetmon.wyrmsofnyrus.block.BlockHiveCreepBlockInactive;
import com.vetpetmon.wyrmsofnyrus.block.BlockHiveCreepBlock;
import com.vetpetmon.wyrmsofnyrus.ElementswyrmsofnyrusMod;

@ElementswyrmsofnyrusMod.ModElement.Tag
public class OreDictCreepBlocks extends ElementswyrmsofnyrusMod.ModElement {
	public OreDictCreepBlocks(ElementswyrmsofnyrusMod instance) {
		super(instance, 18);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		OreDictionary.registerOre("creepblocks", new ItemStack(BlockHiveCreepTop.block, (int) (1)));
		OreDictionary.registerOre("creepblocks", new ItemStack(BlockHiveCreepBlock.block, (int) (1)));
		OreDictionary.registerOre("creepblocks", new ItemStack(BlockHiveCreepTopInactive.block, (int) (1)));
		OreDictionary.registerOre("creepblocks", new ItemStack(BlockHiveCreepBlockInactive.block, (int) (1)));
	}
}
