package vvr.web.utils;

import java.util.UUID;

/**
 * 文件上传的工具类
 * @author wwr
 *
 */
public class UploadUtils {

	public static String makeFileName(String fileName) {
		
		//先查询
		int index = fileName.lastIndexOf(".");
		
		//截取
		String lastName = fileName.substring(index, fileName.length());
		
		//生成唯一的字符串,UUID生成的字符串格式qweqw-sad-asda  所以将- 替换成空字符串
		String name = UUID.randomUUID().toString().replaceAll("-", "");
		
		return name + lastName;
	}
	
	public static void main(String[] args) {
		String fileName = "qqq.jpg";
		String newFileName = makeFileName(fileName);
		System.out.println(newFileName);
	}
}
