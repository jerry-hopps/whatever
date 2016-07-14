package net.nemo.whatever.messaging;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import net.nemo.whatever.service.MailService;

public class EmailQueueListener implements MessageListener{

	@Autowired
	private MailService mailService;
	
	public void onMessage(Message message) {
		Map<String, Object> map = getMessageMap(message);
		try{
			mailService.sendMessageWithTemplate(map.get("from").toString(), 
					map.get("to").toString(), 
					map.get("subject").toString(), 
					map.get("template").toString(), 
					(Map<String, Object>)map.get("model"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private Map<String, Object> getMessageMap(Message message){
		ByteArrayInputStream bais = null;
		try{
			bais = new ByteArrayInputStream(message.getBody());
			ObjectInputStream is = new ObjectInputStream(bais);
			return (Map<String, Object>)is.readObject();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try{
				bais.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
}
