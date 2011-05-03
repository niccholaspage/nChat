package com.niccholaspage.nChat;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

public class nChatPlayerListener extends PlayerListener{
	 public static nChat plugin;
	 static String messageFormat;
	 static String colorcharacter;
	  public nChatPlayerListener(nChat instance) {
	        plugin = instance;
	    }
	  public void setMessageFormat(String mf, String c){
		  messageFormat = mf;
		  colorcharacter = c;
	  }
	  public void onPlayerChat(PlayerChatEvent event) {
		  Player player = event.getPlayer();
		  String message = event.getMessage();
		  if (message.startsWith("/")) return;
		  String out = messageFormat;
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
		  String[] old = new String[]{"+name", "+group", "+prefix", "+suffix", "+world", "&", "+message"};
		  String[] replacements = new String[]{player.getDisplayName(), group, prefix, suffix, world, "¤", message};
		  out = plugin.replaceSplit(out, old, replacements);
		  out = out.replaceAll("(&([a-f0-9]))", "\u00A7$2");
			if ((plugin.Permissions.has(player, "nChat.colors")) || (plugin.Permissions.has(player, "nChat.colours"))) {
			    out = out.replace(colorcharacter, "¤");
			}
			event.setFormat(out);
	  }
}
