package vvr.demo1;

/**
 * 切面类：切入点 +通知（通知包含类型）
 * 此处写的是通知，即要做什么
 * @author wwr
 *
 */
public class MyAspectXml {

	/**
	 * 要做的是输出一句话
	 */
	public void log() {
		System.out.println("打印日志...");
	}
}
