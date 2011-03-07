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
		  //Make the message a string.
			//Get the player that talked.
		  Player player = event.getPlayer();
		  String message = event.getMessage();
		  if (message.startsWith("/")){
			  return;
		  }
		  String out = messageFormat;
		  String world = player.getWorld().getName();
		  String group = nChat.Permissions.getGroup(world, player.getName());
		  String prefix = nChat.Permissions.getGroupPrefix(world, group);
		  String suffix = nChat.Permissions.getGroupSuffix(world, group);
		  String userPrefix = nChat.Permissions.getPermissionString(world, player.getName(), "prefix");
		  String userSuffix = nChat.Permissions.getPermissionString(world, player.getName(), "suffix");
		  if (userPrefix != null) prefix = userPrefix;
		  if (userSuffix != null) suffix = userSuffix;
		  if (prefix == null) prefix = "";
		  if (suffix == null) suffix = "";
		  out = out.replace("+name", player.getDisplayName());
		  out = out.replace("+group", group);
		  out = out.replace("+prefix", prefix);
		  out = out.replace("+suffix", suffix);
		  out = out.replace("+world", world);
		  out = out.replace("&", "¤");
		  out = out.replace("+message", message);
			if ((nChat.Permissions.has(player, "nChat.colors")) || (nChat.Permissions.has(player, "nChat.colours"))) {
			    out = out.replace(colorcharacter, "¤");
			}
			event.setFormat(out);
	  }
}
