<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html"; charset="utf-8" >
<title>cas client demo1</title>
</head>
<body>      <!-- request: 内置对象  getRemoteUser(): 获取登录用户名 -->
欢迎来到品优购  <%=request.getRemoteUser() %>

<a href="http://localhost:9100/cas/logout?service=http://www.baidu.com">退出登录</a>
</body>
</html>
