package vvr.service;

import org.springframework.transaction.annotation.Transactional;

import vvr.dao.UserDao;
import vvr.domain.User;
import vvr.web.utils.MD5Utils;
//事务
@Transactional
public class UserServiceImpl implements UserService {

	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 验证登录名是否存在
	 */
	@Override
	public User checkCode(String user_code) {
		return userDao.checkCode(user_code);
	}

	/**
	 * 保存用户
	 */
	@Override
	public void save(User user) {
		
		String pwd = user.getUser_password();
		//密码加密
		user.setUser_password(MD5Utils.md5(pwd));
		
		//设置登录状态，默认为1
		user.setUser_state("1");
		
		userDao.save(user);
	}

	/**
	 * 用户登录，验证登录名和密码
	 * 密码需要加密后再查询
	 */
	@Override
	public User login(User user) {
		
		String pwd = user.getUser_password();
		user.setUser_password(MD5Utils.md5(pwd));
		
		return userDao.login(user);
	}
	
}
