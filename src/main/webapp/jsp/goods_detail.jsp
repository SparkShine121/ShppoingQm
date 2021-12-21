<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/11/16
  Time: 12:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>商品详情</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <!-- jquery -->
    <script type="text/javascript" src="/resources/js/jquery-3.3.1.js"></script>
    <!-- bootstrap -->
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.css"/>
    <script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
    <!-- jquery-validator -->
    <script type="text/javascript" src="/jquery-validation/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/jquery-validation/localization/messages_zh.min.js"></script>
    <!-- layer -->
    <script type="text/javascript" src="/layer/layer.js"></script>
    <!-- md5.js -->
    <script type="text/javascript" src="/js/md5.min.js"></script>
    <!-- common.js -->
    <script type="text/javascript" src="/js/common.js"></script>
</head>
<body>

<div class="panel panel-default">
    <div class="panel-heading">秒杀商品详情</div>
    <div class="panel-body">
        <c:if test="${user==null}">
            <span id="userTip" style="color: red"> 您还没有<a href="/login"><em>登录</em></a>，请登陆后再操作<br/></span>
        </c:if>
        <%--    <span>没有收货地址的提示。。。</span>--%>
    </div>
    <table class="table" id="goodslist">
        <tr>
            <td>商品名称</td>
            <td colspan="3" id="goodsName">${goods.productName}</td>
        </tr>
        <tr>
            <td>商品图片</td>
            <td colspan="3"><img id="goodsImg" src="${goods.productImage}" width="200" height="200"/></td>
        </tr>
        <tr>
            <td>秒杀开始时间</td>

            <td id="startTime"><fmt:formatDate value="${goods.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>
                <input type="hidden" id="remainSeconds" value="${remainSeconds}"/>
                <span id="miaoshaTip"></span>
            </td>
            <td>
                <%--        	<form id="miaoshaForm" method="post" action="/do_miaosha">--%>
                <%--        		<button class="btn btn-primary btn-block" type="submit" id="buyButton">立即秒杀</button>--%>
                <%--        		<input type="hidden" name="goodsId" value="${goods.id}"  id="goodsId" />--%>
                <%--        	</form>--%>
                <button class="btn btn-primary btn-block" type="button" id="buyButton" onclick="doMiaosha()">立即秒杀
                </button>
                <input type="hidden" name="goodsId" value="${goods.id}" id="goodsId"/>
            </td>
        </tr>
        <tr>
            <td>商品原价</td>
            <td colspan="3" id="goodsPrice">${goods.price}</td>
        </tr>
        <tr>
            <td>秒杀价</td>
            <td colspan="3" id="miaoshaPrice">${goods.miaoshaPrice}</td>
        </tr>
        <tr>
            <td>库存数量</td>
            <td colspan="3" id="stockCount">${goods.stock}</td>
        </tr>
    </table>
</div>
</body>
<script>

    function doMiaosha() {
        $.ajax({
            url: "/do_miaosha",
            type: "POST",
            data: {
                goodsId: $("#goodsId").val(),
            },
            success: function (json) {
                if (json.code == 0) {
                    // window.location.href = "/miaosha_order_detail?orderId=" + data.goodsId;
                    getMiaoshaResult($("#goodsId").val());
                } else {
                    layer.msg(json.msg);
                }
            },
            error: function () {
                layer.msg("客户端请求有误");
            }
        });

    }

    function getMiaoshaResult(goodsId) {
        g_showLoading();
        $.ajax({
            url: "/miaosha/result",
            type: "GET",
            data: {
                goodsId: $("#goodsId").val(),
            },
            success: function (json) {
                if (json.code == 0) {
                    var result = json.data;
                    if (result < 0) {
                        layer.msg("对不起，秒杀失败");
                    } else if (result == 0) {//继续轮询
                        setTimeout(function () {
                            getMiaoshaResult(goodsId);
                        }, 200);
                    } else {
                        layer.confirm("恭喜你，秒杀成功！查看订单？", {btn: ["确定", "取消"]},
                            function () {
                                window.location.href = "/miaosha_detail/" + result;
                            },
                            function () {
                                layer.closeAll();
                            });
                    }
                } else {
                    layer.msg(data.msg);
                }
            },
            error: function () {
                layer.msg("客户端请求有误");
            }
        });
    }


    // function getDetail(){
    //   var goodsId = g_getQueryString("goodsId");
    //   $.ajax({
    //     url:"/goods/detail/"+goodsId,
    //     type:"GET",
    //     success:function(data){
    //       if(data.code == 0){
    //         render(data.data);
    //       }else{
    //         layer.msg(data.msg);
    //       }
    //     },
    //     error:function(){
    //       layer.msg("客户端请求有误");
    //     }
    //   });
    // }
    $(function () {
        countDown();
        // getDetail();
    });


    function countDown() {
        var remainSeconds = $("#remainSeconds").val();
        var timeout;
        if (remainSeconds > 0) {//秒杀还没开始，倒计时
            $("#buyButton").attr("disabled", true);
            $("#miaoshaTip").html("秒杀倒计时：" + remainSeconds + "秒");
            timeout = setTimeout(function () {
                $("#countDown").text(remainSeconds - 1);
                $("#remainSeconds").val(remainSeconds - 1);
                countDown();
            }, 1000);
        } else if (remainSeconds == 0) {//秒杀进行中
            $("#buyButton").attr("disabled", false);
            if (timeout) {
                clearTimeout(timeout);
            }
            $("#miaoshaTip").html("秒杀进行中");
        } else {//秒杀已经结束
            $("#buyButton").attr("disabled", true);
            $("#miaoshaTip").html("秒杀已经结束");
        }
    }

</script>
</html>

