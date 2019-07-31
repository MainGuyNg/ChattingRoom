<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/7/25
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<link rel="stylesheet" type="text/css" href="${ctx}/css/friend_list_result.css"/>
<script type="text/javascript" src="${ctx}/js/jquery-3.4.1.min.js"></script>
<script>
    function modify_friend(id) {
        var friendId = document.getElementById(id).value;
        window.location = "${ctx}/relation/modify_friend?friendId=" + friendId;
    }

    function delete_friend(id) {
        var friendId = document.getElementById(id).value;
        window.location = "${ctx}/relation/delete_friend?friendId=" + friendId;
    }
</script>
<head>
    <title>分组好友</title>
</head>
<body>
<div id="friend_list_result_all">
    <div id="friend_list_result_div_top">
        <font>分组好友</font><br/>
    </div>
    <div id="friend_list_result_div_middle">
        <div style="width: 300px;margin: auto;">
            <div id="friend_list_result">
                <c:forEach items="${mvcObject.map.friendList}" var="listData" varStatus="userStatus">
                    <div class="friend_list_result_content">
                        名称：<a href="${ctx}/relation/personal_info?userId=${listData.userId}">${listData.nickname}</a>
                        <button type="button" id="modify_friend_button_${userStatus.index}" value="${listData.userId}"
                                onclick="modify_friend(this.id)">编辑
                        </button>
                        <button type="button" id="delete_friend_button_${userStatus.index}" value="${listData.userId}"
                                onclick="delete_friend(this.id)">删除
                        </button>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <div id="friend_list_result_div_bottom">
        <div id="friend_list_result_div_bottom_content">
            <button type="button" onclick="window.location='${ctx}/relation/query_friend_list'">返回上一页</button>
        </div>
    </div>
</div>

</body>
</html>
