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
<link rel="stylesheet" type="text/css" href="${ctx}/css/query_friend_info.css"/>
<script type="text/javascript" src="${ctx}/js/jquery-3.4.1.min.js"></script>
<head>
    <title>好友信息</title>
</head>
<body>
<div id="query_friend_info_all">
    <div id="query_friend_info_div_top">
        <font>好友信息</font>
        <div id="query_friend_info_headIcon_div">
            <c:if test="${mvcObject.map.user.headUrl == null}">
                <img src="${ctx}/img/1.jpg" id="headUrl"/>
            </c:if>
            <c:if test="${mvcObject.map.user.headUrl !=null}">
                <img src="${ctx}/${mvcObject.map.user.headUrl}" id="headUrl"/>
            </c:if>
        </div>
    </div>
    <div id="query_friend_info_div_middle">
        <div style="width: 300px;margin: auto;">
            <div id="query_friend_info_div_middle_left">
                <div class="query_friend_info_div_middle_left_content">账号：</div>
                <div class="query_friend_info_div_middle_left_content">昵称：</div>
                <div class="query_friend_info_div_middle_left_content">性别：</div>
                <div class="query_friend_info_div_middle_left_content">手机号：</div>
                <div class="query_friend_info_div_middle_left_content">联系地址：</div>
                <div class="query_friend_info_div_middle_left_content">生日：</div>
                <div class="query_friend_info_div_middle_left_content">登录时间：</div>
            </div>

            <div id="query_friend_info_div_middle_right">
                <div class="query_friend_info_div_middle_right_content">
                    ${mvcObject.map.user.accountNumber}
                </div>
                <div class="query_friend_info_div_middle_right_content">
                    ${mvcObject.map.user.nickname}
                </div>
                <div class="query_friend_info_div_middle_right_content">
                    <c:if test="${mvcObject.map.user.sex ==1}">
                        男
                    </c:if>
                    <c:if test="${mvcObject.map.user.sex==2}">
                        女
                    </c:if>
                </div>
                <div class="query_friend_info_div_middle_right_content">
                    ${mvcObject.map.user.telephone}
                </div>
                <div class="query_friend_info_div_middle_right_content">
                    ${mvcObject.map.user.location}
                </div>
                <div class="query_friend_info_div_middle_right_content">
                    <fmt:formatDate value="${mvcObject.map.user.birthday}" pattern="yyyy-MM-dd"/>
                </div>
                <div class="query_friend_info_div_middle_right_content">
                    <fmt:formatDate value="${mvcObject.map.user.loginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </div>
            </div>
        </div>
    </div>
    <div id="query_friend_info_div_bottom">
        <div id="query_friend_info_div_bottom_content">
            <a href="${ctx}/relation/query_friend_list">返回</a>
        </div>
    </div>
</div>
</body>
</html>
