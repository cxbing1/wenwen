package com.xbcheng.wenwen.controller;

import com.xbcheng.wenwen.async.EventModel;
import com.xbcheng.wenwen.async.EventProducer;
import com.xbcheng.wenwen.async.EventType;
import com.xbcheng.wenwen.model.Comment;
import com.xbcheng.wenwen.model.Question;
import com.xbcheng.wenwen.model.User;
import com.xbcheng.wenwen.service.*;
import com.xbcheng.wenwen.util.EntityType;
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

    @Autowired
    private LikeService likeService;

    @Autowired
    private FollowService followService;

    @Autowired
    private EventProducer eventProducer;



    @PostMapping("/question/add")
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title, @RequestParam("content") String content,HttpSession session){
        Question question = new Question();
        question.setTitle(title);
        question.setContent(content);
        User user = (User) session.getAttribute("user");
        question.setUserId(user.getId());

        String result = questionService.addQuestion(question);

        eventProducer.fireEvent(new EventModel().setEventType(EventType.Question)
                                                .setActionId(user.getId())
                                                .setEntityType(EntityType.ENTITY_QUESTION)
                                                .setEntityId(questionService.getByCondition(question).get(0).getId())
                                                .setEntityOwnerId(user.getId()));

        return result;

    }

    @GetMapping("/question/{qid}")
    public String questionDetail(@PathVariable int qid, Model model,HttpSession session){
        User hostUser = (User) session.getAttribute("user");

        Question question = questionService.selectById(qid);
        model.addAttribute("question",question);
        model.addAttribute("user",userService.findById(question.getUserId()));

        model.addAttribute("followed",hostUser!=null&&followService.isFollower(hostUser.getId(),EntityType.ENTITY_QUESTION,qid));
        List<Integer> followUserIds = followService.getFollowers(EntityType.ENTITY_QUESTION,qid,0,10);
        List<User> followUsers = new ArrayList<>();

        for(int id : followUserIds){
            followUsers.add(userService.findById(id));
        }

        model.addAttribute("followUsers",followUsers);


        List<Comment> commentList = commentService.selectByEntity(qid,EntityType.ENTITY_QUESTION);

        List<Map<String,Object>> commentVos = new ArrayList<>();

        for(Comment comment:commentList){
            Map<String,Object> vo= new HashMap<>();
            vo.put("comment",comment);
            vo.put("user",userService.findById(comment.getUserId()));
            vo.put("likeCount",likeService.getLikeCount(EntityType.ENTITY_COMMENT,comment.getId()));
            if(hostUser==null){
                vo.put("liked",0);
            }else{
                vo.put("liked",likeService.getLikeStatus(hostUser.getId(),EntityType.ENTITY_COMMENT,comment.getId()));
            }
            commentVos.add(vo);
        }

        model.addAttribute("commentVos",commentVos);

        return "detail";

    }

    @GetMapping("/question/delete/{qid}")
    public String deleteQuestion(@PathVariable("qid") int questionId,HttpSession session){

        User user = (User) session.getAttribute("user");

        if(user==null){
            return "redirect:/reglogin";
        }

        Question question = questionService.selectById(questionId);
        if(question.getUserId()==user.getId()){
            questionService.deleteQuestion(questionId);
            return "redirect:/user/"+user.getId();
        }

        return "redirect:/question/"+questionId;



    }

}
