package com.vetpetmon.wyrmsofnyrus;

import com.vetpetmon.wyrmsofnyrus.item.ItemBase;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.io.File;

public interface IProxywyrmsofnyrus {
	File getDataDir();

	void preInit(FMLPreInitializationEvent event);

	void init(FMLInitializationEvent event);

	void postInit(FMLPostInitializationEvent event);

	void serverLoad(FMLServerStartingEvent event);

	void registerColors();

    void registerItemRenderer(Item item, int meta, String id);
}
