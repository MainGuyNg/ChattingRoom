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
<link rel="stylesheet" type="text/css" href="${ctx}/css/query_user_result.css"/>
<script type="text/javascript" src="${ctx}/js/jquery-3.4.1.min.js"></script>
<script>
    $(function () {
        $("#add_friend_button").click(function () {
            var userId = $("#add_friend_button").val();
            window.location="${ctx}/relation/add_friend?userId="+userId;
        })
    })
</script>
<head>
    <title>查询结果</title>
</head>
<body>
<div id="query_user_result_all">
    <div id="query_user_result_div_top">
        <font>查询结果</font><br/>
    </div>
    <div id="query_user_result_div_middle">
        <div style="width: 300px;margin: auto;">
            <div id="query_user_result">
                <c:forEach items="${mvcObject.map.userList}" var="listData">
                    <div class="query_user_result_content">
                        名称：<a href="${ctx}/relation/personal_info?userId=${listData.userId}">${listData.nickname}</a>
                        <button type="button" id="add_friend_button" value="${listData.userId}">添加为好友</button>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <div id="query_user_result_div_bottom">
        <div id="query_user_result_div_bottom_content">
            <button type="button" onclick="window.location='${ctx}/relation/query_friend_list'">返回上一页</button>
        </div>
    </div>
</div>

</body>
</html>
