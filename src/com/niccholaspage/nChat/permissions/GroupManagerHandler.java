package com.niccholaspage.nChat.permissions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.niccholaspage.nChat.nChat;

public class GroupManagerHandler implements PermissionsHandler {
	private final nChat plugin;
	
	private final GroupManager groupManager;
	
	private final Map<Player, AnjoPermissionsHandler> handlers;
	
	public GroupManagerHandler(nChat plugin, Plugin groupManagerPlugin){
		this.plugin = plugin;
		
		groupManager = (GroupManager) groupManagerPlugin;
		
		handlers = new HashMap<Player, AnjoPermissionsHandler>();
	}
	
	private void cleanupHandlers(){
		for (Player player : new HashSet<Player>(handlers.keySet())){
			if (player == null || !player.isOnline()){
				handlers.remove(player);
			}
		}
	}
	
	private AnjoPermissionsHandler getHandler(Player player){
		cleanupHandlers();
		
		AnjoPermissionsHandler handler = handlers.get(player);
		
		if (handler == null){
			handler = groupManager.getWorldsHolder().getWorldPermissions(player);
			
			handlers.put(player, handler);
		}
		
		return handler;
	}
	
	public boolean hasPermission(CommandSender sender, String permission) {
		return true;
	}
	
	public String getGroup(String name, String world){
		Player player = plugin.getServer().getPlayer(name);
		
		if (player == null){
			return "Invalid Player";
		}
		
		return getHandler(player).getGroup(player.getName());
	}
	
	public String getPrefix(String name, String world){
		Player player = plugin.getServer().getPlayer(name);
		
		if (player == null){
			return "Invalid Player";
		}
		
		return getHandler(player).getUserPrefix(player.getName());
	}
	
	public String getSuffix(String name, String world){
		Player player = plugin.getServer().getPlayer(name);
		
		if (player == null){
			return "Invalid Player";
		}
		return getHandler(player).getUserSuffix(player.getName());
	}
	
	public void reload(){
		
	}
}
