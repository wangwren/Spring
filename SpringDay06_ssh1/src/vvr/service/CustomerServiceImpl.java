package vvr.service;

import vvr.dao.CustomerDao;
import vvr.domain.Customer;

public class CustomerServiceImpl implements CustomerService {

	private CustomerDao customerDao;
	
	
	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}


	@Override
	public void save(Customer customer) {
		
		System.out.println("ÒµÎñÂß¼­²ã...");
		customerDao.save(customer);

	}

}
