package net.nemo.whatever.test;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;

import net.nemo.whatever.service.ChatService;
import net.nemo.whatever.service.ConvertionService;
import net.nemo.whatever.service.MessageService;

public class Test {
	
	
	private static void testMessageService(){
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
		
		ChatService chatService = (ChatService) applicationContext.getBean("chatService");
		MessageService messageService = (MessageService) applicationContext.getBean("messageService");
		
		List<Map> messages = messageService.findMessages(1);
		
		System.out.println(JSON.toJSON(messages));
	}
	
	private static void testConvertionService(){
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
		ConvertionService convertionService = (ConvertionService) applicationContext.getBean("convertionService");

		convertionService.convert();
	}

	public static void main(String[] args) {
		testMessageService();
		
	}
}
