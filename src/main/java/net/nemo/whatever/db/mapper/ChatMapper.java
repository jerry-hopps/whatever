package net.nemo.whatever.db.mapper;

import org.apache.ibatis.annotations.Param;

import net.nemo.whatever.entity.Chat;
import net.nemo.whatever.entity.User;

public interface ChatMapper {
	int insert(Chat chat);
	Chat findBySenderAndReceiver(@Param("sender") String sender,@Param("receiver") User receiver);
}
