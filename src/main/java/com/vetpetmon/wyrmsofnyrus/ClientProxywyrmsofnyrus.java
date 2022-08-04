package com.vetpetmon.wyrmsofnyrus;

import com.google.common.collect.Lists;
import com.vetpetmon.wyrmsofnyrus.block.BlockHiveCreepedGrass;
import com.vetpetmon.wyrmsofnyrus.block.IWyrmBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.client.model.obj.OBJLoader;

import java.io.File;
import java.util.List;

import static com.vetpetmon.wyrmsofnyrus.client.blocks.biomeColor.*;

public class ClientProxywyrmsofnyrus implements IProxywyrmsofnyrus {

	private static List<Block> coloredBlocks = Lists.newArrayList();;

	@Override
	public void init(FMLInitializationEvent event) {
		registerColors();
	}

	@Override //Did i really forget to override registerColors() which caused this whole mess to begin with?
	public void registerColors() {
		for (final Block block : coloredBlocks) {
			final IWyrmBlocks blocks = (IWyrmBlocks) block;
			if (blocks.getBlockColor() != null) {
				Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(blocks.getBlockColor(), new Block[]{block});
			}
			if (blocks.getItemColor() != null) {
				Minecraft.getMinecraft().getItemColors().registerItemColorHandler(blocks.getItemColor(), new Block[]{block});
			}
		}
		blockColorInt();
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
