package com.niccholaspage.nChat;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigHandler {
	private final File configFile;
	
	private final File phrasesFile;
	
	private final YamlConfiguration config;
	
	private String messageFormat;
	
	private String meFormat;
	
	private String colorCharacter;
	
	private String timestampFormat;
	
	private String joinMessage;
	
	private String leaveMessage;
	
	public ConfigHandler(File configFile, File phrasesFile){
		this.configFile = configFile;
		
		this.phrasesFile = phrasesFile;
		
		this.config = YamlConfiguration.loadConfiguration(configFile);
		
		load();
	}
	
	public void load(){
		config.setDefaults(getDefaultConfig());
		
		config.options().copyDefaults(true);
		
		//Converting of old configs by removing the options from nChat subsection and moving them to the main node
		ConfigurationSection nChatSection = config.getConfigurationSection("nChat");
		
		if (nChatSection != null){
			for (String key : nChatSection.getKeys(false)){
				config.set(key, nChatSection.get(key));
			}
			
			config.set("nChat", null);
		}
		
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		messageFormat = config.getString("messageformat");
		
		meFormat = config.getString("meformat");
		
		colorCharacter = config.getString("colorcharacter");
		
		timestampFormat = config.getString("timestampformat");
		
		joinMessage = config.getString("joinmessage");
		
		leaveMessage = config.getString("leavemessage");
		
		for (Phrase phrase : Phrase.values()){
			phrase.reset();
		}
		
		if (!phrasesFile.exists()){
			return;
		}
		
		YamlConfiguration phrasesConfig = YamlConfiguration.loadConfiguration(phrasesFile);
		
		Set<String> keys = phrasesConfig.getKeys(false);
		
		for (Phrase phrase : Phrase.values()){
			String phraseConfigName = phrase.getConfigName();
			
			if (keys.contains(phraseConfigName)){
				phrase.setMessage(phrasesConfig.getString(phraseConfigName));
			}
		}
	}
	
	private YamlConfiguration getDefaultConfig(){
		YamlConfiguration defaultConfig = new YamlConfiguration();
		
		defaultConfig.set("messageformat", "[+prefix+group+suffix&f] +name: +message");
		defaultConfig.set("meformat", "* +rname +message");
		defaultConfig.set("colorcharacter", "~");
		defaultConfig.set("timestampformat", "hh:mm:ss");
		defaultConfig.set("joinmessage", "&e+rname has joined the game");
		defaultConfig.set("leavemessage", "&e+rname has left the game");
		
		return defaultConfig;
	}
	
	public String getMessageFormat(){
		return messageFormat;
	}
	
	public String getMeFormat(){
		return meFormat;
	}
	
	public String getColorCharacter(){
		return colorCharacter;
	}
	
	public String getTimestampFormat(){
		return timestampFormat;
	}
	
	public String getJoinMessage(){
		return joinMessage;
	}
	
	public String getLeaveMessage(){
		return leaveMessage;
	}
}
