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
public class LikeHandler implements EventHandler {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Override
    public void doHandle(EventModel eventModel) {

        messageService.addMessage(eventModel.getEntityOwnerId(),EntityType.ENTITY_SYSTEM_USER,
                "用户"+userService.findById(eventModel.getActionId()).getName()+"赞了你的评论");
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.LIKE);
    }
}
