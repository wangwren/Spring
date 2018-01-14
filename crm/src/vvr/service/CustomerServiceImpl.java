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

}
