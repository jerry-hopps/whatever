package net.nemo.whatever.entity;

import java.util.Date;
import java.util.List;

import net.nemo.whatever.util.DateUtil;

public class Chat {
	private Date time;
	private String chatOwner;
	private String receiverName;
	private String receiverEmail;
	private boolean groupChat = false;
	private List<ChatMessage> chatMessages;
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
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverEmail() {
		return receiverEmail;
	}
	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
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
	@Override
	public String toString() {
		return "Time: " + DateUtil.formatDate(this.time) + System.getProperty("line.separator") +
				"Chat Owner: " + this.chatOwner + System.getProperty("line.separator") +
				"Receiver's Name: " + this.receiverName + System.getProperty("line.separator") +
				"Receiver's Email: " + this.receiverEmail + System.getProperty("line.separator") +
				"Group Chat?: " + this.groupChat + System.getProperty("line.separator") +
				"Chat Messages: " + this.chatMessages + System.getProperty("line.separator") +
				"--------------------------------" + System.getProperty("line.separator");
	}
}
