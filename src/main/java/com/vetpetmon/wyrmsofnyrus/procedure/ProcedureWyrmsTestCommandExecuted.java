package com.vetpetmon.wyrmsofnyrus.procedure;

import net.minecraftforge.fml.common.FMLCommonHandler;

import net.minecraft.world.World;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.server.MinecraftServer;

import java.util.Map;

import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrusModVariables;
import com.vetpetmon.wyrmsofnyrus.ElementswyrmsofnyrusMod;

@ElementswyrmsofnyrusMod.ModElement.Tag
public class ProcedureWyrmsTestCommandExecuted extends ElementswyrmsofnyrusMod.ModElement {
	public ProcedureWyrmsTestCommandExecuted(ElementswyrmsofnyrusMod instance) {
		super(instance, 7);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			System.err.println("Failed to load dependency world for procedure WyrmsTestCommandExecuted!");
			return;
		}
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
						new TextComponentString((("Current Wyrm invasion status: ") + "" + ((wyrmsofnyrusModVariables.wyrmInvasionStatus)) + ""
								+ (" || Points: ") + "" + ((wyrmsofnyrusModVariables.WorldVariables.get(world).wyrmInvasionPoints)))));
		}
	}
}
