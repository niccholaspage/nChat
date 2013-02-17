package com.niccholaspage.nChat.api;

import com.niccholaspage.nChat.nChat;

public class API {
	private final nChat plugin;
	
	public API(nChat plugin){
		this.plugin = plugin;
	}
	
	public String getMessageFormat(){
		return plugin.getConfig().getString("messageformat");
	}
	
	public String getMeFormat(){
		return plugin.getConfig().getString("meformat");
	}
	
	public String getTimestampFormat(){
		return plugin.getConfig().getString("timestampformat");
	}
	
	public String getJoinMessage(){
		return plugin.getConfig().getString("joinmessage");
	}
	
	public String getLeaveMessage(){
		return plugin.getConfig().getString("leavemessage");
	}
}
