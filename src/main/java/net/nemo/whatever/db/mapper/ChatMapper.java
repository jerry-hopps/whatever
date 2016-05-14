package net.nemo.whatever.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import net.nemo.whatever.entity.Chat;
import net.nemo.whatever.entity.User;

public interface ChatMapper {
	int insert(Chat chat);
	Chat findBySenderAndReceiver(@Param("sender") String sender,@Param("receiver") User receiver);
	List<Chat> selectChats(@Param("receiver_id")int receiver_id);
	Chat findById(Integer id);
}
