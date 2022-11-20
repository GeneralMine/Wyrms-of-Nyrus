package com.vetpetmon.wyrmsofnyrus;

import com.vetpetmon.wyrmsofnyrus.block.BlockHiveCreepedGrass;
import com.vetpetmon.wyrmsofnyrus.compat.hbm;
import com.vetpetmon.wyrmsofnyrus.entity.WyrmRegister;
import com.vetpetmon.wyrmsofnyrus.evo.evoPoints;
import com.vetpetmon.wyrmsofnyrus.synapselib.*;
import com.vetpetmon.wyrmsofnyrus.synapselib.NetworkMessages.messageReg;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.util.function.Supplier;

import static com.vetpetmon.wyrmsofnyrus.client.renderEngine.renderEngine;
import static com.vetpetmon.wyrmsofnyrus.config.ConfigBase.*;

@Mod(modid = wyrmsofnyrus.MODID, name = wyrmsofnyrus.NAME, version = wyrmsofnyrus.VERSION, dependencies = "required-after:geckolib3")
public class wyrmsofnyrus {
    public static final String MODID = libVars.ModID;
    public static final String NAME = libVars.ModName;
    public static final String VERSION = libVars.ModVersion;

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
        wyrmsofnyrus.logger.info(synapseLib.initializeMSG());
        hbm.compatInit();

        //threading.checkThreads(); //We know this works

        reloadConfig();
        setCanon();

        messageReg.init();

        MinecraftForge.EVENT_BUS.register(this);
        GameRegistry.registerWorldGenerator(elements, 5);
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new AutoReg.GuiHandler());
        elements.preInit(event);
        MinecraftForge.EVENT_BUS.register(elements);
        elements.getElements().forEach(element -> element.preInit(event));
        proxy.preInit(event);
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

        WyrmRegister.register();
        //MixinBootstrap.init();
        //Mixins.addConfiguration("mixins.wyrmsofnyrus.compat.json");
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void BlockColorHandlerInit(ColorHandlerEvent.Block event){
        BlockColors blockColors = event.getBlockColors();
        blockColors.registerBlockColorHandler((state, worldIn, pos, tintIndex) ->
                        worldIn != null && pos != null ? BiomeColorHelper.getGrassColorAtPos(worldIn, pos) : ColorizerGrass.getGrassColor(0.5D, 1.0D),
                BlockHiveCreepedGrass.block);
    }
    @SubscribeEvent
    @SuppressWarnings("deprecation")
    @SideOnly(Side.CLIENT)
    public void ItemColorHandlerInit(ColorHandlerEvent.Item event){
        ItemColors itemColors = event.getItemColors();
        itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
                BlockHiveCreepedGrass.block);
    }


    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        evoPoints.minimum();
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
