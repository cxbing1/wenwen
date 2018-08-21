package com.xbcheng.wenwen.service;

import com.xbcheng.wenwen.model.Message;

import java.util.List;

public interface MessageService {

    public String addMessage(int toId,int fromId,String content);
    public List<Message> selectByConversationId(String conversationId);
}
