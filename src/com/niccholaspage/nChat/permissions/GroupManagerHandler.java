package com.niccholaspage.nChat.permissions;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class GroupManagerHandler implements PermissionsHandler {
	private final GroupManager groupManager;
	
	public GroupManagerHandler(Plugin groupManagerPlugin){
		groupManager = (GroupManager) groupManagerPlugin;
	}
	
	public boolean hasPermission(CommandSender sender, String permission) {
		if (sender instanceof Player){
			Player player = (Player) sender;
			
			return getHandler(player).has(player, permission);
		}else {
			return true;
		}
	}
	
	private AnjoPermissionsHandler getHandler(Player player){
		return groupManager.getWorldsHolder().getWorldPermissions(player);
	}
	
	public PlayerPermissionsHandler getPlayerPermissionsHandler(Player player){
		AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getWorldPermissions(player);
		
		return new GroupManagerPlayerHandler(player, handler);
	}
	
	public void reload(){
		
	}
}
