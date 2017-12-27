package vvr.jdbc.demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * 使用IOC方式使用JDBC模板类
 * @author wwr
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Demo1_1 {

	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 插入操作
	 */
	@Test
	public void save() {
		
		jdbcTemplate.update("insert into t_account(name,money) values(?,?)", "嗯嗯",100000);
	}
	
	/**
	 * 修改操作
	 */
	@Test
	public void update() {
		jdbcTemplate.update("update t_account set name=? where id=?", "啦啦",4);
	}
	
	/**
	 * 删除操作
	 */
	@Test
	public void delete() {
		jdbcTemplate.update("delete from t_account where id=?", 4);
	}
	
	/**
	 * 查询单条数据
	 */
	@Test
	public void queryOne() {
		//rowMapper是一个接口，需要自己完成实现类
		Account ac = jdbcTemplate.queryForObject("select * from t_account where id=?", new BeanMapper(), 1);
		System.out.println(ac);
	}
	
	/**
	 * 查询全部数据
	 */
	@Test
	public void queryAll() {
		List<Account> list = jdbcTemplate.query("select * from t_account", new BeanMapper());
		System.out.println(list);
	}
}

/**
 * 手动编写实现类，rowMapper是一行一行封装数据，不需要使用循环
 * @author wwr
 *
 */
class BeanMapper implements RowMapper<Account>{

	@Override
	public Account mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Account ac = new Account();
		ac.setId(rs.getInt("id"));
		ac.setName(rs.getString("name"));
		ac.setMoney(rs.getDouble("money"));
		
		return ac;
	}
	
}
