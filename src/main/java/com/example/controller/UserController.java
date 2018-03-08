package com.example.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.User;
import com.example.service.IUserService;

@Controller
public class UserController {

	@Resource
	private IUserService userService;
	
	@RequestMapping("/toIndex")
	public String toIndex() {
		System.out.println(11223344);
		User user = userService.findById("001");
		System.out.println(user);
		
		return "/index";
	}
	
}
