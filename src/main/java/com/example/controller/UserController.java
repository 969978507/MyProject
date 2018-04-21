package com.example.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.User;
import com.example.service.IUserService;
import com.example.util.QrCodeUtil;
import com.example.util.RedisUtil;
import com.example.util.RedisUtils;

@Controller
public class UserController {
	protected static Logger log = LoggerFactory.getLogger(RedisUtil.class);  

	@Resource
	private IUserService userService;
	
	@Autowired
    private RedisUtils redisUtils;  //记得注入
	
    
	@RequestMapping("/toIndex")
	public String toIndex() {
		/*redisUtils.set("name", "7777");
		System.out.println(redisUtils.exists("name"));
		System.out.println(redisUtils.get("name"));
		System.out.println(11223344);
		User user = userService.findById("001");
		System.out.println(user);*/
		return "/index";
	}
	
	@ResponseBody
	@RequestMapping("toAjax")
	public String toAjax(User user) {
		return "11";
	}
	
	@ResponseBody
	@RequestMapping("getImg")
	public void toAjax(HttpServletResponse response) {
		QrCodeUtil.encode("www.baidu.com", response);
	}
    
}
