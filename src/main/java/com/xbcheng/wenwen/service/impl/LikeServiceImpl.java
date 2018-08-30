package com.xbcheng.wenwen.service.impl;

import com.xbcheng.wenwen.async.EventModel;
import com.xbcheng.wenwen.async.EventProducer;
import com.xbcheng.wenwen.async.EventType;
import com.xbcheng.wenwen.mapper.CommentMapper;
import com.xbcheng.wenwen.service.LikeService;
import com.xbcheng.wenwen.util.JedisAdapter;
import com.xbcheng.wenwen.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private JedisAdapter jedisAdapter;

    @Autowired
    private EventProducer eventProducer;

    private CommentMapper commentMapper;

    @Override
    public long like(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType,entityId);
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType,entityId);

        jedisAdapter.sadd(likeKey,String.valueOf(userId));
        jedisAdapter.srem(disLikeKey,String.valueOf(userId));


        EventModel eventModel = new EventModel();
        eventModel.setEventType(EventType.LIKE);
        eventModel.setActionId(userId);
        eventModel.setEntityType(entityType);
        eventModel.setEntityId(entityId);
        eventModel.setExts("questionId",String.valueOf(commentMapper.selectByPrimaryKey(entityId).getEntityId()));
        eventModel.setEntityOwnerId(commentMapper.selectByPrimaryKey(entityId).getUserId());
        //将点赞通知事件放入异步队列 
        eventProducer.fireEvent(eventModel);



        return jedisAdapter.scard(likeKey);

    }

    @Override
    public long disLike(int userId, int entityType, int entityId) {

        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType,entityId);
        String likeKey = RedisKeyUtil.getLikeKey(entityType,entityId);

        jedisAdapter.sadd(disLikeKey,String.valueOf(userId));
        jedisAdapter.srem(likeKey,String.valueOf(userId));

        return jedisAdapter.scard(disLikeKey);
    }

    @Override
    public int getLikeStatus(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType,entityId);

        if(jedisAdapter.sismember(likeKey,String.valueOf(userId))){
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType,entityId);
        if(jedisAdapter.sismember(disLikeKey,String.valueOf(userId))){
            return -1;
        }

        return 0;
    }

    @Override
    public long getLikeCount(int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType,entityId);
        return jedisAdapter.scard(likeKey);
    }


}
