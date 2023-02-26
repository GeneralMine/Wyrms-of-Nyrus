package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.WyrmVariables;
import net.minecraft.world.World;


public class InvasionStatus {

	public static float getDifficulty(float baseDiff) {
		return baseDiff;
	}

	public static void executescript(World world) {
			double invasionP = InvasionPoints.get(world);
			if (invasionP <= (Invasion.iPointsIStage1Threshold)) {
				WyrmVariables.wyrmInvasionStatus = "Arriving";
				InvasionPoints.setDifficulty(world,getDifficulty(1.0F));
			} else if (invasionP <= (Invasion.iPointsIStage2Threshold)) {
				WyrmVariables.wyrmInvasionStatus = "Scouting";
				InvasionPoints.setDifficulty(world,getDifficulty(1.5F));
			} else if (invasionP <= (Invasion.iPointsIStage3Threshold)) {
				WyrmVariables.wyrmInvasionStatus = "Establishing hive";
				InvasionPoints.setDifficulty(world,getDifficulty(2.0F));
			} else if (invasionP <= (Invasion.iPointsIStage4Threshold)) {
				WyrmVariables.wyrmInvasionStatus = "Expanding";
				InvasionPoints.setDifficulty(world,getDifficulty(3.0F));
			} else if (invasionP <= (Invasion.iPointsIStage5Threshold)) {
				WyrmVariables.wyrmInvasionStatus = "Invading";
				InvasionPoints.setDifficulty(world,getDifficulty(4.5F));
			} else if (invasionP <= (Invasion.iPointsIStage6Threshold)) {
				WyrmVariables.wyrmInvasionStatus = "Dominant species";
				InvasionPoints.setDifficulty(world,getDifficulty(5.5F));
			} else if (invasionP > (Invasion.iPointsIStage6Threshold)) {
				WyrmVariables.wyrmInvasionStatus = "Terraforming";
				InvasionPoints.setDifficulty(world,getDifficulty(6.0F));
			} else {
				WyrmVariables.wyrmInvasionStatus = "Unknown";
				InvasionPoints.setDifficulty(world,getDifficulty(1.0F));
			}
	}
}
