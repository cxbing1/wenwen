package com.xbcheng.wenwen.async;

import com.alibaba.fastjson.JSONObject;
import com.xbcheng.wenwen.util.JedisAdapter;
import com.xbcheng.wenwen.util.RedisKeyUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;

@Service
public class EventProducer {

    private  static Logger logger = LoggerFactory.getLogger(EventProducer.class);

    @Autowired
    private JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel){
        String json = JSONObject.toJSONString(eventModel);
        String key = RedisKeyUtil.getEventQueueKey();
        try{
            jedisAdapter.lpush(key,json);
            return true;
        }catch (Exception e){
            logger.error("事件发送失败");
        }

        return false;

    }




}
