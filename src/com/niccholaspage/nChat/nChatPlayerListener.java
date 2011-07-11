package com.niccholaspage.nChat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class nChatPlayerListener extends PlayerListener{
	 public nChat plugin;
	 public nChatPlayerListener(nChat plugin) {
		 this.plugin = plugin;
	 }
	 public void onPlayerJoin(PlayerJoinEvent event){
		 if (plugin.joinMessage == "") return;
		 Player player = event.getPlayer();
		 String[] old = new String[]{"+name", "+rname", "&"};
		 String[] replacements = new String[]{player.getDisplayName(), player.getName(), "¤"};
		 String out = plugin.replaceSplit(plugin.joinMessage, old, replacements);
		 out = out.replaceAll("%", "%%");
		 event.setJoinMessage(out);
	 }
	 public void onPlayerQuit(PlayerQuitEvent event){
		 if (plugin.leaveMessage == "") return;
		 Player player = event.getPlayer();
		 String[] old = new String[]{"+name", "+rname", "&"};
		 String[] replacements = new String[]{player.getDisplayName(), player.getName(), "¤"};
		 String out = plugin.replaceSplit(plugin.leaveMessage, old, replacements);
		 out = out.replaceAll("%", "%%");
		 event.setQuitMessage(out);
	 }
	 public void onPlayerKick(PlayerKickEvent event){
		 if (plugin.leaveMessage == "") return;
		 Player player = event.getPlayer();
		 String[] old = new String[]{"+name", "+rname", "&"};
		 String[] replacements = new String[]{player.getDisplayName(), player.getName(), "¤"};
		 String out = plugin.replaceSplit(plugin.leaveMessage, old, replacements);
		 out = out.replaceAll("%", "%%");
		 event.setLeaveMessage(out);
	 }
	@SuppressWarnings("deprecation")
	public void onPlayerChat(PlayerChatEvent event) {
		  Player player = event.getPlayer();
		  String message = event.getMessage();
		  if (message.startsWith("/")) return;
		  String out = plugin.messageFormat;
		  String world = player.getWorld().getName();
		  String group;
		  String prefix;
		  String suffix;
		  String userPrefix;
		  String userSuffix;
		  if (plugin.permissions3){
			  group = plugin.Permissions.getPrimaryGroup(world, player.getName());
			  prefix = plugin.Permissions.getGroupRawPrefix(world, group);
			  suffix = plugin.Permissions.getGroupRawSuffix(world, group);
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
		event.setFormat(out);
	  }
}
