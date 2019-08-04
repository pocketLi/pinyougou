<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Freemarker测试</title>
</head>
<body>
<h2>
    ${name}---${message}
</h2>
<#-- 这是注释；在页面中是看不到任何信息；连注释一切在生成的文件中完全消失 -->
<br><hr><br>
assign指定变量<br>
<#assign str="传智播客"/>
${str}<br>
<#assign linkMan={"mobile":"13000000000", "address":"吉山村"}/>
联系电话：${linkMan.mobile}；地址：${linkMan.address}
<br><hr><br>
include引入其它模版文件<br>
<#include "header.ftl"/>
<br><hr><br>
if条件控制语句<br>
<#assign bool=true/>
<#if bool>
    bool的值为true
<#else>
    bool的值为false
</#if>
<br><hr><br>
list循环控制语句<br>
<#list goodsList as goods>
    ${goods.name} --- ${goods.price}<br>
</#list>

<br><hr><br>
eval ---> json字符串转换对象<br>

<#assign objStr='{"name":"itcast","age":12}'/>
<#assign obj=objStr?eval />
${obj.name} --- ${obj.age}


<br><hr><br>
日期格式化：<br>

.now表示当前时间${.now}<br>
当前日期显示：${today?date}<br>
当前日时间显示：${today?time}<br>
当前日日期时间显示：${today?datetime}<br>
当前日期格式化显示：${today?string("yyyy年MM月dd日 HH:mm:ss SSSS")}<br>
<br><hr><br>
数值类型显示处理：<br>
直接显示：${number} ---- 数值转为字符串?c : ${number?c}

<br><hr><br>
空值处理：!表示如果空值则什么都不显示；如果要显示则可以在!之后添加内容<br>
直接使用!什么都不显示：${emp!}；如果空值要显示内容：${emp!"emp的值为空"}<br><br>

??? 前面两个??表示判断变量是否存在，如果存在则返回true，如果不存在则返回false；后面一个?表示函数的调用。<br>

<#assign bool3=false>
${bool3???string}<br>


<#if str5??>
    str5 存在
<#else >
    str5 不存在
</#if>




<br><br><br><br><br><br><br><br><br><br>
</body>
</html>