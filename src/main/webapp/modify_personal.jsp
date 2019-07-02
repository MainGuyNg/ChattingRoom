<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/7/2
  Time: 17:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<link rel="stylesheet" type="text/css" href="${ctx}/css/modify_personal.css"/>
<script type="text/javascript" src="${ctx}/js/jquery-3.4.1.min.js"></script>
<script>
    $(function () {
        $("#modify_button").click(function () {
            var nickname = $("#nickname").val();
            var sex = $("input[name='sex']").val();
            var birthday = $("#birthday").val();
            birthday = new Date(birthday);
            var location = $("#location").val();
            var telephone = $("#telephone").val();

            var user = {
                nickname: nickname,
                sex: sex,
                birthday: birthday,
                location: location,
                telephone: telephone
            };
            console.info(user);

            $.ajax({
                type: "POST",
                async: false,
                url: "${ctx}/user/modify",
                data: user,
                success: function (data) {
                    console.info("success");
                    console.info(data);
                    var resCode = data.code;

                    if (resCode == "200") {
                        alert("修改个人资料成功");
                    } else if (resCode == "100") {
                        alert("修改个人资料失败，请重新输入数据");
                    }
                },
                error: function (data) {
                    console.info("error");
                    console.info(data);
                }
            });
        });
    })
</script>
<head>
    <title>修改个人信息</title>
</head>
<body>
<div id="modify_all">
    <form id="modify_form" method="post">
        <div id="modify_div_top">
            <font>修改个人信息</font>
        </div>
        <div id="modify_div_middle">
            <div style="width: 300px;margin: auto;">
                <div id="modify_div_middle_left">
                    <div class="modify_div_middle_left_content">昵称：</div>
                    <div class="modify_div_middle_left_content">性别：</div>
                    <div class="modify_div_middle_left_content">出生日期：</div>
                    <div class="modify_div_middle_left_content">详细地址：</div>
                    <div class="modify_div_middle_left_content">手机号：</div>
                </div>

                <div id="modify_div_middle_right">
                    <div class="modify_div_middle_right_content">
                        <input type="text" id="nickname" required="required" maxlength="16" placeholder="请输入昵称"/>
                    </div>
                    <div class="modify_div_middle_right_content">
                        <input type="radio" name="sex" value="0"/>男
                        <input type="radio" name="sex" value="1"/>女
                    </div>
                    <div class="modify_div_middle_right_content">
                        <input type="date" id="birthday" required="required"/>
                    </div>
                    <div class="modify_div_middle_right_content">
                        <input type="text" id="location" required="required" maxlength="200" placeholder="请输入地址"/>
                    </div>
                    <div class="modify_div_middle_right_content">
                        <input type="text" id="telephone" required="required" maxlength="11" placeholder="请输入手机号"
                               oninput="value=value.replace(/[^\d]/g,'')"/>
                    </div>
                </div>
            </div>
            <div id="modify_div_bottom">
                <div id="modify_div_bottom_content">
                    <button type="submit" id="modify_button">修改</button>
                    <button type="button" onclick="window.location='${ctx}/user/personal_info'">返回</button>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>