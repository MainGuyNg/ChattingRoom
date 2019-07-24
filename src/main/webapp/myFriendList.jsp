<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/7/24
  Time: 11:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<link rel="stylesheet" href="${ctx}/css/myFriendList.css" type="text/css"/>
<script type="text/javascript" src="${ctx}/js/jquery-3.4.1.min.js"></script>
<script>
    $(function () {
        var mvcObject = '${mvcObject}';
        var data = JSON.parse(mvcObject);
        var code = data.code;
        console.info(code + "..." + data.msg);
        if (code == "100") {
            var friendList = data.map.friendList;
            for (var i = 0; i < friendList.length; i++) {
                var list = friendList[i];
                var listId = list.listId;
                var listName = list.listName;
                var friendCount = list.friendCount;
                $("#friend_list").append("<div class='friend_list_content'><a href=" + "'${ctx}/relation/queryFriendByListId?listId=" + listId + "'>" + listName + "  (" + friendCount + ")</a></div>");
            }
        } else if (code == "202") {
            alert(data.msg);
        } else if (code == "200") {
            alert(data.msg);
        }
    })
</script>
<head>
    <title>Title</title>
</head>
<body>
<div id="friend_list_all">
    <div id="friend_list_div_top">
        <font>好友列表</font><br/>
    </div>
    <div id="friend_list_div_middle">
        <div style="width: 300px;margin: auto;">
            <div id="friend_list_div_middle_left">
                <div class="friend_list_div_middle_left_content">好友账号：</div>
                <div class="friend_list_div_middle_left_content">好友昵称：</div>
            </div>

            <div id="friend_list_div_middle_right">
                <div class="friend_list_div_middle_right_content">
                    <form action="${ctx}/relation/queryUserByAccountNumber" method="get">
                        <input type="text" id="accountNumber" name="accountNumber" maxlength="16" placeholder="请输入好友账号"
                               style="width: 120px;"/>
                        <input type="submit" value="搜索"/>
                    </form>
                </div>
                <div class="friend_list_div_middle_right_content">
                    <form action="${ctx}/relation/queryUserByNickname" method="get">
                        <input type="text" id="nickname" name="nickname" maxlength="16" placeholder="请输入好友昵称" style="width: 120px;"/>
                        <input type="submit" value="搜索"/>
                    </form>
                </div>
            </div>

            <div id="friend_list">
            </div>
        </div>
    </div>
    <div id="friend_list_div_bottom">
        <div id="friend_list_div_bottom_content">
            <button type="button" onclick="window.location='${ctx}/send.jsp'">返回上一页</button>
        </div>
    </div>
</div>


</body>
</html>
