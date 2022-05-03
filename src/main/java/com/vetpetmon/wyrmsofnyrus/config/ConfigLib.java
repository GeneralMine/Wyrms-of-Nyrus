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
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.File;

import static com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus.proxy;

@Config(modid = wyrmsofnyrus.MODID, name = "Wyrms")
public class ConfigLib {

    // All config fields are made accessible here.
    @Config.Comment("Wyrm Invasion configuration.")
    @Config.Name("Invasion")
    public static final Invasion invasion = new Invasion();

    @Config.Comment("Debugging for developers and testers.")
    @Config.Name("Debug")
    public static final Debug debug = new Debug();

    @Config.Comment("AI configuration.")
    @Config.Name("AI")
    public static final AI ai = new AI();

    @Config.Comment("Mob property configuration.")
    @Config.Name("Radiogenetics")
    public static final Radiogenetics radiogenetics = new Radiogenetics();

    public static class Invasion {
        @Config.Comment("Enable or disable the wyrm invasion")
        @Config.Name("Enable invasion")
        public boolean invasionEnabled = true;
        @Config.Comment("Makes probers mean and scary.")
        @Config.Name("Enable probing")
        public boolean probingEnabled = true;

        @Config.Comment("Makes hive creep blocks spread.")
        @Config.Name("Enable creeping")
        public boolean creepEnabled = true;
        @Config.Comment("Rate of which hive creep blocks spread, ratio is 1 in every x ticks, based on RNG.")
        @Config.Name("Creeping rate")
        public int creepSpreadRate = 50;
        @Config.Comment("Invasion points gained from a creep block's creation. Preferably keep this low.")
        @Config.Name("Points per creep")
        public float creepSpreadPoints = 0.015f;
    }

    public static class Debug {
        @Config.Comment("Enable or disable console logging")
        @Config.Name("Enable Debugging")
        public boolean LOGGINGENABLED = false;

        @Config.Comment("The amount of console printing you want to destroy your hard drive with. Values above 10 won't do anything different. The larger this number, the more gets outputted. All increments of levels also include previous levels' loggings, so be careful about using this for extensive periods of times. \n- Level 1: minimal logging, will simply give a few warnings. \n- Level 2: Will output events like invasion event occurrences or invasion stage increments. \n- Level 3: Will output events like wyrmlings growing up \n- Level 4: Will output block mutations \n- Level 5: Will reveal block updates, entity deaths, etc. \n- Level 6: Major variable changes will be outputted into the log. \n- Level 7: All script variable changes will now output to the log, entity animation changes, and most sound plays are logged. \n- Level 8: EVERY METHOD CALL WILL BE OUTPUTTED INTO THE LOG. \n- Level 9: YOU BETTER HAVE A GOOD REASON WHY YOU WANT TO SEE EVERY SINGLE INPUT PUT INTO THOSE METHOD CALLS. \n- Level 10: I am not responsible if you brick your RAM or PC. You asked for it. Does everything in the previous levels, but also outputs more stuff like entity ticks and their NBT values, as well as every RNG roll, every... Thing. Turn back now, save your PC before it's too late.")
        @Config.Name("Debug level")
        @Config.RangeInt(min = 1, max = 10)
        @Config.SlidingOption()
        public int DEBUGLEVEL = 1;
    }

    public static class Radiogenetics {
        public boolean immuneToFalling = true;
        public boolean immuneToCacti = false;

        public int workerProductivity = 2500;
    }

    public static class AI {
        public boolean suicidalWyrms = false;
        public boolean attackMobs = true;
        public boolean attackAnimals = true;

        public boolean savageAIMode = true;
    }

    @Mod.EventBusSubscriber(modid = wyrmsofnyrus.MODID)
    private static class EventHandler
    {
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(wyrmsofnyrus.MODID))
            {
                ConfigManager.sync(wyrmsofnyrus.MODID, Config.Type.INSTANCE);
            }
        }
    }

}
