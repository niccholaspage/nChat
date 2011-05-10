package com.niccholaspage.nChat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

public class nChatPlayerListener extends PlayerListener{
	 public static nChat plugin;
	  public nChatPlayerListener(nChat instance) {
	        plugin = instance;
	    }
	  public void onPlayerChat(PlayerChatEvent event) {
		  Player player = event.getPlayer();
		  String message = event.getMessage();
		  if (message.startsWith("/")) return;
		  String out = plugin.messageFormat;
		  String world = player.getWorld().getName();
		  String group = plugin.Permissions.getGroup(world, player.getName());
		  String prefix = plugin.Permissions.getGroupPrefix(world, group);
		  String suffix = plugin.Permissions.getGroupSuffix(world, group);
		  String userPrefix = plugin.Permissions.getPermissionString(world, player.getName(), "prefix");
		  String userSuffix = plugin.Permissions.getPermissionString(world, player.getName(), "suffix");
		  if (userPrefix != null) prefix = userPrefix;
		  if (userSuffix != null) suffix = userSuffix;
		  if (prefix == null) prefix = "";
		  if (suffix == null) suffix = "";
		  Date now = new Date();
		  SimpleDateFormat dateFormat = new SimpleDateFormat(plugin.timestampFormat);
		  String time = dateFormat.format(now);
		  String[] old = new String[]{"+name", "+group", "+prefix", "+suffix", "+world", "+timestamp", "&", "+message"};
		  String[] replacements = new String[]{player.getDisplayName(), group, prefix, suffix, world, time, "¤", message};
		  out = plugin.replaceSplit(out, old, replacements);
		if ((plugin.Permissions.has(player, "nChat.colors")) || (plugin.Permissions.has(player, "nChat.colours"))) {
			out = out.replace(plugin.colorCharacter, "¤");
		}
		out = out.replaceAll("%", "%%");
		event.setFormat(out);
	  }
}
