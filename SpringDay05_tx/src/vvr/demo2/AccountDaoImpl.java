package vvr.demo2;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao {
	
	/*第一种方案，注入jdbcTemplate
	 * private JdbcTemplate jdbcTemplate;
	
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}*/

	@Override
	public void outMoney(String out, double money) {
		
		//jdbcTemplate.update(sql, args);
		
		/*
		 *第二种方案，继承 JdbcDaoSupport类
		 *第一种方案中被注释的代码在父类中存在
		 */
		//this.getJdbcTemplate().update(sql, args);
		
		/*
		 * 第三种方案(最终方案)
		 * 不需要在配置文件中注入jdbcTemplate,而是注入dataSource
		 * 因为父类中存在一个方法，当jdbc模板为null时，就通过dataSource创建一个jdbc模板
		 */
		this.getJdbcTemplate().update("update t_account set money = money - ? where name = ?",money,out);
		
	}

	@Override
	public void inMoney(String in, double money) {
		
		this.getJdbcTemplate().update("update t_account set money = money + ? where name = ?",money,in);

	}

}
