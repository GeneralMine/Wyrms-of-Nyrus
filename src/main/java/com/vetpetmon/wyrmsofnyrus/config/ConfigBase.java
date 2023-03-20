package com.vetpetmon.wyrmsofnyrus.config;

/*
    This handy file is the configuration library that WoN uses. I may make it a separate library in the future
    for other mod developers to use to quickly create configuration files, but it is easiest for me to make this
    embedded in WoN itself for full customization.
*/

import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import net.minecraftforge.common.config.Configuration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.vetpetmon.synapselib.util.CFG.createDirectory;

public class ConfigBase {

    // Reorganization and code optimizations done by Byte and [NAME REDACTED]
    // Might move more of this nonsense to SynapseLib

    private static final int defaultConfig = 0; // 0 for Classic, 1 for Death World, 2 for Dark Forest.
    private static final String[] factoryConfigs = {"Classic","Death World","Dark Forest"};
    public static int selectedPreset, presetsVersion = 12;
    private static String ConfigDirectory = WyrmsOfNyrus.proxy.getDataDir().getPath() + "/config/WyrmsOfNyrus/" ;
    private static Configuration general, wyrms, debug, evo, world, invasion, addons, manifest;

    public static void setConfigPreset() {
        selectedPreset = Client.configPreset;
        if (Client.configPreset < 0) selectedPreset = defaultConfig;
        if (selectedPreset > 3) {
            WyrmsOfNyrus.logger.info("Using or creating user-defined config preset folder, configs are in folder: " + selectedPreset);
            customConfigGen();
        }
        else WyrmsOfNyrus.logger.info("Using factory preset: " + selectedPreset);
    }

    public static int presetInts(int normal, int deathWorld, int darkForest, int id) {
        switch(id) {
            case(1): return deathWorld;
            case(2): return darkForest;
            default: return normal;
        }
    }
    public static int[] presetIntArrays(int[] normal, int[] deathWorld, int[] darkForest, int id) {
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
    public static float[] presetFloatArrays(float[] normal, float[] deathWorld, float[] darkForest, int id) {
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
    public static String presetStrings(String normal, String deathWorld, String darkForest, int id) {
        switch(id) {
            case(1): return deathWorld;
            case(2): return darkForest;
            default: return normal;
        }
    }

    public static String[] presetStringArrays(String[] normal, String[] deathWorld, String[] darkForest, int id) {
        switch(id) {
            case(1): return deathWorld;
            case(2): return darkForest;
            default: return normal;
        }
    }

    public static void activatePreset() {
        WyrmsOfNyrus.logger.info("Active preset: " + selectedPreset);
        createDirectories(selectedPreset); //Do this to avoid NullPointer Errors
        reloadConfig(selectedPreset);
    }

    // Specific for WoN.
    public static void reloadConfig(int id) {
        Configuration[] configs = {general, wyrms, debug, evo, world, invasion, addons, manifest};

        for (Configuration i:configs) i.load();
        Debug.loadFromConfig(debug); // load this first so that debug config messages can properly function
        ConfigManifest.createManifest(manifest);
        AI.loadFromConfig(general, id);
        Radiogenetics.loadFromConfig(general, id);
        WyrmStats.loadFromConfig(wyrms, id);
        Evo.loadFromConfig(evo, id);
        WorldConfig.loadFromConfig(world, id);
        Invasion.loadFromConfig(invasion, id);
        Addons.loadFromConfig(addons, id);

        for (Configuration i:configs) i.save();
    }

    /**
     * Creates our mod's first time or update configs dialogue message.
     */
    public static void firstTimeDialogue() {
        JFrame jf = new JFrame();
        JDialog jd = new JDialog(jf);
        jf.setTitle("Wyrms of Nyrus");
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

        jd.add(jl);
        jd.add(jl2);
        jd.add(jl3);
        jf.pack();
        jd.add(norm);
        jd.setVisible(true);
    }

    public static void checkFactorySettings() {
        boolean oneConfigInvalidated = false;
        for (int i = 0; i< factoryConfigs.length;i++) {
            WyrmsOfNyrus.logger.info("Checking factory settings for " + i);
            ConfigDirectory = WyrmsOfNyrus.proxy.getDataDir().getPath() + "/config/WyrmsOfNyrus/" + i+ "/";
            if (!Files.exists(Paths.get(ConfigDirectory))) {
                WyrmsOfNyrus.logger.info("Factory preset \"" + i + "\" does not exist, making it in the following directory: " + ConfigDirectory);
                oneConfigInvalidated = true;
            }
            createDirectories(i); //Properly directs the next bit of code to check manifest
            reloadConfig(i);
            if (ConfigManifest.compareVersion()) {
                WyrmsOfNyrus.logger.warn("Factory preset \"" + i + "\" has version mismatch.");
                File folder = new File(ConfigDirectory);
                File[] files = folder.listFiles();
                if(files!=null) {
                    for(File f: files) f.delete();
                    folder.delete();
                }
                WyrmsOfNyrus.logger.info("Attempted to delete directory and update it.");
                createDirectories(i);
                reloadConfig(i);
            }
        }
        if (oneConfigInvalidated && !GraphicsEnvironment.isHeadless()) firstTimeDialogue(); //Check if the environment is not a terminal before calling to create a new window, as terminal interfaces aren't capable of bringing up graphical interfaces like windows and will otherwise crash. Dedicated server fix.
    }
    public static void customConfigGen() {
        if (!Files.exists(Paths.get(WyrmsOfNyrus.proxy.getDataDir().getPath() + "/config/WyrmsOfNyrus/" + selectedPreset +"/"))) {
            ConfigDirectory = WyrmsOfNyrus.proxy.getDataDir().getPath() + "/config/WyrmsOfNyrus/" + selectedPreset + "/";
            WyrmsOfNyrus.logger.info("Custom preset \"" + selectedPreset + "\" does not exist, making it in the following directory: " + ConfigDirectory);
            WyrmsOfNyrus.logger.info("This is a custom preset. Once this generates, you are free to edit it as you wish.");
            createDirectories(selectedPreset);
            reloadConfig(selectedPreset);
        }
    }

    private static void createDirectories(int id) {
        ConfigDirectory = WyrmsOfNyrus.proxy.getDataDir().getPath() + "/config/WyrmsOfNyrus/" + id +"/";
        general = createDirectory("general", ConfigDirectory);
        wyrms = createDirectory("wyrms", ConfigDirectory);
        debug = createDirectory("debug", ConfigDirectory);
        evo = createDirectory("evolution", ConfigDirectory);
        world = createDirectory("world", ConfigDirectory);
        invasion = createDirectory("invasion", ConfigDirectory);
        addons = createDirectory("addons", ConfigDirectory + "addons/");
        manifest = createDirectory("manifest", ConfigDirectory);
    }

}
