package vvr.dao;


import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import vvr.domain.Customer;
import vvr.domain.PageBean;

public class CustomerDaoImpl extends HibernateDaoSupport implements CustomerDao {

/*  如果继承了HibernateDaoSupport,就不必写如下代码，类似于JdbcDaoSupport
 * 	private HibernateTemplate hibernateTemplate;
	
	
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}*/


	@Override
	public void save(Customer customer) {
		
		this.getHibernateTemplate().save(customer);
		
	}

	/**
	 * 分页查询
	 */
	@Override
	public PageBean<Customer> findByPage(Integer pageCode, Integer pageSize, DetachedCriteria criteria) {
		
		PageBean<Customer> pageBean = new PageBean<Customer>();
		pageBean.setPageCode(pageCode);
		pageBean.setPageSize(pageSize);
		
		
		//先查询总记录数，方便得出总页数   select count(*)
		criteria.setProjection(Projections.rowCount());	//条件查询，查询出共有多少条数据
		List<Number> list = (List<Number>) this.getHibernateTemplate().findByCriteria(criteria);
		if(list != null && list.size() > 0) {
			int totalCount = list.get(0).intValue();
			//总记录数
			pageBean.setTotalCount(totalCount);
		}
		
		//分页查询前，先将条件设为null，即为select *，如果不设为空，则还是select count(*)
		criteria.setProjection(null);
		
		//分页查询  select * from 表 limit ?,?
		//Spring封装了hibernate的分页查询，从哪开始查，查多少条数据
		List<Customer> beanList = (List<Customer>) this.getHibernateTemplate().findByCriteria(criteria, (pageCode - 1) * pageSize, pageSize);
		
		
		//封装至PageBean中
		pageBean.setBeanList(beanList);
		return pageBean;
	}

	/**
	 * 查询出指定客户的信息
	 */
	@Override
	public Customer findById(Long cust_id) {
		
		return this.getHibernateTemplate().get(Customer.class, cust_id);
	}

	@Override
	public void delete(Customer customer) {
		
		this.getHibernateTemplate().delete(customer);
	}

}
