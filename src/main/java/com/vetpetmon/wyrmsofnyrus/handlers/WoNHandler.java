package com.vetpetmon.wyrmsofnyrus.handlers;

import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.evo.evoPoints;
import com.vetpetmon.wyrmsofnyrus.invasion.InvasionEvent;
import com.vetpetmon.wyrmsofnyrus.invasion.InvasionScheduler;
import com.vetpetmon.wyrmsofnyrus.invasion.InvasionStatus;
import com.vetpetmon.wyrmsofnyrus.invasion.VisitorEvent;
import com.vetpetmon.wyrmsofnyrus.synapselib.libVars;
import com.vetpetmon.wyrmsofnyrus.wyrmVariables;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(modid = libVars.ModID)
public class WoNHandler {

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(wyrmsofnyrus.MODID))
        {
            ConfigManager.sync(wyrmsofnyrus.MODID, Config.Type.INSTANCE);
            wyrmsofnyrus.logger.info("Configuration loaded or changed.");
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Entity entity = event.player;
        World world = entity.world;
        int x = (int) entity.posX;
        int y = (int) entity.posY;
        int z = (int) entity.posZ;
        boolean invasionActive = wyrmVariables.WorldVariables.get(world).invasionStarted;
        //if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 10) wyrmsofnyrus.logger.info("[WONHANDLER] onPlayerTick was called successfully.");
        // INVASION
        if (Invasion.invasionEnabled && (!entity.world.isRemote && event.phase == TickEvent.Phase.END)) {
            if (!invasionActive && Invasion.invasionStartsNaturally) {
                if (InvasionScheduler.detectDayChange(world)) {
                    VisitorEvent.visitorEvent(false, world, x, y, z);
                }
            }
            if (invasionActive) {
                // Check and see if this is not the world starting, as it starts at 0 before it is set ----[|]
                //                                                                                          |
                // Detects events every x ticks, ----[|]                                                    |
                // or x/20 seconds, x/20/60 minutes...|                                                     |
                //                                    V                                                     V
                if (InvasionScheduler.runSchedule(world)) {
                    java.util.HashMap<String, Object> dependencies = new java.util.HashMap<>();
                    dependencies.put("x", x);
                    dependencies.put("y", y);
                    dependencies.put("z", z);
                    dependencies.put("world", world);
                    dependencies.put("entity", entity);
                    dependencies.put("event", event);
                    InvasionEvent.invasionEvent(dependencies);
                }
            }
        }

    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        World world = event.world;
        //if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 10) wyrmsofnyrus.logger.info("[WONHANDLER] onWorldTick was called successfully.");
        // EVOLUTION
        evoPoints.decay(world);
        // INVASION
        if (Invasion.invasionEnabled) InvasionStatus.executescript(world);
    }
}
