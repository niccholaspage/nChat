package com.niccholaspage.nChat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.niccholaspage.nChat.Phrase;
import com.niccholaspage.nChat.nChat;

public class nChatCommand implements CommandExecutor {
	private final nChat plugin;
	
	public nChatCommand(nChat plugin){
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		boolean reloadPermission = sender.hasPermission("nChat.reload");
		
		if (args.length < 1){
			if (!sender.hasPermission("nChat.version")){
				return true;
			}
			
			sender.sendMessage(Phrase.NCHAT_COMMAND_CREDIT.parse(plugin.getDescription().getVersion()));
			
			if (reloadPermission){
				sender.sendMessage(Phrase.NCHAT_CONFIG_RELOAD_HOW_TO.parse(cmd.getName()));
			}
			
			return true;
		}
		
		if (args[0].equalsIgnoreCase("reload")){
			if (!reloadPermission){
				return true;
			}
			
			plugin.reloadConfig();
			
			sender.sendMessage(Phrase.NCHAT_CONFIG_RELOADED.parse());
		}
		return true;
	}
}
