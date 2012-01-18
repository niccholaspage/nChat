package com.niccholaspage.nChat;

import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.server.ServerListener;

public class nChatServerListener extends ServerListener {
	private final nChat plugin;
	
	public nChatServerListener(nChat plugin){
		this.plugin = plugin;
	}
	
	public void onServerCommand(ServerCommandEvent event){
		String command = event.getCommand();
		
		if (command == null) return;

		if (command.toLowerCase().startsWith("/me ")){
			String message = command.substring(command.indexOf(" ")).trim();
			
			plugin.getServer().broadcastMessage(plugin.formatMessage(null, plugin.getConfigHandler().getMeFormat(), message));
		}
	}
}
