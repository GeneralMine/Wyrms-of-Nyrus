package com.vetpetmon.wyrmsofnyrus.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;

import static com.vetpetmon.synapselib.util.CFG.createConfigBool;

@Config(modid = "wyrmsofnyrus", name = "Wyrms of Nyrus Client")
public class Client {
    @Config.Name("Fancy Animations")
    @Config.Comment({"Enables extensive animation value checks for situations like being in the air (for non-flying enemies) or being in water (for non-aquatic enemies).", "Disabling this can increase performance on clients."})
    public static boolean fancyAnimations = true;
    @Config.Name("Config Preset")
    @Config.Comment({"The index of the configuration preset you wish to use.", "Setting this to -1 will make the mod use this version's default configurations.", "Factory presets are defined in a range from 0 to 2, with increasing difficulty. Any value above this will use user-defined configurations.", "The hard cap is because generating configurations increases loading times!"})
    @Config.RequiresMcRestart
    @Config.RangeInt(min = -1, max = 10)
    public static int configPreset = -1;

    @Deprecated
    public static void loadFromConfig(Configuration config) {

        final String CATEGORY = "Clientside";

        config.addCustomCategoryComment(CATEGORY, "\nUser-end settings. Can be reloaded at any point in time.\n");

        fancyAnimations = createConfigBool(config, CATEGORY, "Enable fancy animations", "Allows for extra animations to be played for certain wyrms that have more than 2-3 animations, reduces the amount of conditionals being checked and might help with FPS on lower end PCs. Default: true", true);


    }
}
