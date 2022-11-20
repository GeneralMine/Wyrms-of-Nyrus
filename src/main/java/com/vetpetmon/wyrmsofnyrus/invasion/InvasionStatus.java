package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.World;

import java.util.Map;

import com.vetpetmon.wyrmsofnyrus.wyrmVariables;
import com.vetpetmon.wyrmsofnyrus.AutoReg;

@AutoReg.ModElement.Tag
public class InvasionStatus extends AutoReg.ModElement {
	public InvasionStatus(AutoReg instance) {
		super(instance, 12);
	}

	public static float getDifficulty(float baseDiff) {
		// First factor: What's the basic invasion stage difficulty here?
		float dFactorOne = baseDiff;
		// Second factor: Is this EXtended Canon and what is that difficulty option set too?
		float dFactorTwo;
		if(Invasion.isEXCANON()) {dFactorTwo = Invasion.getEXCANONDIFFICULTY();}
		else {dFactorTwo = 1;}

		float finalDifficulty = (dFactorOne * dFactorTwo);

		return finalDifficulty;
	}

	public static void executescript(Map<String, Object> dependencies) {
		if (Invasion.invasionEnabled) {
			World world = (World) dependencies.get("world");
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

	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event) {
		if (event.phase == TickEvent.Phase.END && Invasion.invasionEnabled) {
			World world = event.world;
			java.util.HashMap<String, Object> dependencies = new java.util.HashMap<>();
			dependencies.put("world", world);
			dependencies.put("event", event);
			this.executescript(dependencies);
		}
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
	}
}
