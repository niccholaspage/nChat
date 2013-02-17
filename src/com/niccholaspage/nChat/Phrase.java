package com.niccholaspage.nChat;

import org.bukkit.command.CommandSender;

public enum Phrase {
	COMMAND_CREDIT("nChat $1 by niccholaspage"),
	CONFIG_RELOAD_HOW_TO("Type /$1 reload to reload the configuration"),
	CONFIG_RELOADED("The nChat configuration has been reloaded."),
	FAILED_ENABLE("Failed to setup permissions and/or chat! Disabling nChat.");

	private final String defaultMessage;
	
	private final boolean categorized;

	private String message;
	
	private Phrase(String defaultMessage){
		this(defaultMessage, false);
	}

	private Phrase(String defaultMessage, boolean categorized){
		this.defaultMessage = defaultMessage;
		
		this.categorized = categorized;

		message = defaultMessage + "";
	}

	public void setMessage(String message){
		this.message = message;
	}

	private String getMessage(){
		return message;
	}

	public void reset(){
		message = defaultMessage + "";
	}

	public String getConfigName(){
		String name = name();
		
		if (categorized){
			name = name.replaceFirst("_", ".");
		}
		
		return name.toLowerCase();
	}

	public String parse(String... params){
		String parsedMessage = getMessage();

		if (params != null){
			for (int i = 0; i < params.length; i++){
				parsedMessage = parsedMessage.replace("$" + (i + 1), params[i]);
			}
		}

		return parsedMessage;
	}
	
	public String parseWithoutSpaces(String... params){
		return parse(params).replace(" ", "");
	}
	
	public void send(CommandSender sender, String... params){
		sender.sendMessage(parse(params));
	}
}