package com.example.mapper;

import com.example.entity.User;

public interface UserMapper extends SqlMapper {
	
	public User findById(String Id); 
	
	public int save(User user);
	
	public int update(User user);
	
	public int delete(String id);
}
