package com.vetpetmon.wyrmsofnyrus.invasion;

// imports ALL events automatically.

import com.vetpetmon.synapselib.util.RNG;
import com.vetpetmon.wyrmsofnyrus.config.Debug;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.invasion.events.CreepPodDrop;
import com.vetpetmon.wyrmsofnyrus.invasion.events.Incursion;
import com.vetpetmon.wyrmsofnyrus.invasion.events.ScoutingPodRaid;
import com.vetpetmon.wyrmsofnyrus.invasion.events.SmallPodRaid;
import com.vetpetmon.wyrmsofnyrus.WyrmVariables;
import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;


public class InvasionEvent {
	public static void invasionEvent(EntityPlayer player, World world) {
		int eventRolled;
		double wyrmInvasionDifficulty = WyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty;
		if(wyrmInvasionDifficulty > 1.5) {eventRolled = (RNG.getIntRangeInclu(1,3));}
		else if(wyrmInvasionDifficulty >= 2.0) {eventRolled = (RNG.getIntRangeInclu(1,6));}
		else if(wyrmInvasionDifficulty >= 2.5) {eventRolled = (RNG.getIntRangeInclu(2,9));}
		else if(wyrmInvasionDifficulty >= 3.25) {eventRolled = (RNG.getIntRangeInclu(2,13));}
		else if(wyrmInvasionDifficulty >= 3.75) {eventRolled = (RNG.getIntRangeInclu(2,16));}
		else {eventRolled = (RNG.getIntRangeInclu(1,3));}
		if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 2) WyrmsOfNyrus.logger.info("[INVASION] Rolled event: " + eventRolled);


		if ( eventRolled == 1) {
			ScoutingPodRaid.callEvent(player.posX,player.posZ,world);}
		else if ( eventRolled == 4 && Invasion.isCreepEnabled()) {
            CreepPodDrop.callEvent(player.posX,player.posZ,world);}
		else if ( eventRolled == 5) {
			Incursion.callEvent(player.posX,player.posZ,world,1);
			if (Invasion.isCreepEnabled()) CreepPodDrop.callEvent(player.posX,player.posZ,world);}
		else if ( eventRolled == 8) {
			ScoutingPodRaid.callEvent(player.posX,player.posZ,world,5,1); //Spawn warriors instead
			if (Invasion.isCreepEnabled()) CreepPodDrop.callEvent(player.posX,player.posZ,world);}
		else if ( eventRolled == 10) {
			Incursion.callEvent(player.posX,player.posZ,world,2);}
		else if ( eventRolled == 12) {
			Incursion.callEvent(player.posX,player.posZ,world,3);}
		else if ( eventRolled == 15) {
			ScoutingPodRaid.callEvent(player.posX,player.posZ,world,5,2);
			if (Invasion.isCreepEnabled()){
				CreepPodDrop.callEvent(player.posX,player.posZ,world);
			}
			SmallPodRaid.callEvent(player.posX,player.posZ,world);
		}
		else {
			SmallPodRaid.callEvent(player.posX,player.posZ,world);}
	}
}
