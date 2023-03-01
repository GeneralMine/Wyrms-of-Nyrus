package com.vetpetmon.wyrmsofnyrus;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.io.File;

public class ServerProxyWyrmsOfNyrus implements IProxyWyrmsOfNyrus {
	@Override
	public File getDataDir() {
		return FMLCommonHandler.instance().getMinecraftServerInstance().getDataDirectory();
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
	}

	@Override
	public void init(FMLInitializationEvent event) {
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
	}

	@Override
	public void serverLoad(FMLServerStartingEvent event) {
	}

	@Override
	public void registerColors() {

	}

	@Override
	public void registerItemRenderer(Item item, int meta, String id) {

	}
	@Override
	public boolean isServer() {return true;}
}
