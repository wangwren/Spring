package vvr.web.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import vvr.domain.Customer;
import vvr.domain.Dict;
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
		
		/**
		 * 按条件查询，拼接条件
		 */
		String cust_name = customer.getCust_name();
		//如果输入的客户名称不为空并且客户名称不为空字符串
		if(cust_name != null && !cust_name.trim().isEmpty()) {
			criteria.add(Restrictions.like("cust_name", "%"+ cust_name +"%"));
		}
		
		Dict level = customer.getLevel();
		//如果客户级别不为空，并且不为空的字符串
		if(level != null && !level.getDict_id().trim().isEmpty()) {
			criteria.add(Restrictions.eq("level.dict_id", level.getDict_id()));
		}
		
		Dict source = customer.getSource();
		//如果客户来源不为空，并且不为空的字符串
		if(source != null && !source.getDict_id().trim().isEmpty()) {
			criteria.add(Restrictions.eq("source.dict_id", source.getDict_id()));
		}
		
		PageBean<Customer> page = customerService.findByPage(pageCode,pageSize,criteria);
		
		request.put("page", page);
		
		//完成按条件查询的数据回显，可以手动将数据加入值栈，也可以不用，因为在按条件查询时，已经将指定数据封装
		//到模型驱动中了。值栈中会有CustomerAction这个对象，该对象中有set或get方法的属性才会加入到值栈中
		//由于customer是模型驱动加载，所以在jsp界面可以通过model来取值，此时的model就是customer
		
		
		return "page";
	}


}
