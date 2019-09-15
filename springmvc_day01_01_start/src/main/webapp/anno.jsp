<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/5/28
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <%-- 常用注解 --%>
    <a href="anno/testRequestParam?/name=haha">@RequestParam注解</a>

<br/>
    <form action="anno/testRequestBody" method="post" >     <%-- <form>表单标签--%>
           用户姓名: <input type="text" name="useranme" /><br/>
           用户年龄: <input type="text" name="age" /><br/>
           <input type="submit" value="提交" />     <%-- 提交按钮--%>
    </form>

    <a href="anno/testPathVariable/10">@PathVariable注解</a><br/>

    <a href="anno/testRequestHaeder">@RequestHeader注解</a><br/>

    <a href="anno/testCookieValue">@CookieValue注解</a><br/>

    <br/>
    <form action="anno/testModelAttribute" method="post" >     <%-- <form>表单标签--%>
        用户姓名: <input type="text" name="uanme" /><br/>
        用户年龄: <input type="text" name="age" /><br/>
        <input type="submit" value="提交" />     <%-- 提交按钮--%>
    </form>     <br/>

    <a href="anno/testSessionAttributes">@SessionAttributes注解01</a>     <br/>

    <a href="anno/getSessionAttributes">@SessionAttributes注解02</a>     <br/>

    <a href="anno/deleteSessionAttributes">@SessionAttributes注解03</a>     <br/>


</body>
</html>
