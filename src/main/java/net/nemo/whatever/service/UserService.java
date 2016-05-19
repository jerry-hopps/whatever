package net.nemo.whatever.service;

import org.apache.log4j.Logger;

import net.nemo.whatever.db.mapper.UserMapper;
import net.nemo.whatever.entity.User;

public class UserService {
	
	private Logger logger = Logger.getLogger(UserService.class);

	private UserMapper userMapper;
	
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	public UserMapper getUserMapper() {
		return userMapper;
	}
	
	public int addUser(User user){
		
		User u = userMapper.findByEmail(user.getEmail());
		if(u==null){
			this.userMapper.insert(user);
			logger.info(String.format("Inserting new user into DB : [%s]", user.toString()));
			u = user;
		}
		return u.getId();
	}
	
	public User findUserById(Integer id){
		return this.userMapper.findById(id);
	}
	
	public User findByEmail(String email){
		return this.userMapper.findByEmail(email);
	}
	
	public void updatePassword(User user){
		this.userMapper.updatePassword(user);
	}
	
	public void updateStatus(User user){
		this.userMapper.updateStatus(user);
	}
}
