package com.example.service.impl;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	//@Cacheable  spring 会在其被调用后将返回值缓存起来，以保证下次利用同样的参数来执行该方法时可以直接从缓存中获取结果，而不需要再次执行该方法。
	//@CachePut  标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。
	//@CacheEvict 用来标注在需要清除缓存元素的方法或类上的。
	@Resource
	private UserMapper userDao;
	
	@Override
	@Cacheable(value="user", key="'id_'+#id")
	public User findById(String id) {
		return userDao.findById(id);
	}
	
	@Override
	public int save(User user) {
		return userDao.save(user);
	}
	@Override
	public int update(User user) {
		return userDao.update(user);
	}
	@Override
	@CacheEvict(value="user", key="'id_'+#id")
	public int delete(String id) {
		return userDao.delete(id);
	}

}
