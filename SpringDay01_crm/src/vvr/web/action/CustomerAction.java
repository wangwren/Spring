package vvr.web.action;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

import vvr.service.CustomerService;

public class CustomerAction extends ActionSupport {

	public String save() {
		
		System.out.println("保存客户，WEB层...");
		
		/*ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		CustomerService cs = (CustomerService) ac.getBean("customerService");
		cs.save();*/
		
		//web整合方式加载
		ServletContext sc = ServletActionContext.getServletContext();
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sc);
		CustomerService cs = (CustomerService) context.getBean("customerService");
		cs.save();
		return NONE;
	}
}
