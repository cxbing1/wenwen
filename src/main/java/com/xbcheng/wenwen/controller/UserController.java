package com.xbcheng.wenwen.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xbcheng.wenwen.mapper.UserMapper;
import com.xbcheng.wenwen.model.Question;
import com.xbcheng.wenwen.model.User;
import com.xbcheng.wenwen.repository.UserRepository;
import com.xbcheng.wenwen.service.CommentService;
import com.xbcheng.wenwen.service.FollowService;
import com.xbcheng.wenwen.service.QuestionService;
import com.xbcheng.wenwen.service.UserService;
import com.xbcheng.wenwen.util.EntityType;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowService followService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private  CommentService commentService;




    @PostMapping("/reg")
    public String register(String username, String password,String next, Model model, HttpSession session){

        Map<String,Object> map= userService.registerService(username, password);

        if(map.containsKey("msg")){
            model.addAttribute("msg",map.get("msg"));
            return "login";
        }

        session.setAttribute("user",map.get("user"));

        if(!StringUtil.isNullOrEmpty(next)){
            return "redirect:"+next;
        }
        return "redirect:/";

    }

    @PostMapping("/login")
    public String login(String username, String password,String next,Model model,HttpSession session){

        Map<String,Object> map = userService.loginService(username,password);

        if(map.containsKey("msg")){
            model.addAttribute("msg",map.get("msg"));
            return "login";
        }

        session.setAttribute("user",map.get("user"));

        if(!StringUtil.isNullOrEmpty(next)&&!next.equals("/addComment")){
            return "redirect:"+next;
        }
        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:index";
    }

    @RequestMapping("/reglogin")
    public String reglogin(String next,Model model){
        model.addAttribute("next",next);
        return "login";
    }

    @GetMapping("/user/{userId}")
    public String user(@PathVariable Integer userId,Model model,HttpSession session,
                       @RequestParam(value="pageNum", defaultValue="1") int pageNum,
                       @RequestParam(value="pageSize", defaultValue="10") int pageSize){

        User user = userService.findById(userId);

        model.addAttribute("curUser",user);

        Map<String ,Object> profileUser = new HashMap<>();
        profileUser.put("user",user);
        profileUser.put("followerCount",followService.getFollowersCount(EntityType.ENTITY_USER,userId));
        profileUser.put("followeeCount",followService.getFolloweesCount(userId,EntityType.ENTITY_USER));

        User hostUser = (User)session.getAttribute("user");
        profileUser.put("followed",followService.isFollower(hostUser.getId(),EntityType.ENTITY_USER,userId));
        profileUser.put("commentCount",commentService.selectByUserId(user.getId()).size());

        PageHelper.startPage(pageNum, pageSize);
        List<Question> questionList = questionService.getQuestionListByUserId(userId);
        PageInfo<Question> pageInfo = new PageInfo<>(questionList);

        List<Map<String,Object>> questionVos = new ArrayList<>();

        for(Question question : pageInfo.getList()){
            Map<String,Object> questionVo = new HashMap<>();

            questionVo.put("question",question);
            questionVo.put("user",userService.findById(question.getUserId()));
            questionVo.put("followCount",followService.getFollowersCount(EntityType.ENTITY_QUESTION,question.getId()));
            questionVos.add(questionVo);
        }

        model.addAttribute("pageInfo",pageInfo);

        model.addAttribute("profileUser",profileUser);

        model.addAttribute("questionVos",questionVos);

        return "profile";


    }




}
