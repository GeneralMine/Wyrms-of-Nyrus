package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.config.Debug;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.wyrmVariables;

import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.world.World;

import java.util.Map;

public class InvasionBlockSpread {
    public static void run(Map<String, Object> dependencies) {
        if (Invasion.invasionEnabled) {
            World world = (World) dependencies.get("world");
            wyrmVariables.WorldVariables.get(world).wyrmInvasionPoints = (wyrmVariables.WorldVariables.get(world).wyrmInvasionPoints)
                    + Invasion.creepSpreadPoints;
            wyrmVariables.WorldVariables.get(world).syncData(world);
            if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 8) wyrmsofnyrus.logger.info("Invasion points increased by " + Invasion.creepSpreadPoints + " from creep spread");
        }
    }
}
