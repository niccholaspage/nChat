package com.niccholaspage.nChat.api;

public class Node {
	private final String name;

	private String value = "";

	public Node(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setValue(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
}
