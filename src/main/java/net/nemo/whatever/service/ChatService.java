package net.nemo.whatever.service;

import java.util.List;

import org.apache.log4j.Logger;

import net.nemo.whatever.db.mapper.ChatMapper;
import net.nemo.whatever.entity.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
	
	private static Logger logger = Logger.getLogger(ChatService.class);

	@Autowired
	private ChatMapper chatMapper;
	
	public int addChat(Chat chat){
		Chat c = this.chatMapper.findBySenderAndReceiver(chat.getChatOwner(), chat.getReceiver());
		if(c==null){
			logger.info(String.format("Inserting chat data into DB: [%s]", chat.toString()));
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
