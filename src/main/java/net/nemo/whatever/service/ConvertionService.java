package net.nemo.whatever.service;

import javax.mail.Message;

import org.springframework.beans.factory.annotation.Autowired;

import net.nemo.whatever.db.mapper.UserMapper;
import net.nemo.whatever.entity.Chat;
import net.nemo.whatever.util.MailMessageConverter;

public class ConvertionService {

	private MailService mailService;
	
	@Autowired(required=true)
	private UserMapper userMapper;
	
	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public void convert(){
		try{
			mailService.connect();
			Message[] messages = mailService.receiveMessage();
			for(int i = 0; i < messages.length; i++){
				Message message = messages[i];
				Chat chat = MailMessageConverter.fromMailMessage(message);
				
				System.out.println(userMapper.insert(chat.getReceiver()));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			mailService.disconnect();
		}
	}
}
