package net.nemo.whatever.service;

import net.nemo.whatever.service.converter.MailMessageConverter;
import net.nemo.whatever.entity.Attachment;
import net.nemo.whatever.entity.Chat;
import net.nemo.whatever.entity.User;
import net.nemo.whatever.util.DESCoder;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConversionService {
	
	private static Logger logger = Logger.getLogger(ConversionService.class);

	@Autowired
	private MailService mailService;
	@Autowired
	private UserService userService;
	@Autowired
	private ChatService chatService;
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
				chat.setReceiver(this.userService.addUser(chat.getReceiver()));

                for(net.nemo.whatever.entity.Message msg : chat.getMessages()){
					msg.setReceiver(chat.getReceiver());
					msg.setChat(chat);
				}
				for(Attachment attachment : chat.getAttachments()){
					attachment.setPath(String.format("<img src='/assets/%s'>", attachment.getPath()));
                    attachment.setChat(chat);
				}
                this.chatService.addChat(chat);

                if(User.STATUS_NEW.equals(chat.getReceiver().getStatus()))
                    invokeRegistration(chat.getReceiver());

				logger.info(String.format("***End processing message : %d of %d", i+1, messages.length));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			mailService.disconnect();
		}
		
		logger.info("*****End conversion of message*****");
	}

	private void invokeRegistration(User receiver) throws Exception{
        logger.info(String.format("This is the first time receiving this user's messages, sending registration email to this user(%s)", receiver.getEmail()));

        String encryptedStr = null;
        byte[] inputData = DESCoder.encrypt(receiver.getEmail().getBytes(), DESCoder.KEY);
        encryptedStr = DESCoder.encryptBASE64(inputData);

        this.sendRegisterEmail(receiver.getEmail(), receiver.getId(), encryptedStr);

        receiver.setStatus(User.STATUS_NEW);
        receiver.setPassword(DESCoder.KEY);
        this.userService.update(receiver);
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

        logger.info(String.format("Email sent successfully"));
	}
}
