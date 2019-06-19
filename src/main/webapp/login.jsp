<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<link rel="stylesheet" href="css/login.css" type="text/css"/>
<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>测试spring websocket</title>
</head>
<body>
<div id="login_all">
    <form id="login_form" action="${ctx}/user/login" method="post">
        <div id="login_div_top">
            <font>在线聊天室</font><br/>
        </div>
        <div id="login_div_middle">
            <div style="width: 300px;margin: auto;">
                <div id="login_div_middle_left">
                    <div class="login_div_middle_left_content">用户名：</div>
                    <div class="login_div_middle_left_content">密码：</div>
                </div>
                <div id="login_div_middle_right">
                    <div class="login_div_middle_right_content">
                        <input type="text" id="accountNumber" name="accountNumber" placeholder="请输入用户名" maxlength="10" required="required"/>
                    </div>
                    <div class="login_div_middle_right_content">
                        <input type="password" id="password" name="password" placeholder="请输入密码" maxlength="16" required="required"/>
                    </div>
                </div>
            </div>
        </div>
        <div id="login_div_bottom">
            <div id="login_div_bottom_content">
                <input type="submit" value="登录"/>
                <button type="button" onclick="window.location='register.jsp'">注册</button>
            </div>
        </div>
    </form>
</div>
</body>