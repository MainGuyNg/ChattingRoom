package com.mg.core;

import com.mg.model.User;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class WebSocketHandler extends TextWebSocketHandler {

    private static final Map<Object, WebSocketSession> userMap;  //Map来存储WebSocketSession，key用user对象存储，value是当前用户的websocketSession对象，即在线用户列表

    //用户标识
    private static final String USER_ID = "WEBSOCKET_USERID";   //对应拦截器中的beforeHandShake传入的Map的key

    static {
        userMap = new HashMap<Object, WebSocketSession>();
    }

    public WebSocketHandler() {
    }

    @Override
    //连接成功后会触发onOpen方法
    //翻译：连接成功建立以后
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        User requestUser = (User) session.getAttributes().get(USER_ID);
        userMap.put(requestUser, session);
        sendMessageToAllUsers(new TextMessage("用户 " + requestUser.getNickname() + " 进入聊天室，当前用户数量为：" + userMap.size()));
    }

    @Override
    //js调用websocket.send时候，会调用该方法
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        if (message != null) {
            String messageContent = message.getPayload();
            User requestUser = (User) session.getAttributes().get(USER_ID);
            if (messageContent.contains("&-:")) {       //前端页面send.jsp如果单发会带有"&-:"的字符串
                String[] stringArr = messageContent.split("&-:");    //stringArr[0]将会是接收人，stringArr[1]将会是内容
                String receiver = stringArr[0];
                messageContent = stringArr[1];
                sendMessageToUser(receiver, new TextMessage(requestUser.getNickname() + " 对你说：" + messageContent));
            } else {
//                sendMessageToAllUsers(new TextMessage("来自 " + requestUser.getNickname() + " 的群发:" + messageContent));
            }
        }
    }

    @Override
    //出现异常调用该方法
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        System.out.println("传输出现异常，关闭websocket连接... ");
        User requestUser = (User) session.getAttributes().get(USER_ID);
        userMap.get(requestUser).close();
        userMap.remove(requestUser);
    }

    @Override
    //关闭连接时触发
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        User requestUser = (User) session.getAttributes().get(USER_ID);
        if (requestUser != null) {
            System.out.println(requestUser.getAccountNumber() + "...连接关闭");
            userMap.get(requestUser).close();
            userMap.remove(requestUser);
            sendMessageToAllUsers(new TextMessage("用户 " + requestUser.getNickname() + " 已断开连接，当前用户数量为 " + userMap.size()));
        }
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
        Set keySet = userMap.keySet();
        for (Object obj : keySet) {
            User user = (User) obj;
            WebSocketSession webSocketSession = userMap.get(user);
            if (receiver.equals(user.getNickname())) {
                try {
                    if (webSocketSession.isOpen()) {
                        webSocketSession.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            } else {
                try {
                    if (webSocketSession.isOpen()) {
                        webSocketSession.sendMessage(new TextMessage("Cannot find this user"));
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
        Set keySet = userMap.keySet();
        for (Object obj : keySet) {
            User user = (User) obj;
            WebSocketSession webSocketSession = userMap.get(user);
            try {
                if (webSocketSession.isOpen()) {
                    webSocketSession.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
