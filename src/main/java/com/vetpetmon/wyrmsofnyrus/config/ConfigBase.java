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
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.vetpetmon.synapselib.util.CFG.createDirectory;
import static com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus.proxy;

public class ConfigBase {

    // Reorganization and code optimizations done by Byte and [NAME REDACTED]
    // Might move more of this nonsense to SynapseLib

    private static final int defaultConfig = 1; // 0 for Classic, 1 for Death World, 2 for Dark Forest.
    private static final String[] factoryConfigs = {"Classic","Death World","Dark Forest"};
    public static int selectedPreset, presetsVersion = 7, tempPreset;
    private static String ConfigDirectory = proxy.getDataDir().getPath() + "/config/WyrmsOfNyrus/" ;
    private static Configuration general, wyrms, debug, evo, world, invasion, manifest;

    public static void setConfigPreset() {
        selectedPreset = Client.configPreset;
        if (Client.configPreset < 0) selectedPreset = defaultConfig;
        if (selectedPreset > 3) {
            wyrmsofnyrus.logger.info("Using or creating user-defined config preset folder, configs are in folder: " + selectedPreset);
            customConfigGen();
        }
        else wyrmsofnyrus.logger.info("Using factory preset: " + selectedPreset);
    }

    public static int presetInts(int normal, int deathWorld, int darkForest, int id) {
        switch(id) {
            case(1): return deathWorld;
            case(2): return darkForest;
            default: return normal;
        }
    }
    public static float presetFloats(float normal, float deathWorld, float darkForest, int id) {
        switch(id) {
            case(1): return deathWorld;
            case(2): return darkForest;
            default: return normal;
        }
    }

    public static boolean presetBools(boolean normal, boolean deathWorld, boolean darkForest, int id) {
        switch(id) {
            case(1): return deathWorld;
            case(2): return darkForest;
            default: return normal;
        }
    }

    public static void activatePreset() {
        wyrmsofnyrus.logger.info("Active preset: " + selectedPreset);
        createDirectories(selectedPreset); //Do this to avoid NullPointer Errors
        reloadConfig(selectedPreset);
    }

    // Specific for WoN.
    public static void reloadConfig(int id) {
        Configuration[] configs = {general, wyrms, debug, evo, world, invasion, manifest};

        for (Configuration i:configs) i.load();
        Debug.loadFromConfig(debug); // load this first so that debug config messages can properly function
        ConfigManifest.createManifest(manifest);
        AI.loadFromConfig(general);
        Radiogenetics.loadFromConfig(general, id);
        wyrmStats.loadFromConfig(wyrms, id);
        Evo.loadFromConfig(evo, id);
        WorldConfig.loadFromConfig(world, id);
        Invasion.loadFromConfig(invasion, id);

        for (Configuration i:configs) i.save();
    }

    /**
     * Creates our mod's first time or update configs dialogue message.
     */
    public static void firstTimeDialogue() {
        JFrame jf = new JFrame();
        JDialog jd = new JDialog(jf);
        jd.setLayout(new FlowLayout());

        jd.setBounds(600, 500, 800, 175);
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

        jd.add(jl);jd.add(jl2);jd.add(jl3);jd.add(norm);
        jd.setVisible(true);
    }

    public static void checkFactorySettings() {
        boolean oneConfigInvalidated = false;
        for (int i = 0; i< factoryConfigs.length;i++) {
            wyrmsofnyrus.logger.info("Checking factory settings for " + i);
            ConfigDirectory = proxy.getDataDir().getPath() + "/config/WyrmsOfNyrus/" + i+ "/";
            if (!Files.exists(Paths.get(ConfigDirectory))) {
                wyrmsofnyrus.logger.info("Factory preset \"" + i + "\" does not exist, making it in the following directory: " + ConfigDirectory);
                oneConfigInvalidated = true;
            }
            createDirectories(i); //Properly directs the next bit of code to check manifest
            reloadConfig(i);
            if (ConfigManifest.compareVersion()) {
                wyrmsofnyrus.logger.warn("Factory preset \"" + i + "\" has version mismatch.");
                File folder = new File(ConfigDirectory);
                File[] files = folder.listFiles();
                if(files!=null) {
                    for(File f: files) f.delete();
                    folder.delete();
                }
                wyrmsofnyrus.logger.info("Attempted to delete directory and update it.");
                createDirectories(i);
                reloadConfig(i);
            }
        }
        if (oneConfigInvalidated && !GraphicsEnvironment.isHeadless()) firstTimeDialogue(); //Check if the environment is not a terminal before calling to create a new window, as terminal interfaces aren't capable of bringing up graphical interfaces like windows and will otherwise crash. Dedicated server fix.
    }
    public static void customConfigGen() {
        if (!Files.exists(Paths.get(proxy.getDataDir().getPath() + "/config/WyrmsOfNyrus/" + selectedPreset +"/"))) {
            ConfigDirectory = proxy.getDataDir().getPath() + "/config/WyrmsOfNyrus/" + selectedPreset + "/";
            wyrmsofnyrus.logger.info("Custom preset \"" + selectedPreset + "\" does not exist, making it in the following directory: " + ConfigDirectory);
            wyrmsofnyrus.logger.info("This is a custom preset. Once this generates, you are free to edit it as you wish.");
            createDirectories(selectedPreset);
            reloadConfig(selectedPreset);
        }
    }

    private static void createDirectories(int id) {
        ConfigDirectory = proxy.getDataDir().getPath() + "/config/WyrmsOfNyrus/" + id +"/";
        general = createDirectory("general", ConfigDirectory);
        wyrms = createDirectory("wyrms", ConfigDirectory);
        debug = createDirectory("debug", ConfigDirectory);
        evo = createDirectory("evolution", ConfigDirectory);
        world = createDirectory("world", ConfigDirectory);
        invasion = createDirectory("invasion", ConfigDirectory);
        manifest = createDirectory("manifest", ConfigDirectory);
    }

}
