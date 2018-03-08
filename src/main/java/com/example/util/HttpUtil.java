package com.example.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {
	protected static Logger logger=LoggerFactory.getLogger(HttpUtil.class);  
	/**
	 * 发送post请求
	 */
	public static Map<String, String> sendPostRequest(String reqURL, Map<String, String> reqParams, String reqCharset){
		StringBuilder reqData = new StringBuilder();
		for(Map.Entry<String, String> entry: reqParams.entrySet()){
			try{
				reqData.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), reqCharset)).append("&");
			}catch(UnsupportedEncodingException e){
				logger.error("编码字符串[" + entry.getValue() + "]时发生异常:系统不支持该字符集[" + reqCharset + "]");
				logger.error("", e);
				reqData.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
		}
		if(reqData.length() > 0){
			reqData.setLength(reqData.length() - 1); // 删除最后一个&符号
		}
		return sendPostRequest(reqURL, reqData.toString());
	}

	/**
	 * 发送post请求
	 */
	public static Map<String, String> sendPostRequest(String reqURL, String reqData){
		System.out.println("url:\t"+reqURL+"parameter:\t"+reqData);
		Map<String, String> respMap = new HashMap<String, String>();
		HttpURLConnection httpURLConnection = null;
		OutputStream out = null; // 写
		InputStream in = null; // 读
		String respBody = null; // HTTP响应报文体
		String respCharset = "UTF-8";
		try{
			URL sendUrl = new URL(reqURL);
			httpURLConnection = (HttpURLConnection) sendUrl.openConnection();
			httpURLConnection.setDoInput(true); // true表示允许获得输入流,读取服务器响应的数据,该属性默认值为true
			httpURLConnection.setDoOutput(true); // true表示允许获得输出流,向远程服务器发送数据,该属性默认值为false
			httpURLConnection.setUseCaches(false); // 禁止缓存
			httpURLConnection.setReadTimeout(30000); // 30秒读取超时
			httpURLConnection.setConnectTimeout(30000); // 30秒连接超时
			httpURLConnection.setRequestMethod("POST");
			out = httpURLConnection.getOutputStream();
			out.write(reqData.getBytes());
			out.flush(); // 发送数据
			StringBuilder respHeader = new StringBuilder();
			Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
			for(Map.Entry<String, List<String>> entry: headerFields.entrySet()){
				StringBuilder sb = new StringBuilder();
				for(int i = 0; i < entry.getValue().size(); i++){
					sb.append(entry.getValue().get(i));
				}
				if(null == entry.getKey()){
					respHeader.append(sb.toString());
				}else{
					respHeader.append(entry.getKey()).append(": ").append(sb.toString());
				}
				respHeader.append("\r\n");
			}
			String contentType = httpURLConnection.getContentType();
			if(null != contentType && contentType.toLowerCase().contains("charset")){
				respCharset = contentType.substring(contentType.lastIndexOf("=") + 1);
			}
			in = httpURLConnection.getInputStream();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			byte[] buff = new byte[1024];
			int len = -1;
			while((len = in.read(buff)) != -1){
				buffer.write(buff, 0, len);
			}
			respBody = buffer.toString(respCharset);
			respMap.put("respCode", String.valueOf(httpURLConnection.getResponseCode()));
			respMap.put("respBody", respBody);
			respMap.put("respMsg", respHeader.toString() + "\r\n" + respBody);
			return respMap;
		}catch(Exception e){
			logger.error("与[" + reqURL + "]通信异常");
			logger.error("", e);
			return respMap;
		}finally{
			if(out != null){
				try{
					out.close();
				}catch(Exception e){
					logger.error("关闭输出流时发生异常");
					logger.error("", e);
				}
			}
			if(in != null){
				try{
					in.close();
				}catch(Exception e){
					logger.error("关闭输入流时发生异常");
					logger.error("", e);
				}
			}
			if(httpURLConnection != null){
				httpURLConnection.disconnect();
				httpURLConnection = null;
			}
		}
	}

	/**
	 * 发送get请求
	 */
	public static Map<String, String> sendGetRequest(String reqURL, Map<String, String> reqParams, String reqCharset){
		StringBuilder reqData = new StringBuilder();
		for(Map.Entry<String, String> entry: reqParams.entrySet()){
			try{
				reqData.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), reqCharset)).append("&");
			}catch(UnsupportedEncodingException e){
				logger.error("编码字符串[" + entry.getValue() + "]时发生异常:系统不支持该字符集[" + reqCharset + "]");
				logger.error("", e);
				reqData.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
		}
		if(reqData.length() > 0){
			reqData.setLength(reqData.length() - 1); // 删除最后一个&符号
		}
		logger.info("--------请求数据的拼接:\t"+reqData.toString());
		return sendGetRequest(reqURL, reqData.toString());
	}

	/**
	 * 发送get请求
	 */
	public static Map<String, String> sendGetRequest(String reqURL, String reqData){
		Map<String, String> respMap = new HashMap<String, String>();
		HttpURLConnection httpURLConnection = null;
		OutputStream out = null; // 写
		InputStream in = null; // 读
		try{
			URL sendUrl = null;
			if(StringUtils.isBlank(reqData)){
				sendUrl = new URL(reqURL);
			}else{
				sendUrl = new URL(reqURL + "?" + reqData);
			}
			logger.info("--------get的请求地址:\t"+sendUrl);
			httpURLConnection = (HttpURLConnection) sendUrl.openConnection();
			httpURLConnection.setDoInput(true); // true表示允许获得输入流,读取服务器响应的数据,该属性默认值为true
			httpURLConnection.setDoOutput(true); // true表示允许获得输出流,向远程服务器发送数据,该属性默认值为false
			httpURLConnection.setUseCaches(false); // 禁止缓存
			httpURLConnection.setReadTimeout(30000); // 30秒读取超时
			httpURLConnection.setConnectTimeout(30000); // 30秒连接超时
			httpURLConnection.setRequestMethod("GET");
			if(httpURLConnection.getResponseCode() == 200){
				// 得到输入流
				InputStream is = httpURLConnection.getInputStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while(-1 != (len = is.read(buffer))){
					baos.write(buffer, 0, len);
					baos.flush();
				}
				respMap.put("respBody", baos.toString("utf-8"));
				return respMap;
			}
			return respMap;
		}catch(Exception e){
			logger.error("与[" + reqURL + "]通信异常");
			logger.error("", e);
			return respMap;
		}finally{
			if(out != null){
				try{
					out.close();
				}catch(Exception e){
					logger.error("关闭输出流时发生异常");
					logger.error("", e);
				}
			}
			if(in != null){
				try{
					in.close();
				}catch(Exception e){
					logger.error("关闭输入流时发生异常");
					logger.error("", e);
				}
			}
			if(httpURLConnection != null){
				httpURLConnection.disconnect();
				httpURLConnection = null;
			}
		}
	}
}
