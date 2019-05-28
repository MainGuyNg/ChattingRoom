package com.mg.config;

import com.mg.core.WebSocketHandler;
import com.mg.interceptor.WebSocketInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    /*
        registerWebSocketHandlers：这个方法是向spring容器注册一个handler地址，我把他理解成requestMapping

        addInterceptors：拦截器，当建立websocket连接的时候，我们可以通过继承spring的HttpSessionHandshakeInterceptor来搞事情。

        setAllowedOrigins：跨域设置，*表示所有域名都可以，不限制， 域包括ip：port, 指定*可以是任意的域名，不加的话默认localhost+本服务端口

        withSockJS： 这个是应对浏览器不支持websocket协议的时候降级为轮询的处理。
     */
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(webSocketHandler(), "/websocket/socketServer").addInterceptors(new WebSocketInterceptor()).setAllowedOrigins("*");
        webSocketHandlerRegistry.addHandler(webSocketHandler(), "/sockjs/socketServer").setAllowedOrigins("http://193.112.8.102:8080").addInterceptors(new WebSocketInterceptor()).withSockJS();
    }

    @Bean
    public TextWebSocketHandler webSocketHandler() {
        return new WebSocketHandler();
    }

}
