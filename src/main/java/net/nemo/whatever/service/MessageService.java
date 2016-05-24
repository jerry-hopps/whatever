package net.nemo.whatever.service;

import java.util.List;

import org.apache.log4j.Logger;

import net.nemo.whatever.db.mapper.MessageMapper;
import net.nemo.whatever.entity.Message;

public class MessageService {

	private Logger logger = Logger.getLogger(MessageService.class);
	
	private MessageMapper messageMapper;
	
	public void setMessageMapper(MessageMapper messageMapper) {
		this.messageMapper = messageMapper;
	}
	
	public MessageMapper getMessageMapper() {
		return messageMapper;
	}
	
	public int addMessage(Message message){
		Message m = this.messageMapper.findBy(message.getTime(), message.getSender(), message.getReceiver(), message.getContent());		
		if(m==null){
			this.messageMapper.insert(message);
			logger.info(String.format("Inserted message into DB： [%s]", message.toString()));
			m = message;
		}
		
		return m.getId();
	}
	
	public List<Message> findMessages(Integer chatId, Integer receiverId){
		return this.messageMapper.findMessages(receiverId, chatId);
	}
}
