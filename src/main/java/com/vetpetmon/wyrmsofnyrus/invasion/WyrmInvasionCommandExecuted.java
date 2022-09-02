package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraftforge.fml.common.FMLCommonHandler;

import net.minecraft.world.World;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.server.MinecraftServer;

import java.util.Map;
import java.util.HashMap;

import com.vetpetmon.wyrmsofnyrus.wyrmVariables;
import com.vetpetmon.wyrmsofnyrus.AutoReg;

@AutoReg.ModElement.Tag
public class WyrmInvasionCommandExecuted extends AutoReg.ModElement {
	public WyrmInvasionCommandExecuted(AutoReg instance) {
		super(instance, 15);
	}

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
				wyrmVariables.WorldVariables.get(world).wyrmInvasionPoints = 0.0D;
				wyrmVariables.WorldVariables.get(world).syncData(world);
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
				System.out.println((("Points: ") + "" + ((wyrmVariables.WorldVariables.get(world).wyrmInvasionPoints))));
			} else if ((((new Object() {
				public String getText() {
					String param = (String) cmdparams.get("0");
					if (param != null) {
						return param;
					}
					return "";
				}
			}.getText())).equals("forceVisit"))) {
				wyrmVariables.WorldVariables.get(world).invasionStarted = false;
				System.out.println("Invasion forced");
				wyrmVariables.WorldVariables.get(world).syncData(world);
				{
					Map<String, Object> $_dependencies = new HashMap<>();
					$_dependencies.put("x", x);
					$_dependencies.put("y", y);
					$_dependencies.put("z", z);
					VisitorEvent.executeProcedure($_dependencies, true, world);
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
				wyrmVariables.WorldVariables.get(world).invasionStarted = true;
				wyrmsofnyrus.logger.info("Wyrm invasion started by command usage.");
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
