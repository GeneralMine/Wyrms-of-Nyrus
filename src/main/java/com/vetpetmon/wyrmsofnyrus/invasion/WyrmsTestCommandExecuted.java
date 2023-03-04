package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.entity.ai.gestalt.GestaltHostMind;
import com.vetpetmon.wyrmsofnyrus.evo.EvoPoints;
import com.vetpetmon.wyrmsofnyrus.WyrmVariables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.HashMap;
import java.util.Map;

public class WyrmsTestCommandExecuted {

	public static void executescript(Map<String, Object> dependencies) {
		World world = (World) dependencies.get("world");
		Entity entity = (Entity) dependencies.get("entity");
		MinecraftServer mcserv = FMLCommonHandler.instance().getMinecraftServerInstance();
		HashMap cmdparams = (HashMap) dependencies.get("cmdparams");
		if ((((new Object() {
			public String getText() {
				String param = (String) cmdparams.get("0");
				if (param != null) return param;
				return "";
			}
		}.getText())).equals("reloadClient"))) {
			//ConfigBase.reloadClient();
			if (mcserv != null &&(entity instanceof EntityPlayer))
				entity.sendMessage(new TextComponentString(
						"This command parameter is obsolete, please edit the Client configs in the in-game Mods menu instead."));

		} else {
			if ((world.getDifficulty() == EnumDifficulty.PEACEFUL)) {
				{
					if (mcserv != null &&(entity instanceof EntityPlayer))
						entity.sendMessage(new TextComponentString(
								"The world is in peaceful mode! It is recommended you at least lock the difficulty to Easy for the wyrms to work properly."));
				}
			} else if (!Invasion.invasionEnabled) {
				entity.sendMessage(new TextComponentString(
						"Invasions are not enabled, many features are missing."));
			} else {
				{
					if (mcserv != null)
						entity.sendMessage(new TextComponentString("Wyrms of Nyrus is working properly."));
				}
			}
		}
		{
			if (mcserv != null)
				entity.sendMessage(
						new TextComponentString(
								(
										("Current Wyrm invasion status: ")
												+ ((WyrmVariables.wyrmInvasionStatus))
												+ (" || Points: ")
												+ (InvasionPoints.get(world))
												+ (" || Evolution: ")
												+ (EvoPoints.get(world))
												+ (" || Min Evolution: ")
												+ (EvoPoints.minEvoCap)
												+ (" || Infamy: ")
												+ (GestaltHostMind.getAttentionLevel(world))
								)
						)
				);
		}
	}
}
