package com.niccholaspage.nChat.permissions;

import org.bukkit.command.CommandSender;

public interface PermissionsHandler {
	public boolean hasPermission(CommandSender sender, String permission);
	
	public String getGroup(String name, String world);
	
	public String getPrefix(String name, String world);
	
	public String getSuffix(String name, String world);
}