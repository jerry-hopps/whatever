package net.nemo.whatever.entity;

public enum ChatMessageType {
	TEXT(0), IMAGE(1), LINK(2);
	
	private int typeCode;
	
	private ChatMessageType(int typeCode){
		this.typeCode = typeCode;
	}
}
