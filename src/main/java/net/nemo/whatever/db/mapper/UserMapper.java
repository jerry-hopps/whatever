package net.nemo.whatever.db.mapper;

import net.nemo.whatever.entity.User;

public interface UserMapper {
	int insert(User user);
	
	User findByEmail(String email);
	User findById(Integer id);
}
