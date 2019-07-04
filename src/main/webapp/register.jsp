<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<link rel="stylesheet" type="text/css" href="${ctx}/css/register.css"/>
<script type="text/javascript" src="${ctx}/js/jquery-3.4.1.min.js"></script>
<script>
    $(function () {
        $("#register_button").click(function () {
            var password = $("#password").val();
            var confirm_password = $("#confirm_password").val();
            if (password != confirm_password) {
                alert("两次输入的密码不一致！！")
                return;
            } else {
                var accountNumber = $("#accountNumber").val();
                var sex = $("input[name='sex']:checked").val();
                var nickname = $("#nickname").val();
                var birthday = $("#birthday").val();
                birthday = new Date(birthday);
                var location = $("#location").val();
                var telephone = $("#telephone").val();

                var user = {
                    accountNumber: accountNumber,
                    password: password,
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
                    url: "${ctx}/user/register",
                    data: user,
                    success: function (data) {
                        console.info("success");
                        console.info(data);
                        var resCode = data.code;

                        if (resCode == "200") {
                            alert("注册成功，请重新登录");
                            window.location = "login.jsp";
                        } else if (resCode == "100") {
                            alert("注册失败，请重新输入数据");
                            window.location = "register.jsp";
                        }
                    },
                    error: function (data) {
                        console.info("error");
                        console.info(data);
                    }
                });
            }
        });
    })
</script>
<head>
    <title>Title</title>
</head>
<body>
<div id="register_all">
    <form id="register_form" method="post">
        <div id="register_div_top">
            <font>注册页面</font>
        </div>
        <div id="register_div_middle">
            <div style="width: 300px;margin: auto;">
                <div id="register_div_middle_left">
                    <div class="register_div_middle_left_content">用户名：</div>
                    <div class="register_div_middle_left_content">密码：</div>
                    <div class="register_div_middle_left_content">确认密码：</div>
                    <div class="register_div_middle_left_content">昵称：</div>
                    <div class="register_div_middle_left_content">性别：</div>
                    <div class="register_div_middle_left_content">出生日期：</div>
                    <div class="register_div_middle_left_content">详细地址：</div>
                    <div class="register_div_middle_left_content">手机号：</div>
                </div>

                <div id="register_div_middle_right">
                    <div class="register_div_middle_right_content">
                        <input type="text" id="accountNumber" required="required" maxlength="16" placeholder="请输入用户名"/>
                    </div>
                    <div class="register_div_middle_right_content">
                        <input type="password" id="password" required="required" maxlength="16" placeholder="请输入密码"/>
                    </div>
                    <div class="register_div_middle_right_content">
                        <input type="password" id="confirm_password" required="required" maxlength="16"
                               placeholder="请再次输入密码"/>
                    </div>
                    <div class="register_div_middle_right_content">
                        <input type="text" id="nickname" required="required" maxlength="16" placeholder="请输入昵称"/>
                    </div>
                    <div class="register_div_middle_right_content">
                        <input type="radio" name="sex" value="1"/>男
                        <input type="radio" name="sex" value="2"/>女
                    </div>
                    <div class="register_div_middle_right_content">
                        <input type="date" id="birthday" required="required"/>
                    </div>
                    <div class="register_div_middle_right_content">
                        <input type="text" id="location" required="required" maxlength="200" placeholder="请输入地址"/>
                    </div>
                    <div class="register_div_middle_right_content">
                        <input type="text" id="telephone" required="required" maxlength="11" placeholder="请输入手机号"
                               oninput="value=value.replace(/[^\d]/g,'')"/>
                    </div>
                </div>
            </div>
            <div id="register_div_bottom">
                <div id="register_div_bottom_content">
                    <button type="submit" id="register_button">注册</button>
                    <button type="button" onclick="window.location='login.jsp'">返回</button>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>
