<html>
<head>
    <title>demo</title>
    <meta charset="utf-8">
</head>
<body>
<#-- 我是一个注释, 不会输出,不会生成在源代码里 -->
<!-- html注释 会生成在源代码里 -->

<#include "head.ftl">

${name},你好! ${message}

<#assign linkman="王先生"><br>
   联系人: ${linkman}<br>
</assign>

<#if success=true>
    你已通过实名认证!
<#else>
    你未通过实名认证!
</#if><br>

------商品列表------<br>
<#list goodsList as goods>
    ${goods_index+1} 商品名称:${goods.name}  商品价格:${goods.price}<br>
</#list>

一共${goodsList?size}条记录<br>

<#assign text="{'bank':'工商银行','account':'12165486356'}" />
<#assign data=text?eval />
开户行:${data.bank}    账号:${data.account}<br>

当前日期:${today?date}<br>
当前时间:${today?time}<br>
当前日期+时间:${today?datetime}<br>
日期格式化: ${today?string('yyyy年MM月dd日')}<br>

当前积分:  ${point?c}<br>

<#if aaa??>
    aaa变量存在  ${aaa}
<#else >
    aaa变量不存在
</#if><br>

${bbb!'bbb没有被赋值'}

<#if point gt 100>
  黄金会员
</#if>

</body>
</html>