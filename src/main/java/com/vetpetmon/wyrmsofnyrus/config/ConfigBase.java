package com.vetpetmon.wyrmsofnyrus.config;

/*
    This handy file is the configuration library that WoN uses. I may make it a separate library in the future
    for other mod developers to use to quickly create configuration files, but it is easiest for me to make this
    embedded in WoN itself for full customization.
*/

import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;

import static com.vetpetmon.wyrmsofnyrus.config.Invasion.isEXCANON;
import static com.vetpetmon.wyrmsofnyrus.synapselib.CFG.*;
import static com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus.proxy;

public class ConfigBase {

    // Reorganization and code optimizations done by Byte and [NAME RETRACTED]
    // ConfigLib is now officially part of SynapseLib, you're welcome. -Byte
    private static final String ConfigDirectory = proxy.getDataDir().getPath() + "/config/WyrmsOfNyrus/" ;
    private static final Configuration
            general = createDirectory("general", ConfigDirectory),
            wyrms = createDirectory("wyrms", ConfigDirectory),
            debug = createDirectory("debug", ConfigDirectory),
            evo = createDirectory("evolution", ConfigDirectory),
            invasion = createDirectory("invasion", ConfigDirectory),
            clientside = createDirectory("client", ConfigDirectory);
    private static final Configuration[] configs = {general, wyrms, debug, evo, invasion, clientside};


    // Specific for WoN.
    public static void reloadConfig() {

        for (Configuration i:configs) i.load();
        AI.loadFromConfig(general);
        Radiogenetics.loadFromConfig(general);
        wyrmStats.loadFromConfig(wyrms);
        Debug.loadFromConfig(debug);
        Evo.loadFromConfig(evo);
        Invasion.loadFromConfig(invasion);
        Client.loadFromConfig(clientside);

        for (Configuration i:configs) i.save();
        wyrmsofnyrus.logger.info("Configuration loaded or changed.");
    }

    public static void reloadClient() {
        clientside.load();
        Client.loadFromConfig(clientside);
        clientside.save();
        wyrmsofnyrus.logger.info("Client's configuration reloaded.");
    }

    public static void setCanon() {
        if (isEXCANON()) {
            wyrmsofnyrus.logger.info("We are in EX-tended canon mode!");
            Invasion.invasionEnabled = true;
            Invasion.probingEnabled = true;
            Invasion.creepEnabled = true;
            Invasion.creepSpreadRate = 5;
            Invasion.creepSpreadPoints = 0.05F;
            AI.attackMobs = true;
            AI.attackAnimals = true;
            AI.savageAIMode = true;
            AI.performanceAIMode = false;
            Radiogenetics.immuneToCacti = true;
            Radiogenetics.immuneToFalling = true;
        }
    }

    @Mod.EventBusSubscriber(modid = wyrmsofnyrus.MODID)
    private static class EventHandler
    {
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(wyrmsofnyrus.MODID))
            {
                ConfigManager.sync(wyrmsofnyrus.MODID, Config.Type.INSTANCE);
                wyrmsofnyrus.logger.info("Configuration loaded or changed.");
            }
        }
    }

}
