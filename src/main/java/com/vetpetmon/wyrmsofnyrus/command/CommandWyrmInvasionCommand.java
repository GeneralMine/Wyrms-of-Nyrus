
package com.vetpetmon.wyrmsofnyrus.command;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.entity.Entity;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ICommand;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;

import com.vetpetmon.wyrmsofnyrus.invasion.WyrmInvasionCommandExecuted;
import com.vetpetmon.wyrmsofnyrus.AutoReg;

@AutoReg.ModElement.Tag
public class CommandWyrmInvasionCommand extends AutoReg.ModElement {
	public CommandWyrmInvasionCommand(AutoReg instance) {
		super(instance, 14);
	}

	@Override
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandHandler());
	}
	public static class CommandHandler implements ICommand {
		@Override
		public int compareTo(ICommand c) {
			return getName().compareTo(c.getName());
		}

		@Override
		public boolean checkPermission(MinecraftServer server, ICommandSender var1) {
			return true;
		}

		@Override
		public List getAliases() {
			return new ArrayList();
		}

		@Override
		public List getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
			List<String> list = new ArrayList<>();
			if (args.length == 1) {
				list.add("reset");
				list.add("print");
				list.add("forceVisit");
				list.add("startInvasion");
				list.add("stopInvasion");
			}
			return list;
		}

		@Override
		public boolean isUsernameIndex(String[] string, int index) {
			return true;
		}

		@Override
		public String getName() {
			return "won_invasion";
		}

		@Override
		public String getUsage(ICommandSender var1) {
			return "/won_invasion [<arguments>]";
		}

		@Override
		public void execute(MinecraftServer server, ICommandSender sender, String[] cmd) {
			int x = sender.getPosition().getX();
			int y = sender.getPosition().getY();
			int z = sender.getPosition().getZ();
			Entity entity = sender.getCommandSenderEntity();
			if (entity != null) {
				World world = entity.world;
				HashMap<String, String> cmdparams = new HashMap<>();
				int[] index = {0};
				Arrays.stream(cmd).forEach(param -> {
					cmdparams.put(Integer.toString(index[0]), param);
					index[0]++;
				});
				{
					Map<String, Object> $_dependencies = new HashMap<>();
					$_dependencies.put("cmdparams", cmdparams);
					$_dependencies.put("x", x);
					$_dependencies.put("y", y);
					$_dependencies.put("z", z);
					$_dependencies.put("world", world);
					$_dependencies.put("entity", entity);
					WyrmInvasionCommandExecuted.executescript($_dependencies);
				}
			}
		}
	}
}
