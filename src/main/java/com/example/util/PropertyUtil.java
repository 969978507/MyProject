package com.example.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
	/**
	 * 通过文件路劲获取Properties
	 */
	public static Properties getProperty(String file){
		Properties p = new Properties();
		try{
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			InputStream inStream = null;
			inStream = cl.getResourceAsStream(file);
			p.load(inStream);
		}catch(IOException e){
			e.printStackTrace();
		}
		return p;
	}

	/**
	 * 获取取property文件的值
	 */
	public static String getValue(Properties pro, String key){
		return "" + pro.get(key);
	}
}
