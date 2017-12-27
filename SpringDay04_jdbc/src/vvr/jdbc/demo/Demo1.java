package vvr.jdbc.demo;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Spring中jdbc的模板类
 * @author wwr
 *
 */
public class Demo1 {

	/**
	 * 先使用new的方法演示
	 */
	@Test
	public void run() {
		
		//JDBC模板类
		JdbcTemplate template = new JdbcTemplate();
		
		//获取Spring所提供的连接池
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/spring_day03?useSSL=false");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		
		//设置连接池
		template.setDataSource(dataSource);
		
		template.update("insert into t_account(name,money) values(?,?)", "哈哈",100000);
	}
}
