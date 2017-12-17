package vvr.spring.demo1;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * @author wwr
 *
 *@Component(value="userService") 等价于  <bean id="userService" class="vvr.spring.demo1.UserServiceImpl">
 *组件注解，标记类
 */
@Component(value="userService")
public class UserServiceImpl implements UserService {

	/*
	 * 属性注入，@Value 可以注入基本数据类型和String类型
	 * 使用注解注入不必提供setter方法
	 */
	@Value(value="哈哈")
	private String name;
	
	/*
	 * 注入引用
	 */
	@Resource(name="userDao")
	private UserDao userDao;
	
	
	/*	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setName(String name) {
		this.name = name;
	}
*/
	
	@Override
	public void sayHello() {
		
		System.out.println("Hello Spring!!!" + name);
		userDao.save();

	}

}
