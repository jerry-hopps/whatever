package net.nemo.whatever.service;

import org.springframework.beans.factory.annotation.Autowired;

import net.nemo.whatever.db.mapper.UserMapper;
import net.nemo.whatever.entity.User;

public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	public int addUser(User user){
		return this.userMapper.insert(user);
	}
}
