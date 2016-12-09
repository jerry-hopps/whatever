package net.nemo.whatever.service;

import org.apache.http.HeaderElementIterator;
import org.apache.log4j.Logger;

import net.nemo.whatever.db.mapper.UserMapper;
import net.nemo.whatever.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	private Logger logger = Logger.getLogger(UserService.class);

	@Autowired
	private UserMapper userMapper;
	
	public User addUser(User user){
		User u = userMapper.findByEmail(user.getEmail());
		if(u==null){
			this.userMapper.insert(user);
			logger.info(String.format("Inserting new user into DB : [%s]", user.toString()));
			u = user;
		}
		return u;
	}
	
	public User findUserById(Integer id){
		return this.userMapper.findById(id);
	}
	
	public User findByEmail(String email){
		return this.userMapper.findByEmail(email);
	}
	
	public User findByOpenId(String openId){
		return this.userMapper.findByOpenId(openId);
	}
	
	public void update(User user){
		this.userMapper.update(user);
	}
	
}
