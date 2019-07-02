<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/7/2
  Time: 17:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<link rel="stylesheet" type="text/css" href="${ctx}/css/modify_password.css"/>
<script type="text/javascript" src="${ctx}/js/jquery-3.4.1.min.js"></script>
<script>
    $(function () {

        $("#modify_password_button").click(function () {
            var newPassword = $("#newPassword").val();
            var confirm_password = $("#confirm_password").val();

            if (newPassword != confirm_password) {
                alert("两次输入的密码不一致");
                return;
            } else {
                $("#modify_password_form").submit();
            }
        });
    })
</script>
<head>
    <title>修改密码</title>
</head>
<body>
<div id="modify_password_all">
    <form id="modify_password_form" action="${ctx}/user/modify_password" method="post">
        <div id="modify_password_div_top">
            <font>修改密码</font>
        </div>
        <div id="modify_password_div_middle">
            <div style="width: 300px;margin: auto;">
                <div id="modify_password_div_middle_left">
                    <div class="modify_password_div_middle_left_content">旧密码：</div>
                    <div class="modify_password_div_middle_left_content">新密码：</div>
                    <div class="modify_password_div_middle_left_content">确认密码：</div>
                </div>

                <div id="modify_password_div_middle_right">
                    <div class="modify_password_div_middle_right_content">
                        <input type="password" id="oldPassword" name="oldPassword" required="required" maxlength="16"
                               placeholder="请输入旧密码"/>
                    </div>
                    <div class="modify_password_div_middle_right_content">
                        <input type="password" id="newPassword" name="newPassword" required="required" maxlength="16"
                               placeholder="请输入新密码"/>
                    </div>
                    <div class="modify_password_div_middle_right_content">
                        <input type="password" id="confirm_password" required="required" maxlength="16"
                               placeholder="请再次输入密码"/>
                    </div>
                </div>
            </div>
            <div id="modify_password_div_bottom">
                <div id="modify_password_div_bottom_content">
                    <button type="button" id="modify_password_button">修改</button>
                    <button type="button" onclick="window.location='${ctx}/user/personal_info'">返回</button>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>
