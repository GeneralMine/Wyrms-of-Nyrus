package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.config.ConfigLib;
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

	static ConfigLib.Invasion invasion = ConfigLib.invasion;

	public static void executescript(Map<String, Object> dependencies) {
		if (invasion.invasionEnabled) {
			World world = (World) dependencies.get("world");
			if (((wyrmVariables.WorldVariables.get(world).wyrmInvasionPoints) <= 500)) {
				wyrmVariables.wyrmInvasionStatus = "Arriving";
				wyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty = 1.0;
			} else if (((wyrmVariables.WorldVariables.get(world).wyrmInvasionPoints) <= 1500)) {
				wyrmVariables.wyrmInvasionStatus = "Scouting";
				wyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty = 1.5;
			} else if (((wyrmVariables.WorldVariables.get(world).wyrmInvasionPoints) <= 5000)) {
				wyrmVariables.wyrmInvasionStatus = "Establishing hive";
				wyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty = 2.0;
			} else if (((wyrmVariables.WorldVariables.get(world).wyrmInvasionPoints) <= 25000)) {
				wyrmVariables.wyrmInvasionStatus = "Expanding";
				wyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty = 3.0;
			} else if (((wyrmVariables.WorldVariables.get(world).wyrmInvasionPoints) <= 100000)) {
				wyrmVariables.wyrmInvasionStatus = "Invading";
				wyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty = 4.5;
			} else if (((wyrmVariables.WorldVariables.get(world).wyrmInvasionPoints) <= 100000)) {
				wyrmVariables.wyrmInvasionStatus = "Dominant species";
				wyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty = 5.5;
			} else if (((wyrmVariables.WorldVariables.get(world).wyrmInvasionPoints) > 250000)) {
				wyrmVariables.wyrmInvasionStatus = "Terraforming";
				wyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty = 6.0;
			} else {
				wyrmVariables.wyrmInvasionStatus = "Unknown";
				wyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty = 1.0;
			}
		}
	}

	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event) {
		if (event.phase == TickEvent.Phase.END && invasion.invasionEnabled) {
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
