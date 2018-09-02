package com.xbcheng.wenwen.async.handler;

import com.xbcheng.wenwen.async.EventModel;
import com.xbcheng.wenwen.async.EventType;
import com.xbcheng.wenwen.model.Comment;
import com.xbcheng.wenwen.model.Message;
import com.xbcheng.wenwen.service.CommentService;
import com.xbcheng.wenwen.service.MessageService;
import com.xbcheng.wenwen.util.EntityType;
import com.xbcheng.wenwen.util.JedisAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CommentHandler implements EventHandler {

   @Autowired
   private MessageService messageService;


    @Override
    public void doHandle(EventModel eventModel) {
        String content=null;
        if(eventModel.getEntityType()==EntityType.ENTITY_QUESTION){
            content = "用户"+eventModel.getActionId()+"回答了你的问题127.0.0.1:8080/question/"+eventModel.getEntityId();
        }else if(eventModel.getEntityType()==EntityType.ENTITY_USER){
            content = "用户"+eventModel.getActionId()+"回复了你在该问题下的回答127.0.0.1:8080/question/"+ eventModel.getExts("questionId");
        }

        messageService.addMessage(eventModel.getEntityOwnerId(),EntityType.ENTITY_SYSTEM_USER,content);
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.COMMENT);
    }
}
