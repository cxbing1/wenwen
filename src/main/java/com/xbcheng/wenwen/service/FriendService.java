package com.xbcheng.wenwen.service;

import java.util.Map;

public interface FriendService {

    public String agreeBecomeFriend(int userId,int friendId,int messageId);
    public String tryBecomeFriend(int userId,String friendName);
    public String getFriendList(int userId);
}
