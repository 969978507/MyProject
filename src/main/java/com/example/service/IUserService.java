package com.example.service;

import com.example.entity.User;

public interface IUserService {

	public User findById(String id);
	
	public int save(User user);
	
	public int update(User user);
	
	public int delete(String id);
}
