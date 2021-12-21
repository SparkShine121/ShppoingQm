<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
</head>
<link rel="stylesheet" href="/resources/layui/css/layui.css">
<link rel="stylesheet" href="/jsp/css/bootstrap.css">

<script src="/resources/layui/layui.all.js"></script>
<script src="/resources/sweet/sweetalert2.all.min.js"></script>
<script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.js"></script>
<script src="/jsp/js/bootstrap.min.js"></script>
<style>
    td {
        height: 30px;
    }
</style>
<body>
<table class="table table-bordered" style="margin-top: 20px">
    <tr style="text-align: center;vertical-align: middle">
        <td>#</td>
        <td style="font-size: 18px">商铺名称</td>
        <td style="font-size: 18px">商铺地址</td>
        <td style="font-size: 18px">手机号码</td>
        <td style="font-size: 18px">商铺类别</td>
        <td style="font-size: 18px">详情</td>
        <td style="font-size: 18px">操作</td>
    </tr>

    <c:forEach items="${shopList}" var="shop" varStatus="idxStatus">

        <tr style="text-align: center;vertical-align: middle">
            <td style="font-size: 15px"><c:out value='${idxStatus.index+1}'/></td>
            <td style="font-size: 15px"><span name="shopName">${shop.shopName}</span></td>
            <td style="font-size: 15px">${shop.shopAddr}</td>
            <td style="font-size: 15px">${shop.shopPhone}</td>
            <td style="font-size: 15px">${shop.shopType}</td>
            <td style="font-size: 15px;width: 300px">${shop.shopDetail}</td>
                <%--            <td style="font-size: 15px"><span  onclick="urlBtn(${shop.id})">同意</span></td>--%>
                <%--        <td style="font-size: 15px"><span onclick="urlBtnBTY(${shop.id})">不同意</span></td>--%>
            <td>
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal"
                        data-whatever="${shop.shopName}">审批
                </button>
            </td>
        </tr>

    </c:forEach>

</table>

</form>

<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="exampleModalLabel">New message</h4>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="recipient-name" class="control-label">Recipient:</label>
                        <input type="text" class="form-control" id="recipient-name">
                    </div>
                    <div class="form-group">
                        <label for="message-text" class="control-label">Message:</label>
                        <textarea class="form-control" id="message-text"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Send message</button>
            </div>
        </div>
    </div>
</div>
<script>
    $('#exampleModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var recipient = button.data('whatever') // Extract info from data-* attributes
        var modal = $(this)
        modal.find('.modal-title').text('New message to ' + recipient)
        modal.find('.modal-body input').val(recipient)
    })
</script>
<script>
    function urlBtn(id) {
        swal({
            type: 'success',
            html: "<h2>审批已通过</h2>",
            confirmButtonText: "确定",
        }).then(function (result) {
            window.location.href = "/ptAdmin/audit/tongyi/" + id;
        });
    }

    function urlBtnBTY(id) {
        swal({
            type: 'error',
            html: "<h2>审批已驳回</h2>",

            confirmButtonColor: "#DD6B55",

            confirmButtonText: "确认",

        }).then(function (result) {
            window.location.href = "/ptAdmin/audit/butongyi/" + id;
        });
    }
</script>
</body>
</html>
