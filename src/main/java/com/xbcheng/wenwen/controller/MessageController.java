package com.xbcheng.wenwen.controller;

import com.xbcheng.wenwen.mapper.UserMapper;
import com.xbcheng.wenwen.model.Message;
import com.xbcheng.wenwen.model.User;
import com.xbcheng.wenwen.service.MessageService;
import com.xbcheng.wenwen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserMapper userService;

    @PostMapping("/msg/addMessage")
    @ResponseBody
    public String addMessage(String toName, String content, HttpSession session){

        if(session.getAttribute("user")==null){
            return ResultUtil.fail(999,"请先登陆");
        }

        User toUser = userService.selectByName(toName);
        if(toUser==null){
            return ResultUtil.fail("用户不存在");
        }

            User user = (User) session.getAttribute("user");
        if(user.getName().equals(toName)){
            return ResultUtil.fail("无法给自己发送信息");
        }
        return messageService.addMessage(toUser.getId(),user.getId(),content);

    }
    @PostMapping("/msg/sendMessage")
    public String sendMessage(String toName, String content, HttpSession session){

        if(session.getAttribute("user")==null){
            return "login";
        }

        User toUser = userService.selectByName(toName);
        User user = (User) session.getAttribute("user");
        messageService.addMessage(toUser.getId(),user.getId(),content);

        return "redirect:/msg/detail?conversationUserId="+toUser.getId();

    }

    @GetMapping("/msg/detail")
    public String getConversationDetail(Integer conversationUserId,Model model,HttpSession session){

        User user = (User)session.getAttribute("user");
        int userId =user.getId();
        User conversationUser = userService.selectByPrimaryKey(conversationUserId);

        int minId = userId<conversationUserId?userId:conversationUserId;
        int maxId = userId>conversationUserId?userId:conversationUserId;

        String conversationId = minId+"_"+maxId;

        List<Map<String,Object>> messageVos = new ArrayList<>();
        List<Message> messageList = messageService.selectByConversationId(conversationId);
        for(Message message:messageList){
            Map<String,Object> vo = new HashMap<>();
            vo.put("message",message);
            vo.put("user",userService.selectByPrimaryKey(message.getFromId()));

            messageVos.add(vo);
        }
        model.addAttribute("conversationUser",conversationUser);
        model.addAttribute("messageVos",messageVos);

        messageService.updateHasRead(conversationId,user.getId());

        return "letterDetail";
    }

    @RequestMapping("/msg/list")
    public String getConversationList(Model model,HttpSession session){

        User user = (User) session.getAttribute("user");

        List<Message> conversationList = messageService.getConversationList(user.getId());
        List<Map<String,Object>> conversationVos = new ArrayList<>();

        int userId = user.getId();
        for(Message conversation:conversationList){
            Map<String,Object> vo = new HashMap<>();
            vo.put("conversation",conversation);
            int conversationUserId = userId==conversation.getToId()?conversation.getFromId():conversation.getToId();
            vo.put("user",userService.selectByPrimaryKey(conversationUserId));
            vo.put("unread",messageService.getConversationUnreadCount(conversation.getConversationId(),userId));
            conversationVos.add(vo);

        }

        model.addAttribute("conversationVos",conversationVos);

        return "letter";

    }
}
