package com.xbcheng.wenwen.controller;

import com.xbcheng.wenwen.async.EventModel;
import com.xbcheng.wenwen.async.EventProducer;
import com.xbcheng.wenwen.async.EventType;
import com.xbcheng.wenwen.model.User;
import com.xbcheng.wenwen.service.*;
import com.xbcheng.wenwen.util.EntityType;
import com.xbcheng.wenwen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FollowController {

    @Autowired
    private  FollowService followService;

    @Autowired
    private  EventProducer eventProducer;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;




    @PostMapping("/followUser")
    @ResponseBody
    public String followUser(@RequestParam("userId") int followUserId, HttpSession session){
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

    @GetMapping("/user/{uid}/followers")
    public String followers(@PathVariable("uid") int userId, Model model, HttpSession session){

        List<Integer> userIdList = followService.getFollowers(EntityType.ENTITY_USER,userId,0,10);

        User hostUser = (User) session.getAttribute("user");


        if(hostUser!=null){
            model.addAttribute("followerVos",getUserInfo(hostUser.getId(),userIdList));
        }else{
            model.addAttribute("followerVos",getUserInfo(0,userIdList));
        }

        model.addAttribute("curUser",userService.findById(userId));
        model.addAttribute("followerCount",followService.getFollowersCount(EntityType.ENTITY_USER,userId));

        return "followers";

    }

    @GetMapping("/user/{uid}/followees")
    public String followees(@PathVariable("uid") int userId, Model model, HttpSession session){

        List<Integer> userIdList = followService.getFollowees(userId,EntityType.ENTITY_USER,0,10);

        User hostUser = (User) session.getAttribute("user");

        if(hostUser!=null){
            model.addAttribute("followeeVos",getUserInfo(hostUser.getId(),userIdList));
        }else{
            model.addAttribute("followeeVos",getUserInfo(0,userIdList));
        }

        model.addAttribute("curUser",userService.findById(userId));
        model.addAttribute("followeeCount",followService.getFolloweesCount(userId,EntityType.ENTITY_USER));

        return "followees";

    }


    public List<Map<String,Object>>  getUserInfo(int hostUserId,List<Integer> userIdList){
        List<Map<String,Object>> followVos = new ArrayList<>();

        for(int userId:userIdList){
            Map<String,Object> followVo = new HashMap<>();

            followVo.put("user",userService.findById(userId));
            followVo.put("followerCount",followService.getFollowersCount(EntityType.ENTITY_USER,userId));
            followVo.put("followeeCount",followService.getFolloweesCount(userId,EntityType.ENTITY_USER));

            if(hostUserId>0){
                followVo.put("followed",followService.isFollower(hostUserId,EntityType.ENTITY_USER,userId));
            }else{
                followVo.put("followed",false);
            }

            followVo.put("commentCount",commentService.selectByUserId(userId).size());

            followVos.add(followVo);

        }


        return followVos;
    }
}
