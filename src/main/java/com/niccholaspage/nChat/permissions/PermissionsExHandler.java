package com.niccholaspage.nChat.permissions;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PermissionsExHandler implements PermissionsHandler {
	private final PermissionManager pexHandler;
	
	public PermissionsExHandler(){
		this.pexHandler = PermissionsEx.getPermissionManager();
	}
	
	public boolean hasPermission(CommandSender sender, String permission){
		if (sender instanceof Player){
			Player player = (Player) sender;
			
			return pexHandler.has(player, permission);
		} else {
			return true;
		}
	}
	
	public PlayerPermissionsHandler getPlayerPermissionsHandler(Player player){
		return new PermissionsExPlayerHandler(player, pexHandler.getUser(player));
	}
	
	public void reload(){
		
	}
}
