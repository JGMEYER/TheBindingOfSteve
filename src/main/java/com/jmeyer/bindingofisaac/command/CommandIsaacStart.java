package com.jmeyer.bindingofisaac.command;

import java.util.ArrayList;
import java.util.List;

import com.jmeyer.bindingofisaac.IsaacMod;
import com.jmeyer.bindingofisaac.network.GameStartMessage;
import com.jmeyer.bindingofisaac.network.IsaacMoveMessage;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandIsaacStart implements ICommand {
	
	private final List<String> aliases;
	
	public CommandIsaacStart() {
		aliases = new ArrayList<String>();
		aliases.add("isaac_start");
	}

	@Override
	public int compareTo(ICommand o) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "isaac_start";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "isaac_start";
	}

	@Override
	public List<String> getCommandAliases() {
		return aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		System.out.println("CommmandIsaacStart");
		if(sender instanceof EntityPlayer) {
			IsaacMod.network.sendToServer(new GameStartMessage());
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}

	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos pos) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}
	
}
