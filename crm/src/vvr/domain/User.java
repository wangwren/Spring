package vvr.domain;

/**
 * 封装用户信息
 * @author wwr
 *
 */
public class User {
	
	//主键
	private Long user_id;
	//用户账号
	private String user_code;
	//用户名
	private String user_name;
	//用户密码
	private String user_password;
	//用户状态,1代表正常，0代表暂停
	private String user_state;
	
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getUser_state() {
		return user_state;
	}
	public void setUser_state(String user_state) {
		this.user_state = user_state;
	}
	
}
