package com.example.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;

public class RedisUtil {
	
	private static Jedis jedis = new Jedis("127.0.0.1", 6379);
	protected static Logger log = LoggerFactory.getLogger(RedisUtil.class);  
	
	/**
	 * 设置字符串类型缓存
	 */
	public static boolean set(String key, String value) {
		try {
			jedis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("redis设置缓存失败：" +e.getMessage());
			return false;
		}
		return true;
	}
	/**
	 * 设置其他类型缓存
	 */
	public static boolean set(String key, Object value) {
		try {
			String json = JSONObject.toJSONString(value);
			jedis.set(key, json);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("redis设置缓存失败：" +e.getMessage());
			return false;
		}
		return true;
	}
	/**
	 * 设置其他类型缓存，包括缓存过期时间
	 */
	public static boolean set(String key, Object value, int outTime) {
		try {
			String json = JSONObject.toJSONString(value);
			jedis.setex(key, outTime, json);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("redis设置缓存失败：" +e.getMessage());
			return false;
		}
		return true;
	}
	/**
	 * 删除缓存
	 */
	public static boolean del(String key) {
		try {
			jedis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("redis删除缓存失败：" +e.getMessage());
			return false;
		}
		return true;
	}
	/**
	 * 拿到缓存
	 */
	public static Object get(String key) {
		try {
			Object object = jedis.get(key);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 拿到对象型缓存
	 */
	public static <T>T get(String key, Class<T> clazz) {
		try {
			String object = jedis.get(key);
			return JSONObject.parseObject(object, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
