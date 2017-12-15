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
#### Spring的2.5版本中提供了一种:p名称空间的注入
- 需要先引入 p 名称空间
    - 在schema的名称空间中加入该行：`xmlns:p="http://www.springframework.org/schema/p"`
- 使用p名称空间的语法
    - p:属性名 = ""
    - p:属性名-ref = ""
- 测试
```java
<bean id="person" class="com.itheima.demo4.Person" p:pname="老王" p:car2-ref="car2"/>
```
#### Spring的3.0提供了一种:SpEL注入方式
- SpEL：Spring Expression Language是Spring的表达式语言，有一些自己的语法
- 语法:`#{SpEL}`
- 例如
```java
<!-- SpEL的方式 -->
    <bean id="person" class="com.itheima.demo4.Person">
        <property name="pname" value="#{'小风'}"/>
        <property name="car2" value="#{car2}"/>
    </bean>
```
- 还支持调用类中的属性或者方法
#### 数组，集合(List,Set,Map),Properties等的注入
- 如果是数组或者List集合，注入配置文件的方式相同
```java
<!-- 注入数组 -->
    	<property name="arrs">
    		<list>
    			<value>aaa</value>
    			<value>bbb</value>
    		</list>
    	</property>
    	
    	<!-- 注入List -->
    	<property name="list">
    		<list>
    			<!-- 如果list的泛型是一个对象，也可以注入引用，格式: <ref bean="person"/> -->
    			<value>ccc</value>
    			<value>ddd</value>
    		</list>
    	</property>
```
- 如果是Set集合，注入的配置文件方式:
```java
<!-- 注入Set -->
    	<property name="set">
    		<set>
    			<value>eee</value>
    			<value>fff</value>
    		</set>
    	</property>
```
- 如果是Map集合，注入的配置方式:
```java
<!-- 注入Map -->
    	<property name="map">
    		<map>
    			<entry key="123" value="ggg"></entry>
    			<entry key="456" value="hhh"></entry>
    		</map>
    	</property>
```
- 如果是properties属性文件的方式，注入的配置:
```java
<!-- 注入properties文件 -->
    	<property name="pro">
    		<props>
    			<prop key="username">root</prop>
    			<prop key="password">root</prop>
    		</props>
    	</property>
```
#### Spring框架的配置文件分开管理
在src目录下又多创建了一个配置文件，现在是两个核心的配置文件，那么加载这两个配置文件的方式有两种  
- 主配置文件中包含其他配置文件
```java
<import resource="applicationContext2.xml"/>
```
- 工厂创建的时候直接加载多个配置文件:
```java
 ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                    "applicationContext.xml","applicationContext2.xml");
```
#### Spring框架整合WEB(不是最终方案)
1. 创建JavaWEB项目，引入Spring的开发包。编写具体的类和方法。
    * 环境搭建好后，启动服务器来测试项目，发送每访问一次都会加载一次配置文件，这样效率会非常非常慢！
2. 解决上面的问题
    * 将工厂创建好了以后放入到ServletContext域中.使用工厂的时候,从ServletContext中获得.
        * ServletContextListener:用来监听ServletContext对象的创建和销毁的监听器.
        * 当ServletContext对象创建的时候:创建工厂 , 将工厂存入到ServletContext
3. Spring整合Web项目
    * 引入spring-web-4.2.4.RELEASE.jar包
    * 配置监听器(在web.xml文件中配置,写法固定)
```java
<!-- 配置Spring的核心监听器: -->
         <listener>
            <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
         </listener>
         <context-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:applicationContext.xml</param-value>
         </context-param>
```
4. 修改servlet的代码
    * 从ServletContext中获得工厂
    * 具体代码如下
```java
ServletContext servletContext = ServletActionContext.getServletContext();
        // 需要使用WEB的工厂的方式
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        CustomerService cs = (CustomerService) context.getBean("customerService");
        cs.save();  
```
 