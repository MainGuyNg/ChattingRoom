package com.mg.interceptor;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class WebSocketInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//        //强转成HttpServletRequest，如果不可使用则使用下方注释代码
//        //事实证明就是无法强转成HttpServletRequest
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse resp = (HttpServletResponse) response;
//        //获取session，做定向发消息
//        HttpSession session = req.getSession();
//        System.out.println("能跑下来算你牛逼！！！");
//        if (session != null) {
//            String username = (String) session.getAttribute("USERNAME");
//            if (username != null) {
//                attributes.put("WEBSOCKET_USERID", username);
//            }
//        }


        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession();
            if (session != null) {
                //使用userName区分WebSocketHandler，以便定向发送消息
                String accountNumber = (String) session.getAttribute("ACCOUNTNUMBER");  //一般直接保存user实体
                if (accountNumber != null) {
                    attributes.put("WEBSOCKET_USERID", accountNumber);
                }

            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
