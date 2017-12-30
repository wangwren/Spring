package vvr.demo1;

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
