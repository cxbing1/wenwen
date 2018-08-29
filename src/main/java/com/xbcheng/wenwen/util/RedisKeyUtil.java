package com.xbcheng.wenwen.util;

public class RedisKeyUtil {

    public static String getLikeKey(int entityType,int entityId){
        return "Like"+":"+entityType+":"+entityId;
    }

    public static String getDisLikeKey(int entityType,int entityId){
        return "DisLike"+":"+entityType+":"+entityId;
    }
}
