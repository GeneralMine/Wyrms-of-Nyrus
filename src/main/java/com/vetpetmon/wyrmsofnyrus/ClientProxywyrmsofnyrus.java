package com.vetpetmon.wyrmsofnyrus;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.io.File;
import java.util.List;

public class ClientProxywyrmsofnyrus implements IProxywyrmsofnyrus {

	private static List<Block> coloredBlocks = Lists.newArrayList();;

	@Override
	public void init(FMLInitializationEvent event) {
		registerColors();
	}

	@Override
	public void registerColors() {
	}

	@Override
	public File getDataDir() {
		return Minecraft.getMinecraft().mcDataDir;
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		OBJLoader.INSTANCE.addDomain("wyrmsofnyrus");
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
	}

	@Override
	public void serverLoad(FMLServerStartingEvent event) {
	}
}
