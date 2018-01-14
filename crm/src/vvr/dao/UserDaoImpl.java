package vvr.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import vvr.domain.User;

public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

	/**
	 * 验证登录名是否重复
	 */
	@Override
	public User checkCode(String user_code) {
		
		List<User> list = (List<User>) this.getHibernateTemplate().find("from User where user_code = ?", user_code);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}

	/**
	 * 保存用户
	 */
	@Override
	public void save(User user) {
		
		this.getHibernateTemplate().save(user);
		
	}

	/**
	 * 用户登录
	 * 条件为登录名，密码，状态
	 */
	@Override
	public User login(User user) {
		
		//使用QBC查询
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		criteria.add(Restrictions.eq("user_code", user.getUser_code()));
		criteria.add(Restrictions.eq("user_password", user.getUser_password()));
		criteria.add(Restrictions.eq("user_state", "1"));
		
		List<User> list = (List<User>) this.getHibernateTemplate().findByCriteria(criteria);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}

}
