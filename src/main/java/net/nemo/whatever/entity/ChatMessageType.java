package net.nemo.whatever.entity;

public enum ChatMessageType {
	TEXT(0), IMAGE(1), LINK(2);
	
	private int code;
	
	private ChatMessageType(int code){
		this.code = code;
	}
}
