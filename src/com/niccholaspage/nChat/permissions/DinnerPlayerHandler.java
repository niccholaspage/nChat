package com.niccholaspage.nChat.permissions;

import java.util.Map;

import org.bukkit.entity.Player;

public class DinnerPlayerHandler implements PlayerPermissionsHandler {
	private final Player player;
	
	private final DinnerPermissionsHandler handler;
	
	private final DinnerPermissionsConfigHandler configHandler;
	
	public DinnerPlayerHandler(Player player, DinnerPermissionsHandler handler, DinnerPermissionsConfigHandler configHandler){
		this.player = player;
		
		this.handler = handler;
		
		this.configHandler = configHandler;
	}
	
	public String getGroup(){
		return "Not Supported";
	}
	
	private String getInfo(String value, Map<String, String> map){
		for (String loop : map.keySet()){
			if (handler.hasPermission(player, "nChat.info." + loop)){
				return map.get(loop);
			}
		}
		
		return "Not Found";
	}
	
	public String getPrefix(){
		return getInfo("prefix", configHandler.getPrefixes());
	}
	
	public String getSuffix(){
		return getInfo("suffix", configHandler.getSuffixes());
	}
}
