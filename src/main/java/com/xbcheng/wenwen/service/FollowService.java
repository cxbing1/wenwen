package com.xbcheng.wenwen.service;

import java.util.List;

public interface FollowService {

    public boolean follow(int userId,int entityType,int entityId);

    public boolean unFollow(int userId,int entityType,int entityId);

    public List<Integer> getFollowers(int entityType,int entityId,int offset,int count);

    public List<Integer> getFollowees(int userId,int entityType,int offset,int count);

    public long getFollowersCount(int entityType, int entityId);

    public long getFolloweesCount(int userId,int entityType );

    public boolean isFollower(int userId,int entityType,int entityId);
}
