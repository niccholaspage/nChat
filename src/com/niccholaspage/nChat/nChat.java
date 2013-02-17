package com.niccholaspage.nChat;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.niccholaspage.nChat.api.API;
import com.niccholaspage.nChat.api.ChatFormatEvent;
import com.niccholaspage.nChat.api.Node;
import com.niccholaspage.nChat.api.PlayerChatFormatEvent;
import com.niccholaspage.nChat.commands.*;

public class nChat extends JavaPlugin {
	private Permission permission;

	private Chat chat;

	private API api;

	public void onEnable(){
		new nChatPlayerListener(this);

		new nChatServerListener(this);

		if (!setupPermissions() || !setupChat()){
			log(Phrase.FAILED_ENABLE);

			setEnabled(false);

			return;
		}

		getDataFolder().mkdirs();

		getConfig().options().copyDefaults(true);

		saveConfig();

		api = new API(this);

		getCommand("nchat").setExecutor(new nChatCommand(this));
	}

	public void log(Phrase phrase, String... args){
		log(phrase.parse(args));
	}

	public void log(String message){
		getLogger().info("[nChat] " + message);
	}

	public API getAPI(){
		return api;
	}

	private boolean setupPermissions(){
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);

		if (permissionProvider != null){
			permission = permissionProvider.getProvider();
		}

		return permission != null;
	}

	private boolean setupChat(){
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(Chat.class);

		if (chatProvider != null){
			chat = chatProvider.getProvider();
		}

		return chat != null;
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

			group = permission.getPrimaryGroup(world, name);

			prefix = chat.getPlayerPrefix(world, name);

			suffix = chat.getPlayerSuffix(world, name);
		}

		Date now = new Date();

		SimpleDateFormat dateFormat = new SimpleDateFormat(api.getTimestampFormat());

		String time = dateFormat.format(now);

		String[] old = new String[]{"+name", "+rname", "+group", "+prefix", "+suffix", "+world", "+timestamp", "&", "+message"};

		String[] replacements = new String[]{displayName, name, group, prefix, suffix, world, time, "\u00A7", message};

		out = replaceSplit(out, old, replacements);

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

		out = out.replaceAll("%", "%%");

		return out;
	}

	public String replaceSplit(String str, String[] search, String[] replace){
		if (search.length != replace.length){
			return "";
		}

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
