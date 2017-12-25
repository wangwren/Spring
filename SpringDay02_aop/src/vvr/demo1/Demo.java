package vvr.demo1;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试AOP功能
 * @author wwr
 *需求：在save方法执行执行之前打印一条语句。使用AOP实现
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Demo {

	@Resource(name="customerDao")
	private CustomerDao cDao;
	
	
	@Test
	public void run() {
		
		cDao.save();
		//cDao.update();
	}
}
