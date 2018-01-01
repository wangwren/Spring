package vvr.web.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import vvr.domain.Customer;
import vvr.service.CustomerService;

public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{

	/**
	 * 客户逻辑层
	 */
	private static final long serialVersionUID = 1796491760814489483L;
	
	//使用模型驱动封装数据，需要new出对象
	private Customer customer = new Customer();
	private CustomerService customerService;
	
	
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	@Override
	public Customer getModel() {
		// TODO Auto-generated method stub
		return customer;
	}
	
	/**
	 * 保存客户的方法
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		
		System.out.println("web层...");
		customerService.save(customer);
		
		return NONE;
	}

}
