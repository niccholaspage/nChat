package com.niccholaspage.nChat.permissions;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface PermissionsHandler {
	public boolean hasPermission(CommandSender sender, String permission);
	
	public PlayerPermissionsHandler getPlayerPermissionsHandler(Player player);
	
	public void reload();
}