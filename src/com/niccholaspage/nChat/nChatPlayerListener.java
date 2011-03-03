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
		  String group = nChat.Permissions.getGroup(player.getName());
		  String prefix = nChat.Permissions.getGroupPrefix(group);
		  String suffix = nChat.Permissions.getGroupSuffix(group);
		  String userPrefix = nChat.Permissions.getPermissionString(player.getName(), "prefix");
		  String userSuffix = nChat.Permissions.getPermissionString(player.getName(), "suffix");
		  if (userPrefix != null){
			  prefix = userPrefix;
		  }
		  if (userSuffix != null){
			  suffix = userSuffix;
		  }
		  if (prefix == null) prefix = "";
		  if (suffix == null) suffix = "";
		  out = out.replaceAll("\\+name", player.getDisplayName());
		  out = out.replaceAll("\\+group", group);
		  out = out.replaceAll("\\+prefix", prefix);
		  out = out.replaceAll("\\+suffix", suffix);
		  out = out.replaceAll("&", "¤");
		  out = out.replace("+message", message);
			if ((nChat.Permissions.has(player, "nChat.colors")) || (nChat.Permissions.has(player, "nChat.colours"))) {
			    out = out.replaceAll(colorcharacter, "¤");
			}
			System.out.println(out);
			plugin.getServer().broadcastMessage(out);
		  event.setCancelled(true);
	  }
}
