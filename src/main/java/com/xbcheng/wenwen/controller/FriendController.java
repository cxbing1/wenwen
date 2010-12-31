package com.xbcheng.wenwen.controller;

import com.alibaba.fastjson.JSONObject;
import com.xbcheng.wenwen.service.FriendService;
import com.xbcheng.wenwen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class FriendController {

    @Autowired
    FriendService friendService;


    @ResponseBody
    @PostMapping("/android/agreeBecomeRelative")
    public String becomeRelative(@RequestBody JSONObject jsonObject){

        int userId = jsonObject.getInteger("userId");
        int  relativeId = jsonObject.getInteger("relativeId");
        int messageId = jsonObject.getInteger("messageId");

        return friendService.agreeBecomeFriend(userId,relativeId,messageId);

    }

    @ResponseBody
    @PostMapping("/android/tryBecomeRelative")
    public String tryBecomeRelative(@RequestBody JSONObject jsonObject){

        int userId = jsonObject.getInteger("userId");
        String relativeName = jsonObject.getString("relativeName");

       return friendService.tryBecomeFriend(userId,relativeName);

    }

    @ResponseBody
    @PostMapping("/android/getRelativeList")
    public String getRelativeList(@RequestBody JSONObject jsonObject){

        int userId = jsonObject.getInteger("userId");

        return friendService.getFriendList(userId);

    }


}
