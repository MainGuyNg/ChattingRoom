package com.mg.controller;

import com.mg.core.WebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/websocket")
public class WebSocketController {

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Bean//这个注解会从Spring容器拿出Bean
    public WebSocketHandler infoHandler() {
        return new WebSocketHandler();
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        System.out.println(this.getCurrentTime() + "  用户 " + username + " 访问登录接口");
        HttpSession session = request.getSession();
        session.setAttribute("USERNAME", username); //一般直接保存user实体
        session.setMaxInactiveInterval(30*60);
        return "send";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("USERNAME");
        System.out.println(this.getCurrentTime() + "  用户 " + username + " 访问注销接口");
        session.invalidate();
        return "login";
    }

    public String getCurrentTime() {
        return simpleDateFormat.format(System.currentTimeMillis());
    }
}
