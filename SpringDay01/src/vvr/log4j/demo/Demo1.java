package vvr.log4j.demo;

import org.apache.log4j.Logger;
import org.junit.Test;

//日志的使用
public class Demo1 {

	private Logger log = Logger.getLogger(Demo1.class);
	
	@Test
	public void run() {
		
		log.info("执行了");
	}
}
