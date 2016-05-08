package net.nemo.whatever.entity;

import java.util.Date;

import net.nemo.whatever.util.DateUtil;

public class Message {
	private String id;
	private Date time;
	private String sender;
	private ChatMessageType type;
	private String content;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSender() {
		return sender;
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
