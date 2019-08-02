<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/8/2
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<link rel="stylesheet" type="text/css" href="${ctx}/css/modify_result.css"/>
<script type="text/javascript" src="${ctx}/js/jquery-3.4.1.min.js"></script>
<script>
    var time = 5;
    var count;

    function delayRedirect() {
        time -= 1;
        if (time > 0) {
            count = setTimeout("delayRedirect()", 1000);
            $("#delay_redirect").text(time + "秒后返回聊天室页面.....");
        } else {
            window.location = "${ctx}/send.jsp";
            clearTimeout(count);
        }
    }

    delayRedirect();
</script>
<head>
    <title>结果页</title>
</head>
<body>
<div id="modify_result_all">
    <div id="modify_result_div_top">
        <font>结果页</font>
    </div>
    <div id="modify_result_div_middle">
        <div style="width: 300px;margin: auto;">
            <font>code: ${mvcObject.code}<br/></font>
            <font>msg: ${mvcObject.msg}<br/></font>
            <a href="${ctx}/send.jsp" id="delay_redirect">5秒后返回聊天室页面.....</a>
        </div>
    </div>
</div>

</body>
</html>
