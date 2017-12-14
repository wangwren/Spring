package vvr.demo4;

public class Car {

	//通过构造方法注入
	private String name;
	private double price;
	
	public Car(String name, double price) {
		super();
		this.name = name;
		this.price = price;
	}

	@Override
	public String toString() {
		return "Car [name=" + name + ", price=" + price + "]";
	}
	
	
	
}
