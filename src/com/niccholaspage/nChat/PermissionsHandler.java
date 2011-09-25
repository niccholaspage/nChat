package com.niccholaspage.nChat;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class PermissionsHandler {
	private enum PermissionType {
		PERMISSIONS_3,
		PERMISSIONS_2,
		PERMISSIONS_EX,
		BUKKIT_PERMS;
	}
	
	private final PermissionType type;
	
	private final Plugin plugin;
	
	public PermissionsHandler(Server server){
		Plugin permissions = server.getPluginManager().getPlugin("Permissions");
		
		Plugin PEX = server.getPluginManager().getPlugin("PermissionsEx");
		
		if(PEX != null){
			type = PermissionType.PERMISSIONS_EX;
			
			plugin = permissions;
		}else if (permissions != null) {
			String version = permissions.getDescription().getVersion();
			
			if (version.startsWith("3")){
				type = PermissionType.PERMISSIONS_3;
			}else {
				type = PermissionType.PERMISSIONS_2;
			}
			
			plugin = permissions;
		}else {
			type = PermissionType.BUKKIT_PERMS;
			
			plugin = null;
		}
		
		if (plugin == null){
			System.out.println("[nChat] Hooked into Bukkit Perms");
		}else {
			System.out.println("[nChat] Hooked into " + plugin.getDescription().getName() + " " + plugin.getDescription().getVersion());
		}
	}
	
	public boolean hasPermission(CommandSender sender, String permission){
		switch (type){
		case PERMISSIONS_EX: return internalPEXCheck(sender, permission);
		case PERMISSIONS_3:
		case PERMISSIONS_2: return internalP2Check(sender, permission);
		default: return sender.hasPermission(permission);
		}
	}
	
	public String getPrefix(String name, String world){
		switch (type){
		case PERMISSIONS_2: return internalP2Prefix(name, world);
		case PERMISSIONS_3: return ((Permissions) plugin).getHandler().getUserPrefix(world, name);
		case PERMISSIONS_EX: return PermissionsEx.getPermissionManager().getUser(name).getOption("prefix");
		default: return "Not supported yet";
		}
	}
	
	public String getSuffix(String name, String world){
		switch (type){
		case PERMISSIONS_2: return internalP2Suffix(name, world);
		case PERMISSIONS_3: return ((Permissions) plugin).getHandler().getUserSuffix(world, name);
		case PERMISSIONS_EX: return PermissionsEx.getPermissionManager().getUser(name).getOption("suffix");
		default: return "Not supported yet";
		}
	}
	
	@SuppressWarnings("deprecation")
	public String getGroup(String name, String world){
		switch (type){
		case PERMISSIONS_2: return ((Permissions) plugin).getHandler().getGroup(world, name);
		case PERMISSIONS_3: return ((Permissions) plugin).getHandler().getPrimaryGroup(world, name);
		case PERMISSIONS_EX: return PermissionsEx.getPermissionManager().getUser(name).getGroups(name)[0].getName();
		default: return "Unsupported by Bukkit Perms";
		}
	}
	
	@SuppressWarnings("deprecation")
	private String internalP2Prefix(String name, String world){
		PermissionHandler handler = ((Permissions) plugin).getHandler();
		
		String group = getGroup(name, world);
		
		String prefix = handler.getGroupPrefix(world, group);
		
		String userPrefix = handler.getPermissionString(world, name, "prefix");
		
		if (userPrefix != null) prefix = userPrefix;
		
		if (prefix == null) prefix = "";
		
		return prefix;
	}
	
	@SuppressWarnings("deprecation")
	private String internalP2Suffix(String name, String world){
		PermissionHandler handler = ((Permissions) plugin).getHandler();
		
		String group = getGroup(name, world);
		
		String suffix = handler.getGroupSuffix(world, group);
		
		String userSuffix = handler.getPermissionString(world, name, "suffix");
		
		if (userSuffix != null) suffix = userSuffix;
		
		if (suffix == null) suffix = "";
		
		return suffix;
	}
	
	private boolean internalPEXCheck(CommandSender sender, String permission){
		if (sender instanceof Player){
			Player player = (Player) sender;
			
			return PermissionsEx.getPermissionManager().has(player, permission);
		} else {
			return true;
		}
	}
	
	private boolean internalP2Check(CommandSender sender, String permission){
		if (sender instanceof Player){
			Player player = (Player) sender;
			
			return ((Permissions) plugin).getHandler().has(player, permission);
		} else {
			return true;
		}
	}
}
