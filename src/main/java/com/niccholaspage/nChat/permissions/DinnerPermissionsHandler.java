package com.niccholaspage.nChat.permissions;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.niccholaspage.nChat.nChat;

public class DinnerPermissionsHandler implements PermissionsHandler {
	private final nChat plugin;
	
	private DinnerPermissionsConfigHandler configHandler;
	
	public DinnerPermissionsHandler(nChat plugin){
		this.plugin = plugin;
	}
	
	public boolean hasPermission(CommandSender sender, String permission) {
		return sender.hasPermission(permission);
	}
	
	public String getGroup(String name, String world) {
		return "Not Supported";
	}
	
	private String getInfo(String name, String world, String value, Map<String, String> map){
		Player player = plugin.getServer().getPlayer(name);
		
		if (player == null){
			return "Invalid Player";
		}
		
		for (String loop : map.keySet()){
			if (hasPermission(player, "nChat.info." + loop)){
				return map.get(loop);
			}
		}
		
		return "Not Found";
	}
	
	public String getPrefix(String name, String world){
		return getInfo(name, world, "prefix", configHandler.getPrefixes());
	}
	
	public String getSuffix(String name, String world){
		return getInfo(name, world, "suffix", configHandler.getSuffixes());
	}
	
	public void reload(){
		File configFile = new File(plugin.getDataFolder(), "info.yml");

		try {
			configFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		configHandler = new DinnerPermissionsConfigHandler(configFile);
	}
}