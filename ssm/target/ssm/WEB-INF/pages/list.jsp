<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/5/31
  Time: 11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    <%-- taglib 遍历的标签库 --%>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <h3>查询所有的账户</h3>
        <%-- c:forEach标签:遍历 items: 取出集合  var: 名字--%>
    <c:forEach items="${list}" var="account">
        ${account.name}
    </c:forEach>
</body>
</html>
