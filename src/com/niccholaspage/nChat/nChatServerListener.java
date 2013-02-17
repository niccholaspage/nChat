package com.niccholaspage.nChat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

public class nChatServerListener implements Listener {
	private final nChat plugin;
	
	public nChatServerListener(nChat plugin){
		this.plugin = plugin;
		
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onServerCommand(ServerCommandEvent event){
		String command = event.getCommand();
		
		if (command == null) return;

		if (command.toLowerCase().startsWith("/me ")){
			String message = command.substring(command.indexOf(" ")).trim();
			
			plugin.getServer().broadcastMessage(plugin.formatMessage(null, plugin.getAPI().getMeFormat(), message));
		}
	}
}
