package vvr.web.action;

import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;

import com.opensymphony.xwork2.ActionSupport;

import vvr.domain.Linkman;
import vvr.domain.PageBean;
import vvr.service.LinkmanService;

/**
 * 联系人控制逻辑层
 * @author wwr
 *
 */
public class LinkmanAction extends ActionSupport implements RequestAware{

	private static final long serialVersionUID = -1576267734510388727L;
	
	private Linkman linkman;
	
	private LinkmanService linkmanService;
	
	private Integer pageCode = 1;
	private Integer pageSize = 2;
	
	private Map<String,Object> request;


	public Integer getPageCode() {
		return pageCode;
	}

	public void setPageCode(Integer pageCode) {
		if(pageCode == null) {
			pageCode = 1;
		}
		this.pageCode = pageCode;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setLinkmanService(LinkmanService linkmanService) {
		this.linkmanService = linkmanService;
	}

	public Linkman getLinkman() {
		return linkman;
	}

	public void setLinkman(Linkman linkman) {
		this.linkman = linkman;
	}
	
	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
	
	
	/**
	 * 分页查询联系人
	 * @return
	 * @throws Exception
	 */
	public String findByPage() throws Exception{
		
		//System.out.println("--------------" + pageCode);
		DetachedCriteria criteria = DetachedCriteria.forClass(Linkman.class);
		
		PageBean<Linkman> page = linkmanService.findByPage(this.pageCode,this.pageSize,criteria);
		
		request.put("page", page);
		
		return "page";
	}
	

}
