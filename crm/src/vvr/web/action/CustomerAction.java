package vvr.web.action;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.RequestAware;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import vvr.domain.Customer;
import vvr.domain.Dict;
import vvr.domain.PageBean;
import vvr.service.CustomerService;
import vvr.web.utils.UploadUtils;

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
	
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

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
	 * 文件上传，需要在CustomerAction中定义属性，命名是有规则的
	 * private File upload;	 表示要上传的文件
	 * private String uploadFileName;  表示上传文件的名称
	 * private String uploadContentType;  表示上传的文件类型
	 * 
	 * 提供set方法，struts拦截器就会将文件相关内容注入
	 */
	
	/**
	 * 保存客户的方法
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		
		//文件上传，如果用户选择了文件
		if(uploadFileName != null) {
			
			//给文件名提供一个唯一的文件名
			String newFileName = UploadUtils.makeFileName(uploadFileName);
			
			//指定上传的路径，不要传到项目中的路径，这样服务器重新部署文件就没了，最好保存到tomcat的目录下
			String path = "D:\\Tomact\\webapps\\upload\\";
			
			//创建一个file对象
			File file = new File(path + newFileName);
			
			//简单的上传文件方式，将原文件拷贝到指定的文件目录中，Apache提供
			FileUtils.copyFile(upload, file);
			
			customer.setFilePath(path + newFileName);
		}
		
		customerService.save(customer);
		
		return "add";
	}
	
	
	/**
	 * 提供客户分页数据
	 * @return
	 * @throws Exception
	 */
	public String findByPage() throws Exception{
		
		//查询条件，默认没有条件，指定了要查哪个对象
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
	
	/**
	 * 提供客户添加的UI界面
	 * @return
	 * @throws Exception
	 */
	public String initAddUI() throws Exception{
		return "initAddUI";
	}
	
	/**
	 * 删除指定客户，并删除该客户上传的文件
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception{
		
		//先查询出该客户信息，获得上传文件的路径
		customer = customerService.findById(customer.getCust_id());
		
		//获取上传文件的路径
		String filePath = customer.getFilePath();
		
		//删除客户
		customerService.delte(customer);
		
		//删除文件
		File file = new File(filePath);
		//如果这个文件存在
		if(file.exists()) {
			file.delete();
		}
		
		return "delete";
	}

	
	/**
	 * 初始化客户修改界面
	 * @return
	 * @throws Exception
	 */
	public String initUpdate() throws Exception{
		
		//得到客户数据，其实可以不用将该客户数据保存至request域中，该customer对象已经存在值栈中，直接在
		//jsp界面中通过model取出就行，也可以存，这里就不存了
		customer = customerService.findById(customer.getCust_id());
		
		return "initUpdate";
	}
	
	/**
	 * 修改客户
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception{
		
		//如果修改客户上传了新的文件
		if(uploadFileName != null) {
			
			//删除旧的文件
			String oldFilePath = customer.getFilePath();
			//如果旧的文件路径存在
			if(oldFilePath != null && !oldFilePath.trim().isEmpty()) {
				File f = new File(oldFilePath);
				f.delete();
			}
			
			//上传新的文件
			String newFileName = UploadUtils.makeFileName(uploadFileName);
			String path = "D:\\Tomact\\webapps\\upload\\";
			File file = new File(path + newFileName);
			FileUtils.copyFile(upload, file);
			
			//更新数据库中该客户的文件路径
			customer.setFilePath(path + newFileName);
		}
		
		//修改客户
		customerService.update(customer);
		
		return "update";
	}

}
