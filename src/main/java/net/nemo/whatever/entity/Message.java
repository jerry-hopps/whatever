package net.nemo.whatever.entity;

import java.util.Date;

import javax.sql.rowset.Predicate;

import net.nemo.whatever.util.DateUtil;

public class Message {
	private Integer id;
	private Date time;
	private Chat chat;
	private String sender;
	private User receiver;
	private ChatMessageType type;
	private String content;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Chat getChat() {
		return chat;
	}
	public void setChat(Chat chat) {
		this.chat = chat;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSender() {
		return sender;
	}
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	public ChatMessageType getType() {
		return type;
	}
	public void setType(ChatMessageType type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return this.sender + "/" + DateUtil.formatDate(this.time) + "/" + this.type + "/" + this.content;
	}
}