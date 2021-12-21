<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>系统通知</title>
    <link rel="stylesheet" href="/resources/layui/css/layui.css">
</head>
<body>
<div class="layui-row">
    <blockquote class="layui-elem-quote">
        <h2>系统通知</h2>
    </blockquote>
    <table id="grdNoticeList" lay-filter="grdNoticeList"></table>
</div>
<script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.js"></script>

<script src="/resources/layui/layui.all.js"></script>

<script>

    //    function showUnreadNews()
    //    {
    layui.table.render({
        elem: "#grdNoticeList",
        id: "grdNoticeList",
        url: "/admin/notice/list",
        page: false,
        cols: [[
            {field: "", title: "序号", width: "10%", style: "height:60px", type: "numbers"},
            {
                field: "create_time", title: "通知时间", width: "20%", templet: function (d) {
                    var newDate = new Date(d.createTime);
                    return newDate.getFullYear() + "-" +
                        (newDate.getMonth() + 1) + "-" + newDate.getDate()
                        + " " + newDate.getHours() + ":" + newDate.getMinutes() + ":" + newDate.getSeconds();
                }
            },
            {field: "content", title: "通知内容", width: "60%"}
        ]]
    })
    // }
    // setInterval('showUnreadNews()',2000);//轮询执行，500ms一次

</script>
</body>
</html>