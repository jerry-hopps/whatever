package net.nemo.whatever.entity;

import java.util.List;

public class Chat {
	private Integer id;
	private String chatOwner;
	private User receiver;
	private boolean groupChat = false;
	private List<Message> messages;
	private List<Attachment> attachments;
	
	public Chat(){}
	public Chat(Integer id, String chatOwner, Boolean groupChat) {
		super();
		this.id = id;
		this.chatOwner = chatOwner;
		this.groupChat = groupChat;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getChatOwner() {
		return chatOwner;
	}
	public void setChatOwner(String chatOwner) {
		this.chatOwner = chatOwner;
	}
	public boolean isGroupChat() {
		return groupChat;
	}
	public void setGroupChat(boolean groupChat) {
		this.groupChat = groupChat;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	public User getReceiver() {
		return receiver;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	@Override
	public String toString() {
		return String.format("Owner: %s, Group Chat? : %s", this.chatOwner, String.valueOf(this.groupChat));
	}
}
