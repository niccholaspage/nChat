package com.niccholaspage.nChat;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.niccholaspage.nChat.commands.*;
import com.nijiko.permissions.Group;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class nChat extends JavaPlugin {
	private final nChatPlayerListener playerListener = new nChatPlayerListener(this);
	//Permissions Handler
	public PermissionHandler Permissions;
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
	//Is Permissions 3?
	public boolean permissions3;
	@Override
	public void onDisable() {
		System.out.println("nChat Disabled");

	}
	@Override
	public void onEnable() {
		//Create the pluginmanage pm.
		PluginManager pm = getServer().getPluginManager();
		//Register events (like a boss)
		pm.registerEvent(Event.Type.PLAYER_CHAT, playerListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_KICK, playerListener, Event.Priority.Normal, this);
		//Register commands
		getCommand("nchat").setExecutor(new nChatCommand(this));
		//Get the infomation from the yml file.
		PluginDescriptionFile pdfFile = getDescription();
		//Setup Permissions
		setupPermissions();
		readConfig();
		//Print that the plugin has been enabled!
		System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );

	}
	public void readConfig() {
		new File("plugins/nChat/").mkdir();
		try {
			new File("plugins/nChat/config.yml").createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Configuration config = new Configuration(new File("plugins/nChat/config.yml"));
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
		colorCharacter = config.getString("nChat.colorcharacter");
		timestampFormat = config.getString("nChat.timestampformat");
		joinMessage = config.getString("nChat.joinmessage");
		leaveMessage = config.getString("nChat.leavemessage");
	}
	@SuppressWarnings("deprecation")
	public String formatMessage(String out, PlayerEvent event){
		if (out == null || out == ""){
			return null;
		}
		String message = "";
		if (event instanceof PlayerChatEvent){
			message = ((PlayerChatEvent)event).getMessage();
		}
		Player player = event.getPlayer();
		String world = player.getWorld().getName();
		String group;
		String prefix = null;
		String suffix = null;
		String userPrefix;
		String userSuffix;
		if (permissions3){
			group = Permissions.getPrimaryGroup(world, player.getName());
			Group groupObject = Permissions.getDefaultGroup("?");
			if (Permissions.getDefaultGroup(world) == null && group.equals("Default") && groupObject != null){
				group = groupObject.getName();
			}
			userPrefix = Permissions.getUserPrefix(world, player.getName());
			userSuffix = Permissions.getUserSuffix(world, player.getName());
		}else {
			group = Permissions.getGroup(world, player.getName());
			prefix = Permissions.getGroupPrefix(world, group);
			suffix = Permissions.getGroupSuffix(world, group);
			userPrefix = Permissions.getPermissionString(world, player.getName(), "prefix");
			userSuffix = Permissions.getPermissionString(world, player.getName(), "suffix");
		}
		if (userPrefix != null) prefix = userPrefix;
		if (userSuffix != null) suffix = userSuffix;
		if (prefix == null) prefix = "";
		if (suffix == null) suffix = "";
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(timestampFormat);
		String time = dateFormat.format(now);
		String[] old = new String[]{"+name", "+rname", "+group", "+prefix", "+suffix", "+world", "+timestamp", "&", "+message"};
		String[] replacements = new String[]{player.getDisplayName(), player.getName(), group, prefix, suffix, world, time, "\u00A7", message};
		out = replaceSplit(out, old, replacements);
		if ((Permissions.has(player, "nChat.colors")) || (Permissions.has(player, "nChat.colours"))) {
			out = out.replace(colorCharacter, "\u00A7");
		}
		out = out.replaceAll("%", "%%");
		return out;
	}
	private void setupPermissions() {
		Plugin perm = getServer().getPluginManager().getPlugin("Permissions");
		if (perm != null) {
			Permissions = ((Permissions)perm).getHandler();
			permissions3 = perm.getDescription().getVersion().startsWith("3");
		} else {
			System.out.println("[nChat] Permissions not detected, no formatting.");
		}
	}

	public String replaceSplit(String str, String[] search, String[] replace) {
		if (search.length != replace.length) return "";
		for (int i = 0; i < search.length; i++){
			String[] split = search[i].split(",");
			for (int j = 0; j < split.length; j++){
				str = str.replace(split[j], replace[i]);
			}
		}
		return str;
	}

	private void writeNode(String node,Object value, Configuration config){
		if (config.getProperty(node) == null) config.setProperty(node, value);
	}

	public boolean hasPermission(CommandSender sender, String permission){
		if (sender instanceof Player){
			return Permissions.has((Player)sender, permission);
		}else {
			return true;
		}
	}
}
