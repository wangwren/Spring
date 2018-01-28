package vvr.web.interceptor;

import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

import vvr.domain.User;

/**
 * 用户的拦截器，判断用户是否已经登录，如果登录，执行下一个拦截器。
 * 如果没有登录，返回到登录页面（不能对所有的请求都拦截，login和regist方法不能拦截）
 * 继承指定拦截器
 * @author wwr
 *
 */
public class UserInterceptor extends MethodFilterInterceptor implements SessionAware {

	private static final long serialVersionUID = -8421678755268452755L;
	private Map<String,Object> session;
	
	@Override
	public void setSession(Map<String, Object> session) {

		this.session = session;
	}

	/**
	 * 拦截目标Action的方法
	 */
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		
		/**
		 * 使用这种Map方式的session，会抛空异常，原因不清楚，反正在拦截器中不好使
		 * User user = (User) session.get("user");
		 */
		
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		//如果没有登录
		if(user == null){
			return "login";
		}
		// 执行下一个拦截器
		return invocation.invoke();
	}

}
