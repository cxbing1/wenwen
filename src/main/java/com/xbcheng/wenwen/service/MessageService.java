package com.xbcheng.wenwen.service;

import com.xbcheng.wenwen.model.Message;

import java.util.List;

public interface MessageService {

    public String addMessage(int toId,int fromId,String content);
    public List<Message> selectByConversationId(String conversationId);
    public List<Message> getConversationList(int userId);
    public List<Message> selectToMeMessageList(int userId);
    public int getConversationUnreadCount(String conversationId,Integer userId);
    public int updateHasRead(String conversationId,Integer userId);
    public int deleteMessage(int id);
    public int updateByPrimaryKeySelective(Message message);

    public String androidSendRemindMessage(int userId,String data);
    public Message findMessageById(int messageId);


}
