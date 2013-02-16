package com.niccholaspage.nChat.permissions;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.bananaco.bpermissions.api.ApiLayer;
import de.bananaco.bpermissions.api.util.CalculableType;

public class bPermissionsHandler implements PermissionsHandler {
	public boolean hasPermission(CommandSender sender, String permission) {
		if (sender instanceof Player){
			Player player = (Player) sender;
			
			return ApiLayer.hasPermission(player.getWorld().getName(), CalculableType.USER, player.getName(), permission);
		}else {
			return true;
		}
	}
	
	public String getGroup(String name, String world){
		return ApiLayer.getGroups(world, CalculableType.USER, name)[0];
	}
	
	public String getPrefix(String name, String world){
		return ApiLayer.getValue(world, CalculableType.USER, name, "prefix");
	}
	
	public String getSuffix(String name, String world){
		return ApiLayer.getValue(world, CalculableType.USER, name, "suffix");
	}
	
	public void reload(){
		
	}
}
