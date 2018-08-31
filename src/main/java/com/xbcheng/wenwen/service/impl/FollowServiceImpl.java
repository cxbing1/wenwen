package com.xbcheng.wenwen.service.impl;

import com.xbcheng.wenwen.service.FollowService;
import com.xbcheng.wenwen.util.JedisAdapter;
import com.xbcheng.wenwen.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    JedisAdapter jedisAdapter;


    @Override
    public boolean follow(int userId, int entityType, int entityId) {

        String followerkey = RedisKeyUtil.getFollowerKey(entityType,entityId);
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId,entityType);

        Date date = new Date();
        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedis.multi();
        tx.zadd(followerkey,date.getTime(),String.valueOf(userId));
        tx.zadd(followeeKey,date.getTime(),String.valueOf(entityId));
        List<Object> result = tx.exec();
        if(jedis!=null)
            jedis.close();


        return result.size()==2;
    }

    @Override
    public boolean unFollow(int userId, int entityType, int entityId) {
        String followerkey = RedisKeyUtil.getFollowerKey(entityType,entityId);
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId,entityType);

        Date date = new Date();
        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedis.multi();
        tx.zrem(followerkey,String.valueOf(userId));
        tx.zrem(followeeKey,String.valueOf(entityId));
        List<Object> result = tx.exec();
        jedis.close();


        return result.size()==2;
    }

    @Override
    public List<Integer> getFollowers(int entityType, int entityId,int offset,int count) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);

        return getIdListFromSet(jedisAdapter.zrevrange(followerKey,offset,count));
    }

    @Override
    public List<Integer> getFollowees(int userId, int entityType,int offset,int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId,entityType);

        return getIdListFromSet(jedisAdapter.zrevrange(followeeKey,offset,count));
    }

    @Override
    public long getFollowersCount(int entityType, int entityId){
        String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);

        return jedisAdapter.zcard(followerKey);
    }

    @Override
    public long getFolloweesCount(int userId,int entityType){
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId,entityType);

        return jedisAdapter.zcard(followeeKey);
    }

    @Override
    public boolean isFollower(int userId, int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);

        return jedisAdapter.zscore(followerKey,String.valueOf(userId))!=null;

    }


    public List<Integer> getIdListFromSet(Set<String> set){
        List<Integer> ids = new ArrayList<>();

        for(String id:set){
            ids.add(Integer.parseInt(id));
        }

        return ids;
    }
}
