package net.nemo.whatever.service;

import net.nemo.whatever.db.mapper.UserMapper;
import net.nemo.whatever.entity.User;

public class UserService {

	private UserMapper userMapper;
	
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	public UserMapper getUserMapper() {
		return userMapper;
	}
	
	public int addUser(User user){
		User u = userMapper.findByEmail(user.getEmail());
		return u==null ? this.userMapper.insert(user) : u.getId();
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
