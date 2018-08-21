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
        return messageService.addMessage(toUser.getId(),user.getId(),content);

    }

    @GetMapping("/msg/detail")
    public String getConversationDetail(String conversationId,Model model){

        List<Map<String,Object>> messageVos = new ArrayList<>();
        List<Message> messageList = messageService.selectByConversationId(conversationId);
        for(Message message:messageList){
            Map<String,Object> vo = new HashMap<>();
            vo.put("message",message);
            vo.put("user",userService.selectByPrimaryKey(message.getFromId()));

            messageVos.add(vo);
        }

        model.addAttribute("messageVos",messageVos);

        return "letterDetail";
    }


}
