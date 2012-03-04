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
		
		prefixes = initInfo(prefixesSection);
		
		ConfigurationSection suffixesSection = config.getConfigurationSection("suffixes");
		
		suffixes = initInfo(suffixesSection);
	}
	
	private Map<String, String> initInfo(ConfigurationSection section){
		Map<String, String> map = new HashMap<String, String>();
		
		if (section != null){
			for (String key : section.getKeys(false)){
				String value = section.getString(key);
				
				if (value != null){
					map.put(key, value);
				}
			}
		}
		
		return map;
	}
	
	private YamlConfiguration getDefaultConfig(){
		YamlConfiguration defaultConfig = new YamlConfiguration();
		
		return defaultConfig;
	}
	
	public Map<String, String> getPrefixes(){
		return prefixes;
	}
	
	public Map<String, String> getSuffixes(){
		return suffixes;
	}
}
