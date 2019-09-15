<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/5/29
  Time: 9:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <script src="js/jquery.min.js"></script>        <!-- 引入js文件 -->

    <script >
        // 页面加载,绑定单击事件
        $(function(){
            $("#btn").click(function(){
               //alert("hello btn");
                // 发送ajax请求
                $.ajax({
                    // 编写json格式,设置属性和值
                    url:"User/testAjax",
                    contentType:"application/json;charset=UTF-8",
                    data:'{"username":"haha","password":"123","age":30}',
                    dataType:"json",
                    type:"post",
                    success:function(data){
                        // data服务器端响应的json的数据,进行解析
                        alert(data);
                        alert(data.username) ;
                        alert(data.password) ;
                        alert(data.age) ;
                    }
                });
            });
        });
    </script>
</head>
<body>

<a href="User/testString">testString</a>   <br/>

<a href="User/testVoid">testVoid</a>   <br/>

<a href="User/testModelAndView">testModelAndView</a>   <br/>

<a href="User/testForwardOrRedirect">testForwardOrRedirect</a>   <br/>

<button id="btn">发送ajax的请求</button>     <!-- 按钮标签 -->

</body>
</html>
