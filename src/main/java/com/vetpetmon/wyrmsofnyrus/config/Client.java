package com.vetpetmon.wyrmsofnyrus.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;

import static com.vetpetmon.synapselib.util.CFG.createConfigBool;

@Config(modid = "wyrmsofnyrus", name = "Wyrms of Nyrus Client")
public class Client {
    @Config.Name("Fancy Animations")
    @Config.Comment({"Enables extensive animation value checks for situations like being in the air (for non-flying enemies) or being in water (for non-aquatic enemies).", "Disabling this can increase performance on clients."})
    public static boolean fancyAnimations = true;

    @Config.Name("Music Enabled")
    @Config.Comment("Enables or disables the music that plays when various events happen, including invasion stage changes and Wyrmfollies hunting the player."
    + "The music is streamed by Wyrms of Nyrus, so it is not pre-loaded into memory first thing. There may be lag when music tracks start, so this option is available for users."
    + "Turn this off if you are a server, not a client. Calls to play tracks will still be sent out from servers to client, but the server itself will not play the songs."
    + "This option should ONLY have a memory usage impact when music tracks are streamed. If the music tracks are eating your RAM, disable this and report the memory leak to VPML immediately.")
    public static boolean MusicEnabled = true;

    @Config.Name("Config Preset")
    @Config.Comment({"The index of the configuration preset you wish to use.",
            "Setting this to -1 will make the mod use this version's default configurations.",
            "Factory presets are defined in a range from 0 to 2, with increasing difficulty. Any value above this will use user-defined configurations.",
            "0 = Classic, recommended for new players or nostalgia-fans, vanilla-friendly difficulty; evolution is weakest, Prober special abilities disabled",
            "1 = Death World, medium difficulty, best suited for modpacks with multiple weapon/gear mods like Tinker's Construct; all features enabled, evolution is normal-strength",
            "2 = Dark Forest, hardest difficulty, emulates the canonical power of the Nyral Wyrms. Balanced for mods like HBM's Nuclear Tech Mod; all destructive features enabled, evolution is extremely strong, all variants unlocked on first stage, etc."
    })
    @Config.RequiresMcRestart
    @Config.RangeInt(min = -1, max = 255)
    public static int configPreset = -1;

    @Deprecated
    public static void loadFromConfig(Configuration config) {

        final String CATEGORY = "Clientside";

        config.addCustomCategoryComment(CATEGORY, "\nUser-end settings. Can be reloaded at any point in time.\n");

        fancyAnimations = createConfigBool(config, CATEGORY, "Enable fancy animations", "Allows for extra animations to be played for certain wyrms that have more than 2-3 animations, reduces the amount of conditionals being checked and might help with FPS on lower end PCs. Default: true", true);


    }
}
