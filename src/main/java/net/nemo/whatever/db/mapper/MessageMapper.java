package net.nemo.whatever.db.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.nemo.whatever.entity.Message;
import net.nemo.whatever.entity.User;

public interface MessageMapper {
	
	int insert(Message message);
	void insertList(List<Message> messages);
	int findCount(@Param("msg") Message message);
	List<Map> findMessages(@Param("chat_id") Integer chat_id);
}
