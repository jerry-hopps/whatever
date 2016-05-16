package net.nemo.whatever.service;

import javax.mail.Message;

import net.nemo.whatever.entity.Chat;
import net.nemo.whatever.entity.User;
import net.nemo.whatever.util.DESCoder;
import net.nemo.whatever.util.MailMessageConverter;

public class ConvertionService {

	private MailService mailService;
	private UserService userService;
	private MessageService messageService;
	private ChatService chatService;
	
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
	
	public void convert(){
		try{
			mailService.connect();
			Message[] messages = mailService.receiveMessage();
			for(int i = 0; i < messages.length; i++){
				Message message = messages[i];
				Chat chat = MailMessageConverter.fromMailMessage(message);
				
				int receiverId = this.userService.addUser(chat.getReceiver());
				User receiver = this.userService.findUserById(receiverId);
				
				if(0 == receiver.getStatus()){
					String key = DESCoder.initKey();
					byte[] inputData = receiver.getEmail().getBytes();
			        inputData = DESCoder.encrypt(inputData, key);
					this.sendRegisterEmail(receiver.getEmail(), receiverId, DESCoder.encryptBASE64(inputData));
					
					receiver.setStatus(1);
					this.userService.updateStatus(receiver);
					receiver.setPassword(key);
					this.userService.updatePassword(receiver);
				}
				
				chat.setReceiver(receiver);
				
				int chatId = this.chatService.addChat(chat);
				chat.setId(chatId);
				
				for(net.nemo.whatever.entity.Message msg : chat.getMessages()){
					msg.setReceiver(receiver);
					msg.setChat(chat);
					this.messageService.addMessage(msg);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			mailService.disconnect();
		}
	}
	
	private void sendRegisterEmail(String to, Integer id, String encryptedStr){
		String from = "still0007@163.com";
		String subject = "Welcome to Cunle.me";
		String content = "http://localhost:8080/whatever/register/" + id + "/" + encryptedStr + ".html";
		try{
			mailService.sendMessage(from, to, subject, content);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
