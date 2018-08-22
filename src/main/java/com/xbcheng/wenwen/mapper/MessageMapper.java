package com.xbcheng.wenwen.mapper;

import com.xbcheng.wenwen.model.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKeyWithBLOBs(Message record);

    int updateByPrimaryKey(Message record);

    List<Message> selectByConversationId(String conversationId);

    List<Message> selectConversationList(Integer userId);

    int getConversationUnreadCount(@Param("conversationId") String conversationId,@Param("userId")Integer userId);

    int updateHasRead(@Param("conversationId") String conversationId,@Param("userId")Integer userId);
}