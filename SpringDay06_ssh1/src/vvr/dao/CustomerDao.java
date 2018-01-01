package vvr.dao;

import vvr.domain.Customer;

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
}
