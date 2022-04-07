package com.vetpetmon.wyrmsofnyrus.script;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.World;

import java.util.Map;

import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrusModVariables;
import com.vetpetmon.wyrmsofnyrus.ElementswyrmsofnyrusMod;

@ElementswyrmsofnyrusMod.ModElement.Tag
public class scriptInvasionStatus extends ElementswyrmsofnyrusMod.ModElement {
	public scriptInvasionStatus(ElementswyrmsofnyrusMod instance) {
		super(instance, 12);
	}

	public static void executescript(Map<String, Object> dependencies) {
		World world = (World) dependencies.get("world");
		if (((wyrmsofnyrusModVariables.WorldVariables.get(world).wyrmInvasionPoints) <= 100)) {
			wyrmsofnyrusModVariables.wyrmInvasionStatus = (String) "Arriving";
		} else if (((wyrmsofnyrusModVariables.WorldVariables.get(world).wyrmInvasionPoints) <= 500)) {
			wyrmsofnyrusModVariables.wyrmInvasionStatus = (String) "Scouting";
		} else if (((wyrmsofnyrusModVariables.WorldVariables.get(world).wyrmInvasionPoints) <= 1000)) {
			wyrmsofnyrusModVariables.wyrmInvasionStatus = (String) "Establishing hive";
		} else if (((wyrmsofnyrusModVariables.WorldVariables.get(world).wyrmInvasionPoints) <= 10000)) {
			wyrmsofnyrusModVariables.wyrmInvasionStatus = (String) "Expanding";
		} else if (((wyrmsofnyrusModVariables.WorldVariables.get(world).wyrmInvasionPoints) <= 100000)) {
			wyrmsofnyrusModVariables.wyrmInvasionStatus = (String) "Invading";
		} else if (((wyrmsofnyrusModVariables.WorldVariables.get(world).wyrmInvasionPoints) > 100000)) {
			wyrmsofnyrusModVariables.wyrmInvasionStatus = (String) "Dominant species";
		} else {
			wyrmsofnyrusModVariables.wyrmInvasionStatus = (String) "Unknown";
		}
	}

	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
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
