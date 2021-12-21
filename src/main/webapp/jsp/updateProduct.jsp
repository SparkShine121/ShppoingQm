<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>商家入驻申请</title>
    <link rel="stylesheet" href="/resources/layui/css/layui.css">
    <style>
        /*表单容器*/
        .ns-container {
            position: absolute;
            width: 500px;
            height: 430px;
            top: 120px;
            left: 50%;
            margin-left: -250px;
            padding: 20px;
            box-sizing: border-box;
            border: 1px solid #cccccc;
        }
    </style>
</head>
<body>
<div class="layui-row">
    <blockquote class="layui-elem-quote">
        <h2>商铺入驻</h2>
    </blockquote>
    <table id="grdNoticeList" lay-filter="grdNoticeList"></table>
</div>
<c:if test="${filename==null}">
    <img src="${product.productImage}" width="300px" style="margin-left: 50px;margin-top: 100px">
</c:if>
<c:if test="${filename!=null}">
    <img src="${filename}" width="300px" style="margin-left: 50px;margin-top: 100px">
</c:if>
<div class="ns-container">
    <h1 style="text-align: center;margin-bottom: 20px">修改商品信息</h1>
    <form class="layui-form">
        <c:if test="${filename==null}">
            <div class="layui-form-item">
                <label class="layui-form-label"><a href="/updateFile?id=${product.id}">上传图片</a></label>
                <div class="layui-input-block layui-col-space5">
                    <input name="productImage" value="${product.productImage}" type="text" lay-verify="required|mobile"
                           placeholder="" autocomplete="off" class="layui-input">
                </div>
            </div>
        </c:if>
        <c:if test="${filename!=null}">
            <div class="layui-form-item">
                <label class="layui-form-label"><a href="/updateFileUpload?id=${product.id}">上传图片</a></label>
                <div class="layui-input-block layui-col-space5">
                    <input name="productImage" value="${filename}" type="text" lay-verify="required|mobile"
                           placeholder="" autocomplete="off" class="layui-input">
                </div>
            </div>
        </c:if>
        <div class="layui-form-item">
            <label class="layui-form-label">商品名称</label>
            <div class="layui-input-block layui-col-space5">
                <input name="productName" value="${product.productName}" type="text" lay-verify="required|mobile"
                       placeholder="" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">价格</label>
            <div class="layui-input-block layui-col-space5">
                <input name="price" value="${product.price}" type="text" lay-verify="required|mobile" placeholder=""
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">库存</label>
            <div class="layui-input-block layui-col-space5">
                <input name="stock" value="${product.stock}" type="text" lay-verify="required|mobile" placeholder=""
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">商品详情</label>
            <div class="layui-input-block layui-col-space5">
                <input name="detail" value="${product.detail}" type="text" lay-verify="required|mobile" placeholder=""
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <input type="hidden" name="id" value="${product.id}">
        <!--提交按钮-->
        <div class="layui-form-item " style="text-align: center">
            <button class="layui-btn" type="button" lay-submit lay-filter="sub">确定</button>
        </div>
    </form>
</div>

<script src="/resources/layui/layui.all.js"></script>
<script src="/resources/sweet/sweetalert2.all.min.js"></script>

<script>
    var layForm = layui.form; //layui表单对象
    var $ = layui.$; //jQuery对象

    //表单提交时间
    layForm.on('submit(sub)', function (data) {
        console.info("向服务器提交的表单数据", data.field);
        $.post("/admin/updateProduct", data.field, function (json) {
            console.info(json);
            if (json.code == "0") {
                /*SweetAlert2确定对话框*/
                swal({
                    type: 'success',
                    html: "<h2>更新成功</h2>",
                    confirmButtonText: "确定"
                }).then(function (result) {
                    window.location.href = "/admin/productList";
                });
            } else {
                swal({
                    type: 'warning',
                    html: "<h2>" + json.msg + "</h2>",
                    confirmButtonText: "确定"
                });
            }
        }, "json");
        return false;
    });

</script>
</body>
</html>