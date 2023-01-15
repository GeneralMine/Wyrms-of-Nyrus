package com.vetpetmon.wyrmsofnyrus;

import com.vetpetmon.synapselib.rendering.IHasModel;
import com.vetpetmon.wyrmsofnyrus.block.AllBlocks;
import com.vetpetmon.wyrmsofnyrus.command.CommandWyrmInvasionCommand;
import com.vetpetmon.wyrmsofnyrus.command.CommandWyrmsTest;
import com.vetpetmon.wyrmsofnyrus.compat.hbm;
import com.vetpetmon.wyrmsofnyrus.compat.srp;
import com.vetpetmon.wyrmsofnyrus.compat.synlib;
import com.vetpetmon.wyrmsofnyrus.config.ConfigBase;
import com.vetpetmon.wyrmsofnyrus.creativetab.TabWyrms;
import com.vetpetmon.wyrmsofnyrus.entity.WyrmRegister;
import com.vetpetmon.wyrmsofnyrus.evo.evoPoints;
import com.vetpetmon.wyrmsofnyrus.item.AllItems;
import com.vetpetmon.wyrmsofnyrus.synapselib.NetworkMessages.messageReg;
import com.vetpetmon.wyrmsofnyrus.synapselib.libVars;
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

import static com.vetpetmon.wyrmsofnyrus.client.renderEngine.renderEngine;

@Mod(modid = wyrmsofnyrus.MODID, name = wyrmsofnyrus.NAME, version = wyrmsofnyrus.VERSION, dependencies = "required-after:geckolib3;required-after:synlib;")
public class wyrmsofnyrus {
    public static final String MODID = libVars.ModID;
    public static final String NAME = libVars.ModName;
    public static final String VERSION = libVars.ModVersion;
    public static CreativeTabs wyrmTabs = new TabWyrms(CreativeTabs.getNextID(), "wyrms");
    public static SimpleNetworkWrapper PACKET_HANDLER = NetworkRegistry.INSTANCE.newSimpleChannel("wyrmsofnyrus:a");
    @SidedProxy(clientSide = "com.vetpetmon.wyrmsofnyrus.ClientProxywyrmsofnyrus", serverSide = "com.vetpetmon.wyrmsofnyrus.ServerProxywyrmsofnyrus")
    public static IProxywyrmsofnyrus proxy;

    @Mod.Instance(MODID)
    public static wyrmsofnyrus instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if(logger == null) logger = event.getModLog();
        synlib.init();
        ConfigBase.setConfigPreset(); // Preload configuration settings, this grabs current presets. If a custom preset is selected, it will check and generate the files and directory if it exists.
        ConfigBase.checkFactorySettings(); // also runs firstTimeDialogue() if the checks fail
        ConfigBase.activatePreset(); // NOW activate configurations for real

        hbm.compatInit();
        srp.compatInit();

        //threading.checkThreads(); //We know this works

        messageReg.init();

        MinecraftForge.EVENT_BUS.register(this);
        proxy.preInit(event);
        this.addNetworkMessage(wyrmVariables.WorldSavedDataSyncMessageHandler.class,
                wyrmVariables.WorldSavedDataSyncMessage.class, Side.SERVER, Side.CLIENT);
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.player.world.isRemote) {
            WorldSavedData mapdata = wyrmVariables.MapVariables.get(event.player.world);
            WorldSavedData worlddata = wyrmVariables.WorldVariables.get(event.player.world);
            wyrmsofnyrus.PACKET_HANDLER.sendTo(new wyrmVariables.WorldSavedDataSyncMessage(0, mapdata),
                    (EntityPlayerMP) event.player);
            wyrmsofnyrus.PACKET_HANDLER.sendTo(new wyrmVariables.WorldSavedDataSyncMessage(1, worlddata),
                    (EntityPlayerMP) event.player);
        }
    }

    @SubscribeEvent
    public void onPlayerChangedDimension(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!event.player.world.isRemote) {
            WorldSavedData worlddata = wyrmVariables.WorldVariables.get(event.player.world);
            wyrmsofnyrus.PACKET_HANDLER.sendTo(new wyrmVariables.WorldSavedDataSyncMessage(1, worlddata),
                    (EntityPlayerMP) event.player);
        }
    }

    private int messageID = 0;
    public <T extends IMessage, V extends IMessage> void addNetworkMessage(Class<? extends IMessageHandler<T, V>> handler, Class<T> messageClass,
                                                                           Side... sides) {
        for (Side side : sides)
            wyrmsofnyrus.PACKET_HANDLER.registerMessage(handler, messageClass, messageID, side);
        messageID++;
    }


    @SubscribeEvent
    public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(wyrmsofnyrus.MODID)) {
            ConfigManager.sync(wyrmsofnyrus.MODID, Config.Type.INSTANCE);
            wyrmsofnyrus.logger.warn("Configuration settings were changed. If you didn't switch config presets, you may safely ignore this warning. If you did, restart your game.");
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
        evoPoints.minimum();
        if(srp.isEnabled()) srp.compatPostInt();
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        proxy.serverLoad(event);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandWyrmInvasionCommand.CommandHandler());
        event.registerServerCommand(new CommandWyrmsTest.CommandHandler());
    }

    public static ResourceLocation getResource(final String name)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, name);
    }

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
