package vvr.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import vvr.dao.CustomerDao;
import vvr.domain.Customer;
import vvr.domain.PageBean;

@Transactional
public class CustomerServiceImpl implements CustomerService {

	private CustomerDao customerDao;
	
	
	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}


	@Override
	public void save(Customer customer) {
		
		customerDao.save(customer);

	}


	@Override
	public PageBean<Customer> findByPage(Integer pageCode, Integer pageSize, DetachedCriteria criteria) {
		
		return customerDao.findByPage(pageCode,pageSize,criteria);
	}


	/**
	 * 查询出指定客户的信息
	 */
	@Override
	public Customer findById(Long cust_id) {
		return customerDao.findById(cust_id);
	}


	/**
	 * 删除指定客户信息
	 */
	@Override
	public void delte(Customer customer) {
		
		customerDao.delete(customer);
	}


	@Override
	public void update(Customer customer) {
		
		customerDao.update(customer);
		
	}


	@Override
	public List<Customer> findAll() {
		
		return customerDao.findAll();
	}

}
