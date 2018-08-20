package com.xbcheng.wenwen.controller;

import com.alibaba.fastjson.JSONObject;
import com.xbcheng.wenwen.model.Question;
import com.xbcheng.wenwen.model.User;
import com.xbcheng.wenwen.service.QuestionService;
import com.xbcheng.wenwen.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @PostMapping("/question/add")
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title, @RequestParam("content") String content){
        Question question = new Question();
        question.setTitle(title);
        question.setContent(content);
        User user = (User) session.getAttribute("user");
        question.setUserId(user.getId());

        return questionService.addQuestion(question);

    }

    @GetMapping("/question/{id}")
    public String questionDetail(@PathVariable int id, Model model){

        Question question = questionService.selectById(id);
        model.addAttribute("question",question);
        model.addAttribute("user",userService.findById(question.getUserId()));

        return "detail";

    }

}
