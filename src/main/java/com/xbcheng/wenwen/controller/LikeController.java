package com.xbcheng.wenwen.controller;

import com.xbcheng.wenwen.model.User;
import com.xbcheng.wenwen.service.LikeService;
import com.xbcheng.wenwen.util.EntityType;
import com.xbcheng.wenwen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LikeController {

    @Autowired
    private LikeService likeService;

    @ResponseBody
    @PostMapping("/like")
    public String like(int commentId, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user==null){
            return ResultUtil.fail(999,"请先登陆");
        }

        long likeCount = likeService.like(user.getId(), EntityType.ENTITY_COMMENT,commentId);

        return ResultUtil.success(String.valueOf(likeCount));
    }

    @ResponseBody
    @PostMapping("/dislike")
    public String dislike(int commentId, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user==null){
            return ResultUtil.fail(999,"请先登陆");
        }

        long likeCount = likeService.disLike(user.getId(), EntityType.ENTITY_COMMENT,commentId);

        return ResultUtil.success(String.valueOf(likeCount));
    }

}
