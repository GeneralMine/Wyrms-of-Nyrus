package com.vetpetmon.wyrmsofnyrus.invasion;

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
			wyrmVariables.WorldVariables.get(world).wyrmInvasionPoints = (double) 0;
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
		} else {
			{
				MinecraftServer mcserv = FMLCommonHandler.instance().getMinecraftServerInstance();
				if (mcserv != null)
					mcserv.getPlayerList().sendMessage(new TextComponentString("Parameter not specified!"));
			}
		}
	}
}
