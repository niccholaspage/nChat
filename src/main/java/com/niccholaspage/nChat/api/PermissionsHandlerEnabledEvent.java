package com.niccholaspage.nChat.api;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.niccholaspage.nChat.permissions.PermissionsHandler;

public class PermissionsHandlerEnabledEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	
	private PermissionsHandler permissionsHandler;

	public PermissionsHandlerEnabledEvent(){
		permissionsHandler = null;
	}
	
	public void setPermissionsHandler(PermissionsHandler permissionsHandler){
		this.permissionsHandler = permissionsHandler;
	}
	
	public PermissionsHandler getPermissionsHandler(){
		return permissionsHandler;
	}

	public HandlerList getHandlers(){
		return handlers;
	}

	public static HandlerList getHandlerList(){
		return handlers;
	}
}
