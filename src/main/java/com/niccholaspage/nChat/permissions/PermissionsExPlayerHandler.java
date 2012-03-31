package com.niccholaspage.nChat.permissions;

import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionUser;

public class PermissionsExPlayerHandler implements PlayerPermissionsHandler {
	private final PermissionUser user;
	
	private final String world;
	
	public PermissionsExPlayerHandler(Player player, PermissionUser user){
		world = player.getWorld().getName();
		
		this.user = user;
	}
	
	public String getGroup(){
		return user.getGroups(world)[0].getName();
	}
	
	public String getPrefix(){
		return user.getPrefix(world);
	}
	
	public String getSuffix(){
		return user.getSuffix(world);
	}
}
