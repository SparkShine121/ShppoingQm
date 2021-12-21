<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/11/24
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:forEach items="${lists}" var="list">
    ${list.commentMsg}<br>
    <c:if test="${list.replyComments!=null}">
        <c:forEach items="${list.replyComments}" var="tt">
            字评论：${tt.commentMsg}<br>
        </c:forEach>
    </c:if>
</c:forEach>
</body>
</html>
