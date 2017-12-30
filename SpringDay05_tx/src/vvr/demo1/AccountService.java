package vvr.demo1;

/**
 * 演示转账
 * @author wwr
 *
 */
public interface AccountService {

	/**
	 * 转账方法
	 * @param out  扣钱方
	 * @param in   加钱方
	 * @param money	加的钱数
	 */
	public void pay(String out,String in,double money);
}
