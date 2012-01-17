package com.niccholaspage.nChat;

import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class nChatPlayerListener extends PlayerListener {
	public nChat plugin;

	public nChatPlayerListener(nChat plugin) {
		this.plugin = plugin;
	}
	public void onPlayerJoin(PlayerJoinEvent event){
		event.setJoinMessage(plugin.formatMessage(event.getPlayer(), plugin.getConfigHandler().getJoinMessage(), null));
	}
	public void onPlayerQuit(PlayerQuitEvent event){
		event.setQuitMessage(plugin.formatMessage(event.getPlayer(),  plugin.getConfigHandler().getLeaveMessage(), null));
	}
	public void onPlayerKick(PlayerKickEvent event){
		event.setLeaveMessage(plugin.formatMessage(event.getPlayer(),  plugin.getConfigHandler().getLeaveMessage(), null));
	}
	public void onPlayerChat(PlayerChatEvent event) {
		if (event.getMessage().startsWith("/")) return;
		
		event.setFormat(plugin.formatMessage(event.getPlayer(),  plugin.getConfigHandler().getMessageFormat(), event.getMessage()));
	}
}
