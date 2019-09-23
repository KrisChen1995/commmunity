package com.chenfei.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenfei.community.mapper.UserMapper;
import com.chenfei.community.model.User;

@Service
public class UserService {
	
	
	@Autowired
	private UserMapper userMapper;

	public void createOrUpdate(User user) {
		User dbUser = userMapper.findByAccountId(user.getAccountId());
		if(dbUser == null) {
			
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModified(user.getGmtCreate());
			userMapper.insert(user);
		}else {
			
			dbUser.setGmtModified(System.currentTimeMillis());
			dbUser.setToken(user.getToken());
			dbUser.setName(user.getName());
			dbUser.setAvatarUrl(user.getAvatarUrl());
			userMapper.update(dbUser);
		}		
	}
	
}
