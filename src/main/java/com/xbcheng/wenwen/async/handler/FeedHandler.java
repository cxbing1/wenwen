package com.xbcheng.wenwen.async.handler;

import com.alibaba.fastjson.JSONObject;
import com.xbcheng.wenwen.async.EventModel;
import com.xbcheng.wenwen.async.EventType;
import com.xbcheng.wenwen.model.Feed;
import com.xbcheng.wenwen.service.FeedService;
import com.xbcheng.wenwen.service.QuestionService;
import com.xbcheng.wenwen.service.UserService;
import com.xbcheng.wenwen.util.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FeedHandler implements EventHandler {

    @Autowired
    private FeedService feedService;

    @Override
    public void doHandle(EventModel eventModel) {
        Feed feed =new Feed();
        feed.setUserId(eventModel.getActionId());
        feed.setType(eventModel.getEventType().getValue());

        if(eventModel.getEntityType()==EntityType.ENTITY_QUESTION){
            Map<String,Object> map = new HashMap<>();
            map.put("entityType",eventModel.getEntityType());
            map.put("entityId",eventModel.getEntityId());
            feed.setData(JSONObject.toJSONString(map));
        }



        if(feed.getData()!=null)
            feedService.addFeed(feed);
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.COMMENT,EventType.FOLLOW,EventType.Question);
    }

}
