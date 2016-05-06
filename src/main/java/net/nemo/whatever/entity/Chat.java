package net.nemo.whatever.entity;

import java.util.Date;
import java.util.List;

public class Chat {
	private String id;
	private Date time;
	private String chatOwner;
	private String receiver;
	private boolean groupChat = false;
	private List<ChatMessage> chatMessages;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getDateTime() {
		return time;
	}
	public void setDateTime(Date time) {
		this.time = time;
	}
	public String getChatOwner() {
		return chatOwner;
	}
	public void setChatOwner(String chatOwner) {
		this.chatOwner = chatOwner;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public boolean isGroupChat() {
		return groupChat;
	}
	public void setGroupChat(boolean groupChat) {
		this.groupChat = groupChat;
	}
	public List<ChatMessage> getChatMessages() {
		return chatMessages;
	}
	public void setChatMessages(List<ChatMessage> chatMessages) {
		this.chatMessages = chatMessages;
	}
}
