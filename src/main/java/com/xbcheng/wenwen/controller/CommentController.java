package com.xbcheng.wenwen.controller;

import com.xbcheng.wenwen.async.EventModel;
import com.xbcheng.wenwen.async.EventProducer;
import com.xbcheng.wenwen.async.EventType;
import com.xbcheng.wenwen.model.Comment;
import com.xbcheng.wenwen.model.User;
import com.xbcheng.wenwen.service.CommentService;
import com.xbcheng.wenwen.service.QuestionService;
import com.xbcheng.wenwen.util.EntityType;
import com.xbcheng.wenwen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private QuestionService questionService;

    @PostMapping("/addComment")
    public String addComment(int questionId, String content, HttpSession session){


        User user = (User) session.getAttribute("user");

        Comment comment = new Comment();
        comment.setUserId(user.getId());
        comment.setContent(content);
        comment.setEntityId(questionId);
        comment.setEntityType(EntityType.ENTITY_QUESTION);
        commentService.addComment(comment);

        eventProducer.fireEvent(new EventModel().setActionId(user.getId())
                                                .setEventType(EventType.COMMENT)
                                                .setEntityType(EntityType.ENTITY_QUESTION)
                                                .setEntityId(questionId)
                                                .setEntityOwnerId(questionService.selectById(questionId).getUserId()));

        return "redirect:/question/"+questionId;
    }

    @GetMapping("/deleteComment/{questionId}/{commentId}")
    public String deleteComment(@PathVariable("questionId") int questionId, @PathVariable("commentId") int commentId, String content, HttpSession session){


        User user = (User) session.getAttribute("user");

        if(user==null){
            return "redirect:/reglogin";
        }
        Comment comment = commentService.selectByPrimaryKey(commentId);
        if(comment.getUserId()==user.getId()){
            commentService.delectComent(commentId);
        }

        return "redirect:/question/"+questionId;
    }
}
