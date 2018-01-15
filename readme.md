# Spring
# 目录
- **[Ioc](#ioc)**
    - **[Spring框架中的工厂](#spring框架中的工厂)**
    - **[Spring框架Bean管理的配置文件方式](#spring框架bean管理的配置文件方式)**
        - **[Spring框架中标签的配置](#spring框架中标签的配置)**
        - **[依赖注入(DI)](#依赖注入(DI))**
        - **[Spring框架的属性注入](#spring框架的属性注入)**
        - **[Spring的2.5版本中提供了一种:p名称空间的注入](#spring的2.5版本中提供了一种:p名称空间的注入)**
        - **[Spring的3.0提供了一种:SpEL注入方式](#spring的3.0提供了一种:SpEL注入方式)**
        - **[数组，集合(List,Set,Map),Properties等的注入](#数组，集合(list,set,map),properties等的注入)**
        - **[Spring框架的配置文件分开管理](#spring框架的配置文件分开管理)**
        - **[Spring框架整合WEB(不是最终方案)](#spring框架整合web(不是最终方案))**
    - **[Spring框架的IoC功能之注解的方式](#spring框架的ioc功能之注解的方式)**
        - **[Spring框架中Bean管理的常用注解](#spring框架中bean管理的常用注解)**
        - **[Bean的作用范围和生命周期的注解](#bean的作用范围和生命周期的注解)**
        - **[spring框架整合junit单元测试](#spring框架整合junit单元测试)**
- **[AOP](#aop)**
    - **[AOP底层实现](#aop底层实现)**
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
        - web容器中会自动调用，但是main函数或测试用例需要手动调用(需要使用**`ClassPathXmlApplicationContext`**的close()方法
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
### Spring框架的IoC功能之注解的方式
1. 需要导入一个Spring框架的AOP的jar包，spring-aop.jar
2. 编写类
3. 在src目录下，创建`applicationContext.xml`的配置文件，然后引入约束。(因为现在想使用注解的方式，那么引入的约束发生了变化)
```java
<beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:context="http://www.springframework.org/schema/context" xsi:schemaLocation="
                http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd"> <!-- bean definitions here -->

</beans>
```
4. 在`applicationContext.xml`配置文件中开启组件扫描
```java
<context:component-scan base-package="vvr.spring.demo1"/>
```
**注意**:也可以采用如下配置:  
```java
<context:component-scan base-package="vvr.spring"/>
```
这样是扫描vvr.spring包下所有的内容  
5. 在实现类上添加注解
```java
@Component(value="userService") 
//等价于  <bean id="userService" class="vvr.spring.demo1.UserServiceImpl">
```
6. 编写测试代码
[测试代码](https://github.com/wangwren/Spring/tree/master/SpringDay02/src/vvr/spring/demo1)

#### Spring框架中Bean管理的常用注解
1. @Component  组件 (作用在类上)
2. Spring中提供@Component的三个衍生注解:
    * @Controller   作用在WEB层
    * @Service  作用在业务层
    * @Repository  作用在持久层
3. 属性注入的注解(说明:使用注解注入的方式，可以不提供set方法)
    * 如果注入的是基本数据类型和字符串，可以使用Value注解
        * @Value   用于注入基本数据类型和String类型
    * 如果注入的是对象类型，使用如下注解
        * @Autowired   默认按类型进行自动装配
            * 如果想按名称注入
            * @Qualifier  强制使用名称注入(需要和@Autowired一起使用)
    * @Resource  相当于@Autowired和@Qualifier一起使用
        * 这个是java提供的注解，spring对其支持
        * 属性使用name属性
#### Bean的作用范围和生命周期的注解
1. Bean的作用范围注解
    * 注解为@Scope(value="prototype"),作用在类上。
2. Bean的声明周期的配置
    * 注解如下:
        * @PostConstruct    -- 相当于init-method
        * @PreDestroy       -- 相当于destroy-method
#### Spring框架整合JUnit单元测试
- 为了简化JUnit测试，可以使用Spring框架也可以(保证不必每次测试类都需要写工厂)
- 步骤
    - 必须先有JUnit的环境
    - 在程序中导入:`spring-test.jar`
    - 在具体的测试类上添加注解
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Demo2 {

	@Resource(name="userService")
	private UserService us;
	
	@Test
	public void run() {
		us.sayHello();
	}
}
```
## AOP
### AOP底层实现
1. Srping框架的AOP技术底层也是采用的代理技术，代理的方式提供了两种
    1. 基于JDK的动态代理
        * 必须是面向接口的，只有实现了具体接口的类才能生成代理对象
    2. 基于CGLIB动态代理
        * 对于没有实现了接口的类，也可以产生代理，产生这个类的子类的方式
2. Spring的传统AOP中根据类是否实现接口，来采用不同的代理方式
    1. 如果实现类接口，使用JDK动态代理完成AOP
    2. 如果没有实现接口，采用CGLIB动态代理完成AOP
### Spring基于AspectJ的AOP的开发
[入门代码](https://github.com/wangwren/Spring/tree/master/SpringDay02_aop/src/vvr/demo1)
#### 技术分析之AOP的相关术语
1. Joinpoint(连接点)   -- 所谓连接点是指那些被拦截到的点。在spring中,这些点指的是方法,因为spring只支持方法类型的连接点
2. Pointcut(切入点)        -- 所谓切入点是指我们要对哪些Joinpoint进行拦截的定义
3. Advice(通知/增强)    -- 所谓通知是指拦截到Joinpoint之后所要做的事情就是通知.通知分为前置通知,后置通知,异常通知,最终通知,环绕通知(切面要完成的功能)
4. Introduction(引介) -- 引介是一种特殊的通知在不修改类代码的前提下, Introduction可以在运行期为类动态地添加一些方法或Field
5. Target(目标对象)     -- 代理的目标对象
6. Weaving(织入)      -- 是指把增强应用到目标对象来创建新的代理对象的过程
7. Proxy（代理）        -- 一个类被AOP织入增强后，就产生一个结果代理类
8. Aspect(切面)           -- 是切入点和通知的结合，以后咱们自己来编写和配置的
#### 切入点的表达式
- 切入点表达式的格式如下:
    - `execution([修饰符] 返回值类型 包名.类名.方法名(参数)`
- 修饰符可以省略不写，不是必须要出现的。
- 返回值类型是不能省略的，根据方法来编写返回值。可以使用`*`代替，表示所有的返回类型。
- 包名例如:`vvr.aop.demo`
    - 中间的包名可以使用`*`代替
    - 如果想省略可以使用`*..*`,表示所有的包
- 类名也可以使用`*`代替:`*DaoImpl`
- 方法也可以使用`*`代替
- 参数如果是一个参数可以使用`*`代替，如果想代表任意参数可以使用`..`
#### AOP的通知类型
- 前置通知
    - 在目标类的方法执行之前执行
    - 配置文件信息:`<aop:after method="before" pointcut-ref="myPointcut3"/>`
    - 应用:可以对方法的参数来做校验
- 最终通知
    - 在目标类的方法执行之后执行，如果程序出现了异常，最终通知也会执行。
    - 配置文件信息:`<aop:after method="after" pointcut-ref="myPointcut3"/>`
    - 应用:例如像释放资源
- 后置通知
    - 方法正常执行后的通知。
    - 配置文件信息:`<aop:after-returning method="afterReturning" pointcut-ref="myPointcut2"/>`
    - 应用:可以修改方法的返回值
- 异常抛出通知
    - 在抛出异常后通知
    - 配置文件信息:`<aop:after-throwing method="afterThorwing" pointcut-ref="myPointcut3"/>`
    - 应用:包装异常的信息
- 环绕通知
    - 方法执行前后执行。
    - 配置文件信息:`<aop:around method="around" pointcut="execution(* *..*.*DaoImpl.save*(..))"/>`
    - 注意:目标的方法默认不执行，需要使用`ProceedingJoinPoint`对象来让目标对象的方法执行。
```java
/**
	 * 环绕通知，方法执行前和方法执行后进行通知，默认情况下，目标对象的方法不能执行，需要手动让目标对象的方法执行
	 */
	public void around(ProceedingJoinPoint joinPoint) {
		
		System.out.println("环绕通知1...");
		
		try {
			//手动调用目标方法，固定写法
			joinPoint.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("环绕通知2...");
	}
```
#### AOP注解方式
1. 将目标类配置到Spring配置文件中
```java
<bean id="customerDao" class="vvr.aopanno.demo.CustomerDaoImpl"/>
```
2. 定义切面类
    - 添加切面和通知的注解
        - `@Aspect` 定义切面类的注解
     - 通知类型(注解的参数是切入点的表达式)
        - `@Before` 前置通知
        - `@AfterReturing` 后置通知
        - `@Around` 环绕通知
        - `@After` 最终通知
        - `@AfterThrowing` 异常抛出通知
    - 具体代码:
```java
@Aspect
public class MyAspectAnno {

	/**
	 * 后置通知，value属性中填写切入点表达式
	 */
	@Before(value="MyAspectAnno.fn()")
	public void log() {
		System.out.println("记录日志...");
	}
	
	/**
	 * 最终通知
	 * @param joinPoint
	 */
	@Around(value="MyAspectAnno.fn()")
	public void around(ProceedingJoinPoint joinPoint) {
		System.out.println("环绕通知1...");
		try {
			joinPoint.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("环绕通知2...");
	}
	
	@Pointcut(value="execution(* *..*.*DaoImpl.save*(..))")
	public void fn() {}
}
```
3. 配置文件中定义切面类
`<bean id="myAspectAnno" class="vvr.aopanno.demo.MyAspectAnno"/>`
4. 在配置文件中开启自动代理`<aop:aspectj-autoproxy/>`
5. 完成测试
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Demo {

	@Resource(name="customerDao")
	private CustomerDao customerDao;
	
	@Test
	public void run() {
		
		customerDao.save();
		customerDao.update();
	}
}
```
#### 注解方式的通知类型
   * @Before               -- 前置通知
   * @AfterReturing        -- 后置通知
   * @Around               -- 环绕通知（目标对象方法默认不执行的，需要手动执行）
   * @After                -- 最终通知
   * @AfterThrowing        -- 异常抛出通知
#### 配置通用的切入点
   * 使用@Pointcut定义通用的切入点
```java
 @Aspect
    public class MyAspectAnno {
        @Before(value="MyAspectAnno.fn()")
        public void log(){
            System.out.println("记录日志...");
        }
        @Pointcut(value="execution(public void com.itheima.demo1.CustomerDaoImpl.save())")
        public void fn(){}
    }
```
[AOP注解方式代码](https://github.com/wangwren/Spring/tree/master/SpringDay03_aop/src/vvr/aopanno/demo)  
### Spring框架的JDBC模板
1. Spring框架中提供了很多持久层的模板类来简化编程，使用模板类编写会变得简单。
2. 提供了JDBC模板，Spring框架提供的
    - `JdbcTemplate`类
3. Spring框架可以整合Hibernate框架
    - `HibernateTemplate`类  
代码参照:[JDBC模板](https://github.com/wangwren/Spring/tree/master/SpringDay04_jdbc/src/vvr/jdbc/demo)
#### Spring框架管理开源连接池
- DBCP连接池
    - 引入jar包
        - com.springsource.org.apache.commons.dbcp-1.2.2.osgi.jar
        - com.springsource.org.apache.commons.pool-1.5.3.jar
    - 配置文件
```java
<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
		<property name="driverClassName" value="com.mysql.jdbc.Driver"/>
		<property name="url" value="jdbc:mysql://localhost:3306/spring_day03?useSSL=false"/>
		<property name="username" value="root"/>
		<property name="password" value="root"/>
	</bean>
```
- c3p0连接池
    - 引入jar包
         -  com.springsource.com.mchange.v2.c3p0-0.9.1.2.jar
    - 编写配置文件
```java
 <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
            <property name="driverClass" value="com.mysql.jdbc.Driver"/>
            <property name="jdbcUrl" value="jdbc:mysql:///spring_day03"/>
            <property name="user" value="root"/>
            <property name="password" value="root"/>
        </bean>
```
#### Spring框架的JDBC模板的操作
参考代码:[操作](https://github.com/wangwren/Spring/blob/master/SpringDay04_jdbc/src/vvr/jdbc/demo/Demo1_1.java)  
#### Spring框架的事务管理相关的类和API
1. PlatformTransactionManager接口     -- 平台事务管理器.(真正管理事务的类)。该接口有具体的实现类，根据不同的持久层框架，需要选择不同的实现类！
2. TransactionDefinition接口          -- 事务定义信息.(事务的隔离级别,传播行为,超时,只读)
3. TransactionStatus接口              -- 事务的状态

4. 总结：上述对象之间的关系：平台事务管理器真正管理事务对象.根据事务定义的信息TransactionDefinition 进行事务管理，在管理事务中产生一些状态.将状态记录到TransactionStatus中

5. PlatformTransactionManager接口中实现类和常用的方法
    1. 接口的实现类
        * 如果使用的Spring的JDBC模板或者MyBatis框架，需要选择DataSourceTransactionManager实现类
        * 如果使用的是Hibernate的框架，需要选择HibernateTransactionManager实现类

    2. 该接口的常用方法
        * void commit(TransactionStatus status) 
        * TransactionStatus getTransaction(TransactionDefinition definition) 
        * void rollback(TransactionStatus status) 

6. TransactionDefinition
    1. 事务隔离级别的常量
        * static int ISOLATION_DEFAULT                  -- 采用数据库的默认隔离级别
        * static int ISOLATION_READ_UNCOMMITTED 
        * static int ISOLATION_READ_COMMITTED 
        * static int ISOLATION_REPEATABLE_READ 
        * static int ISOLATION_SERIALIZABLE 

    2. 事务的传播行为常量（不用设置，使用默认值）
        * 先解释什么是事务的传播行为：解决的是业务层之间的方法调用！！

        * PROPAGATION_REQUIRED（默认值） -- A中有事务,使用A中的事务.如果没有，B就会开启一个新的事务,将A包含进来.(保证A,B在同一个事务中)，默认值！！
        * PROPAGATION_SUPPORTS          -- A中有事务,使用A中的事务.如果A中没有事务.那么B也不使用事务.
        * PROPAGATION_MANDATORY         -- A中有事务,使用A中的事务.如果A没有事务.抛出异常.

        * PROPAGATION_REQUIRES_NEW（记）-- A中有事务,将A中的事务挂起.B创建一个新的事务.(保证A,B没有在一个事务中)
        * PROPAGATION_NOT_SUPPORTED     -- A中有事务,将A中的事务挂起.
        * PROPAGATION_NEVER             -- A中有事务,抛出异常.

        * PROPAGATION_NESTED（记）     -- 嵌套事务.当A执行之后,就会在这个位置设置一个保存点.如果B没有问题.执行通过.如果B出现异常,运行客户根据需求回滚(选择回滚到保存点或者是最初始状态)
#### 事务管理转账案例基于AspectJ的XML方式
- 简化开发，可以让Dao继承`JdbcDaoSupport`类，则免去JDBC模板的配置，业务逻辑层中只需注入dataSource即可
- 参照代码[转账之XML](https://github.com/wangwren/Spring/tree/master/SpringDay05_tx/src/vvr/demo1)
#### 事务管理转账案例基于AspectJ的注解方式(最简单的方式)
- 参照代码[转账之注解](https://github.com/wangwren/Spring/tree/master/SpringDay05_tx/src/vvr/demo2)
## SSH框架整合
[第一种整合方案](https://github.com/wangwren/Spring/tree/master/SpringDay06_ssh1)  
[第二种整合方案](https://github.com/wangwren/Spring/tree/master/SpringDay07_ssh2)  
只是整合hibernate时不同，推荐使用第二种整合。  
- struts2需要导入一个`struts2-spring-plugin.jar`的jar包
#### 延迟加载问题
1. 使用延迟加载的时候，再WEB层查询对象的时候程序会抛出异常！
    * 原因是延迟加载还没有发生SQL语句，在业务层session对象就已经销毁了，所以查询到的JavaBean对象已经变成了托管态对象！

    * 注意：一定要先删除javassist-x.xx.x.GA.jar包（jar包冲突了）

2. 解决办法非常简单，Spring框架提供了一个过滤器，让session对象在WEB层就创建，在WEB层销毁。只需要配置该过滤器即可
    * 但是：要注意需要在struts2的核心过滤器之前进行配置
```java
 <filter>
            <filter-name>OpenSessionInViewFilter</filter-name>
            <filter-class>org.springframework.orm.hibernate5.support.OpenSessionInViewFilter</filter-class>
        </filter>
        <filter-mapping>
            <filter-name>OpenSessionInViewFilter</filter-name>
            <url-pattern>/*</url-pattern>
        </filter-mapping
```
## 详细整合方案(以下使用的struts2.3,将对应jar包换成2.5版本即可，项目中我使用的是2.5版本)
### 案例一：SSH框架整合保存客户 ###
	
----------
	
**需求分析**
	
	1. 案例一：SSH框架整合保存客户
	
----------
	
### 技术分析之SSH框架的整合 ###
	
----------
	
**技术分析之SSH框架开发的基本回顾**
	
![](./_image/01-SSH回顾.bmp)
	
----------
	
**技术分析之SSH三大框架需要的jar包**
	
	1. Struts2框架
		* struts-2.3.24\apps\struts2-blank\WEB-INF\lib\*.jar		-- Struts2需要的所有jar包
		* struts2-spring-plugin-2.3.24.jar							---Struts2整合Spring的插件包
	
	2. Hibernate框架
		* hibernate-release-5.0.7.Final\lib\required\*.jar			-- Hibernate框架需要的jar包
		* slf4j-api-1.6.1.jar										-- 日志接口
		* slf4j-log4j12-1.7.2.jar									-- 日志实现
		* mysql-connector-java-5.1.7-bin.jar						-- MySQL的驱动包
	
	3. Spring框架
		* IOC核心包
		* AOP核心包
		* JDBC模板和事务核心包
		* Spring整合JUnit测试包
		* Spring整合Hibernate核心包
		* Spring整合Struts2核心包
	
----------
	
**技术分析之SSH三大框架需要的配置文件**
	
	1. Struts2框架
		* 在web.xml中配置核心的过滤器
			<filter>
				<filter-name>struts2</filter-name>
				<filter-class>org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter</filter-class>
			</filter>
			
			<filter-mapping>
				<filter-name>struts2</filter-name>
				<url-pattern>/*</url-pattern>
			</filter-mapping>
		
		* 在src目录下创建struts.xml，用来配置Action
	
	2. Hibernate框架
		* 在src目录创建hibernate.cfg.xml配置文件
		* 在JavaBean所在的包下映射的配置文件
	
	3. Spring框架
		* 在web.xml配置整合WEB的监听器
			<listener>
				<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
			</listener>
			<context-param>
				<param-name>contextConfigLocation</param-name>
				<param-value>classpath:applicationContext.xml</param-value>
			</context-param>
		
		* 在src目录下创建applicationContext.xml
		* 在src目录下log4j.proerties
	
----------
	
**技术分析之Spring框架整合Struts2框架**
	
	1. 导入CRM项目的UI页面，找到添加客户的页面，修改form表单，访问Action
	2. 编写CustomerAction接收请求，在struts.xml中完成Action的配置
		<package name="crm" extends="struts-default" namespace="/">
			<action name="customer_*" class="com.itheima.web.action.CustomerAction" method="{1}">
				
			</action>
		</package>
	
	3. 在Action中获取到service（开发不会使用，因为麻烦）
		* 可以通过 WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext()); 来获取，但是这种方式编写代码太麻烦了！！
	
	4. Spring整合Struts2框架的第一种方式（Action由Struts2框架来创建）
		* 因为导入的struts2-spring-plugin-2.3.24.jar 包自带一个配置文件 struts-plugin.xml ，该配置文件中有如下代码
			* <constant name="struts.objectFactory" value="spring" />	开启一个常量，如果该常量开启，那么下面的常量就可以使用
			* struts.objectFactory.spring.autoWire = name，该常量是可以让Action的类来自动装配Bean对象！！
	
	5. Spring整合Struts2框架的第二种方式（Action由Spring框架来创建）（推荐大家来使用的）
		* 把具体的Action类配置文件applicatonContext.xml的配置文件中，但是注意：struts.xml需要做修改
		* applicationContext.xml
			* <bean id="customerAction" class="com.itheima.web.action.CustomerAction" scope="prototype">
		
		* struts.xml中的修改，把全路径修改成ID值
			* <action name="customer_*" class="customerAction" method="{1}">
		
		* 第二种方式需要有两个注意的地方
			* Spring框架默认生成CustomerAction是单例的，而Struts2框架是多例的。所以需要配置 scope="prototype"
			* CustomerService现在必须自己手动注入了
	
----------
	
**技术分析之Spring框架整合Hibernate框架（带有hibernate.cfg.xml的配置文件。强调：不能加绑定当前线程的配置）**

	1. 编写CustomerDaoImpl的代码，加入配置并且在CustomerServiceImpl中完成注入
	2. 编写映射的配置文件，并且在hibernate.cfg.xml的配置文件中引入映射的配置文件
	
	3. 在applicationContext.xml的配置文件，配置加载hibernate.cfg.xml的配置
		<bean id="sessionFactory" class="org.springframework.orm.hibernate5.LocalSessionFactoryBean">
			<property name="configLocation" value="classpath:hibernate.cfg.xml"/>
		</bean>
	
	4. 在CustomerDaoImpl中想完成数据的添加，Spring框架提供了一个HibernateDaoSupport的工具类，以后DAO都可以继承该类！！
		public class CustomerDaoImpl extends HibernateDaoSupport implements CustomerDao {
			public void save(Customer c) {
				System.out.println("持久层...");
				this.getHibernateTemplate().save(c);
			}
		}
		
		<bean id="customerDao" class="com.itheima.dao.CustomerDaoImpl">
			<property name="sessionFactory" ref="sessionFactory"/>
		</bean>
	
	5. 开启事务的配置
		* 先配置事务管理器，注意现在使用的是Hibernate框架，所以需要使用Hibernate框架的事务管理器
			<bean id="transactionManager" class="org.springframework.orm.hibernate5.HibernateTransactionManager">
				<property name="sessionFactory" ref="sessionFactory"/>
			</bean>
		
		* 开启注解事务
			<tx:annotation-driven transaction-manager="transactionManager"/>
		
		* 在Service类中添加事务注解
			@Transactional
	
----------
	
**技术分析之Spring框架整合Hibernate框架（不带有hibernate.cfg.xml的配置文件）**
	
	1. Hibernate配置文件中
		* 数据库连接基本参数（4大参数）
		* Hibernate相关的属性
		* 连接池
		* 映射文件
	
	2. 开始进行配置
		* 先配置连接池相关的信息
			<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
				<property name="driverClass" value="com.mysql.jdbc.Driver"/>
				<property name="jdbcUrl" value="jdbc:mysql:///xxx"/>
				<property name="user" value="root"/>
				<property name="password" value="root"/>
			</bean>
		
		* 修改 LocalSessionFactoryBean 的属性配置，因为已经没有了hibernate.cfg.xml的配置文件，所以需要修改该配置，注入连接池
			<bean id="transactionManager" class="org.springframework.orm.hibernate5.HibernateTransactionManager">
				<property name="dataSource" ref="dataSource"/>
			</bean>
		
		* 继续在 LocalSessionFactoryBean 中配置，使用hibernateProperties属性继续来配置其他的属性，注意值是properties属性文件
			<!-- 配置其他的属性 -->
			<property name="hibernateProperties">
				<props>
					<prop key="hibernate.dialect">org.hibernate.dialect.MySQLDialect</prop>
					<prop key="hibernate.show_sql">true</prop>
					<prop key="hibernate.format_sql">true</prop>
					<prop key="hibernate.hbm2ddl.auto">update</prop>
				</props>
			</property>
			<!-- 配置映射 -->
			<property name="mappingResources">
				<list>
					<value>com/itheima/domain/Customer.hbm.xml</value>
				</list>
			</property>
	
----------
	
**技术分析之Hibernate的模板的常用的方法**
	
	1. 增删改的操作:
		* 添加:
			* save(Object obj);
		* 修改:
			* update(Object obj);
		* 删除:
			* delete(Object obj);
	
	2. 查询的操作:
		* 查询一条记录:
			* Object get(Class c,Serializable id);
			* Object load(Class c,Serializable id);
	
	3. 查询多条记录:
		* List find(String hql,Object... args);
	
----------

