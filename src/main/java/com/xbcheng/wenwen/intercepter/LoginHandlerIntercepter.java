package com.xbcheng.wenwen.intercepter;

import com.xbcheng.wenwen.model.User;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

@Component
public class LoginHandlerIntercepter implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("preHandle..."+request.getRequestURI());
        if(request.getSession().getAttribute("user")==null){
            request.setAttribute("msg","请先登陆！");
            response.sendRedirect("/reglogin?next="+request.getRequestURI());
            return false;
        }

        if(request.getRequestURI().contains("/user/-1")){
            User user = (User) request.getSession().getAttribute("user");
            response.sendRedirect("/user/"+user.getId());
        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");
    }
}
