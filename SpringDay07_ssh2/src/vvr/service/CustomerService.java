package vvr.service;

import vvr.domain.Customer;

/**
 * 业务逻辑层
 * @author wwr
 *
 */
public interface CustomerService {

	/**
	 * 保存客户
	 */
	public void save(Customer customer);
}
