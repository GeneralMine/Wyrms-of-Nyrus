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
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.vetpetmon.synapselib.util.CFG.createDirectory;
import static com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus.proxy;

public class ConfigBase {

    // Reorganization and code optimizations done by Byte and [NAME RETRACTED]
    // ConfigLib is now officially part of SynapseLib, you're welcome. -Byte

    // TODO: Make this create folders of presets, with built-in presets defined by JSON files. Set the default to Death World on 0.2.6, preserve original defaults and give in-game config GUI via Forge's built-in configuration system instead of making users have to manually edit configuration files and restart the game. Leave Client configs out of presets. -[NAME REDACTED]
    private static final int defaultConfig = 1; // 0 for Classic, 1 for Death World, 2 for Dark Forest.
    private static String[] factoryConfigs = {"Classic","Death World","Dark Forest"};
    public static int selectedPreset;
    private static String ConfigDirectory = proxy.getDataDir().getPath() + "/config/WyrmsOfNyrus/" ;
    private static Configuration general, wyrms, debug, evo, world, invasion;

    public static void setConfigPreset() {
        selectedPreset = Client.configPreset;
        if (Client.configPreset < 0) selectedPreset = defaultConfig;
        if (selectedPreset >= 4) wyrmsofnyrus.logger.info("Using or creating user-defined config preset folder, configs are in folder: " + selectedPreset);
        else wyrmsofnyrus.logger.info("Using factory preset: " + factoryConfigs[selectedPreset]);
        wyrmsofnyrus.logger.info("Selected preset's ID: " + selectedPreset);
    }

    public static void activatePreset() {
        ConfigDirectory = proxy.getDataDir().getPath() + "/config/WyrmsOfNyrus/" + selectedPreset +"/";
        wyrmsofnyrus.logger.info("Active preset: " + selectedPreset);
        wyrmsofnyrus.logger.info("Opening preset file at: " + ConfigDirectory);
        createDirectories(); //Do this to avoid NullPointer Errors
        reloadConfig();
        wyrmsofnyrus.logger.info("Test variable (creepSpreadRate): " + Invasion.creepSpreadRate);
    }

    // Specific for WoN.
    public static void reloadConfig() {
        Configuration[] configs = {general, wyrms, debug, evo, world, invasion};

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

    /**
     * Creates our mod's first time or update configs dialogue message.
     */
    public static void firstTimeDialogue() {
        JFrame jf = new JFrame();
        JDialog jd = new JDialog(jf);
        jd.setLayout(new FlowLayout());

        jd.setBounds(600, 500, 600, 300);
        JLabel jl = new JLabel("This appears to be your first time with Wyrms of Nyrus, or you have missing/outdated factory configurations.");
        JLabel jl2 = new JLabel("You can safely disregard this message. If you wish to change the preset, there is an in-game option to do so.");
        JLabel jl3 = new JLabel("If your preset choice is unset or invalid, it will default back to this preset: "+ factoryConfigs[defaultConfig]);
        JButton norm = new JButton("Yes");

        norm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jd.setVisible(false);
            }
        });

        jd.add(jl);jd.add(jl2);jd.add(jl3); jd.add(norm);
        jd.setVisible(true);
    }

    public static void checkFactorySettings() {
        boolean oneConfigInvalidated = false;
        for (int i = 0; i< factoryConfigs.length;i++) {
            wyrmsofnyrus.logger.info("Checking factory settings for " + i);
            if (!Files.exists(Paths.get(proxy.getDataDir().getPath() + "/config/WyrmsOfNyrus/" + i +"/"))) {
                ConfigDirectory = proxy.getDataDir().getPath() + "/config/WyrmsOfNyrus/" + i+ "/";
                wyrmsofnyrus.logger.info("Factory preset \"" + i + "\" does not exist, making it in the following directory: " + ConfigDirectory);
                createDirectories();
                reloadConfig();
                oneConfigInvalidated = true;
            }
        }
        if (oneConfigInvalidated) firstTimeDialogue();
    }

    private static void createDirectories() {
        general = createDirectory("general", ConfigDirectory);
        wyrms = createDirectory("wyrms", ConfigDirectory);
        debug = createDirectory("debug", ConfigDirectory);
        evo = createDirectory("evolution", ConfigDirectory);
        world = createDirectory("world", ConfigDirectory);
        invasion = createDirectory("invasion", ConfigDirectory);
    }

}
