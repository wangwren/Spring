package vvr.demo2;

/**
 * 使用注解方式配置事务
 */
import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext2.xml")
public class Demo2 {

	@Resource(name="accountService")
	private AccountService accountService;
	
	@Test
	public void run() {
		
		accountService.pay("哈哈", "呵呵", 1000);
	}
}
