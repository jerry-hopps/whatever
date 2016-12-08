package net.nemo.whatever.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.nemo.whatever.db.mapper.AttachmentMapper;
import net.nemo.whatever.db.mapper.MessageMapper;
import net.nemo.whatever.entity.Attachment;
import net.nemo.whatever.entity.ChatMessageType;
import net.nemo.whatever.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

	private Logger logger = Logger.getLogger(MessageService.class);

	@Autowired
	private MessageMapper messageMapper;
	@Autowired
	private AttachmentMapper attachmentMapper;

	public int addMessage(Message message){
		Message m = this.messageMapper.findBy(message.getTime(), message.getSender(), message.getReceiver(), message.getContent());		
		if(m==null){
			this.messageMapper.insert(message);
			logger.info(String.format("Inserted message into DB： [%s]", message.toString()));
			m = message;
		}
		
		return m.getId();
	}
	
	public List<Map> findMessages(Integer chatId){
		return this.messageMapper.findMessages(chatId);
	}
	
	public Map<String, String> findAttachmentPaths(Integer chatId, List<Message> messages){
		Map<String, String> attachmentPaths = new HashMap<String, String>();
		
		for(Message message : messages){
			if(ChatMessageType.IMAGE == message.getType()){
				Attachment attachment = this.attachmentMapper.findByChat(chatId, message.getContent());
				attachmentPaths.put(attachment.getFileName(), attachment.getPath());
			}
		}
		return attachmentPaths;
	}
}
