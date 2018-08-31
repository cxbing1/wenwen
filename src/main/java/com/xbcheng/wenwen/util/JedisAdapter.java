package com.xbcheng.wenwen.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

@Component
public class JedisAdapter {

    @Autowired
    private JedisPool jedisPool;


    public long sadd(String key,String value ){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.sadd(key,value);
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }

    public long srem(String key,String value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.srem(key,value);
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }

        }
        return 0;
    }

    public boolean sismember(String key,String member){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.sismember(key,member);
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return false;
    }

    public long scard(String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.scard(key);
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }

    public long lpush(String key,String object){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.lpush(key,object);
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }

    public List<String> brpop(int timeout,String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.brpop(timeout,key);
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }


    public Jedis getJedis(){
        return  jedisPool.getResource();
    }

    public long zadd(String key,double score,String value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.zadd(key,score,value);
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }

    public Set<String> zrange(String key,long start,long end){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.zrange(key,start,end);
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }

    public Set<String> zrevrange(String key,int start,int end){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.zrevrange(key,start,end);
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }

    public long zrevrange(String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.zcard(key);
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }

    public Double zscore(String key,String member){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.zscore(key,member);
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }

    public long zcard(String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.zcard(key);
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }


}
