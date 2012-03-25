package com.niccholaspage.nChat.permissions;

import org.bukkit.entity.Player;

import de.bananaco.bpermissions.api.ApiLayer;
import de.bananaco.bpermissions.api.util.CalculableType;

public class bPermissionsPlayerHandler implements PlayerPermissionsHandler {
	private final String name;
	
	private final String world;
	
	public bPermissionsPlayerHandler(Player player){
		name = player.getName();
		
		world = player.getWorld().getName();
	}
	
	public String getGroup(){
		return ApiLayer.getGroups(world, CalculableType.USER, name)[0];
	}
	
	public String getPrefix(){
		return ApiLayer.getValue(world, CalculableType.USER, name, "prefix");
	}
	
	public String getSuffix(){
		return ApiLayer.getValue(world, CalculableType.USER, name, "suffix");
	}
}
