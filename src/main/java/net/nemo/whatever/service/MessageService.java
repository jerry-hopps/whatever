package net.nemo.whatever.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.nemo.whatever.db.mapper.TagMapper;
import net.nemo.whatever.entity.User;
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
    @Autowired
    private TagMapper tagMapper;

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

    public List<Message> findAllLinkMessages(User user){
        return messageMapper.findLinkMessages(user.getId());
    }

    public List<String> findAllLinkTags(User user){
        return this.tagMapper.findLinkTags(user.getId());
    }

    public List<String> findTags(Integer messageId){
        return this.tagMapper.findTags(messageId);
    }

    public List<Message> findLinkMessageByType(User user, String tagName){
        return this.messageMapper.findTaggedLinkMessages(user.getId(), tagName);
    }

    public void addTagForMessage(Integer messageId, String tagName){
        this.tagMapper.addTag(messageId, tagName);
    }
}
