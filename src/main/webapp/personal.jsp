<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/7/2
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<link rel="stylesheet" type="text/css" href="${ctx}/css/personal.css"/>
<script type="text/javascript" src="${ctx}/js/jquery-3.4.1.min.js"></script>
<head>
    <title>个人中心</title>
</head>
<body>
<div id="personal_all">
    <c:forEach items="${userMap}" var="entry">
        <div id="personal_div_top">
            <font>个人中心</font>
            <div>
                <c:if test="${entry.value.headUrl == null}">
                    <img src="${ctx}/img/1.jpg" id="headUrl"/>
                </c:if>
                <c:if test="${entry.value.headUrl !=null}">
                    <img src="${entry.value.headUrl}" id="headUrl"/>
                </c:if>
            </div>
            <div>
                <a href="#">修改头像</a>
            </div>
        </div>
        <div id="personal_div_middle">
            <div style="width: 300px;margin: auto;">
                <div id="personal_div_middle_left">
                    <div class="personal_div_middle_left_content">账号：</div>
                    <div class="personal_div_middle_left_content">昵称：</div>
                    <div class="personal_div_middle_left_content">性别：</div>
                    <div class="personal_div_middle_left_content">手机号：</div>
                    <div class="personal_div_middle_left_content">联系地址：</div>
                    <div class="personal_div_middle_left_content">生日：</div>
                    <div class="personal_div_middle_left_content">登录时间：</div>
                </div>

                <div id="personal_div_middle_right">
                    <div class="personal_div_middle_right_content">
                            ${entry.value.accountNumber}
                    </div>
                    <div class="personal_div_middle_right_content">
                            ${entry.value.nickname}
                    </div>
                    <div class="personal_div_middle_right_content">
                        <c:if test="${entry.value.sex ==0}">
                            男
                        </c:if>
                        <c:if test="${entry.value.sex==1}">
                            女
                        </c:if>
                    </div>
                    <div class="personal_div_middle_right_content">
                            ${entry.value.telephone}
                    </div>
                    <div class="personal_div_middle_right_content">
                            ${entry.value.location}
                    </div>
                    <div class="personal_div_middle_right_content">
                        <fmt:formatDate value="${entry.value.birthday}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </div>
                    <div class="personal_div_middle_right_content">
                        <fmt:formatDate value="${entry.value.loginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </div>
                </div>
            </div>
        </div>
        <div id="personal_div_bottom">
            <div id="personal_div_bottom_content">
                <a href="${ctx}/modify_personal.jsp">编辑</a>
                <a href="${ctx}/modify_password.jsp">修改密码</a>
                <a href="${ctx}/send.jsp">返回</a>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>
