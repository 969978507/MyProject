package com.example.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class WechatUtil {
	protected static Logger log = LoggerFactory.getLogger(WechatUtil.class);  
	private static Properties pro = PropertyUtil.getProperty("wechat.properties");
	/**
	 * 对外开放的获取微信token接口
	 */
	public static String getToken(){
		String token = "";
		if(RedisUtil.get("wechatToken") == null){
			log.info("--------从微信端获取token--------");
			token = getWechatToken();
			token = RedisUtil.get("wechatToken").toString();
		}else{
			log.info("--------从redis获取token--------");
			token = RedisUtil.get("wechatToken").toString();
		}
		return token;
	}
	/**
	 * 调用微信端获取token接口
	 */
	private static String getWechatToken(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("appId", PropertyUtil.getValue(pro, "appId"));
		map.put("secret", PropertyUtil.getValue(pro, "secret"));
		map.put("grant_type", "client_credential");
		String resultMsg = HttpUtil.sendGetRequest(PropertyUtil.getValue(pro, "TOKENURL"), map, "UTF-8").get("respBody");
		String token = "";
		JSONObject object = JSONObject.parseObject(resultMsg);
		try{
			token = object.getString("access_token");
			log.info("--------获取到的token:\t" + token);
		}catch(Exception e){
			token = "获取token失败！错误码为：" + JSONObject.parseObject(resultMsg).getString("errcode");
		}
		return token;
	}
	/**
	* @Description: 请求微信JS-SDK的Ticket
	 */
	public static String requestTicket(){
		String jsTicketUrl = PropertyUtil.getValue(pro, "JSTICKETURL");
		String token = getToken();
		Map<String, String> map = new HashMap<String, String>();
		map.put("access_token", token);
		map.put("type", "jsapi");
		Map<String, String> m = HttpUtil.sendGetRequest(jsTicketUrl, map, "UTF-8");
		JSONObject json = JSONObject.parseObject(m.get("respBody"));
		if(!"ok".equals(json.get("errmsg"))) {
			log.error("--------微信授权失败--------");
			return null;
		}
		return json.get("ticket").toString();
	}
	/**
	 * @Description: 获取微信JS-SDK签名
	 */
	public static String getSign(String jsApiTicket, String timestamp, String url, String noncestr) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("jsapi_ticket", jsApiTicket);
		map.put("timestamp", timestamp);
		map.put("url", url);
		map.put("noncestr", noncestr);
		String sortParam = getUrlToAsc(map);
		String sign = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[] bytes = digest.digest(sortParam.getBytes("UTF-8"));
			sign = MD5.byteArrayToHexString(bytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sign;
	}

	/**
	 * @Description: ASCII码升序排序，并返回url形式 
	 */
	public static String getUrlToAsc(Map<String, String> map) {
		if (map == null) {
			return null;
		}
		Map<String, String> map1 = new TreeMap<String, String>(map);
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> m : map1.entrySet()) {
			sb.append(m.getKey()).append("=").append(m.getValue()).append("&");
		}
		return sb.deleteCharAt(sb.length() - 1).toString();
	}
}
