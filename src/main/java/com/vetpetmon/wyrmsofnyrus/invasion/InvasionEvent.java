package com.vetpetmon.wyrmsofnyrus.invasion;

// imports ALL events automatically.

import com.vetpetmon.wyrmsofnyrus.config.Debug;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.invasion.events.creepwyrmDrop;
import com.vetpetmon.wyrmsofnyrus.invasion.events.massIncursion;
import com.vetpetmon.wyrmsofnyrus.invasion.events.scoutingPodRaid;
import com.vetpetmon.wyrmsofnyrus.invasion.events.smallPodRaid;
import com.vetpetmon.wyrmsofnyrus.synapselib.RNG;
import com.vetpetmon.wyrmsofnyrus.wyrmVariables;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Map;


public class InvasionEvent {
	public static void executescript(Map<String, Object> dependencies) {
		World world = (World) dependencies.get("world");
		int eventRolled = 0;
		double wyrmInvasionDifficulty = wyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty;
		if (wyrmVariables.WorldVariables.get(world).invasionStarted && (RNG.getIntRangeInclu(1,6000) < (wyrmInvasionDifficulty * Invasion.eventFrequency))/*(Math.random() < 0.00001 + (wyrmInvasionDifficulty / 6f * 0.00021f))*/) {
			if(wyrmInvasionDifficulty > 1.5) {eventRolled = (RNG.getIntRangeInclu(1,4));}
			else if(wyrmInvasionDifficulty >= 2.0) {eventRolled = (RNG.getIntRangeInclu(1,5));}
			else if(wyrmInvasionDifficulty >= 2.5) {eventRolled = (RNG.getIntRangeInclu(1,7));}
			else if(wyrmInvasionDifficulty >= 3.25) {eventRolled = (RNG.getIntRangeInclu(1,10));}
			else if(wyrmInvasionDifficulty >= 3.75) {eventRolled = (RNG.getIntRangeInclu(1,11));}
			else {eventRolled = (RNG.getIntRangeInclu(1,3));}
			if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 2) wyrmsofnyrus.logger.info("[INVASION] Rolled event: " + eventRolled);


			if ( eventRolled <= 1) {scoutingPodRaid.call(dependencies);}
			else if ( eventRolled == 4 && Invasion.isCreepEnabled()) {creepwyrmDrop.call(dependencies);}
			else if ( eventRolled == 5) {massIncursion.call(dependencies, 1);}
			else if ( eventRolled == 7) {scoutingPodRaid.call(dependencies);
				if (Invasion.isCreepEnabled())creepwyrmDrop.call(dependencies);}
			else if ( eventRolled == 8) {massIncursion.call(dependencies, 1);}
			else if ( eventRolled == 9) {massIncursion.call(dependencies, 2);}
			else if ( eventRolled == 10) {scoutingPodRaid.call(dependencies);if (Invasion.isCreepEnabled())creepwyrmDrop.call(dependencies);smallPodRaid.Do(dependencies);}
			else {smallPodRaid.Do(dependencies);}
		}
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END && Invasion.invasionEnabled) {
			Entity entity = event.player;
			World world = entity.world;
			int i = (int) entity.posX;
			int j = (int) entity.posY;
			int k = (int) entity.posZ;
			if (wyrmVariables.WorldVariables.get(world).invasionStarted) {
				java.util.HashMap<String, Object> dependencies = new java.util.HashMap<>();
				dependencies.put("x", i);
				dependencies.put("y", j);
				dependencies.put("z", k);
				dependencies.put("world", world);
				dependencies.put("entity", entity);
				dependencies.put("event", event);
				executescript(dependencies);
			}
		}
	}
}
