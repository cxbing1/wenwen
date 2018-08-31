package com.xbcheng.wenwen.async.handler;

import com.xbcheng.wenwen.async.EventModel;
import com.xbcheng.wenwen.async.EventType;

import java.util.List;

public interface EventHandler {

    void doHandle(EventModel eventModel);

    List<EventType> getSupportEventType();
}
