package com.vetpetmon.wyrmsofnyrus.config;

/*
    This handy file is the configuration library that WoN uses. I may make it a separate library in the future
    for other mod developers to use to quickly create configuration files, but it is easiest for me to make this
    embedded in WoN itself for full customization.
*/

import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraftforge.common.config.Configuration;

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
    private static final int defaultConfig = 1; // 0 for Classic, 1 for Death World, 2 for Dark Forest.
    private static String[] factoryConfigs = {"Classic","Death World","Dark Forest"};
    public static int selectedPreset;
    private static final String ConfigDirectory = proxy.getDataDir().getPath() + "/config/WyrmsOfNyrus/" ;
    private static final Configuration
            general = createDirectory("general", ConfigDirectory),
            wyrms = createDirectory("wyrms", ConfigDirectory),
            debug = createDirectory("debug", ConfigDirectory),
            evo = createDirectory("evolution", ConfigDirectory),
            world = createDirectory("world", ConfigDirectory),
            invasion = createDirectory("invasion", ConfigDirectory);
    private static final Configuration[] configs = {general, wyrms, debug, evo, world, invasion};

    public static void setConfigPreset() {
        selectedPreset = Client.configPreset;
        if (Client.configPreset < 0) selectedPreset = defaultConfig;
        if (selectedPreset >= 4) wyrmsofnyrus.logger.info("Using or creating user-defined config preset folder, configs are in folder: " + selectedPreset);
        else wyrmsofnyrus.logger.info("Using factory preset: " + factoryConfigs[selectedPreset]);
        wyrmsofnyrus.logger.info("Selected preset's ID: " + selectedPreset);
    }

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

        for (Configuration i:configs) i.save();
        wyrmsofnyrus.logger.info("Configuration loaded or changed.");
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
    public static void firstTimeDialogue() {
        JFrame jf = new JFrame();
        JDialog jd = new JDialog(jf);
        jd.setLayout(new FlowLayout());

        jd.setBounds(500, 300, 500, 100);
        JLabel jl = new JLabel("This appears to be your first time with Wyrms of Nyrus, or you have missing/outdated factory configurations. \n If your preset choice is unset or invalid, it will default back to this preset: "
                + factoryConfigs[defaultConfig]
                + "\nYou can safely disregard this message. If you wish to change the preset, there is an in-game"
        );

        JButton norm = new JButton("Yes");

        norm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jd.setVisible(false);
            }
        });

        jd.add(jl); jd.add(norm);
        jd.setVisible(true);
    }

}
