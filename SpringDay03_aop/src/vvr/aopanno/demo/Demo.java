package vvr.aopanno.demo;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Demo {

	@Resource(name="customerDao")
	private CustomerDao customerDao;
	
	@Test
	public void run() {
		
		customerDao.save();
		customerDao.update();
	}
}
