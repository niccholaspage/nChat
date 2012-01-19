package com.niccholaspage.nChat.permissions;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class DinnerPermissionsConfigHandler {
	private File configFile;
	
	private YamlConfiguration config;
	
	private Map<String, String> prefixes;
	
	private Map<String, String> suffixes;
	
	public DinnerPermissionsConfigHandler(File configFile){
		this.configFile = configFile;
		
		this.config = YamlConfiguration.loadConfiguration(configFile);
		
		load();
	}
	
	public void load(){
		config.setDefaults(getDefaultConfig());
		
		config.options().copyDefaults(true);
		
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ConfigurationSection prefixesSection = config.getConfigurationSection("prefixes");
		
		prefixes = new HashMap<String, String>();
		
		if (prefixesSection != null){
			for (String key : prefixesSection.getKeys(false)){
				String value = prefixesSection.getString(key);
				
				if (value != null){
					prefixes.put(key, value);
				}
			}
		}
		
		ConfigurationSection suffixesSection = config.getConfigurationSection("suffixes");
		
		suffixes = new HashMap<String, String>();
		
		if (suffixesSection != null){
			for (String key : suffixesSection.getKeys(false)){
				String value = suffixesSection.getString(key);
				
				if (value != null){
					suffixes.put(key, value);
				}
			}
		}
	}
	
	private YamlConfiguration getDefaultConfig(){
		YamlConfiguration defaultConfig = new YamlConfiguration();
		
		defaultConfig.set("prefixes.admin", "Admin");
		
		defaultConfig.set("suffixes.admin", "Example");
		
		return defaultConfig;
	}
	
	public Map<String, String> getPrefixes(){
		return prefixes;
	}
	
	public Map<String, String> getSuffixes(){
		return suffixes;
	}
}
