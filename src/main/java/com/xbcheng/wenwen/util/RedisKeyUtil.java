package com.xbcheng.wenwen.util;

public class RedisKeyUtil {

    public static String getLikeKey(int entityType,int entityId){
        return "Like"+":"+entityType+":"+entityId;
    }

    public static String getDisLikeKey(int entityType,int entityId){
        return "DisLike"+":"+entityType+":"+entityId;
    }

    public static String getEventQueueKey(){
        return "EventQueue";
    }

    public static String getFollowerKey(int entityType,int entityId){
        return "Follower"+":"+entityType+":"+entityId;
    }

    public static String getFolloweeKey(int userId,int entityType){
        return "Followee"+":"+userId+":"+entityType;
    }
}
