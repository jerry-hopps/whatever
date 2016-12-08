package net.nemo.whatever.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.Message;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import net.nemo.whatever.entity.Attachment;
import net.nemo.whatever.entity.Chat;
import net.nemo.whatever.entity.User;
import net.nemo.whatever.util.DESCoder;
import net.nemo.whatever.converter.MailMessageConverter;
import org.springframework.stereotype.Service;

@Service
public class ConversionService {
	
	private static Logger logger = Logger.getLogger(ConversionService.class);

	@Autowired
	private MailService mailService;
	@Autowired
	private UserService userService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private ChatService chatService;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private MailMessageConverter mailMessageConverter;
	@Autowired
	private AmqpTemplate emailAMQPTemplate;

	@Value("${app.domain.name}")
	private String appDomainName;
	@Value("${mail.user}")
	private String emailFromUser;
	
	public void convert(){
		logger.info("*****Begin conversion of message*****");
		
		try{
			mailService.connect();
			Message[] messages = mailService.receiveMessage();
			logger.info(String.format("Found %d messages in mail box", messages.length));
			for(int i = 0; i < messages.length; i++){
				logger.info(String.format("***Begin processing message : %d of %d", i+1, messages.length));
				
				Message message = messages[i];
				Chat chat = this.mailMessageConverter.fromMailMessage(message);
				User receiver = this.userService.findUserById(this.userService.addUser(chat.getReceiver()));
				chat.setReceiver(receiver);
				
				if(0 == receiver.getStatus()){
					logger.info(String.format("This is the first time receiving this user's messages, sending registration email to this user(%s)", receiver.getEmail()));
					
					byte[] inputData = DESCoder.encrypt(receiver.getEmail().getBytes(), DESCoder.KEY);
					this.sendRegisterEmail(receiver.getEmail(), receiver.getId(), DESCoder.encryptBASE64(inputData));
					logger.info(String.format("Email sent successfully"));
					
					receiver.setStatus(1);
					receiver.setPassword(DESCoder.KEY);
					this.userService.update(receiver);
				}
				
				chat.setId(this.chatService.addChat(chat));
				for(net.nemo.whatever.entity.Message msg : chat.getMessages()){
					msg.setReceiver(receiver);
					msg.setChat(chat);
					this.messageService.addMessage(msg);
				}
				for(Attachment attachment : chat.getAttachments()){
					attachment.setChat(chat);
					attachment.setPath(String.format("<img src='/assets/%s'>", attachment.getPath()));
					this.attachmentService.addAttachement(attachment);
				}
				logger.info(String.format("***End processing message : %d of %d", i+1, messages.length));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			mailService.disconnect();
		}
		
		logger.info("*****End conversion of message*****");
	}
	
	private void sendRegisterEmail(String to, Integer id, String encryptedStr){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("email", to);
		model.put("url", String.format("%s/register/%d/%s.html", this.appDomainName, id, encryptedStr.trim()));
		
		Map<String, Object> queueMsg = new HashMap<String, Object>();
		queueMsg.put("from", this.emailFromUser);
		queueMsg.put("to", to);
		queueMsg.put("subject", "Welcome to Cunle.me");
		queueMsg.put("template", "velocity/mail/registration.vm");
		queueMsg.put("model", model);
		emailAMQPTemplate.convertAndSend("email_queue_key", queueMsg);
	}
}
