package com.xbcheng.wenwen.controller;

import com.xbcheng.wenwen.async.EventModel;
import com.xbcheng.wenwen.async.EventProducer;
import com.xbcheng.wenwen.async.EventType;
import com.xbcheng.wenwen.model.User;
import com.xbcheng.wenwen.service.FollowService;
import com.xbcheng.wenwen.service.MessageService;
import com.xbcheng.wenwen.service.QuestionService;
import com.xbcheng.wenwen.service.UserService;
import com.xbcheng.wenwen.util.EntityType;
import com.xbcheng.wenwen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FollowController {

    @Autowired
    private  FollowService followService;

    @Autowired
    private  EventProducer eventProducer;

    @Autowired
    private QuestionService questionService;



    @PostMapping("/followUser")
    @ResponseBody
    public String followUser(int followUserId, HttpSession session){
        User user = (User)session.getAttribute("user");

        if(user==null){
            return ResultUtil.fail(999,"请先登陆");
        }
        Boolean result = followService.follow(user.getId(), EntityType.ENTITY_USER,followUserId);

        eventProducer.fireEvent(new EventModel(EventType.FOLLOW,user.getId(),EntityType.ENTITY_USER,followUserId,followUserId));


        return ResultUtil.getJsonString(result?0:1,String.valueOf(followService.getFollowersCount(EntityType.ENTITY_USER,followUserId)));

    }

    @PostMapping("/unfollowUser")
    @ResponseBody
    public String unFollowUser(@RequestParam("userId") int followUserId, HttpSession session){
        User user = (User)session.getAttribute("user");

        if(user==null){
            return ResultUtil.fail(999,"请先登陆");
        }
        Boolean result = followService.unFollow(user.getId(), EntityType.ENTITY_USER,followUserId);

        eventProducer.fireEvent(new EventModel(EventType.FOLLOW,user.getId(),EntityType.ENTITY_USER,followUserId,followUserId));


        return ResultUtil.getJsonString(result?0:1,String.valueOf(followService.getFollowersCount(EntityType.ENTITY_USER,followUserId)));

    }

    @PostMapping("/followQuestion")
    @ResponseBody
    public String followQuestion(@RequestParam("questionId")int questionId, HttpSession session){
        User user = (User)session.getAttribute("user");

        if(user==null){
            return ResultUtil.fail(999,"请先登陆");
        }
        Boolean result = followService.follow(user.getId(), EntityType.ENTITY_QUESTION,questionId);

        eventProducer.fireEvent(new EventModel(EventType.FOLLOW,user.getId(),EntityType.ENTITY_QUESTION,questionId,questionService.selectById(questionId).getUserId())
                                .setExts("questionId",String.valueOf(questionId)));

        Map<String,Object> info = new HashMap<>();
        info.put("headUrl",user.getHeadUrl());
        info.put("name",user.getName());
        info.put("id",user.getId());
        info.put("count",followService.getFollowersCount(EntityType.ENTITY_QUESTION,questionId));


        return ResultUtil.getJsonString(result?0:1,info);

    }

    @PostMapping("/unfollowQuestion")
    @ResponseBody
    public String unFollowQuestion(@RequestParam("questionId")int questionId, HttpSession session){
        User user = (User)session.getAttribute("user");

        if(user==null){
            return ResultUtil.fail(999,"请先登陆");
        }
        Boolean result = followService.unFollow(user.getId(), EntityType.ENTITY_QUESTION,questionId);

        eventProducer.fireEvent(new EventModel(EventType.UNFOLLOW,user.getId(),EntityType.ENTITY_QUESTION,questionId,questionService.selectById(questionId).getUserId())
                                .setExts("questionId",String.valueOf(questionId)));

        Map<String,Object> info = new HashMap<>();
        info.put("headUrl",user.getHeadUrl());
        info.put("name",user.getName());
        info.put("id",user.getId());
        info.put("count",followService.getFollowersCount(EntityType.ENTITY_QUESTION,questionId));

        return ResultUtil.getJsonString(result?0:1,info);

    }
}
