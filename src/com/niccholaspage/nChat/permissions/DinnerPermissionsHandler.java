package com.niccholaspage.nChat.permissions;

import org.bukkit.command.CommandSender;

public class DinnerPermissionsHandler implements PermissionsHandler {
	
	public boolean hasPermission(CommandSender sender, String permission) {
		return sender.hasPermission(permission);
	}
	
	public String getGroup(String name, String world) {
		return "Not Supported";
	}
	
	public String getPrefix(String name, String world) {
		return "Not Supported";
	}
	
	public String getSuffix(String name, String world) {
		return "Not Supported";
	}
}