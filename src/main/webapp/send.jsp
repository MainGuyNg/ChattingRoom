<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title></title>
    <!--
	<link rel="stylesheet" href="/css/style.css"/>
    -->
    <script type="text/javascript" src="http://cdn.bootcss.com/jquery/3.1.0/jquery.min.js"></script>
    <script type="text/javascript" src="http://cdn.bootcss.com/sockjs-client/1.1.1/sockjs.js"></script>
    <script type="text/javascript">
        var websocket = null;
        if ('WebSocket' in window) {
            console.info("is websocket");
            websocket = new WebSocket("ws://localhost:8080/ChattingRoom/websocket/socketServer");
        } else if ('MozWebSocket' in window) {
            console.info("is mozwebsocket");
            websocket = new MozWebSocket("ws://localhost:8080/ChattingRoom/websocket/socketServer");
        } else {
            console.info("is sockJs");
            websocket = new SockJS("http://localhost:8080/ChattingRoom/sockjs/socketServer");
        }
        websocket.onopen = onOpen;
        websocket.onmessage = onMessage;
        websocket.onerror = onError;
        websocket.onclose = onClose;

        function onMessage(evt) {
            setMessageInnerHTML(evt.data);
        }

        function onOpen() {
        }

        function onError() {
            setMessageInnerHTML("连接websocket时发生异常");
        }

        function onClose() {
            websocketClose();
        }

        function doSendUser() {
            console.info("websocket.readyState=" + websocket.readyState + " websocket.OPEN=" + websocket.OPEN);
            if (websocket.readyState == websocket.OPEN) {
                var toUser = document.getElementById("toUser").value;
                var msg = document.getElementById("inputMsg").value;
                websocket.send(toUser + "&-:" + msg);//调用后台handleTextMessage方法
            } else {
                alert("连接失败!");
            }
            setMessageInnerHTML("你对"+toUser+"说："+msg);
        }


        function doSendAllUsers() {
            if (websocket.readyState == websocket.OPEN) {
                var msg = document.getElementById("inputMsg").value;
                websocket.send(msg);//调用后台handleTextMessage方法
            } else {
                alert("连接失败!");
            }
        }


        window.close = function () {
            websocket.onclose();
        }

        function websocketClose() {
            websocket.close();
        }

        //将消息显示在网页上
        function setMessageInnerHTML(innerHTML) {
            document.getElementById('message').innerHTML += innerHTML + '<br/>';
        }
    </script>
</head>
<body>
<h3>你好,${sessionScope.USERNAME}</h3><button onclick="websocketClose();">退出聊天室</button><br/><br/><br/>


请输入内容：<br/>
<textarea rows="5" cols="50" id="inputMsg" name="inputMsg"></textarea><br/>
发送给：<input type="text" id="toUser" placeholder="请输入要发送的用户名" maxlength="10"/>
<button onclick="doSendUser();">发送</button>
<button onclick="doSendAllUsers();">群发</button>
<br/><br/>

<div>
    <h2>聊天区域</h2>
    <div id="message"></div>
</div>
</body>
</html>