package com.niccholaspage.nChat.permissions;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.niccholaspage.nChat.nChat;

public class DinnerPermissionsHandler implements PermissionsHandler {
	private final nChat plugin;
	
	private final DinnerPermissionsConfigHandler configHandler;
	
	public DinnerPermissionsHandler(nChat plugin){
		this.plugin = plugin;
		
		File configFile = new File(plugin.getDataFolder(), "info.yml");

		try {
			configFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		configHandler = new DinnerPermissionsConfigHandler(configFile);
	}
	
	public boolean hasPermission(CommandSender sender, String permission) {
		return sender.hasPermission(permission);
	}
	
	public String getGroup(String name, String world) {
		return "Not Supported";
	}
	
	public String getPrefix(String name, String world) {
		Player player = plugin.getServer().getPlayer(name);
		
		if (player == null){
			return "Invalid Player";
		}
		
		for (String prefix : configHandler.getPrefixes().keySet()){
			if (hasPermission(player, "nChat.info." + prefix)){
				return configHandler.getPrefixes().get(prefix);
			}
		}
		
		return "Not Found";
	}
	
	public String getSuffix(String name, String world) {
		Player player = plugin.getServer().getPlayer(name);
		
		if (player == null){
			return "Invalid Player";
		}
		
		for (String suffix : configHandler.getSuffixes().keySet()){
			if (hasPermission(player, "nChat.info." + suffix)){
				return configHandler.getSuffixes().get(suffix);
			}
		}
		
		return "Not Found";
	}
}