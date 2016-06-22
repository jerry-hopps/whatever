package net.nemo.whatever.test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.nemo.whatever.service.ConvertionService;

public class Test {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
		ConvertionService convertionService = (ConvertionService) applicationContext.getBean("convertionService");

		convertionService.convert();
	}
}