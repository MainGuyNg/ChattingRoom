<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/7/4
  Time: 15:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<script type="text/javascript" src="${ctx}/js/jquery-3.4.1.min.js"></script>
<script>
    jQuery.DuoImgsYulan = function (file, id) {
        for (var i = 0; i < 3; i++) {
            if (!/image\/\w+/.test(file[i].type)) {
                alert("请选择图片文件");
                return false;
            }
            if (file[i].size > 2048 * 1024) {
                alert("图片不能大于2MB");
                continue;
            }
            var img;
            console.log(document.getElementById("fileImg"));
            console.log(file[i]);
            console.log("file-size=" + file[i].size);
            var reader = new FileReader();
            reader.onloadstart = function (e) {
                console.log("开始读取....");
            }
            reader.onprogress = function (e) {
                console.log("正在读取中....");
            }
            reader.onabort = function (e) {
                console.log("中断读取....");
            }
            reader.onerror = function (e) {
                console.log("读取异常....");
            }
            reader.onload = function (e) {
                console.log("成功读取....");
                var div = document.createElement("div"); //外层 div
                div.setAttribute("style", "position:relative;width:inherit;height:inherit;float:left;z-index:2;width:150px;margin-left:8px;margin-right:8px;");
                var del = document.createElement("div"); //删除按钮div
                del.setAttribute("style", "position: absolute; bottom: 4px; right: 0px; z-index: 99; width: 30px; height:30px;border-radius:50%;")
                var delicon = document.createElement("img");
                delicon.setAttribute("src", "${ctx}/img/close_icon.png");
                delicon.setAttribute("title", "删除");
                delicon.setAttribute("style", "cursor:pointer;width: 30px; height:30px");
                del.onclick = function () {
                    this.parentNode.parentNode.removeChild(this.parentElement);
                    ClearfirtsImg();
                };
                del.appendChild(delicon);
                div.appendChild(del);
                var imgs = document.createElement("img"); //上传的图片
                imgs.setAttribute("name", "loadimgs");
                imgs.setAttribute("src", e.target.result);
                imgs.setAttribute("width", 150);
                if (document.getElementById(id).childNodes.length > 2) {
                    document.getElementById(id).removeChild(document.getElementById(id).firstChild);
                }
                div.appendChild(imgs)
                document.getElementById(id).appendChild(div);
            }
            reader.readAsDataURL(file[i]);
        }
    }

    function FirstImg() {
        $.DuoImgsYulan(document.getElementById("headUrl").files, "ccc");
    }

    function ClearfirtsImg() {
        var file = $("#headUrl")
        file.after(file.clone().val(""));
        file.remove();
    }
</script>
<head>
    <title>Title</title>
</head>
<body>
<form action="${ctx}/user/upload_head_icon" method="post" enctype="multipart/form-data">
    <div style="padding: 20px">
        <input onchange="FirstImg()" name="headUrl" style="opacity:0;position:absolute" type="file" id="headUrl" multiple="">
        <div position:absolute="">
            <span> 点击这里上传图片</span>
        </div>
    </div>
    <fieldset style="width:500px;">
        <legend>图片预览</legend>
        <div style="position: relative;" id="ccc">
        </div>
    </fieldset>
    <input type="submit" value="提交"/>
    <button type="button" onclick="window.location='${ctx}/user/personal_info'">返回个人中心</button>
</form>

</body>
</html>
