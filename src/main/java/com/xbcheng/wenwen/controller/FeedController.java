package com.xbcheng.wenwen.controller;

import com.github.pagehelper.PageInfo;
import com.xbcheng.wenwen.model.Feed;
import com.xbcheng.wenwen.model.Question;
import com.xbcheng.wenwen.model.User;
import com.xbcheng.wenwen.service.FeedService;
import com.xbcheng.wenwen.service.FollowService;
import com.xbcheng.wenwen.service.QuestionService;
import com.xbcheng.wenwen.service.UserService;
import com.xbcheng.wenwen.util.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.swing.BakedArrayList;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FeedController {

    @Autowired
    private FollowService followService;

    @Autowired
    private FeedService feedService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/pullfeeds")
    public String getPullFeeds(Model model, HttpSession session,
                               @RequestParam(value="pageNum", defaultValue="1") int pageNum,
                               @RequestParam(value="pageSize", defaultValue="10") int pageSize){

        User user = (User) session.getAttribute("user");
        List<Integer> followees = null;
        if(user!=null){
            followees = followService.getFollowees(user.getId(),EntityType.ENTITY_USER,0,10);
        }

        PageInfo<Feed> pageInfo = feedService.getUserFeeds(followees,pageNum,pageSize);
        List<Map<String,Object>> feedVos = new ArrayList<>();
        for(Feed feed : pageInfo.getList()){
            Question question = questionService.selectById(Integer.parseInt(feed.get("entityId")));
            if(question==null){
                continue;
            }
            Map<String,Object> feedVo = new HashMap<>();
            feedVo.put("user",userService.findById(feed.getUserId()));
            feedVo.put("question",question);
            feedVo.put("feed",feed);

            feedVos.add(feedVo);
        }
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("feedVos",feedVos);

        return "feeds";

    }
}
