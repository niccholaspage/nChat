package com.niccholaspage.nChat.permissions;

import java.io.File;
import java.io.IOException;

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
	
	public PlayerPermissionsHandler getPlayerPermissionsHandler(Player player){
		return new DinnerPlayerHandler(player, this, configHandler);
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