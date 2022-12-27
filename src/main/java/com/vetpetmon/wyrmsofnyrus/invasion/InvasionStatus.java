package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.wyrmVariables;
import net.minecraft.world.World;


public class InvasionStatus {

	public static float getDifficulty(float baseDiff) {
		// First factor: What's the basic invasion stage difficulty here?
		// Second factor: Is this EXtended Canon and what is that difficulty option set too?
		float dFactorTwo = 1;
		if(Invasion.isEXCANON()) dFactorTwo = Invasion.getEXCANONDIFFICULTY();

		return (baseDiff * dFactorTwo);
	}

	public static void executescript(World world) {
			double invasionP = invasionPoints.get(world);
			if (invasionP <= (Invasion.iPointsIStage1Threshold)) {
				wyrmVariables.wyrmInvasionStatus = "Arriving";
				invasionPoints.setDifficulty(world,getDifficulty(1.0F));
			} else if (invasionP <= (Invasion.iPointsIStage2Threshold)) {
				wyrmVariables.wyrmInvasionStatus = "Scouting";
				invasionPoints.setDifficulty(world,getDifficulty(1.5F));
			} else if (invasionP <= (Invasion.iPointsIStage3Threshold)) {
				wyrmVariables.wyrmInvasionStatus = "Establishing hive";
				invasionPoints.setDifficulty(world,getDifficulty(2.0F));
			} else if (invasionP <= (Invasion.iPointsIStage4Threshold)) {
				wyrmVariables.wyrmInvasionStatus = "Expanding";
				invasionPoints.setDifficulty(world,getDifficulty(3.0F));
			} else if (invasionP <= (Invasion.iPointsIStage5Threshold)) {
				wyrmVariables.wyrmInvasionStatus = "Invading";
				invasionPoints.setDifficulty(world,getDifficulty(4.5F));
			} else if (invasionP <= (Invasion.iPointsIStage6Threshold)) {
				wyrmVariables.wyrmInvasionStatus = "Dominant species";
				invasionPoints.setDifficulty(world,getDifficulty(5.5F));
			} else if (invasionP > (Invasion.iPointsIStage6Threshold)) {
				wyrmVariables.wyrmInvasionStatus = "Terraforming";
				invasionPoints.setDifficulty(world,getDifficulty(6.0F));
			} else {
				wyrmVariables.wyrmInvasionStatus = "Unknown";
				invasionPoints.setDifficulty(world,getDifficulty(1.0F));
			}
	}
}
