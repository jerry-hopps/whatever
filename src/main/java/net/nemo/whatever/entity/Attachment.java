package net.nemo.whatever.entity;

public class Attachment {

	private Integer id;
	private Chat chat;
	private String fileName;
	private String path;
	
	public Attachment(String name, String path) {
		this.fileName = name;
		this.path = path;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Chat getChat() {
		return chat;
	}
	public void setChat(Chat chat) {
		this.chat = chat;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
