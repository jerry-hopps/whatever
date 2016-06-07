package net.nemo.whatever.service;

import javax.mail.Message;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;

import net.nemo.whatever.entity.Attachment;
import net.nemo.whatever.entity.Chat;
import net.nemo.whatever.entity.User;
import net.nemo.whatever.util.DESCoder;
import net.nemo.whatever.util.MailMessageConverter;

@PropertySource("classpath:mail.properties")
public class ConvertionService {
	
	private static Logger logger = Logger.getLogger(ConvertionService.class);

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
	
	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public MessageService getMessageService() {
		return messageService;
	}
	
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	
	public void setChatService(ChatService chatService) {
		this.chatService = chatService;
	}
	
	public ChatService getChatService() {
		return chatService;
	}
	
	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	
	public AttachmentService getAttachmentService() {
		return attachmentService;
	}
	
	public void convert(){
		logger.info("*****Begin conversion of message*****");
		
		try{
			mailService.connect();
			Message[] messages = mailService.receiveMessage();
			logger.info(String.format("Found %d messages in mail box", messages.length));
			for(int i = 0; i < messages.length; i++){
				logger.info(String.format("***Begin processing message : %d of %d", i+1, messages.length));
				
				Message message = messages[i];
				Chat chat = MailMessageConverter.fromMailMessage(message);
				User receiver = this.userService.findUserById(this.userService.addUser(chat.getReceiver()));
				chat.setReceiver(receiver);
				
				if(0 == receiver.getStatus()){
					logger.info(String.format("This is the first time receiving this user's messages, sending registration email to this user(%s)", receiver.getEmail()));
					
					byte[] inputData = DESCoder.encrypt(receiver.getEmail().getBytes(), DESCoder.KEY);
					this.sendRegisterEmail(receiver.getEmail(), receiver.getId(), DESCoder.encryptBASE64(inputData));
					logger.info(String.format("Email sent successfully"));
					
					receiver.setStatus(1);
					this.userService.updateStatus(receiver);
					receiver.setPassword(DESCoder.KEY);
					this.userService.updatePassword(receiver);
				}
				
				chat.setId(this.chatService.addChat(chat));
				for(net.nemo.whatever.entity.Message msg : chat.getMessages()){
					msg.setReceiver(receiver);
					msg.setChat(chat);
					this.messageService.addMessage(msg);
				}
				for(Attachment attachment : chat.getAttachments()){
					attachment.setChat(chat);
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
		String from = this.mailService.getUser();
		String subject = "Welcome to Cunle.me";
		String content = "Click to <a href='http://123.206.51.224:8080/whatever/register/" + id + "/" + encryptedStr.trim() + ".html'>register</a>.";
		try{
			mailService.sendMessage(from, to, subject, content);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
