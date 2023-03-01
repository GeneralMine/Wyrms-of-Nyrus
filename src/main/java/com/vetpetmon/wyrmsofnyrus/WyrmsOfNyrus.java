package com.vetpetmon.wyrmsofnyrus;

import com.vetpetmon.synapselib.rendering.IHasModel;
import com.vetpetmon.wyrmsofnyrus.advancements.Advancements;
import com.vetpetmon.wyrmsofnyrus.block.AllBlocks;
import com.vetpetmon.wyrmsofnyrus.command.CommandWyrmInvasionCommand;
import com.vetpetmon.wyrmsofnyrus.command.CommandWyrmsTest;
import com.vetpetmon.wyrmsofnyrus.compat.*;
import com.vetpetmon.wyrmsofnyrus.config.ConfigBase;
import com.vetpetmon.wyrmsofnyrus.creativetab.TabWyrms;
import com.vetpetmon.wyrmsofnyrus.entity.WyrmRegister;
import com.vetpetmon.wyrmsofnyrus.evo.EvoPoints;
import com.vetpetmon.wyrmsofnyrus.invasion.InvasionScheduler;
import com.vetpetmon.wyrmsofnyrus.item.AllItems;
import com.vetpetmon.wyrmsofnyrus.locallib.networkmessages.MessageReg;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraft.world.storage.WorldSavedData;
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
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import static com.vetpetmon.wyrmsofnyrus.client.RenderEngine.renderEngine;

@Mod(modid = WyrmsOfNyrus.MODID, name = WyrmsOfNyrus.NAME, version = WyrmsOfNyrus.VERSION, dependencies = "required-after:geckolib3;required-after:synlib;")
public class WyrmsOfNyrus {
    public static final String MODID = Constants.ModID;
    public static final String NAME = Constants.ModName;
    public static final String VERSION = Constants.ModVersion;
    public static CreativeTabs wyrmTabs = new TabWyrms(CreativeTabs.getNextID(), "wyrms");
    public static SimpleNetworkWrapper PACKET_HANDLER = NetworkRegistry.INSTANCE.newSimpleChannel("wyrmsofnyrus:a");
    @SidedProxy(clientSide = "com.vetpetmon.wyrmsofnyrus.ClientProxyWyrmsOfNyrus", serverSide = "com.vetpetmon.wyrmsofnyrus.ServerProxyWyrmsOfNyrus")
    public static IProxyWyrmsOfNyrus proxy;

    @Mod.Instance(MODID)
    public static WyrmsOfNyrus instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if(logger == null) logger = event.getModLog();
        SynLib.init();
        ConfigBase.setConfigPreset(); // Preload configuration settings, this grabs current presets. If a custom preset is selected, it will check and generate the files and directory if it exists.
        ConfigBase.checkFactorySettings(); // also runs firstTimeDialogue() if the checks fail
        ConfigBase.activatePreset(); // NOW activate configurations for real

        HBM.compatInit();
        SRP.compatInit();

        //threading.checkThreads(); //We know this works

        MessageReg.init();

        MinecraftForge.EVENT_BUS.register(this);
        proxy.preInit(event);
        this.addNetworkMessage(WyrmVariables.WorldSavedDataSyncMessageHandler.class,
                WyrmVariables.WorldSavedDataSyncMessage.class, Side.SERVER, Side.CLIENT);
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.player.world.isRemote) {
            WorldSavedData mapdata = WyrmVariables.MapVariables.get(event.player.world);
            WorldSavedData worlddata = WyrmVariables.WorldVariables.get(event.player.world);
            WyrmsOfNyrus.PACKET_HANDLER.sendTo(new WyrmVariables.WorldSavedDataSyncMessage(0, mapdata),
                    (EntityPlayerMP) event.player);
            WyrmsOfNyrus.PACKET_HANDLER.sendTo(new WyrmVariables.WorldSavedDataSyncMessage(1, worlddata),
                    (EntityPlayerMP) event.player);
        }
    }

    @SubscribeEvent
    public void onPlayerChangedDimension(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!event.player.world.isRemote) {
            WorldSavedData worlddata = WyrmVariables.WorldVariables.get(event.player.world);
            WyrmsOfNyrus.PACKET_HANDLER.sendTo(new WyrmVariables.WorldSavedDataSyncMessage(1, worlddata),
                    (EntityPlayerMP) event.player);
        }
    }

    private int messageID = 0;
    public <T extends IMessage, V extends IMessage> void addNetworkMessage(Class<? extends IMessageHandler<T, V>> handler, Class<T> messageClass,
                                                                           Side... sides) {
        for (Side side : sides)
            WyrmsOfNyrus.PACKET_HANDLER.registerMessage(handler, messageClass, messageID, side);
        messageID++;
    }


    @SubscribeEvent
    public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(WyrmsOfNyrus.MODID)) {
            ConfigManager.sync(WyrmsOfNyrus.MODID, Config.Type.INSTANCE);
            WyrmsOfNyrus.logger.warn("Configuration settings were changed. If you didn't switch config presets, you may safely ignore this warning. If you did, restart your game.");
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        SoundRegistry.RegisterSounds();

        WyrmRegister.register();
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void BlockColorHandlerInit(ColorHandlerEvent.Block event){
        BlockColors blockColors = event.getBlockColors();
        blockColors.registerBlockColorHandler((state, worldIn, pos, tintIndex) ->
                        worldIn != null && pos != null ? BiomeColorHelper.getGrassColorAtPos(worldIn, pos) : ColorizerGrass.getGrassColor(0.5D, 1.0D),
                AllBlocks.creepedgrass);
    }
    @SubscribeEvent
    @SuppressWarnings("deprecation")
    @SideOnly(Side.CLIENT)
    public void ItemColorHandlerInit(ColorHandlerEvent.Item event){
        ItemColors itemColors = event.getItemColors();
        itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
                AllBlocks.creepedgrass);
    }


    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        EvoPoints.minimum();
        if(SRP.isEnabled()) SRP.compatPostInt();
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        proxy.serverLoad(event);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandWyrmInvasionCommand());
        event.registerServerCommand(new CommandWyrmsTest());
        InvasionScheduler.getScheduler(event.getServer().getEntityWorld());
        Advancements.init(event.getServer());
    }

    public static ResourceLocation getResource(final String name)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, name);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        for (Item item : AllItems.ALL_ITEMS) {
            if (item instanceof IHasModel) {
                ((IHasModel) item).registerModels();
            }
        }

        for (Block block : AllBlocks.ALL_BLOCKS) {
            if (block instanceof IHasModel) {
                ((IHasModel) block).registerModels();
            }
        }
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(AllBlocks.ALL_BLOCKS.toArray(new Block[0]));
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(AllItems.ALL_ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public void registerBiomes(RegistryEvent.Register<Biome> event) {
    }

    @SubscribeEvent
    public void registerPotions(RegistryEvent.Register<Potion> event) {
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void registerModels(ModelRegistryEvent event) {
        for (Item item : AllItems.ALL_ITEMS) {
            if (item instanceof IHasModel) ((IHasModel) item).registerModels();
        }
        for (Block block : AllBlocks.ALL_BLOCKS) {
            if (block instanceof IHasModel) ((IHasModel) block).registerModels();
        }
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
