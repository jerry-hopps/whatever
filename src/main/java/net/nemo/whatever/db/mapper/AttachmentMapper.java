package net.nemo.whatever.db.mapper;

import org.apache.ibatis.annotations.Param;

import net.nemo.whatever.entity.Attachment;

public interface AttachmentMapper {

	void insert(Attachment attachment);
	Attachment findByChat(@Param("chatId") Integer chatId, @Param("fileName") String fileName);
}
