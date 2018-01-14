package vvr.web.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.hibernate.criterion.DetachedCriteria;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import vvr.domain.Customer;
import vvr.domain.PageBean;
import vvr.service.CustomerService;

public class CustomerAction extends ActionSupport implements ModelDriven<Customer>,RequestAware{

	/**
	 * 客户逻辑层
	 */
	private static final long serialVersionUID = 1796491760814489483L;
	
	//使用模型驱动封装数据，需要new出对象
	private Customer customer = new Customer();
	private CustomerService customerService;
	
	//当前页
	private Integer pageCode = 1;
	
	//一页显示多少条数据
	private Integer pageSize = 2;
	
	private Map<String,Object> request;
	
	
	
	public void setPageCode(Integer pageCode) {
		
		if(pageCode == null) {
			pageCode = 1;
		}
		
		this.pageCode = pageCode;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	@Override
	public Customer getModel() {
		return customer;
	}
	
	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;	
	}
	
	/**
	 * 保存客户的方法
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		
		customerService.save(customer);
		
		return NONE;
	}
	
	
	/**
	 * 提供客户分页数据
	 * @return
	 * @throws Exception
	 */
	public String findByPage() throws Exception{
		
		//查询条件，默认没有条件
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class);
		
		PageBean<Customer> page = customerService.findByPage(pageCode,pageSize,criteria);
		
		request.put("page", page);
		
		System.out.println("----------" + page.getTotalCount() + "   " + page.getPageCode());
		
		return "page";
	}


}
