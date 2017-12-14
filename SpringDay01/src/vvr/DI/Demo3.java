package vvr.DI;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo3 {

	@Test
	public void run() {
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		CustomerServiceImpl customerService = (CustomerServiceImpl) ac.getBean("CustomerService");
		customerService.save();
	}
}
