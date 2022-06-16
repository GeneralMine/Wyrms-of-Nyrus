package com.vetpetmon.wyrmsofnyrus;

import com.vetpetmon.wyrmsofnyrus.config.ConfigLib;
import com.vetpetmon.wyrmsofnyrus.entity.WyrmRegister;

import com.vetpetmon.wyrmsofnyrus.synapselib.NetworkMessages.messageReg;
import com.vetpetmon.wyrmsofnyrus.synapselib.threading;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.event.RegistryEvent;

import net.minecraft.world.biome.Biome;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.GeckoLib;

import java.util.function.Supplier;
import org.apache.logging.log4j.Logger;

import static com.vetpetmon.wyrmsofnyrus.client.renderEngine.renderEngine;

@Mod(modid = wyrmsofnyrus.MODID, name = wyrmsofnyrus.NAME, version = wyrmsofnyrus.VERSION)
public class wyrmsofnyrus {
    public static final String MODID = "wyrmsofnyrus";
    public static final String NAME = "Wyrms of Nyrus";
    public static final String VERSION = "0.1.31";

    public AutoReg elements = new AutoReg();

    public static final SimpleNetworkWrapper PACKET_HANDLER = NetworkRegistry.INSTANCE.newSimpleChannel("wyrmsofnyrus:a");
    @SidedProxy(clientSide = "com.vetpetmon.wyrmsofnyrus.ClientProxywyrmsofnyrus", serverSide = "com.vetpetmon.wyrmsofnyrus.ServerProxywyrmsofnyrus")
    public static IProxywyrmsofnyrus proxy;

    @Mod.Instance(MODID)
    public static wyrmsofnyrus instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if(logger == null) logger = event.getModLog();
        wyrmsofnyrus.logger.info(
                "If you experience a glitch anywhere, please ping any Vetpetmon Labs team member in the community discord with the log and description, along if instructions on how to replicate the issue, if needed.\n\n" +
                "Do beware that there may be balancing issues in any development build.");
        wyrmsofnyrus.logger.warn("We hope you are aware that the Wyrms are EXTREMELY destructive to your worlds.\n\n" +
                "By downloading and installing this mod into your instance of Minecraft, you agree that you and your world gets invaded, overran by alien flora, eaten by aliens, probed when you least expect it, blah blah blah...\n" +
                "You can make this mod less destructive by checking the config file after the game loads. You WILL need to restart MC for changes to be applied.");

        threading.checkThreads();

        ConfigLib.reloadConfig();
        ConfigLib.setCanon();

        messageReg.init();

        MinecraftForge.EVENT_BUS.register(this);
        GameRegistry.registerWorldGenerator(elements, 5);
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new AutoReg.GuiHandler());
        elements.preInit(event);
        MinecraftForge.EVENT_BUS.register(elements);
        elements.getElements().forEach(element -> element.preInit(event));
        proxy.preInit(event);

        WyrmRegister.register();
    }


    @SubscribeEvent
    public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(wyrmsofnyrus.MODID)) {
            ConfigManager.sync(wyrmsofnyrus.MODID, Config.Type.INSTANCE);
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        elements.getElements().forEach(element -> element.init(event));
        proxy.init(event);
        SoundRegistry.RegisterSounds();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        elements.getElements().forEach(element -> element.serverLoad(event));
        proxy.serverLoad(event);
    }

    public static ResourceLocation getResource(final String name)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, name);
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(elements.getBlocks().stream().map(Supplier::get).toArray(Block[]::new));
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(elements.getItems().stream().map(Supplier::get).toArray(Item[]::new));
    }

    @SubscribeEvent
    public void registerBiomes(RegistryEvent.Register<Biome> event) {
        event.getRegistry().registerAll(elements.getBiomes().stream().map(Supplier::get).toArray(Biome[]::new));
    }

    @SubscribeEvent
    public void registerPotions(RegistryEvent.Register<Potion> event) {
        event.getRegistry().registerAll(elements.getPotions().stream().map(Supplier::get).toArray(Potion[]::new));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void registerModels(ModelRegistryEvent event) {
        elements.getElements().forEach(element -> element.registerModels(event));
    }

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void registerRenderers(FMLPreInitializationEvent event)
    {
        renderEngine();
    }

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void registerReplacedRenderers(FMLInitializationEvent event)
    {
        GeckoLib.initialize();
    }

    static {
        FluidRegistry.enableUniversalBucket();
    }
}
