package net.nemo.whatever.db.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tonyshi on 2016/12/13.
 */
public interface TagMapper {
    List<String> findAllTags(@Param("user_id") Integer userId, @Param("type") Integer type);
    List<String> findTags(@Param("message_id") Integer messageId);
    void addTag(@Param("message_id") Integer msgId, @Param("tag_name") String tagName);
    void deleteTag(@Param("message_id") Integer msgId, @Param("tag_name") String tagName);
}
