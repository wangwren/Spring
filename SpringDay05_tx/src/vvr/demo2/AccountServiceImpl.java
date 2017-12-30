package vvr.demo2;

import org.springframework.transaction.annotation.Transactional;

/**
 * @Transactional()在类上指定，则该类中的所有方法都将开启事务，括号中可配置数据库操作
 * 在方法上指定，则该方法将开启事务
 * @author wwr
 *
 */
@Transactional
public class AccountServiceImpl implements AccountService {

	private AccountDao accountDao;
	
	
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}


	/**
	 * 在业务逻辑中使用事务
	 */
	@Override
	public void pay(String out, String in, double money) {

		//扣钱
		accountDao.outMoney(out, money);
		
		//模拟异常
		//int num = 1/0;
		
		//加钱
		accountDao.inMoney(in, money);
	}

}
