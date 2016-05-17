package net.nemo.whatever.service;

import java.util.List;

import net.nemo.whatever.db.mapper.ChatMapper;
import net.nemo.whatever.entity.Chat;

public class ChatService {

	private ChatMapper chatMapper;
	
	public ChatMapper getChatMapper() {
		return chatMapper;
	}
	
	public void setChatMapper(ChatMapper chatMapper) {
		this.chatMapper = chatMapper;
	}
	
	public int addChat(Chat chat){
		Chat c = this.chatMapper.findBySenderAndReceiver(chat.getChatOwner(), chat.getReceiver());
		if(c==null){
			this.chatMapper.insert(chat);
			c = chat;
		}
		return c.getId();
	}
	
	public List<Chat> listChats(int receiverId){
		return chatMapper.selectChats(receiverId);
	}
	
	public Chat findById(int id){
		return this.chatMapper.findById(id);
	}
}
