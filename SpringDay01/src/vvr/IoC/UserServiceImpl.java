package vvr.IoC;

public class UserServiceImpl implements UserService {

	//普通属性的依赖注入
	private String name;
	
	
	public void setName(String name) {
		this.name = name;
	}


	@Override
	public void sayHello() {
		
		System.out.println("Hello Spring!!!" + name);
		
	}

}
