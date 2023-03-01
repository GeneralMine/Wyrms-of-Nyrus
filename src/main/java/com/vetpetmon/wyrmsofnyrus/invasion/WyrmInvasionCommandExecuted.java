package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.WyrmVariables;
import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.HashMap;
import java.util.Map;

public class WyrmInvasionCommandExecuted{

	public static void executescript(Map<String, Object> dependencies) {
		if (Invasion.invasionEnabled) {
			HashMap cmdparams = (HashMap) dependencies.get("cmdparams");
			int x = (int) dependencies.get("x");
			int y = (int) dependencies.get("y");
			int z = (int) dependencies.get("z");
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
				WyrmVariables.WorldVariables.get(world).wyrmInvasionPoints = 0.0D;
				WyrmVariables.WorldVariables.get(world).syncData(world);
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
				System.out.println((("Points: ") + "" + ((WyrmVariables.WorldVariables.get(world).wyrmInvasionPoints))));
			} else if ((((new Object() {
				public String getText() {
					String param = (String) cmdparams.get("0");
					if (param != null) {
						return param;
					}
					return "";
				}
			}.getText())).equals("forceVisit"))) {
				WyrmVariables.WorldVariables.get(world).invasionStarted = false;
				System.out.println("Invasion forced");
				WyrmVariables.WorldVariables.get(world).syncData(world);
				{
					VisitorEvent.visitorEvent(true, world,x,y,z);
				}
			} else if ((((new Object() {
				public String getText() {
					String param = (String) cmdparams.get("0");
					if (param != null) {
						return param;
					}
					return "";
				}
			}.getText())).equals("startInvasion"))) {
				WyrmVariables.WorldVariables.get(world).invasionStarted = true;
				WyrmVariables.WorldVariables.get(world).syncData(world);
				WyrmsOfNyrus.logger.info("Wyrm invasion started by command usage.");
			}else if ((((new Object() {
				public String getText() {
					String param = (String) cmdparams.get("0");
					if (param != null) {
						return param;
					}
					return "";
				}
			}.getText())).equals("stopInvasion"))) {
				WyrmVariables.WorldVariables.get(world).invasionStarted = false;
				WyrmVariables.WorldVariables.get(world).syncData(world);
				WyrmsOfNyrus.logger.info("Wyrm invasion ended by command usage.");
			}else {
				{
					MinecraftServer mcserv = FMLCommonHandler.instance().getMinecraftServerInstance();
					if (mcserv != null)
						mcserv.getPlayerList().sendMessage(new TextComponentString("Parameter not specified!"));
				}
			}
		}
	}
}
