package vvr.service;

import vvr.dao.CustomerDao;

public class CustomerServiceImpl implements CustomerService {

	
	private CustomerDao csd;
	
	public void setCsd(CustomerDao csd) {
		this.csd = csd;
	}

	@Override
	public void save() {
		
		System.out.println("±£´æ¿Í»§£¬service²ã...");
		csd.save();

	}

}
