<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/11/20
  Time: 13:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>订单详情</title>
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
    <div class="panel-heading">秒杀订单详情</div>
    <table class="table" id="goodslist">
        <tr>
            <td>商品名称</td>
            <td colspan="3" id="goodsName">${order.productName}</td>
        </tr>
        <tr>
            <td>商品图片</td>
            <td colspan="2"><img src="${order.productImage}" id="goodsImg" width="200" height="200"/></td>
        </tr>
        <tr>
            <td>订单价格</td>
            <td colspan="2" id="orderPrice">${order.miaoshaprice}</td>
        </tr>
        <tr>
            <td>下单时间</td>
            <td id="createDate" colspan="2">${order.createTime}</td>
        </tr>
        <tr>
            <td>订单状态</td>
            <td id="orderStatus">
                <c:if test="${order.status==0}">
                    未支付
                </c:if>
                <c:if test="${order.status==1}">
                    已支付
                </c:if>
            </td>
            <td>
                <button class="btn btn-primary btn-block" type="submit" id="payButton">立即支付</button>
            </td>
        </tr>
        <tr>
            <td>收货人</td>
            <td colspan="2">XXX 18812341234</td>
        </tr>
        <tr>
            <td>收货地址</td>
            <td colspan="2">北京市昌平区回龙观龙博一区</td>
        </tr>
    </table>
</div>
</body>
</html>
<%--<script>--%>
<%--    function render(detail){--%>
<%--        var goods = detail.goods;--%>
<%--        var order = detail.order;--%>
<%--        $("#goodsName").text(goods.goodsName);--%>
<%--        $("#goodsImg").attr("src", goods.goodsImg);--%>
<%--        $("#orderPrice").text(order.goodsPrice);--%>
<%--        $("#createDate").text(new Date(order.createDate).format("yyyy-MM-dd hh:mm:ss"));--%>
<%--        var status = "";--%>
<%--        if(order.status == 0){--%>
<%--            status = "未支付"--%>
<%--        }else if(order.status == 1){--%>
<%--            status = "待发货";--%>
<%--        }--%>
<%--        $("#orderStatus").text(status);--%>

<%--    }--%>

<%--    $(function(){--%>
<%--        getOrderDetail();--%>
<%--    })--%>

<%--    function getOrderDetail(){--%>
<%--        var orderId = g_getQueryString("orderId");--%>
<%--        $.ajax({--%>
<%--            url:"/miaossha_detail",--%>
<%--            type:"GET",--%>
<%--            data:{--%>
<%--                orderId:orderId--%>
<%--            },--%>
<%--            success:function(data){--%>
<%--                if(data.code == 0){--%>
<%--                    render(data.data);--%>
<%--                }else{--%>
<%--                    layer.msg(data.msg);--%>
<%--                }--%>
<%--            },--%>
<%--            error:function(){--%>
<%--                layer.msg("客户端请求有误");--%>
<%--            }--%>
<%--        });--%>
<%--    }--%>


<%--</script>--%>
