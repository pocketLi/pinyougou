<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>cas测试</title>
</head>
<body>
欢迎 <%=request.getRemoteUser()%> 使用一品优购。
<a href="http://cas.pinyougou.com/logout?service=http://www.itcast.cn">退出</a>
</body>
</html>
