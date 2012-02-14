package com.niccholaspage.nChat;

public enum Phrase {
	ENABLED("nChat $0 is now enabled."),
	DISABLED("nChat $0 is now disabled."),
	NCHAT_COMMAND_CREDIT("nChat $0 by niccholaspage"),
	NCHAT_CONFIG_RELOAD_HOW_TO("Type /nChat reload to reload the configuration"),
	NCHAT_CONFIG_RELOADED("The nChat configuration has been reloaded.");

	private String message;

	private Phrase(String message){
		this.message = message;
	}

	public void setMessage(String message){
		this.message = message;
	}

	private String getMessage(){
		return message;
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
}