<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>网络聊天室</title>
    <link rel="stylesheet" href="/resources/css/bootstrap.css">
</head>
<style>
    #contains {
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;

        background-color: pink;
        width: 1000px;
        height: 580px;
        margin: auto;
    }

    #username {
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;

        background-color: powderblue;
        width: 1000px;
        height: 30px;
    }

    #Inchat {
        background-color: rgb(73, 168, 180);
        width: 1000px;
        height: 30px;
    }

    #left {
        border-bottom-left-radius: 10px;
        border: 2px solid #c0c4cc;
        background-color: #ffffff;
        width: 700px;
        height: 520px;
        float: left;
        position: relative;
    }

    #content {
        background-color: #fcfcfc;
        width: 700px;
        height: 350px;
        /*display: none;*/
        visibility: hidden;
    }

    #right {
        border-bottom: 2px solid #c0c4cc;
        border-right: 2px solid #c0c4cc;

        background-color: rgb(217, 217, 217);
        width: 300px;
        height: 520px;
        float: right;
    }

    #hyList {
        height: 170px;
        overflow-y: scroll;
        background: #FFFFFF;
    }

    #xtList {
        height: 270px;
        overflow-y: scroll;
        background: #FFFFFF;
    }

    #input {
        margin-bottom: 0px;
        position: absolute;
        bottom: 0px;
    }

    #mes_left {
        margin-left: 20px;
        float: left;
        font-size: 17px;
        border: 2px aqua;
        max-width: 490px;
    }

    #mes_right {
        margin-right: 20px;
        float: right;
        color: #89d73f;
        font-size: 17px;
        border: 2px aqua;
        max-width: 490px;
    }

    #hy {
        display: block;
        width: 300px;
    }

    textarea {
        resize: none;
        border: none;
        outline: none
    }
</style>
<body>
<div id="contains">
    <div id="username" style="height: 35px;line-height: 35px">
        <h3 style="text-align: center">用户：张三<span>-在线</span></h3></div>
    <div id="Inchat"></div>
    <div id="left">
        <div id="content" style="background-color: rgb(255,255,255)">

        </div>
        <div id="input" style="border-top: 2px solid #c0c4cc;background-color:#FFFFFF">
            <textarea type="text" id="input_text" style="width: 695px;height: 160px;"></textarea>
            <button id="submit" class="btn btn-info" style="float: right">发送</button>
        </div>
    </div>
    <div id="right">
        <p id="hy" style="text-align: center;font-size: 18px;color: #31b0d5;height:30px;
        background-color: #FFFFFF;margin-bottom: 2px; border-right: 2px solid #c0c4cc;
">好友列表</p>
        <div id="hyList">

        </div>

        <p id="jl"
           style="text-align: center;font-size: 18px;color: #31b0d5;height:30px;background-color: #FFFFFF;margin-bottom: 2px">
            聊天记录</p>
        <div id="ltList">

        </div>
        <p id="xt"
           style="text-align: center;font-size: 18px;color: #31b0d5;height:30px;background-color: #FFFFFF;margin-bottom: 2px">
            系统消息</p>
        <div id="xtList">

        </div>
    </div>
</div>
</body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
    var toName;
    var username;

    //点击好友名称展示相关消息
    function showChat(name) {
        toName = name;
        //现在聊天框
        $("#content").html("");
        $("#content").css("visibility", "visible");
        $("#Inchat").html("当前正与" + toName + "聊天");
        //从sessionStorage中获取历史信息
        var chatData = sessionStorage.getItem(toName);
        if (chatData != null) {
            $("#content").html(chatData);
        }
    }

    function showChatltList(toName) {
        $.ajax({
            url: "jilu",
            data: {
                "toName": toName,
                "username": username
            },
            dataType: "json",
            async: false,
            success: function (res) {
                //遍历传回来的聊天记录
                var list = res.data;
                for (var i = 0; i < list.length; i++) {
                    if (list[i].toName == username) {
                        var str = "<span id='mes_left'><h4>" + list[i].message + "</h4></span></br>";
                        $("#content").append(str);
                    }
                    if (list[i].fromName == username) {
                        var str = "<span id='mes_right'><h4>" + list[i].message + "</h4></span></br>";
                        $("#content").append(str);
                    }
                }

                //现在聊天框
                $("#content").css("visibility", "visible");
                $("#Inchat").html("与" + toName + "的聊天记录");
            }
        })
    }

    $(function () {
        $.ajax({
            url: "getUsername",
            success: function (res) {
                username = res;
            },
            async: false //同步请求，只有上面好了才会接着下面，否则拿不到用户名就继续执行的话，用户名会显示不出来
        });
        //建立websocket连接
        //获取host解决后端获取httpsession的空指针异常
        //获取当前域名加端口号
        var host = window.location.host;
        var ws = new WebSocket("ws://" + host + "/chat");
        //  var ws = new WebSocket("ws://localhost:8080/chat");
        ws.onopen = function (evt) {
            console.log("连接成功");
            $("#username").html("<h3 style=\"text-align: center;\">用户：" + username + "<span>-在线</span></h3>");
        }
        ws.onmessage = function (evt) {

            //获取服务端推送的消息
            var dataStr = evt.data;
            //将dataStr转换为json对象
            var res = JSON.parse(dataStr);

            //判断是否是系统消息
            if (res.system) {
                //系统消息
                //1.好友列表展示
                //2.系统广播的展示
                //此处声明的变量是调试时命名的，可以直接合并
                var names = res.message;
                var userlistStr = "";
                var ltListStr = "";
                var broadcastListStr = "";
                var temp01 = "<a style=\"text-align: center; display: block;\" onclick='showChat(\"";
                var temp03 = "\")'>";
                var temp04 = "</a></br>";
                var temp = "";

                var temp07 = "<a style=\"text-align: center;\" onclick='showChatltList(\"";
                var temp08 = "\")'>";
                var temp09 = "</a></br>";
                var temp00 = "";
                for (var name of names) {
                    if (name != username) {
                        temp = temp01 + name + temp03 + name + temp04;
                        temp00 = temp07 + name + temp08 + name + temp09;
                        userlistStr = userlistStr + temp;
                        ltListStr = ltListStr + temp00;
                        broadcastListStr += "<p style='text-align: center'>" + name + "上线了</p>";
                    }
                }

                //渲染好友列表和系统广播
                $("#hyList").html(userlistStr);
                $("#ltList").html(ltListStr);
                $("#xtList").html(broadcastListStr);

            } else {
                //不是系统消息
                var str = "<span id='mes_left'>" + res.message + "</span></br>";
                if (toName === res.fromName) {
                    $("#content").append(str);
                }
                var chatData = sessionStorage.getItem(res.fromName);
                if (chatData != null) {
                    str = chatData + str;
                }
                //保存聊天消息
                sessionStorage.setItem(res.fromName, str);
            }
            ;
        }
        ws.onclose = function () {
            console.log("连接失败");
            $("#username").html("<h3 style=\"text-align: center;\">用户：" + username + "<span>-离线</span></h3>");
        }

        //发送消息
        $("#submit").click(function () {
            //1.获取输入的内容
            var data = $("#input_text").val();
            //2.清空发送框
            $("#input_text").val("");
            var json = {"toName": toName, "message": data};
            //将数据展示在聊天区
            var str = "<span id='mes_right'>" + data + "</span></br>";
            $("#content").append(str);

            var chatData = sessionStorage.getItem(toName);
            if (chatData != null) {
                str = chatData + str;
            }
            sessionStorage.setItem(toName, str);
            //3.发送数据
            ws.send(JSON.stringify(json));
        })
    })
</script>
<script src="https://eqcn.ajz.miesnfu.com/wp-content/plugins/wp-3d-pony/live2dw/lib/L2Dwidget.min.js"></script>
<!--小帅哥： https://unpkg.com/live2d-widget-model-chitose@1.0.5/assets/chitose.model.json-->
<!--萌娘：https://unpkg.com/live2d-widget-model-shizuku@1.0.5/assets/shizuku.model.json-->
<!--小可爱（女）：https://unpkg.com/live2d-widget-model-koharu@1.0.5/assets/koharu.model.json-->
<!--小可爱（男）：https://unpkg.com/live2d-widget-model-haruto@1.0.5/assets/haruto.model.json-->
<!--初音：https://unpkg.com/live2d-widget-model-miku@1.0.5/assets/miku.model.json-->
<!-- 上边的不同链接显示的是不同的小人，这个可以根据需要来选择 下边的初始化部分，可以修改宽高来修改小人的大小，或者是鼠标移动到小人上的透明度，也可以修改小人在页面出现的位置。 -->
<script>
    /*https://unpkg.com/live2d-widget-model-shizuku@1.0.5/assets/shizuku.model.json*/
    L2Dwidget.init({
        "model": {
            jsonPath:
                "https://unpkg.com/live2d-widget-model-shizuku@1.0.5/assets/shizuku.model.json",
            "scale": 1
        }, "display": {
            "position": "right", "width": 110, "height": 150,
            "hOffset": 0, "vOffset": -20
        }, "mobile": {"show": true, "scale": 0.5},
        "react": {"opacityDefault": 0.8, "opacityOnHover": 0.1}
    });
</script>
</html>