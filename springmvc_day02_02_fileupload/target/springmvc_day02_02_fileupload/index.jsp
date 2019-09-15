<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/5/29
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a>传统文件上传</a>

<form action="user/fileuploadl" method="post" enctype="multipart/form-data">
    选择文件: <input type="file" name="upload" /><br/>
              <input type="submit" value="上传" /><br/>
</form>

<a>Springmvc文件上传</a>

<form action="user/fileupload2" method="post" enctype="multipart/form-data">
    选择文件: <input type="file" name="upload" /><br/>
    <input type="submit" value="上传" /><br/>
</form>

<a>Springmvc跨服务器文件上传</a>

<form action="user/fileupload3" method="post" enctype="multipart/form-data">
    选择文件: <input type="file" name="upload" /><br/>
    <input type="submit" value="上传" /><br/>
</form>
</body>
</html>
