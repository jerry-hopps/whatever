package net.nemo.whatever.service;

import java.util.List;

import net.nemo.whatever.db.mapper.AttachmentMapper;
import net.nemo.whatever.db.mapper.MessageMapper;
import net.nemo.whatever.entity.Attachment;
import net.nemo.whatever.entity.Message;
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
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private AttachmentMapper attachmentMapper;
	
	public Chat addChat(Chat chat){
		Chat c = this.chatMapper.findBySenderAndReceiver(chat.getChatOwner(), chat.getReceiver());

		if(c == null)
			this.chatMapper.insert(chat);
		else
            chat.setId(c.getId());

        for(Message message : chat.getMessages()){
            if(this.messageMapper.findCount(message) > 0)
                continue;

            message.setReceiver(chat.getReceiver());
            message.setChat(chat);
            this.messageMapper.insert(message);
        }
        for(Attachment attachment : chat.getAttachments()){
            attachment.setChat(chat);
            this.attachmentMapper.insert(attachment);
        }

		return chat;
	}
	
	public List<Chat> listChats(int receiverId){
		return chatMapper.selectChats(receiverId);
	}
	
	public Chat findById(int id){
		return this.chatMapper.findById(id);
	}
}
