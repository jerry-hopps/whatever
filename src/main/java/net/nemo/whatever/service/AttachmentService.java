package net.nemo.whatever.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.log4j.Logger;
import net.nemo.whatever.db.mapper.AttachmentMapper;
import net.nemo.whatever.entity.Attachment;

@Service
public class AttachmentService {

	private static Logger logger = Logger.getLogger(AttachmentService.class);

	@Autowired
	private AttachmentMapper attachmentMapper;
	
	public Integer addAttachement(Attachment attachment){
		Attachment a = this.attachmentMapper.findByChat(attachment.getChat().getId(), attachment.getFileName());
		if(a==null){
			this.attachmentMapper.insert(attachment);
			logger.info(String.format("Inserted attachment into DB： [%s]", attachment.toString()));
			a = attachment;
		}
		
		return a.getId();
	}
}
