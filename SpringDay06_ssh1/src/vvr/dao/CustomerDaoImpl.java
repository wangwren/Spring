package vvr.dao;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import vvr.domain.Customer;

public class CustomerDaoImpl extends HibernateDaoSupport implements CustomerDao {

/*  如果继承了HibernateDaoSupport,就不必写如下代码，类似于JdbcDaoSupport
 * 	private HibernateTemplate hibernateTemplate;
	
	
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}*/


	@Override
	public void save(Customer customer) {
		
		System.out.println("持久层...");
		//hibernateTemplate.save(customer);
		this.getHibernateTemplate().save(customer);
		
	}

}
