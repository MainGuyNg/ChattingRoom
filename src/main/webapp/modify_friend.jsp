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
<link rel="stylesheet" type="text/css" href="${ctx}/css/modify_friend.css"/>
<script type="text/javascript" src="${ctx}/js/jquery-3.4.1.min.js"></script>
<script>
    $(function () {
        var mvcObject = '${mvcObject}';
        var data = JSON.parse(mvcObject);
        data = data.map;

        var friendId = data.friendId;
        var friendList = data.friendList;

        for (var i = 0; i < friendList.length; i++) {
            var listData = friendList[i];
            var listId = listData.listId;
            var listName = listData.listName;
            $("#modify_friend_list").append("<option value='" + listId + "'>" + listName + "</option>");
        }

        $("#modify_friend_list").change(function () {
            window.location = "${ctx}/relation/move_friend_to_other_list?friendId=" + friendId + "&listId=" + listId;
        });

        $("#modify_friend_remark_form_button").click(function () {
            var friendRemark = $("#friendRemark").val();
            window.location="${ctx}/relation/modify_friend_remark?friendId="+friendId+"&friendRemark="+friendRemark;

        })
    })
</script>
<head>
    <title>分组好友</title>
</head>
<body>
<div id="modify_friend_all">
    <div id="modify_friend_div_top">
        <font>分组好友</font><br/>
    </div>
    <div id="modify_friend_div_middle">
        <div style="width: 300px;margin: auto;">
            <div id="modify_friend">
                修改分组：
                <select id="modify_friend_list" name="friend_list">
                    <option>---请选择分组---</option>
                </select>
                <form id="modify_friend_remark_form" method="get">
                    修改备注：
                    <input type="text" id="friendRemark" name="friendRemark" maxlength="10" style="width: 80px;">
                    <button type="button" id="modify_friend_remark_form_button">修改</button>
                </form>
            </div>
        </div>
    </div>
    <div id="modify_friend_div_bottom">
        <div id="modify_friend_div_bottom_content">
            <button type="button" onclick="window.location='${ctx}/relation/query_friend_list'">返回上一页</button>
        </div>
    </div>
</div>

</body>
</html>
