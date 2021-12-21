<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <%--    <link rel="stylesheet" href="/resources/stitic/layui/css/layui.css">--%>
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
</head>
<body>
<div class="oa-container">
    <h1 style="text-align: center;margin-bottom: 20px">京西商城</h1>
    <form class="layui-form">
        <div class="layui-form-item">
            <input type="text" id="username" lay-verify="required" name="username" placeholder="请输入用户名"
                   autocomplete="off" class="layui-input">
        </div>

        <div class="layui-form-item">
            <input type="password" id="password" lay-verify="required" name="password" placeholder="请输入密码"
                   autocomplete="off" class="layui-input">
        </div>
        <div class="layui-form-item">
            <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="login">登录</button>
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
            url: "/check_login",
            data: JSON.stringify(formdata.field), //提交表单数据
            type: "post",
            contentType: "application/json",
            success: function (json) {
                console.log(json);
                if (json.code == "0") { //登录校验成功
                    //跳转url
                    window.location.href = json.data;
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