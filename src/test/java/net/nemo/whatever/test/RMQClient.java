package net.nemo.whatever.test;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.nemo.whatever.util.StringUtil;

public class RMQClient {

	private static void testEmail() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");

		AmqpTemplate emailAMQPTemplate = (AmqpTemplate) applicationContext.getBean("emailAMQPTemplate");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("email", "tuo.shi@oracle.com");
		model.put("url", "http://www.ileqi.com.cn/whatever/register/" + 2 + "/" + "JD897dsSDSSS^@".trim() + ".html");

		Map<String, Object> queueMsg = new HashMap<String, Object>();
		queueMsg.put("from", "still0007@aliyun.com");
		queueMsg.put("to", "tuo.shi@oracle.com");
		queueMsg.put("subject", "Welcome to Cunle.me");
		queueMsg.put("model", model);
		queueMsg.put("template", "velocity/mail/registration.vm");
		emailAMQPTemplate.convertAndSend("email_queue_key", queueMsg);
	}

	public static void main(String[] args) throws Exception{
		testEmail();
		
	}
}
