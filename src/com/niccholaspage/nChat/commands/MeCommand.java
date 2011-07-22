package com.niccholaspage.nChat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.niccholaspage.nChat.nChat;

public class MeCommand implements CommandExecutor {
	private final nChat plugin;
	public MeCommand(nChat plugin){
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if (plugin.Permissions == null) return true;
		if (!(sender instanceof Player)) return true;
		Player player = (Player) sender;
		if (!plugin.hasPermission(sender, "nChat.me")) return true;
		if (args.length < 1){
			return false;
		}
		String message = "";
		for (String arg : args){
			message += arg + "";
		}
		plugin.getServer().broadcastMessage(plugin.formatMessage(player, plugin.meFormat, message));
		return true;
	}
}
