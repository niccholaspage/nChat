package com.niccholaspage.nChat.api;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@SuppressWarnings("serial")
public class ChatFormatEvent extends Event {
	private final Player player;
	
	private final Set<Node> nodes;
	
	public ChatFormatEvent(Player player) {
		super("ChatFormatEvent");
		
		this.player = player;
		
		this.nodes = new HashSet<Node>();
	}
	
	public Node getNode(String name){
		for (Node loopedNode : nodes){
			if (loopedNode.getName().equalsIgnoreCase(name)){
				return loopedNode;
			}
		}
		
		Node node = new Node(name);
		
		nodes.add(node);
		
		return node;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public Set<Node> getNodes(){
		return nodes;
	}
}
