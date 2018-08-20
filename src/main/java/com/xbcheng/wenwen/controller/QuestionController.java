package com.xbcheng.wenwen.controller;

import com.alibaba.fastjson.JSONObject;
import com.xbcheng.wenwen.model.Comment;
import com.xbcheng.wenwen.model.Question;
import com.xbcheng.wenwen.model.User;
import com.xbcheng.wenwen.service.CommentService;
import com.xbcheng.wenwen.service.QuestionService;
import com.xbcheng.wenwen.service.UserService;
import com.xbcheng.wenwen.util.EntityType;
import com.xbcheng.wenwen.util.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;


    @PostMapping("/question/add")
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title, @RequestParam("content") String content,HttpSession session){
        Question question = new Question();
        question.setTitle(title);
        question.setContent(content);
        User user = (User) session.getAttribute("user");
        question.setUserId(user.getId());

        return questionService.addQuestion(question);

    }

    @GetMapping("/question/{qid}")
    public String questionDetail(@PathVariable int qid, Model model){

        Question question = questionService.selectById(qid);
        model.addAttribute("question",question);
        model.addAttribute("user",userService.findById(question.getUserId()));

        List<Comment> commentList = commentService.selectByEntity(qid,EntityType.ENTITY_QUESTION);

        List<ViewObject> vos = new ArrayList<>();

        for(Comment comment:commentList){
            ViewObject vo = new ViewObject();
            vo.put("comment",comment);
            vo.put("commnetUser",comment.getEntityId());
            vos.add(vo);
        }

        return "detail";

    }

}
