package com.xbcheng.wenwen.service;

import com.xbcheng.wenwen.util.JedisAdapter;
import org.springframework.beans.factory.annotation.Autowired;

public interface LikeService {

    public long like(int userId,int entityType,int entityId);
    public long disLike(int userId,int entityType,int entityId);
    public int getLikeStatus(int userId,int entityType,int entityId);
    public long getLikeCount(int entityType,int entityId);
}
