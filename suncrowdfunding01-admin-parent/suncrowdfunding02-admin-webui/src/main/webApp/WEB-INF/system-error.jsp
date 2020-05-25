<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>error</title>
</head>
<body>
<h1>出错啦</h1>
<%--从请求域中取出exception对象在进一步访问message信息--%>
${requestScope.exception.message}
</body>
</html>
