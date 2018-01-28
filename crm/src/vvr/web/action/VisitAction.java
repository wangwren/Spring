package vvr.web.action;

import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import vvr.domain.PageBean;
import vvr.domain.User;
import vvr.domain.Visit;
import vvr.service.VisitService;

public class VisitAction extends ActionSupport implements ModelDriven<Visit>,SessionAware,RequestAware{

	private static final long serialVersionUID = -6035351988762502185L;

	private VisitService visitService;
	private Visit visit = new Visit();
	private Map<String,Object> session;
	private Map<String,Object> request;
	//当前页
	private Integer pageCode = 1;
	
	//一页显示多少条数据
	private Integer pageSize = 2;
	
	private String beginDate;
	private String endDate;
	
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
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

	public void setVisitService(VisitService visitService) {
		this.visitService = visitService;
	}
	
	@Override
	public Visit getModel() {
		return visit;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	
	/**
	 * 添加客户拜访
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception{
		
		//先获取到当前用户，设置到拜访记录中，再保存
		User user = (User) session.get("user");
		if(user == null) {
			return LOGIN;
		}
		
		visit.setUser(user);
		
		visitService.save(visit);
		
		return "save";
	}
	
	/**
	 * 客户拜访分页
	 * @return
	 * @throws Exception
	 */
	public String findByPage() throws Exception{
		
		//获得登录的用户
		User user = (User) session.get("user");
		if(user == null) {
			return LOGIN;
		}
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Visit.class);
		
		// 拼接查询的条件
		if(beginDate != null && !beginDate.trim().isEmpty()){
			criteria.add(Restrictions.ge("visit_time", beginDate));
		}
		// select * from xxx where visit_time >= ? and visit_time <= ?
		if(endDate != null && !endDate.trim().isEmpty()){
			criteria.add(Restrictions.le("visit_time", endDate));
		}
		
		// 添加查询的条件，当前登录的用户只能看见自己的拜访列表
		criteria.add(Restrictions.eq("user.user_id",user.getUser_id()));
		// 分页查询
		PageBean<Visit> page = visitService.findByPage(this.pageCode,this.pageSize,criteria);
		request.put("page", page);
		
		return "page";
	}

}
