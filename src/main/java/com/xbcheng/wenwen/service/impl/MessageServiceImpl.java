package com.xbcheng.wenwen.service.impl;

import com.xbcheng.wenwen.mapper.MessageMapper;
import com.xbcheng.wenwen.model.Message;
import com.xbcheng.wenwen.model.User;
import com.xbcheng.wenwen.service.MessageService;
import com.xbcheng.wenwen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    public String addMessage(int toId, int fromId,String content){

        Message message = new Message();

        message.setFromId(fromId);
        message.setToId(toId);
        message.setContent(content);
        if(fromId>toId){
            fromId = fromId+toId;
            toId = fromId-toId;
            fromId = fromId-toId;
        }
        message.setConversationId(fromId+"_"+toId);

        if(messageMapper.insertSelective(message)<1){
            return ResultUtil.fail();
        }

        return ResultUtil.success();



    }

    @Override
    public List<Message> selectByConversationId(String conversationId) {
        return messageMapper.selectByConversationId(conversationId);
    }

    @Override
    public List<Message> getConversationList(int userId) {
        return messageMapper.selectConversationList(userId);
    }

    @Override
    public int getConversationUnreadCount(String conversationId, Integer userId) {
        return messageMapper.getConversationUnreadCount(conversationId,userId);
    }

    @Override
    public int updateHasRead(String conversationId, Integer userId) {
        return messageMapper.updateHasRead(conversationId,userId);
    }

    @Override
    public int deleteMessage(int id) {
        return messageMapper.deleteByPrimaryKey(id);
    }


}
