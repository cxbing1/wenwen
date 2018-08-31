package com.xbcheng.wenwen.async.handler;

import com.xbcheng.wenwen.async.EventModel;
import com.xbcheng.wenwen.async.EventType;

import com.xbcheng.wenwen.service.MessageService;
import com.xbcheng.wenwen.service.UserService;
import com.xbcheng.wenwen.util.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class FollowHandler implements EventHandler {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Override
    public void doHandle(EventModel eventModel) {

        String content = "用户"+userService.findById(eventModel.getActionId()).getName();
        if(eventModel.getEventType()==EventType.FOLLOW) {
            content += "关注了你";
        }else if(eventModel.getEventType()==EventType.UNFOLLOW){
            content += "取消关注了你";
        }

        if(eventModel.getEntityType()== EntityType.ENTITY_USER){
            content+="127.0.0.1:8080/user/"+eventModel.getEntityOwnerId()+"/follower";
        }else if(eventModel.getEntityType()== EntityType.ENTITY_QUESTION){
            content+="的问题127.0.0.1:8080/question/"+eventModel.getExts("questionId");
        }

        messageService.addMessage(eventModel.getEntityOwnerId(),EntityType.ENTITY_SYSTEM_USER, content);
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.FOLLOW,EventType.UNFOLLOW);
    }
}
