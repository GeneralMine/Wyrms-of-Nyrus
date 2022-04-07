package com.vetpetmon.wyrmsofnyrus.script;

import net.minecraftforge.fml.common.FMLCommonHandler;

import net.minecraft.world.World;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.server.MinecraftServer;

import java.util.Map;
import java.util.HashMap;

import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrusModVariables;
import com.vetpetmon.wyrmsofnyrus.ElementswyrmsofnyrusMod;

@ElementswyrmsofnyrusMod.ModElement.Tag
public class scriptWyrmInvasionCommandExecuted extends ElementswyrmsofnyrusMod.ModElement {
	public scriptWyrmInvasionCommandExecuted(ElementswyrmsofnyrusMod instance) {
		super(instance, 15);
	}

	public static void executescript(Map<String, Object> dependencies) {
		if (dependencies.get("cmdparams") == null) {
			System.err.println("Failed to load dependency cmdparams for script WyrmInvasionCommandExecuted!");
			return;
		}
		if (dependencies.get("world") == null) {
			System.err.println("Failed to load dependency world for script WyrmInvasionCommandExecuted!");
			return;
		}
		HashMap cmdparams = (HashMap) dependencies.get("cmdparams");
		World world = (World) dependencies.get("world");
		if ((((new Object() {
			public String getText() {
				String param = (String) cmdparams.get("0");
				if (param != null) {
					return param;
				}
				return "";
			}
		}.getText())).equals("reset"))) {
			wyrmsofnyrusModVariables.WorldVariables.get(world).wyrmInvasionPoints = (double) 0;
			wyrmsofnyrusModVariables.WorldVariables.get(world).syncData(world);
			System.out.println("Wyrm invasion points set to 0");
		} else if ((((new Object() {
			public String getText() {
				String param = (String) cmdparams.get("0");
				if (param != null) {
					return param;
				}
				return "";
			}
		}.getText())).equals("print"))) {
			System.out.println((("Points: ") + "" + ((wyrmsofnyrusModVariables.WorldVariables.get(world).wyrmInvasionPoints))));
		} else {
			{
				MinecraftServer mcserv = FMLCommonHandler.instance().getMinecraftServerInstance();
				if (mcserv != null)
					mcserv.getPlayerList().sendMessage(new TextComponentString("Parameter not specified!"));
			}
		}
	}
}
