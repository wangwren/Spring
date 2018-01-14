package vvr.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import vvr.domain.Customer;
import vvr.domain.PageBean;

/**
 * 持久层
 * @author wwr
 *
 */
public interface CustomerDao {

	/**
	 * 保存客户
	 * @param customer
	 */
	public void save(Customer customer);

	public PageBean<Customer> findByPage(Integer pageCode, Integer pageSize, DetachedCriteria criteria);
}
