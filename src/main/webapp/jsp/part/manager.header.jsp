<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<html>
<body>
<nav class="navbar navbar-default top-navbar" role="navigation">
    <div class="navbar-header">
        <a class="navbar-brand" href="#">
            <strong>商城后台管理系统</strong>
        </a>
    </div>
    <ul class="nav navbar-top-links navbar-right">
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">
                ${admin.aname }
                <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
                <li>
                    <a href="LoginServlet?op=logout"><i class="fa fa-sign-out fa-fw"></i>
                        登出
                    </a>
                </li>
            </ul>
        </li>
    </ul>
</nav>
</body>
</html>
