package vvr.demo4;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo4 {

	@Test
	public void run() {
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		Car car = (Car) ac.getBean("car");
		System.out.println(car);
	}
	
	@Test
	public void run2() {
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		Person person = (Person) ac.getBean("person");
		System.out.println(person);
	}
}
