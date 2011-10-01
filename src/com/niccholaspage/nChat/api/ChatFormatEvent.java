package com.niccholaspage.nChat.api;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.event.Event;

@SuppressWarnings("serial")
public class ChatFormatEvent extends Event {
	private final Set<Node> nodes;
	
	public ChatFormatEvent() {
		super("ChatFormatEvent");
		
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
	
	public Set<Node> getNodes(){
		return nodes;
	}
}
