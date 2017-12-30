package vvr.demo2;

public interface AccountDao {

	/**
	 * 扣钱方法
	 * @param out
	 * @param money
	 */
	public void outMoney(String out,double money);
	
	/**
	 * 加钱方
	 * @param in
	 * @param money
	 */
	public void inMoney(String in,double money);
}
