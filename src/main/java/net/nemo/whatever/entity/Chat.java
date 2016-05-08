package net.nemo.whatever.entity;

import java.util.Date;
import java.util.List;

import net.nemo.whatever.util.DateUtil;

public class Chat {
	private Date time;
	private String chatOwner;
	private User receiver;
	private boolean groupChat = false;
	private List<Message> messages;
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
	@Override
	public String toString() {
		return "Time: " + DateUtil.formatDate(this.time) + System.getProperty("line.separator") +
				"Chat Owner: " + this.chatOwner + System.getProperty("line.separator") +
				"Receiver: " + this.receiver.toString() + System.getProperty("line.separator") +
				"Group Chat?: " + this.groupChat + System.getProperty("line.separator") +
				"Chat Messages: " + this.messages + System.getProperty("line.separator") +
				"--------------------------------" + System.getProperty("line.separator");
	}
}
