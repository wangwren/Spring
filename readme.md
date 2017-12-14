# Spring
## Ioc
- IoC   Inverse of Control，控制反转，将对象的创建权反转给Spring
- 使用IOC可以解决的程序耦合性高的问题
- 入门:[Demo2](https://github.com/wangwren/Spring/tree/master/SpringDay01/src/vvr/IoC)
### Spring框架中的工厂
1. `ApplicationContext`接口(推荐)
    - 使用`ApplicationContext`工厂的接口，使用该接口可以获取到具体的Bean对象
    - 该接口有如下两个具体的实现类
        - `ClassPathXmlApplicationContext`:加载类路径下的Spring配置文件(推荐)
        - `FileSystemXmlApplicationContext`:加载本地磁盘下的Spring配置文件
2. `BeanFactory`工厂(是Spring框架早期创建Bean对象的工厂接口，已过时)
    - 使用`BeanFactory`接口也可以获取到Bean对象
```java
public void run(){
    BeanFactory factory = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));
    UserService us = (UserService)factory.getBean(us);
    us.sayHello();
}
```
- `BeanFactory`和`ApplicationContext`的区别
    - `BeanFactory`:BeanFactory采取延迟加载，第一次getBean时才会初始化Bean
    - `ApplicationContext`:在加载applicationContext.xml时就会创建具体的Bean对象的实例，还提供了一些其他的功能
        - 事件传递
        - Bean自动装配
        - 各种不同应用层的Context实现
### Spring框架Bean管理的配置文件方式
#### Spring框架中标签的配置
1. id属性和name属性的区别
    - id:Bean起个名字，在约束中采用ID的约束，唯一
        - 取值要求:必须以字母开始，可以使用字母、数字、连字符、下划线、句号、冒号，id不能出现特殊字符。
    - name:Bean起个名字，没有采用ID的约束
        - 取值要求:name:可出现特殊字符，如果`<bean>`没有id的话，name可以当做id使用
        - Spring框架在整合Struts1的框架的时候，Struts1的框架的访问路径是以`/`开头的,例如:`/bookAction`
2. class属性:Bean对象的完整类名
3. scope属性:scope属性代表Bean的作用范围
    - singleton:单例(默认值，重点)
    - prototype:多例(重点)，在Spring框架整合Struts2框架的时候，Action类也需要交给Spring做管理，配置把Action类配置成多例
    - request:应用在Web项目中，每次HTTP请求都会创建一个新的Bean
    - session:应用在Web项目中，同一个HTTP Session共享一个Bean
    - globalsession:应用在Web项目中，多服务器间的session
4. Bean对象的创建和销毁的两个属性配置
    - 说明:Spring初始化或销毁bean时，有时需要做一些处理工作，因此spring可以在创建和拆卸bean时调用bean的两个生命周期方法
    - init-method:当bean被载入到容器的时候调用init-method属性指定的方法
    - destroy-method:当bean从容器中删除的时候调用destory-method属性指定的方法
    - 想查看destory-method的效果，有如下条件:
        - scope=singleton有效
        - web容器中会自动调用，但是main函数或测试用例需要手动调用(需要使用**`ClassPathXmlApplicationContext`**的close()方法)
#### 依赖注入(DI)
- 概念:
    - IOC       -- Inverse of Control，控制反转，将对象的创建权反转给Spring！！
    - DI        -- Dependency Injection，依赖注入，在Spring框架负责创建Bean对象时，动态的将依赖对象注入到Bean组件中！！
- DI(依赖注入)
    - 例如:如果`UserServiceImpl`的实现类中有一个属性，那么使用Spring框架的IoC功能时，可以通过依赖注入把该属性的值传进来。
    - 具体配置:
```java
  <bean id="us" class="com.itheima.demo1.UserServiceImpl">
            <property name="uname" value="小风"/>
   </bean>
```
代码参考:[Demo3](https://github.com/wangwren/Spring/tree/master/SpringDay01/src/vvr/DI)  
#### Spring框架的属性注入
1. 对于类成员变量，常用的注入方式有两种
    - 构造函数注入
    - 属性setter方法注入
2. 注入方式:
    - 构造方法的注入方式
```java
//java类
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
	
//applicationContext.xml
<!-- 通过构造方法注入 -->
    <bean id="car" class="vvr.demo4.Car">
    	<!-- 通过构造方法注入需要使用constructor-arg标签，name表示属性名，value表示想要传入的值
    		 index表示第几个属性，从0开始 -->
    	<!-- <constructor-arg name="name" value="保时捷"/>
    	<constructor-arg name="price" value="100000000000000"/> -->
    	<constructor-arg index="0" value="怕哪摸哪"/>
    	<constructor-arg index="1" value="100000000000000"/>
    </bean>
```
    - 构造函数注入时，传入的是对象
```java
//通过构造函数注入对象
	
	private String name;
	private Car car;
	
	public Person(String name, Car car) {
		super();
		this.name = name;
		this.car = car;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", car=" + car + "]";
	};
//applicationContext.xml
<!-- 通过构造函数注入对象 -->
    <bean id="person" class="vvr.demo4.Person">
    	<constructor-arg name="name" value="小明"/>
    	<constructor-arg name="car" ref="car"/>
    </bean>
```
3. 属性setter的方式已经提到过，可查看源代码，setter比较常用

