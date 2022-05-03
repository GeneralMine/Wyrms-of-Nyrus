package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.config.ConfigLib;
import net.minecraftforge.fml.common.FMLCommonHandler;

import net.minecraft.world.World;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.server.MinecraftServer;

import java.util.Map;

import com.vetpetmon.wyrmsofnyrus.wyrmVariables;
import com.vetpetmon.wyrmsofnyrus.AutoReg;

@AutoReg.ModElement.Tag
public class WyrmsTestCommandExecuted extends AutoReg.ModElement {
	public WyrmsTestCommandExecuted(AutoReg instance) {
		super(instance, 7);
	}

	static ConfigLib.Invasion invasion = ConfigLib.invasion;

	public static void executescript(Map<String, Object> dependencies) {
		World world = (World) dependencies.get("world");
		MinecraftServer mcserv = FMLCommonHandler.instance().getMinecraftServerInstance();
		if ((world.getDifficulty() == EnumDifficulty.PEACEFUL)) {
			{
				if (mcserv != null)
					mcserv.getPlayerList().sendMessage(new TextComponentString(
							"The world is in peaceful mode! It is recommended you at least lock the difficulty to Easy for the wyrms to work properly."));
			}
		}
		else if (!invasion.invasionEnabled){
			mcserv.getPlayerList().sendMessage(new TextComponentString(
					"Invasions are not enabled, many features are missing."));
		}
		else {
			{
				if (mcserv != null)
					mcserv.getPlayerList().sendMessage(new TextComponentString("Wyrms of Nyral is working properly."));
			}
		}
		{
			if (mcserv != null && invasion.invasionEnabled)
				mcserv.getPlayerList().sendMessage(
						new TextComponentString(
								(
									("Current Wyrm invasion status: ")
									+ ""
									+ ((wyrmVariables.wyrmInvasionStatus))
									+ ""
									+ (" || Points: ")
									+ ""
									+ ((wyrmVariables.WorldVariables.get(world).wyrmInvasionPoints))
									+ (" || The ")
									+ ""
									+ ((wyrmVariables.WorldVariables.get(world).hiveName))
									+ ""
									+ (" Hive is currently invading the world!")
								)
						)
				);
			else {System.out.println("Invasions are disabled, nothing to output.");}
		}
	}
}
