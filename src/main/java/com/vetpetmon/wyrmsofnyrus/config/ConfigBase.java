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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.vetpetmon.synapselib.util.CFG.createDirectory;
import static com.vetpetmon.wyrmsofnyrus.config.Invasion.isEXCANON;
import static com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus.proxy;

public class ConfigBase {

    // Reorganization and code optimizations done by Byte and [NAME RETRACTED]
    // ConfigLib is now officially part of SynapseLib, you're welcome. -Byte

    // TODO: Make this create folders of presets, with built-in presets defined by JSON files. Set the default to Death World on 0.2.6, preserve original defaults and give in-game config GUI via Forge's built-in configuration system instead of making users have to manually edit configuration files and restart the game. Leave Client configs out of presets. -[NAME REDACTED]
    private static final String ConfigDirectory = proxy.getDataDir().getPath() + "/config/WyrmsOfNyrus/" ;
    private static final Configuration
            general = createDirectory("general", ConfigDirectory),
            wyrms = createDirectory("wyrms", ConfigDirectory),
            debug = createDirectory("debug", ConfigDirectory),
            evo = createDirectory("evolution", ConfigDirectory),
            world = createDirectory("world", ConfigDirectory),
            invasion = createDirectory("invasion", ConfigDirectory),
            clientside = createDirectory("client", ConfigDirectory);
    private static final Configuration[] configs = {general, wyrms, debug, evo, world, invasion, clientside};


    // Specific for WoN.
    public static void reloadConfig() {

        //firstTime();

        for (Configuration i:configs) i.load();
        AI.loadFromConfig(general);
        Radiogenetics.loadFromConfig(general);
        wyrmStats.loadFromConfig(wyrms);
        Debug.loadFromConfig(debug);
        Evo.loadFromConfig(evo);
        WorldConfig.loadFromConfig(world);
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

    /**
     * Creates our mod's first time dialogue message, which asks the user what config presets they wish to use.
     * Doesn't work yet, needs to pause FML's loading process to avoid nullPointerExceptions.
     */
    public static void firstTime() {
        JFrame jf = new JFrame();
        JDialog jd = new JDialog(jf);
        jd.setLayout(new FlowLayout());
        final boolean[] choiceSelected = {false};

        jd.setBounds(500, 300, 500, 100);
        JLabel jl = new JLabel("This appears to be your first time with Wyrms of Nyrus, or you have missing configurations. \n What difficulty preset would you like to use?");

        JButton norm = new JButton("Regular");
        JButton dw = new JButton("Death World");

        norm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choiceSelected[0] = true;
                jd.setVisible(false);
            }
        });
        dw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choiceSelected[0] = true;
                jd.setVisible(false);
            }
        });

        jd.add(jl); jd.add(norm);jd.add(dw);
        jd.setVisible(true);

        while(!choiceSelected[0]){

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
