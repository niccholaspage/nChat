package com.niccholaspage.nChat.api;

import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;

public class nChatEventListener extends CustomEventListener {
	/**
	 * Called when nChat is formatting a chat message
	 * 
	 * @param event
	 */
	public void onChatFormat(ChatFormatEvent event) {

	}
	
	public void onCustomEvent(Event event){
		if (event instanceof ChatFormatEvent){
			onChatFormat((ChatFormatEvent) event);
		}
	}
}
