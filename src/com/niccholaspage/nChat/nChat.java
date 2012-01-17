package com.niccholaspage.nChat;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.niccholaspage.nChat.permissions.*;
import com.niccholaspage.nChat.permissions.PermissionsHandler;
import com.niccholaspage.nChat.api.ChatFormatEvent;
import com.niccholaspage.nChat.api.Node;
import com.niccholaspage.nChat.api.PlayerChatFormatEvent;
import com.niccholaspage.nChat.commands.*;

public class nChat extends JavaPlugin {
	private final nChatPlayerListener playerListener = new nChatPlayerListener(this);
	//Permissions Handler
	private PermissionsHandler permissionsHandler;
	//Config Handler
	private ConfigHandler configHandler;
	
	public void onDisable() {
		System.out.println("nChat Disabled");

	}
	
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		
		//Register events (like a boss)
		pm.registerEvent(Event.Type.PLAYER_CHAT, playerListener, Priority.Lowest, this);
		pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Lowest, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Lowest, this);
		pm.registerEvent(Event.Type.PLAYER_KICK, playerListener, Priority.Lowest, this);
		
		setupPermissions();
		
		loadConfig();
		
		//Register commands
		getCommand("nchat").setExecutor(new nChatCommand(this));
		getCommand("me").setExecutor(new MeCommand(this));
		
		//Print that the plugin has been enabled!
		System.out.println("nChat version " + getDescription().getVersion() + " is enabled!");

	}
	
	private void setupPermissions(){
		Plugin permissions = getServer().getPluginManager().getPlugin("Permissions");
		
		Plugin PEX = getServer().getPluginManager().getPlugin("PermissionsEx");
		
		if(PEX != null){
			permissionsHandler = new PermissionsExHandler();
		}else if (permissions != null) {
			String version = permissions.getDescription().getVersion();
			
			if (version.startsWith("3")){
				permissionsHandler = new Permissions3Handler(permissions);
			}else {
				permissionsHandler = new Permissions2Handler(permissions);
			}
		}else {
			permissionsHandler = new DinnerPermissionsHandler();
		}
	}
	
	public void loadConfig(){
		File configFile = new File(getDataFolder(), "config.yml");

		try {
			configFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		configHandler = new ConfigHandler(configFile);
	}
	
	public String formatMessage(Player player, String out, String message){
		if (out == null || out == ""){
			return null;
		}
		
		String name = "";
		
		String displayName = "";
		
		String world = "";
		
		String group = "";
		
		String prefix = "";
		
		String suffix = "";
		
		if (player != null){
			name = player.getName();
			
			displayName = player.getDisplayName();
			
			world = player.getWorld().getName();
			
			group = permissionsHandler.getGroup(name, world);
			
			prefix = permissionsHandler.getPrefix(name, world);
			
			suffix = permissionsHandler.getSuffix(name, world);
		}
		
		Date now = new Date();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(configHandler.getTimestampFormat());
		
		String time = dateFormat.format(now);
		
		String[] old = new String[]{"+name", "+rname", "+group", "+prefix", "+suffix", "+world", "+timestamp", "&", "+message"};
		
		String[] replacements = new String[]{displayName, name, group, prefix, suffix, world, time, "\u00A7", message};
		
		out = replaceSplit(out, old, replacements);
		
		//API time
		ChatFormatEvent event;
		
		if (player != null){
			event = new PlayerChatFormatEvent(player);
		}else {
			event = new ChatFormatEvent();
		}
		
		getServer().getPluginManager().callEvent(event);
		
		for (Node node : event.getNodes()){
			if (node.getValue() == null){
				continue;
			}
			
			out = out.replace("+" + node.getName(), node.getValue());
		}
		
		if (player != null){
			if ((permissionsHandler.hasPermission(player, "nChat.colors")) || (permissionsHandler.hasPermission(player, "nChat.colours"))) {
				out = out.replace(configHandler.getColorCharacter(), "\u00A7");
			}
		}
		
		out = out.replaceAll("%", "%%");
		
		return out;
	}
	
	public ConfigHandler getConfigHandler(){
		return configHandler;
	}
	
	public PermissionsHandler getPermissionsHandler(){
		return permissionsHandler;
	}

	public String replaceSplit(String str, String[] search, String[] replace) {
		if (search.length != replace.length) return "";
		
		for (int i = 0; i < search.length; i++){
			String[] split = search[i].split(",");
			
			for (int j = 0; j < split.length; j++){
				if (replace[i] == null) continue;
				
				str = str.replace(split[j], replace[i]);
			}
		}
		
		return str;
	}
}
