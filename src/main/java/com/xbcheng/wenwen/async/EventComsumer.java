package com.xbcheng.wenwen.async;



import com.alibaba.fastjson.JSON;
import com.xbcheng.wenwen.async.handler.EventHandler;
import com.xbcheng.wenwen.util.JedisAdapter;
import com.xbcheng.wenwen.util.RedisKeyUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class EventComsumer implements InitializingBean , ApplicationContextAware {
    private Logger logger = LoggerFactory.getLogger(EventComsumer.class);

    private Map<EventType, List<EventHandler>> config = new HashMap<>();
    private ApplicationContext applicationContext;

    @Autowired
    private JedisAdapter jedisAdapter;


    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String,EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);

        if(beans!=null){
            for(Map.Entry<String,EventHandler> entry:beans.entrySet()){
                List<EventType> eventTypes = entry.getValue().getSupportEventType();

                for(EventType eventType:eventTypes){
                    if(!config.containsKey(eventType)){
                        config.put(eventType,new ArrayList<EventHandler>());
                    }

                    config.get(eventType).add(entry.getValue());
                }
            }
        }

        Thread thread = new Thread(new Runnable(){

            @Override
            public void run() {
                String key = RedisKeyUtil.getEventQueueKey();
                List<String> events = jedisAdapter.brpop(0,key);

                for(String event : events){

                    if(event.equals(key)){
                        continue;
                    }
                    EventModel eventModel = JSON.parseObject(event,EventModel.class);

                    if(!config.containsKey(eventModel.getEventType())){
                        logger.error("事件类型无法识别");
                        continue;
                    }

                    for(EventHandler eventHandler : config.get(eventModel.getEventType())){
                        eventHandler.doHandle(eventModel);
                    }
                }
            }
        });

        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
