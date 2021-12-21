<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>立即注册</title>
    <link rel="stylesheet" href="/resources/layui/css/layui.css">
    <style>
        body {
            background-color: #F2F2F2;
        }

        .oa-container {
            /*background-color: white;*/
            position: absolute;
            width: 400px;
            height: 350px;
            top: 50%;
            left: 50%;
            padding: 20px;
            margin-left: -200px;
            margin-top: -175px;
        }

        #username, #password {
            text-align: center;
            font-size: 24px;
        }
    </style>
    <%--    <script type="text/javascript">--%>
    <%--        function refresh() {--%>
    <%--            document.getElementById('captcha_img').src="/kaptcha?"+Math.random();--%>
    <%--        }--%>
    <%--    </script>--%>
</head>
<body>
<div class="oa-container">
    <h1 style="text-align: center;margin-bottom: 20px">注册</h1>
    <form class="layui-form">
        <div class="layui-form-item" style="margin-right: 30px">
            <label class="layui-form-label" style="font-size: 20px">用户名</label>
            <div class="layui-input-block">
                <input type="text" name="username" placeholder="请输入用户名" lay-verify="required" class="layui-input"
                       autocomplete="off">
            </div>
        </div>
        <div class="layui-form-item" style="margin-right: 30px">
            <label class="layui-form-label" style="font-size: 20px">密码</label>
            <div class="layui-input-block">
                <input type="password" name="password" placeholder="请输入密码" lay-verify="required" class="layui-input"
                       autocomplete="off">
            </div>
        </div>
        <div class="layui-form-item" style="margin-right: 30px">
            <label class="layui-form-label" style="font-size: 20px">密码</label>
            <div class="layui-input-block">
                <input type="password" name="password2" placeholder="请再次输入密码" lay-verify="required" class="layui-input"
                       autocomplete="off">
            </div>
        </div>

        <div class="layui-form-item" style="margin-right: -10px;margin-left: 20px;margin-top: 20px">
            <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="login">立即注册</button>
        </div>
    </form>
</div>
<script src="/resources/layui/layui.all.js"></script>
<script>
    // 表单提交事件
    layui.form.on("submit(login)", function (formdata) {//data参数包含了当前表单的数据
        console.log(formdata);
        //发送ajax请求进行登录校验
        layui.$.ajax({
            url: "/check_register",
            data: formdata.field, //提交表单数据
            type: "post",
            dataType: "json",
            success: function (json) {
                console.log(json);
                if (json.code == "0") { //登录校验成功
                    //跳转url
                    window.location.href = "/jsp/login.jsp";
                } else {
                    layui.layer.msg(json.msg + "", {icon: 2});
                }
            }
        })
        return false;//submit提交事件返回true则表单提交,false则阻止表单提交
    })
</script>
</body>
</html>