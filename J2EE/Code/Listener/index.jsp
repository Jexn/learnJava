<%--
  Created by IntelliJ IDEA.
  User: Cube
  Date: 2019/1/25
  Time: 20:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>域属性测试</title>
</head>
<body>
<h1>域属性变动</h1>
<%

    application.setAttribute("id","9527");
    application.setAttribute("id","9528");
    application.removeAttribute("id");

    session.setAttribute("company","Google");
    session.setAttribute("company","Microsoft");
    session.removeAttribute("company");

    request.setAttribute("request","local");
    request.setAttribute("request","remote");
    request.removeAttribute("request");
%>
</body>
</html>
