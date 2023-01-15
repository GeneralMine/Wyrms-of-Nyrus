package com.vetpetmon.wyrmsofnyrus.config;

import com.vetpetmon.synapselib.util.CFG;
import net.minecraftforge.common.config.Configuration;

public class ConfigManifest {
    private static int version;

    public static void createManifest(Configuration config) {
        final String CATEGORY = "MANIFEST";
        config.addCustomCategoryComment(CATEGORY,  "\nMeta information. DO NOT TOUCH!\n");

        version = CFG.createConfigInt(config, CATEGORY, "Configuration version", "Used by the configuration system to compare and see if the preset was updated in the next version of Wyrms of Nyrus.", ConfigBase.presetsVersion);
    }

    /**
     * Compare manifest version with expected version
     * @return true if there is a difference
     */
    public static boolean compareVersion() {
        if (ConfigBase.presetsVersion != version) return true;
        else return false;
    }
}
