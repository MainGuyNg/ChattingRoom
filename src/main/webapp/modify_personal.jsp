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
        $("#modify_personal_button").click(function () {
            var nickname = $("#nickname").val();
            var sex = $("input[name='sex']:checked").val();
            var birthday = $("#birthday").val();
            var location = $("#location").val();
            var telephone = $("#telephone").val();

            if (birthday == null || birthday.trim() == "") {
                var user = {
                    nickname: nickname,
                    sex: sex,
                    location: location,
                    telephone: telephone
                };
            } else {
                birthday = new Date(birthday);
                var user = {
                    nickname: nickname,
                    sex: sex,
                    birthday: birthday,
                    location: location,
                    telephone: telephone
                };
            }
            console.info(user);

            $.ajax({
                type: "POST",
                async: false,
                url: "${ctx}/user/modify_personal_info",
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
<div id="modify_personal_all">
    <form id="modify_personal_form" method="post">
        <div id="modify_personal_div_top">
            <font>修改个人信息</font>
        </div>
        <div id="modify_personal_div_middle">
            <div style="width: 300px;margin: auto;">
                <div id="modify_personal_div_middle_left">
                    <div class="modify_personal_div_middle_left_content">昵称：</div>
                    <div class="modify_personal_div_middle_left_content">性别：</div>
                    <div class="modify_personal_div_middle_left_content">出生日期：</div>
                    <div class="modify_personal_div_middle_left_content">详细地址：</div>
                    <div class="modify_personal_div_middle_left_content">手机号：</div>
                </div>

                <div id="modify_personal_div_middle_right">
                    <div class="modify_personal_div_middle_right_content">
                        <input type="text" id="nickname" maxlength="16" placeholder="请输入昵称"/>
                    </div>
                    <div class="modify_personal_div_middle_right_content">
                        <input type="radio" name="sex" value="1"/>男
                        <input type="radio" name="sex" value="2"/>女
                    </div>
                    <div class="modify_personal_div_middle_right_content">
                        <input type="date" id="birthday"/>
                    </div>
                    <div class="modify_personal_div_middle_right_content">
                        <input type="text" id="location" maxlength="200" placeholder="请输入地址"/>
                    </div>
                    <div class="modify_personal_div_middle_right_content">
                        <input type="text" id="telephone" maxlength="11" placeholder="请输入手机号"
                               oninput="value=value.replace(/[^\d]/g,'')"/>
                    </div>
                </div>
            </div>
            <div id="modify_personal_div_bottom">
                <div id="modify_personal_div_bottom_content">
                    <button type="submit" id="modify_personal_button">修改</button>
                    <button type="button" onclick="window.location='${ctx}/user/personal_info'">返回</button>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>
