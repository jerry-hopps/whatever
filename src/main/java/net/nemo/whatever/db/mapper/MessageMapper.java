package net.nemo.whatever.db.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.nemo.whatever.entity.Message;
import net.nemo.whatever.entity.User;

public interface MessageMapper {
	
	int insert(Message message);
	void insertList(List<Message> messages);
	Message findBy(@Param("time") Date timestamp, @Param("sender") String sender, @Param("receiver") User receiver, @Param("content") String content);
}
