package com.vetpetmon.wyrmsofnyrus.invasion;

// pool of events to draw from:
import com.vetpetmon.wyrmsofnyrus.invasion.events.smallPodRaid;
// end of event pool
import com.vetpetmon.wyrmsofnyrus.wyrmVariables;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import java.util.Map;

import com.vetpetmon.wyrmsofnyrus.AutoReg;

@AutoReg.ModElement.Tag
public class InvasionEvent extends AutoReg.ModElement {
	public InvasionEvent(AutoReg instance) {
		super(instance, 25);
	}

	public static void executescript(Map<String, Object> dependencies) {
		int x = (int) dependencies.get("x");
		int z = (int) dependencies.get("z");
		World world = (World) dependencies.get("world");
		if (
				(Math.random() < 0.0001 + wyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty / 6f * 0.00021f)
				&& (wyrmVariables.MapVariables.get(world).invasionStarted)
		)
			{
				java.util.HashMap<String, Object> depens = new java.util.HashMap<>();
				depens.put("x", x);
				depens.put("z", z);
				depens.put("world", world);
				smallPodRaid.Do(dependencies);
			}
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			Entity entity = event.player;
			World world = entity.world;
			int i = (int) entity.posX;
			int j = (int) entity.posY;
			int k = (int) entity.posZ;
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

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
	}
}
