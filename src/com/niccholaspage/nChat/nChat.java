package com.niccholaspage.nChat;

import java.io.File;
import java.io.IOException;

import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class nChat extends JavaPlugin {
	private final nChatPlayerListener playerListener = new nChatPlayerListener(this);
    //Permissions Handler
    public PermissionHandler Permissions;
    //Message Format
    public String messageFormat;
    //Color character
    public String colorCharacter;
    @Override
	public void onDisable() {
		System.out.println("nChat Disabled");
		
	}
    @Override
	public void onEnable() {
		//Create the pluginmanage pm.
		PluginManager pm = getServer().getPluginManager();
		//Create PlayerCommand listener
	    pm.registerEvent(Event.Type.PLAYER_CHAT, this.playerListener, Event.Priority.Normal, this);
       //Get the infomation from the yml file.
        PluginDescriptionFile pdfFile = this.getDescription();
        //Setup Permissions
        setupPermissions();
        readConfig();
        //Print that the plugin has been enabled!
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
		
	}
    private void readConfig() {
		File folder = new File("plugins/nChat/");
		if (!(folder.exists())){
			folder.mkdir();
		}
		File file = new File("plugins/nChat/config.yml");
		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	Configuration config = new Configuration(file);
    	config.load();
    	writeDefaultNode("nChat", "", config);
    	writeDefaultNode("nChat.messageformat", "[+prefix+group+suffix&f] +name: +message", config);
    	writeDefaultNode("nChat.colorcharacter", "~", config);
    	config.save();
    	// Reading from yml file
    	messageFormat = config.getString("nChat.messageformat", "[+prefix+group+suffix&f] +name: +message");
    	colorCharacter = config.getString("nChat.colorcharacter", "~");
        }
    private void setupPermissions() {
        Plugin perm = this.getServer().getPluginManager().getPlugin("Permissions");

        if (Permissions == null) {
            if (perm != null) {
                Permissions = ((Permissions)perm).getHandler();
            } else {
            	System.out.println("[nChat] Permissions not detected, disabling nChat.");
            	getPluginLoader().disablePlugin(this);
            }
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
	
	private void writeDefaultNode(String node,String value, Configuration config){
		if (config.getProperty(node) == null) config.setProperty(node, value);
	}
}
