package com.xbcheng.wenwen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xbcheng.wenwen.mapper.FriendMapper;
import com.xbcheng.wenwen.mapper.MessageMapper;
import com.xbcheng.wenwen.mapper.UserMapper;
import com.xbcheng.wenwen.model.Friend;
import com.xbcheng.wenwen.model.Message;
import com.xbcheng.wenwen.model.User;
import com.xbcheng.wenwen.service.FriendService;
import com.xbcheng.wenwen.service.MessageService;
import com.xbcheng.wenwen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    FriendMapper friendMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    MessageService messageService;
    @Override
    public String agreeBecomeFriend(int userId, int friendId,int messageId) {



        User friend = userMapper.selectByPrimaryKey(friendId);

        if(friend==null){
            ResultUtil.fail("用户不存在！");
        }

        if(isFriend(userId,friendId)){
            ResultUtil.fail("你们已经关联！");
        }

        if(userId>friendId){
            userId = userId+friendId;
            friendId = userId-friendId;
            userId = userId-friendId;
        }

        Friend friendRelation = new Friend();
        friendRelation.setUserId(userId);
        friendRelation.setFriendId(friendId);
        if(friendMapper.insertSelective(friendRelation)<1){
            return ResultUtil.fail("关联失败！");
        }

        Message message = new Message();
        message.setId(messageId);
        message.setHasRead(1);
        messageMapper.updateByPrimaryKeySelective(message);

       return ResultUtil.success("成功和"+friend.getName()+"关联为亲属");


    }

    @Override
    public String tryBecomeFriend(int userId, String friendName) {
        User user = userMapper.selectByPrimaryKey(userId);
        User friend = userMapper.selectByName(friendName);

        if(friend==null){
           return  ResultUtil.fail("用户不存在！");
        }

        int friendId = friend.getId();
        if(userId==friendId){
            return ResultUtil.fail("不能关联自己！");
        }
        if(isFriend(userId,friendId)){
            return ResultUtil.fail("你们已经关联！");
        }
        String content = ResultUtil.getJsonString(-2,user.getName()+"请求关联为亲属",null);
        return messageService.addMessage(friendId,userId,content);


    }

    @Override
    public String getFriendList(int userId) {
        List<Integer> friendIdList = friendMapper.selectFriendList(userId);
        if(friendIdList==null||friendIdList.size()==0){
            return ResultUtil.fail("暂无关联亲属");
        }

        List<User> friendList = new ArrayList<>();
        for(int friendId : friendIdList){
            friendList.add(userMapper.selectByPrimaryKey(friendId));
        }

        return ResultUtil.getJsonString(0,friendList);
    }


    public boolean isFriend(int userId,int friendId){
        List<Integer> friendList = friendMapper.selectFriendList(userId);
        if(friendList.contains(friendId)){
            return true;
        }

        return false;
    }
}
