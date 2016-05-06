package net.nemo.whatever.service;

import javax.mail.Message;

import net.nemo.whatever.entity.Chat;
import net.nemo.whatever.util.MailMessageConverter;

public class ConvertionService {

	private MailService mailService;
	
	public void convert(){
		try{
			mailService.connect();
			Message[] messages = mailService.receiveMessage();
			for(int i = 0; i < messages.length; i++){
				Message message = messages[i];
				Chat chat = MailMessageConverter.fromMailMessage(message);
				
				System.out.println(chat);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			mailService.disconnect();
		}
	}
}
