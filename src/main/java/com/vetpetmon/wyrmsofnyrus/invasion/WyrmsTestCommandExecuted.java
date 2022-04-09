package com.vetpetmon.wyrmsofnyrus.invasion;

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

	public static void executescript(Map<String, Object> dependencies) {
		World world = (World) dependencies.get("world");
		if ((world.getDifficulty() == EnumDifficulty.PEACEFUL)) {
			{
				MinecraftServer mcserv = FMLCommonHandler.instance().getMinecraftServerInstance();
				if (mcserv != null)
					mcserv.getPlayerList().sendMessage(new TextComponentString(
							"The world is in peaceful mode! It is recommended you at least lock the difficulty to Easy for the wyrms to work properly."));
			}
		} else {
			{
				MinecraftServer mcserv = FMLCommonHandler.instance().getMinecraftServerInstance();
				if (mcserv != null)
					mcserv.getPlayerList().sendMessage(new TextComponentString("Wyrms of Nyral is working properly."));
			}
		}
		{
			MinecraftServer mcserv = FMLCommonHandler.instance().getMinecraftServerInstance();
			if (mcserv != null)
				mcserv.getPlayerList().sendMessage(
						new TextComponentString((("Current Wyrm invasion status: ") + "" + ((wyrmVariables.wyrmInvasionStatus)) + ""
								+ (" || Points: ") + "" + ((wyrmVariables.WorldVariables.get(world).wyrmInvasionPoints)))));
		}
	}
}
