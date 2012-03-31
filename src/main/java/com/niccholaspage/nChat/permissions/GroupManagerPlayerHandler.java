package com.niccholaspage.nChat.permissions;

import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GroupManagerPlayerHandler implements PlayerPermissionsHandler {
	private final String name;
	
	private final AnjoPermissionsHandler handler;
	
	public GroupManagerPlayerHandler(Player player, AnjoPermissionsHandler handler){
		name = player.getName();
		
		this.handler = handler;
	}
	
	public boolean hasPermission(CommandSender sender, String permission) {
		return true;
	}
	
	public String getGroup(){
		return handler.getGroup(name);
	}
	
	public String getPrefix(){
		return handler.getUserPrefix(name);
	}
	
	public String getSuffix(){
		return handler.getUserSuffix(name);
	}
}
