package net.nemo.whatever.service;

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
		return c==null ? this.chatMapper.insert(chat) : c.getId();
	}
}
