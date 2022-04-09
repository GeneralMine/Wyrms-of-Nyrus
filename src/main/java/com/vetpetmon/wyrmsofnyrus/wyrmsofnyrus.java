package com.vetpetmon.wyrmsofnyrus;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import com.vetpetmon.wyrmsofnyrus.entity.WyrmRegister;
import net.minecraftforge.event.RegistryEvent;

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

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    }
}

