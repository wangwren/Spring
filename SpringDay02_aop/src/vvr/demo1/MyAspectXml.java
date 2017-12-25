package vvr.demo1;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 切面类：切入点 +通知（通知包含类型）
 * 此处写的是通知，即要做什么
 * @author wwr
 */
public class MyAspectXml {

	/**
	 * 要做的是输出一句话
	 */
	public void log() {
		System.out.println("打印日志...");
	}
	
	/**
	 * 最终通知，即在目标方法执行之后再执行，如果发生异常也会执行
	 */
	public void after() {
		System.out.println("最终通知...");
	}
	
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
}
