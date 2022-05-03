package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.config.ConfigLib;
import com.vetpetmon.wyrmsofnyrus.wyrmVariables;

import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.world.World;

import java.util.Map;

public class InvasionBlockSpread {

    static ConfigLib.Invasion invasion = ConfigLib.invasion;
    static ConfigLib.Debug debug = ConfigLib.debug;

    public static void run(Map<String, Object> dependencies) {
        if (invasion.invasionEnabled) {
            World world = (World) dependencies.get("world");
            wyrmVariables.WorldVariables.get(world).wyrmInvasionPoints = (wyrmVariables.WorldVariables.get(world).wyrmInvasionPoints)
                    + invasion.creepSpreadPoints;
            wyrmVariables.WorldVariables.get(world).syncData(world);
            if (debug.LOGGINGENABLED && debug.DEBUGLEVEL >= 8) wyrmsofnyrus.logger.info("Invasion points increased by " + invasion.creepSpreadPoints + " from creep spread");
        }
    }
}
