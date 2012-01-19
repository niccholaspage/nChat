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
		boolean reloadPermission = plugin.getPermissionsHandler().hasPermission(sender, "nChat.reload");
		
		if (args.length < 1){
			if (!plugin.getPermissionsHandler().hasPermission(sender, "nChat.version")) return true;
			
			sender.sendMessage(ChatColor.BLUE + "nChat " + plugin.getDescription().getVersion() + " by niccholaspage");
			
			if (reloadPermission){
				sender.sendMessage(ChatColor.BLUE + "Type /nChat reload to reload the configuration");
			}
			
			return true;
		}
		
		if (args[0].equalsIgnoreCase("reload")){
			if (reloadPermission) return true;
			
			plugin.loadConfig();
			
			sender.sendMessage(ChatColor.BLUE + "The nChat configuration has been reloaded.");
		}
		return true;
	}
}
