package com.vetpetmon.wyrmsofnyrus;

import com.vetpetmon.wyrmsofnyrus.client.renderer.entity.HexePodGeoRenderer;
import com.vetpetmon.wyrmsofnyrus.client.renderer.entity.WyrmlingGeoRenderer;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityHexePod;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmling;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;
import com.vetpetmon.wyrmsofnyrus.entity.WyrmRegister;
import software.bernie.geckolib3.GeckoLib;

@Mod(modid = wyrmsofnyrus.MODID, name = wyrmsofnyrus.NAME, version = wyrmsofnyrus.VERSION)
public class wyrmsofnyrus
{
    public static final String MODID = "wyrmsofnyrus";
    public static final String NAME = "Wyrms of Nyrus";
    public static final String VERSION = "0.1.1";

    @Mod.Instance(MODID)
    public static wyrmsofnyrus instance;

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        WyrmRegister.register();
    }

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void registerRenderers(FMLPreInitializationEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityHexePod.class, HexePodGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWyrmling.class, WyrmlingGeoRenderer::new);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    }

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void registerReplacedRenderers(FMLInitializationEvent event)
    {
            GeckoLib.initialize();
    }
}

