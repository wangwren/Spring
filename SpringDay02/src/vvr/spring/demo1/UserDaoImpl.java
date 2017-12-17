package vvr.spring.demo1;

import org.springframework.stereotype.Repository;

@Repository(value="userDao")
public class UserDaoImpl implements UserDao {

	@Override
	public void save() {
		
		System.out.println("±£´æ¿Í»§...");

	}

}
