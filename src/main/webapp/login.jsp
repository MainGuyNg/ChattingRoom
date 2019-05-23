<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>测试spring websocket</title>
</head>
<body>

<h1>在线聊天室</h1><br/>
<form action="${ctx}/websocket/login" method="post">
    用户名：<input type="text" name="username" placeholder="请输入用户名" maxlength="10" required="required" /><br/>
    密码：<input type="password" name="password" placeholder="请输入密码" maxlength="16" required="required"/><br/>
    <input type="submit" value="登录"/>
    <button type="button" onclick="toRegistry">注册</button>
</form>
</body>