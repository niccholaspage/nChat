package com.niccholaspage.nChat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.niccholaspage.nChat.nChat;

public class MeCommand implements CommandExecutor {
	private final nChat plugin;
	public MeCommand(nChat plugin){
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if (plugin.Permissions == null) return true;
		if (!plugin.hasPermission(sender, "nChat.me")) return true;
		if (args.length < 1){
			return false;
		}
		plugin.getServer().broadcastMessage(plugin.formatMessage(plugin.meFormat, null));
		return true;
	}
}
