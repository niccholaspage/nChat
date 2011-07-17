package com.niccholaspage.nChat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class nChatPlayerListener extends PlayerListener{
	public nChat plugin;

	public nChatPlayerListener(nChat plugin) {
		this.plugin = plugin;
	}
	@SuppressWarnings("deprecation")
	private String formatMessage(String out, PlayerEvent event){
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
		if (plugin.permissions3){
			group = plugin.Permissions.getPrimaryGroup(world, player.getName());
			userPrefix = plugin.Permissions.getUserPrefix(world, player.getName());
			userSuffix = plugin.Permissions.getUserSuffix(world, player.getName());
		}else {
			group = plugin.Permissions.getGroup(world, player.getName());
			prefix = plugin.Permissions.getGroupPrefix(world, group);
			suffix = plugin.Permissions.getGroupSuffix(world, group);
			userPrefix = plugin.Permissions.getPermissionString(world, player.getName(), "prefix");
			userSuffix = plugin.Permissions.getPermissionString(world, player.getName(), "suffix");
		}
		if (userPrefix != null) prefix = userPrefix;
		if (userSuffix != null) suffix = userSuffix;
		if (prefix == null) prefix = "";
		if (suffix == null) suffix = "";
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(plugin.timestampFormat);
		String time = dateFormat.format(now);
		String[] old = new String[]{"+name", "+rname", "+group", "+prefix", "+suffix", "+world", "+timestamp", "&", "+message"};
		String[] replacements = new String[]{player.getDisplayName(), player.getName(), group, prefix, suffix, world, time, "¤", message};
		out = plugin.replaceSplit(out, old, replacements);
		if ((plugin.Permissions.has(player, "nChat.colors")) || (plugin.Permissions.has(player, "nChat.colours"))) {
			out = out.replace(plugin.colorCharacter, "¤");
		}
		out = out.replaceAll("%", "%%");
		return out;
	}
	public void onPlayerJoin(PlayerJoinEvent event){
		event.setJoinMessage(formatMessage(plugin.joinMessage, event));
	}
	public void onPlayerQuit(PlayerQuitEvent event){
		event.setQuitMessage(formatMessage(plugin.leaveMessage, event));
	}
	public void onPlayerKick(PlayerKickEvent event){
		event.setLeaveMessage(formatMessage(plugin.leaveMessage, event));
	}
	public void onPlayerChat(PlayerChatEvent event) {
		if (event.getMessage().startsWith("/")) return;
		event.setFormat(formatMessage(plugin.messageFormat, event));
	}
}
