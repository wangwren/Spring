package vvr.IoC;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo2 {

	/**
	 * 使用Spring的IoC
	 */
	@Test
	public void run() {
		
		//读取配置文件：ClassPathXmlApplicationContext  是指类路径下的文件，即src下
		//使用Spring工厂
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		//getBean中写的是配置文件中想要获得的对象的id
		UserService us = (UserService) ac.getBean("userService");
		us.sayHello();
	}
}
