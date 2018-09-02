package com.xbcheng.wenwen.controller;

import com.xbcheng.wenwen.mapper.QuestionMapper;
import com.xbcheng.wenwen.mapper.UserMapper;
import com.xbcheng.wenwen.model.Question;
import com.xbcheng.wenwen.model.User;
import com.xbcheng.wenwen.model.ViewObject;
import com.xbcheng.wenwen.service.CommentService;
import com.xbcheng.wenwen.service.FollowService;
import com.xbcheng.wenwen.service.QuestionService;
import com.xbcheng.wenwen.service.UserService;
import com.xbcheng.wenwen.util.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    FollowService followService;

    @RequestMapping(value={"/index","/"})
    public String index(Model model){

        List<Map<String,Object> > questionVos = new ArrayList<>();
        List<Question> questionList = questionService.getQuestionList();
        for(Question question:questionList){
            Map<String,Object> vo = new HashMap<>();
            vo.put("question",question);
            vo.put("user",userService.findById(question.getUserId()));
            vo.put("followCount",followService.getFollowersCount(EntityType.ENTITY_QUESTION,question.getId()));
            questionVos.add(vo);
        }


        model.addAttribute("questionVos",questionVos);
        return "index";
    }

    @RequestMapping("/home")
    public String home(){
        return "profile";
    }
}
