package com.mg.core;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebSocketHandler extends TextWebSocketHandler {

    private static final Map<String, WebSocketSession> userMap;  //Map来存储WebSocketSession，key用USER_ID 即在线用户列表

    //用户标识
    private static final String USER_ID = "WEBSOCKET_USERID";   //对应监听器中的key


    static {
        userMap = new HashMap<String, WebSocketSession>();
    }

    public WebSocketHandler() {
    }


    @Override
    //连接成功后会触发onOpen方法
    //翻译：连接成功建立以后
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = (String) session.getAttributes().get(USER_ID);
        userMap.put(userId, session);
        sendMessageToAllUsers(new TextMessage("用户 " + userId + " 进入聊天室，当前用户数量为：" + userMap.size()));
    }

    @Override
    //js调用websocket.send时候，会调用该方法
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        if (message != null) {
            String messageContent = message.getPayload();
            String sender = (String) session.getAttributes().get(USER_ID);
            if (messageContent.contains("&-:")) {       //前端页面send.jsp如果单发会带有"&-:"的字符串
                String[] stringArr = messageContent.split("&-:");    //stringArr[0]将会是接收人，stringArr[1]将会是内容
                String receiver = stringArr[0];
                messageContent = stringArr[1];
                sendMessageToUser(receiver, new TextMessage(sender + " 对你说：" + messageContent));
            } else {
                sendMessageToAllUsers(new TextMessage("来自 " + sender + " 的群发:" + messageContent));
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        System.out.println("传输出现异常，关闭websocket连接... ");
        String userId = (String) session.getAttributes().get(USER_ID);
        userMap.remove(userId);
    }

    @Override
    //关闭连接时触发
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = (String) session.getAttributes().get(USER_ID);
        System.out.println(userId + "...连接关闭");
        userMap.remove(userId);
        sendMessageToAllUsers(new TextMessage("用户 " + userId + " 已断开连接，当前用户数量为 " + userMap.size()));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


    /**
     * 给某个用户发送消息
     *
     * @param receiver
     * @param message
     */
    public void sendMessageToUser(String receiver, TextMessage message) {
        for (String id : userMap.keySet()) {
            //遍历userMap，找到id单独发送
            if (id.equals(receiver)) {
                try {
                    if (userMap.get(id).isOpen()) {
                        userMap.get(id).sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            } else {
                try {
                    if (userMap.get(id).isOpen()) {
                        userMap.get(id).sendMessage(new TextMessage("Cannot find this user"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToAllUsers(TextMessage message) {
        for (String userId : userMap.keySet()) {
            try {
                if (userMap.get(userId).isOpen()) {
                    userMap.get(userId).sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
