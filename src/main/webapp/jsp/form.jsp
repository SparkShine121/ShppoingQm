<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<img src="${filename}" width="300px" style="margin-left: 50px;margin-top: 100px">
<div class="ns-container">
    <h1 style="text-align: center;margin-bottom: 20px">申请单</h1>
    <form class="layui-form">
        <div class="layui-form-item">
            <label class="layui-form-label"><a href="/file">上传图片</a></label>
            <div class="layui-input-block layui-col-space5">
                <input name="productImg" type="text" lay-verify="required|mobile" placeholder="" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">店铺类别</label>
            <div class="layui-input-block layui-col-space5">
                <input name="shopType" type="text" lay-verify="required|mobile" placeholder="" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">店铺名称</label>
            <div class="layui-input-block layui-col-space5">
                <input name="shopName" type="text" lay-verify="required|mobile" placeholder="" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">地址</label>
            <div class="layui-input-block layui-col-space5">
                <input name="shopAddr" type="text" lay-verify="required|mobile" placeholder="" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">店铺简介</label>
            <div class="layui-input-block layui-col-space5">
                <input name="shopDetail" type="text" lay-verify="required|mobile" placeholder="" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">手机号码</label>
            <div class="layui-input-block layui-col-space5">
                <input name="shopPhone" type="text" lay-verify="required|mobile" placeholder="" autocomplete="off"
                       class="layui-input">
            </div>
        </div>

        <!--提交按钮-->
        <div class="layui-form-item " style="text-align: center">
            <button class="layui-btn" type="button" lay-submit lay-filter="sub">立即申请</button>
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
        $.post("/admin/shengqing", data.field, function (json) {
            console.info(json);
            if (json.code == "0") {
                /*SweetAlert2确定对话框*/
                swal({
                    type: 'success',
                    html: "<h2>申请单已提交,等待平台管理员审批</h2>",
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