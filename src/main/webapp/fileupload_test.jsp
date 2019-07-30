<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/7/3
  Time: 11:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${ctx}/user/upload_head_icon" method="post" enctype="multipart/form-data">
    <input type="file" id="headUrl" name="headUrl" required="required"/>
    <input type="submit" value="上传"/>
</form>

</body>
</html>
