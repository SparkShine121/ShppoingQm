<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/10/30
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<script src="/resources/js/jquery-3.3.1.js"></script>
<link rel="stylesheet" href="/resources/css/bootstrap.css">
<script src="/resources/js/bootstrap.min.js"></script>
<body>
<!-- 搭建显示页面 -->
<div class="container">
    <!-- 标题 -->
    <div class="row">
        <div class="col-md-12">
            <h1>商品列表</h1>
        </div>
    </div>
    <!-- 按钮 -->
    <div class="row">
        <div class="col-md-4 col-md-offset-8" style="margin-bottom: 5px">
            <button class="btn btn-primary">新增商品</button>
            <button class="btn btn-danger">批量上架</button>
        </div>
    </div>
    <!-- 显示表格数据 -->
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover">
                <tr>
                    <th style="text-align: center;vertical-align: middle">#</th>
                    <th style="text-align: center;vertical-align: middle">名称</th>
                    <th style="text-align: center;vertical-align: middle">图片</th>
                    <th style="text-align: center;vertical-align: middle">价格</th>
                    <th style="text-align: center;vertical-align: middle">详情</th>
                    <th style="text-align: center;vertical-align: middle">库存</th>
                    <th style="text-align: center;vertical-align: middle" colspan="2">操作</th>
                </tr>
                <c:forEach items="${pageinfo.list }" var="emp">
                    <tr>
                        <th style="text-align: center;vertical-align: middle"></th>
                        <th style="text-align: center;vertical-align: middle">${emp.productName}</th>
                        <th style="text-align: center;vertical-align: middle"><img src="${emp.productImage}"
                                                                                   height="80px"></th>
                        <th style="text-align: center;vertical-align: middle">${emp.price}</th>
                        <th style="text-align: center;vertical-align: middle">${emp.detail}</th>
                        <th style="text-align: center;vertical-align: middle">${emp.stock}</th>
                        <th style="text-align: center;vertical-align: middle">
                                <%--                            <button class="btn btn-primary btn-sm" onclick="xiugai()" id="update" name="${emp.id}" value="${emp.id}">--%>
                                <%--                                编辑--%>
                                <%--                            </button>--%>
                                <%--                            <button class="btn btn-danger btn-sm">--%>
                                <%--                                删除--%>
                                <%--                            </button>--%>
                            <a href="/admin/updateProductYm/${emp.id}">编辑</a>
                            <a href="/admin/deleteProduct/${emp.id}">删除</a>
                        </th>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>

    <!-- 显示分页信息 -->
    <div class="row">
        <!--分页文字信息  -->
        <div class="col-md-6">当前 ${pageinfo.pageNum }页,总${pageinfo.pages }
            页,总 ${pageinfo.total } 条记录
        </div>
        <!-- 分页条信息 -->
        <div class="col-md-6">
            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <li><a href="/admin/productList?pageNum=1">首页</a></li>
                    <c:if test="${pageinfo.hasPreviousPage }">
                        <li><a href="/admin/productList?pageNum=${pageinfo.pageNum-1}"
                               aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
                        </a></li>
                    </c:if>

                    <c:forEach items="${pageinfo.navigatepageNums }" var="page_Num">
                        <c:if test="${page_Num == pageinfo.pageNum }">
                            <li class="active"><a href="#">${page_Num }</a></li>
                        </c:if>
                        <c:if test="${page_Num != pageinfo.pageNum }">
                            <li><a href="/admin/productList?pageNum=${page_Num }">${page_Num }</a></li>
                        </c:if>

                    </c:forEach>
                    <c:if test="${pageinfo.hasNextPage }">
                        <li><a href="/admin/productList?pageNum=${pageinfo.pageNum+1 }"
                               aria-label="Next"> <span aria-hidden="true">&raquo;</span>
                        </a></li>
                    </c:if>
                    <li><a href="/admin/productList?pageNum=${pageinfo.pages}">末页</a></li>
                </ul>
            </nav>
        </div>
    </div>
    <script>
        function xiugai() {
            var id = $('#update').val();
            alert(id);
            /**可以获取的到值*/
            window.location.href = "/admin/updateProductYm/" + id;
        }
    </script>
</div>
</body>
</html>
