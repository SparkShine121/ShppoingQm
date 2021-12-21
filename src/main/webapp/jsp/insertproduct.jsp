<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>新增商品</title>
    <link rel="stylesheet" href="/resources/layui/css/layui.css">
    <style>
        /*表单容器*/
        .ns-container {
            position: absolute;
            width: 500px;
            height: 420px;
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
        <h2>新增商品</h2>
    </blockquote>
    <table id="grdNoticeList" lay-filter="grdNoticeList"></table>
</div>
<img src="${filename}" width="300px" style="margin-left: 50px;margin-top: 100px">

<div class="ns-container">
    <h1 style="text-align: center;margin-bottom: 20px">新增商品</h1>

    <form class="layui-form">

        <div class="layui-form-item">
            <label class="layui-form-label"><a href="/file">上传图片</a></label>
            <div class="layui-input-block layui-col-space5">
                <input name="productImage" type="text" value="${filename}" lay-verify="required|mobile" placeholder=""
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">商品名称</label>
            <div class="layui-input-block layui-col-space5">
                <input name="productName" type="text" lay-verify="required|mobile" placeholder="" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">商品价格</label>
            <div class="layui-input-block layui-col-space5">
                <input name="price" type="text" lay-verify="required|mobile" placeholder="" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">商品详情</label>
            <div class="layui-input-block layui-col-space5">
                <input name="detail" type="text" lay-verify="required|mobile" placeholder="" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">商品库存</label>
            <div class="layui-input-block layui-col-space5">
                <input name="stock" type="text" lay-verify="required|mobile" placeholder="" autocomplete="off"
                       class="layui-input">
            </div>
        </div>

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
        $.post("/admin/saveProduct", data.field, function (json) {
            console.info(json);
            if (json.code == "0") {
                /*SweetAlert2确定对话框*/
                swal({
                    type: 'success',
                    html: "<h2>商品新增成功</h2>",
                    confirmButtonText: "确定"
                }).then(function (result) {
                    window.location.href = "/admin/notice";
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