<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>京西商城商家后台管理系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/resources/layui/css/layui.css">
</head>
<script type="text/javascript">
    var websocket = null;
    if ('WebSocket' in window) {
        var host = window.location.host;
        // websocket = new WebSocket("ws://"+host+"/webSocket");
        websocket = new WebSocket("ws://" + host + "/orderChat");
    } else {
        alert('该浏览器不支持订单提醒!');
    }

    websocket.onopen = function (event) {
        console.log('建立连接');
    }

    websocket.onclose = function (event) {
        console.log('连接关闭');
    }

    websocket.onmessage = function (event) {
        alert('收到消息:' + event.data)
        //弹窗提醒, 播放音乐
        $('#dingdanxiaoxi').modal('show');

        document.getElementById('notice').play();
    }

    websocket.onerror = function () {
        alert('websocket通信发生错误！');
    }

    window.onbeforeunload = function () {
        websocket.close();
    }

</script>
<body class="layui-layout-body">
<!-- Layui后台布局CSS -->
<div class="layui-layout layui-layout-admin">
    <!--头部导航栏-->
    <div class="layui-header">
        <!--系统标题-->
        <div class="layui-logo" style="font-size:18px;margin-left: 45% ">京西商城商家后台管理</div>
        <!--右侧当前用户信息-->
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:void(0)">
                    <!--图标-->
                    <span class="layui-icon layui-icon-user" style="font-size: 20px">
                    </span>
                    <!--用户信息-->
                    姓名[部门-职务]
                </a>
            </li>
            <!--注销按钮-->
            <li class="layui-nav-item"><a href="#">注销</a></li>
        </ul>
    </div>
    <!--左侧菜单栏-->
    <div class="layui-side layui-bg-black">
        <!--可滚动菜单-->
        <div class="layui-side-scroll">
            <!--可折叠导航栏-->
            <ul class="layui-nav layui-nav-tree">
                <!--父节点-->
                <li class="layui-nav-item layui-nav-itemed">
                    <%--                    <a href="javascript:void(0)">申请商铺入驻</a>--%>
                    <dl class="layui-nav-child module" data-node-id="1"></dl>
                </li>
                <!--子节点-->
                <dd class="function" data-parent-id="1">
                    <a href="/admin/form" target="ifmMain">申请商铺入驻</a>
                </dd>
                <dd class="function" data-parent-id="1">
                    <a href="/admin/notice" target="ifmMain">消息通知</a>
                </dd>
                <li class="layui-nav-item layui-nav-itemed">
                    <%--                    <a href="javascript:void(0)">申请商铺入驻</a>--%>
                    <dl class="layui-nav-child module" data-node-id="2"></dl>
                </li>
                <!--子节点-->
                <dd class="function" data-parent-id="2">
                    <a href="/admin/insertProduct" target="ifmMain">新增商品</a>
                </dd>
                <dd class="function" data-parent-id="2">
                    <a href="/admin/productList" target="ifmMain">商品列表</a>
                </dd>
                <dd class="function" data-parent-id="1">
                    <a href="/main" target="ifmMain">客户聊天</a>
                </dd>
            </ul>
        </div>
    </div>
    <!--主体部分采用iframe嵌入其他页面-->
    <div class="layui-body" style="overflow-y: hidden">
        <iframe name="ifmMain" style="border: 0px;width: 100%;height: 100%"></iframe>
    </div>
    <!--版权信息-->
    <%--    <div class="layui-footer">--%>
    <%--        每个账号只能注册一个商铺，不允许重复注册--%>
    <%--    </div>--%>
</div>
<!--LayUI JS文件-->
<script src="../resources/layui/layui.all.js"></script>
<script>
    //将所有功能根据parent_id移动到指定模块下
    layui.$(".function").each(function () {
        var func = layui.$(this);
        var parentId = func.data("parent-id");
        layui.$("dl[data-node-id=" + parentId + "]").append(func);
    })
    //刷新折叠菜单
    layui.element.render('nav');
</script>
</body>
</html>