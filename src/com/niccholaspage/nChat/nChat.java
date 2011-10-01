package com.niccholaspage.nChat;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.niccholaspage.nChat.api.ChatFormatEvent;
import com.niccholaspage.nChat.api.Node;
import com.niccholaspage.nChat.api.PlayerChatFormatEvent;
import com.niccholaspage.nChat.commands.*;

public class nChat extends JavaPlugin {
	private final nChatPlayerListener playerListener = new nChatPlayerListener(this);
	//Message Format
	public String messageFormat;
	//The me command message format
	public String meFormat;
	//Color character
	public String colorCharacter;
	//Timestamp format
	public String timestampFormat;
	//Join Message
	public String joinMessage;
	//Leave Message
	public String leaveMessage;
	//Permission Handler
	private PermissionsHandler permissionsHandler;
	
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
		
		permissionsHandler = new PermissionsHandler(getServer());
		
		readConfig();
		
		//Register commands
		getCommand("nchat").setExecutor(new nChatCommand(this));
		getCommand("me").setExecutor(new MeCommand(this));
		
		//Print that the plugin has been enabled!
		System.out.println("nChat version " + getDescription().getVersion() + " is enabled!");

	}
	
	public void readConfig() {
		getDataFolder().mkdir();
		
		File configFile = new File(getDataFolder(), "config.yml");
		
		try {
			configFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Configuration config = new Configuration(configFile);
		
		config.load();
		
		writeNode("nChat", "", config);
		writeNode("nChat.messageformat", "[+prefix+group+suffix&f] +name: +message", config);
		writeNode("nChat.meformat", "* +rname +message", config);
		writeNode("nChat.colorcharacter", "~", config);
		writeNode("nChat.timestampformat", "hh:mm:ss", config);
		writeNode("nChat.joinmessage", "&e+rname has joined the game", config);
		writeNode("nChat.leavemessage", "&e+rname has left the game", config);
		
		config.save();
		
		// Reading from yml file
		messageFormat = config.getString("nChat.messageformat");
		meFormat = config.getString("nChat.meformat");
		colorCharacter = config.getString("nChat.colorcharacter");
		timestampFormat = config.getString("nChat.timestampformat");
		joinMessage = config.getString("nChat.joinmessage");
		leaveMessage = config.getString("nChat.leavemessage");
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
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(timestampFormat);
		
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
				out = out.replace(colorCharacter, "\u00A7");
			}
		}
		
		out = out.replaceAll("%", "%%");
		
		return out;
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

	private void writeNode(String node,Object value, Configuration config){
		if (config.getProperty(node) == null) config.setProperty(node, value);
	}
}
