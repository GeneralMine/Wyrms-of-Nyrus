package com.vetpetmon.wyrmsofnyrus.invasion;

// imports ALL events automatically.

import com.vetpetmon.synapselib.util.RNG;
import com.vetpetmon.wyrmsofnyrus.config.Debug;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.invasion.events.creepwyrmDrop;
import com.vetpetmon.wyrmsofnyrus.invasion.events.massIncursion;
import com.vetpetmon.wyrmsofnyrus.invasion.events.scoutingPodRaid;
import com.vetpetmon.wyrmsofnyrus.invasion.events.smallPodRaid;
import com.vetpetmon.wyrmsofnyrus.wyrmVariables;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;


public class InvasionEvent {
	public static void invasionEvent(EntityPlayer player, World world) {
		int eventRolled;
		double wyrmInvasionDifficulty = wyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty;
		if(wyrmInvasionDifficulty > 1.5) {eventRolled = (RNG.getIntRangeInclu(1,4));}
		else if(wyrmInvasionDifficulty >= 2.0) {eventRolled = (RNG.getIntRangeInclu(1,5));}
		else if(wyrmInvasionDifficulty >= 2.5) {eventRolled = (RNG.getIntRangeInclu(1,7));}
		else if(wyrmInvasionDifficulty >= 3.25) {eventRolled = (RNG.getIntRangeInclu(1,10));}
		else if(wyrmInvasionDifficulty >= 3.75) {eventRolled = (RNG.getIntRangeInclu(1,11));}
		else {eventRolled = (RNG.getIntRangeInclu(1,3));}
		if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 2) wyrmsofnyrus.logger.info("[INVASION] Rolled event: " + eventRolled);


		if ( eventRolled <= 1) {scoutingPodRaid.callEvent(player.posX,player.posZ,world);}
		else if ( eventRolled == 4 && Invasion.isCreepEnabled()) {creepwyrmDrop.callEvent(player.posX,player.posZ,world);}
		else if ( eventRolled == 5) {massIncursion.callEvent(player.posX,player.posZ,world,1);
			if (Invasion.isCreepEnabled())creepwyrmDrop.callEvent(player.posX,player.posZ,world);}
		else if ( eventRolled == 7) {scoutingPodRaid.callEvent(player.posX,player.posZ,world);
			if (Invasion.isCreepEnabled())creepwyrmDrop.callEvent(player.posX,player.posZ,world);}
		else if ( eventRolled == 8) {massIncursion.callEvent(player.posX,player.posZ,world,2);}
		else if ( eventRolled == 9) {massIncursion.callEvent(player.posX,player.posZ,world,3);}
		else if ( eventRolled == 10) {scoutingPodRaid.callEvent(player.posX,player.posZ,world);if (Invasion.isCreepEnabled())creepwyrmDrop.callEvent(player.posX,player.posZ,world);smallPodRaid.callEvent(player.posX,player.posZ,world);}
		else {smallPodRaid.callEvent(player.posX,player.posZ,world);}
	}
}
