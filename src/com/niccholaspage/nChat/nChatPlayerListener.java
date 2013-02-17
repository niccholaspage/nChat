package com.niccholaspage.nChat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class nChatPlayerListener implements Listener {
	public nChat plugin;

	public nChatPlayerListener(nChat plugin) {
		this.plugin = plugin;
		
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event){
		if (event.isCancelled()){
			return;
		}
		
		Player player = event.getPlayer();
		
		String command = event.getMessage();
		
		if (command == null) return;

		if (command.toLowerCase().startsWith("/me ")){
			String message = command.substring(command.indexOf(" ")).trim();
			
			plugin.getServer().broadcastMessage(plugin.formatMessage(player, plugin.getAPI().getMeFormat(), message));
			
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent event){
		event.setJoinMessage(plugin.formatMessage(event.getPlayer(), plugin.getAPI().getJoinMessage(), event.getJoinMessage()));
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerQuit(PlayerQuitEvent event){
		event.setQuitMessage(plugin.formatMessage(event.getPlayer(),  plugin.getAPI().getLeaveMessage(), event.getQuitMessage()));
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerKick(PlayerKickEvent event){
		event.setLeaveMessage(plugin.formatMessage(event.getPlayer(),  plugin.getAPI().getLeaveMessage(), event.getLeaveMessage()));
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerChat(PlayerChatEvent event){
		if (event.getMessage().startsWith("/")) return;
		
		event.setFormat(plugin.formatMessage(event.getPlayer(), plugin.getAPI().getMessageFormat(), event.getMessage()));
	}
}
