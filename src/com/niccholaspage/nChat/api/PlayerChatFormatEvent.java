package com.niccholaspage.nChat.api;

import org.bukkit.entity.Player;

public class PlayerChatFormatEvent extends ChatFormatEvent {
	private final Player player;

	public PlayerChatFormatEvent(Player player) {
		this.player = player;
	}
	
	public Player getPlayer(){
		return player;
	}
}
