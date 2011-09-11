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
		
		String message = arrayToString(args, " ");
		
		plugin.getServer().broadcastMessage(plugin.formatMessage(player, plugin.meFormat, message));
		return true;
	}
	
	private String arrayToString(String[] array, String seperator){
		String out = "";
		
		for (int i = 0; i < array.length; i++){
			out = out + array[i] + seperator;
		}
		
		return out;
	}
}
