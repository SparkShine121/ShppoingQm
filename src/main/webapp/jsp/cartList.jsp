<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>购物车</title>
    <link rel="stylesheet" type="text/css" href="/jsp/css/bootstrap.css">
    <script type="text/javascript" src="/jsp/js/jquery-2.0.3.min.js"></script>
    <script type="text/javascript" src="/jsp/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="http://api.map.baidu.com/api?v=2.0&ak=4IU3oIAMpZhfWZsMu7xzqBBAf6vMHcoa"></script>
    <!-- 参数ak=""是开发者密钥，在百度地图开放平台官网可免费获取 -->
    <script type="text/javascript">
        $(function () {
            //给下订单按钮注册事件
            $("#orderbtn").click(function () {
                //限制用户的第二次点击
                $(this).attr("disabled", "disabled");
                //判断地址和电话是否为空，如果为空，提示用户，阻止提交
                if ($("#phone").val() == '') {
                    alert('请填写电话')
                    return;//直接让函数在此处结束
                }
                //提交表单
                $("#orderform").submit();


            })
            calc()
            $(".add").click(function () {
                var curval = $(this).next().val();
                $(this).next().val(parseInt(curval) + 1);
                calc()//计算总价
            })
            $(".reduce").click(function () {
                var curval = $(this).prev().val();
                if (curval == 0) {
                    $(this).prev().val(parseInt(curval));
                } else {
                    $(this).prev().val(parseInt(curval) - 1);
                }
                calc()
            })
            $(".numtxt").change(function () {
                calc()

            })

        })

        function calc() {
            var total = 0;
            //循环计算总价
            trs = $("tr:gt(0)")
            trs.each(function () {

                //当前tr
                var price = $(this).children("td").eq(2).html()
                var num = $(this).children("td").eq(3).children("input").eq(1).val()
                total += parseFloat(price) * parseInt(num);
                //显示总价
                $("#totalPrice").val(total)
            })
        }
    </script>

</head>
<body>

<div class="conatiner">
    <br>
    <div style="margin-bottom: 10px">
        <a href="findFoods" style="font-size: 25px;margin-right: 550px">放回商家首页</a><span
            style="font-size: 35px;margin-bottom: 20px">购物车</span>
    </div>
    <form action="/toOrder" method="post" id="orderform" class="form-inline">
        <table class="table table-hover">
            <tr>
                <th>&emsp;</th>
                <th style="vertical-align: middle !important;text-align: center;"><font size="5">菜品名称</font>
                </th>

                <th style="vertical-align: middle !important;text-align: center;"><font size="5">价格</font></th>

                <th style="vertical-align: middle !important;text-align: center;"><font size="5">操作</font></th>
            </tr>
            <c:forEach items="${cartList}" var="cart" varStatus="idxStatus">
                <tr>
                    <td><img src="${cart.productImage}" width="150px" height="100px" style="padding: 10px"/></td>
                    <td style="vertical-align: middle !important;text-align: center;font-size: 25px">${cart.productName}</td>

                    <td style="vertical-align: middle !important;text-align: center;font-size: 25px;color: red;">
                            ${cart.price}</td>

                    <td style="vertical-align: middle !important;text-align: center;font-size: 25px">
                        <input class="add btn btn-info" type="button" value="+">
                        <input value="1" class="numtxt form-control" name="saleNum" style="width:120px">
                        <input class="reduce btn btn-info" type="button" value="-">
                    </td>
                </tr>
            </c:forEach>

        </table>
        <div class="form-group">
            <label style="font-size: 20px">&emsp;总价：</label>
            <input value="0" id="totalPrice" class="form-control" name="totalPrice"
                   style="font-size: 20px;background-color:#fff;" readonly="readonly">
        </div>
        <div class="form-group"><label for="suggestId" style="font-size: 20px">&emsp;送餐详细地址：</label>
            <input name="address" id="suggestId" class="form-control" required="required" style="font-size: 20px">
        </div>
        <div class="form-group"><label for="phone" style="font-size: 20px">&emsp;电话：</label>
            <input name="phone" id="phone" class="form-control" required="required" style="font-size: 20px;">
        </div>

        &emsp;&emsp;&emsp;<input type="button" value="下订单" class="btn btn-success" id="orderbtn">
        <br>
        <br>
        <br>
    </form>
</div>

<script type="text/javascript">
    loadMapAutocomplete("suggestId");

    function loadMapAutocomplete(mySuggestId) {
        Ac = new BMap.Autocomplete( //建立一个自动完成的对象
            {
                "input": suggestId,
            });
    }
</script>
</body>
</html>