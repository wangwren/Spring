package vvr.DI;

public class CustomerServiceImpl {

	//实际开发中，业务层往往依赖于持久层
	//Spring中就需要依赖注入，不需要new，在service写一个dao的属性，并写set方法
	private CustomerDaoImpl customerDao;
	
	
	public void setCustomerDao(CustomerDaoImpl customerDao) {
		this.customerDao = customerDao;
	}


	public void save() {
		System.out.println("我是业务层service....");
		
		//此时运行程序必错，因为没有实例化，需要在配置文件中加入引用
		customerDao.save();
	}
}
