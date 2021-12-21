<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<body>
<!--导航栏class="active-menu-->
<nav class="navbar-default navbar-side" role="navigation">
    <div class="sidebar-collapse">
        <ul class="nav" id="main-menu">
            <li><a href="/jsp/statistical.jsp">
                <i class="fa fa-desktop"></i>统计信息</a></li>
            <li><a href="/jsp/CustomerServlet?op=show">
                <i class="fa fa-table"></i>客户管理 </a></li>
            <li><a href="/jsp/GoodsServlet?op=show">
                <i class="fa fa-table"></i>商品管理</a></li>
            <li><a href="/jsp/OrderServlet?op=show">
                <i class="fa fa-desktop"></i>订单管理</a></li>
        </ul>
    </div>
</nav>
</body>
</html>
