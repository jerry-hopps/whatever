package net.nemo.whatever.service;

import java.util.Date;

import javax.mail.Message;

import org.springframework.beans.factory.annotation.Autowired;

import net.nemo.whatever.entity.Chat;
import net.nemo.whatever.entity.User;
import net.nemo.whatever.util.DESCoder;
import net.nemo.whatever.util.MailMessageConverter;

public class ConvertionService {

	@Autowired
	private MailService mailService;
	@Autowired
	private UserService userService;
	@Autowired
	private MessageService messageService;
	@Autowired
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
		System.out.println(new Date());
		System.out.println("--------------------------------------");
		
		try{
			mailService.connect();
			Message[] messages = mailService.receiveMessage();
			System.out.println("------Received " + messages.length + " Messages.");
			for(int i = 0; i < messages.length; i++){
				Message message = messages[i];
				Chat chat = MailMessageConverter.fromMailMessage(message);
				
				int receiverId = this.userService.addUser(chat.getReceiver());
				User receiver = this.userService.findUserById(receiverId);
				
				if(0 == receiver.getStatus()){
					System.out.println("------This is the first time receiving this user's messages.");
					String key = "f1s7XT6zdac=";
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

		System.out.println("--------------------------------------");
	}
	
	private void sendRegisterEmail(String to, Integer id, String encryptedStr){
		String from = "still0007@aliyun.com";
		String subject = "Welcome to Cunle.me";
		String content = "<a href='http://localhost:8080/whatever/register/" + id + "/" + encryptedStr.trim() + ".html'>Register</a>";
		try{
			mailService.sendMessage(from, to, subject, content);
			System.out.println("------Has sent registration email to user.");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
