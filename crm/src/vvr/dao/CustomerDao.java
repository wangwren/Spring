package vvr.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import vvr.domain.Customer;
import vvr.domain.PageBean;

/**
 * 持久层
 * @author wwr
 * @param <T>
 * @param <T>
 * 继承通用的接口需要指定操作哪一个对象，即指定泛型
 */
public interface CustomerDao extends BaseDao<Customer>{

	/**
	 * 保存客户
	 * @param customer
	 */
	
	//一下方法在BaseDao中都有，如果有特殊需要，再在子类中编写就可以了，父类已经写好
/*	public void save(Customer customer);

	public PageBean<Customer> findByPage(Integer pageCode, Integer pageSize, DetachedCriteria criteria);

	public Customer findById(Long cust_id);

	public void delete(Customer customer);

	public void update(Customer customer);*/
}
