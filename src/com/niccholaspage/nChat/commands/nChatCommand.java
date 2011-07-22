package com.niccholaspage.nChat.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.niccholaspage.nChat.nChat;

public class nChatCommand implements CommandExecutor {
	private final nChat plugin;
	public nChatCommand(nChat plugin){
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if (plugin.Permissions == null) return true;
		if (args.length < 1){
			if (!plugin.hasPermission(sender, "nChat.version")) return true;
			sender.sendMessage(ChatColor.BLUE + "nChat " + plugin.getDescription().getVersion() + " by niccholaspage");
			return true;
		}
		if (args[0].equalsIgnoreCase("reload")){
			if (!plugin.hasPermission(sender, "nChat.reload")) return true;
			plugin.readConfig();
			sender.sendMessage(ChatColor.BLUE + "The nChat configuration has been reloaded.");
		}
		return true;
	}
}
