
package com.vetpetmon.wyrmsofnyrus.command;

import com.vetpetmon.wyrmsofnyrus.invasion.WyrmsTestCommandExecuted;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class CommandWyrmsTest extends CommandBase {
	public CommandWyrmsTest() {
		super();
	}

	@Override
	public int getRequiredPermissionLevel() {
		//Level 2 ops can do commands like setblock, gamemode, and give. They can't kick/ban or stop the server.
		return 2;
	}
	@Override
	public int compareTo(ICommand c) {
		return getName().compareTo(c.getName());
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender var1) {
		return var1.canUseCommand(this.getRequiredPermissionLevel(), this.getName());
	}

	@Override
	public List getAliases() {
		return new ArrayList();
	}

	@Override
	public List getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
		List<String> list = new ArrayList<>();
		if (args.length == 1) {
			list.add("reloadClient");
		}
		return list;
	}

	@Override
	public boolean isUsernameIndex(String[] string, int index) {
		return true;
	}

	@Override
	public String getName() {
		return "wyrms";
	}

	@Override
	public String getUsage(ICommandSender var1) {
		return "/wyrms [<arguments>]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] cmd) {
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
				$_dependencies.put("world", world);
				$_dependencies.put("entity", entity);
				WyrmsTestCommandExecuted.executescript($_dependencies);
			}
		}
	}
}
