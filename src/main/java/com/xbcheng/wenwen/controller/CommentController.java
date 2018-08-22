package com.xbcheng.wenwen.controller;

import com.xbcheng.wenwen.model.Comment;
import com.xbcheng.wenwen.model.User;
import com.xbcheng.wenwen.service.CommentService;
import com.xbcheng.wenwen.util.EntityType;
import com.xbcheng.wenwen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/addComment")
    public String addComment(int questionId, String content, HttpSession session){


        User user = (User) session.getAttribute("user");

        Comment comment = new Comment();
        comment.setUserId(user.getId());
        comment.setContent(content);
        comment.setEntityId(questionId);
        comment.setEntityType(EntityType.ENTITY_QUESTION);
        commentService.addComment(comment);

        return "redirect:/question/"+questionId;
    }
}
