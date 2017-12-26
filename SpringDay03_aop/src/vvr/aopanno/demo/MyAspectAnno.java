package vvr.aopanno.demo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 使用注解方式完成AOP
 * @author wwr
 *
 */
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
