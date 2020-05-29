<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@include file="/WEB-INF/include-head.jsp" %>
    <title>尚筹网</title>
    <link href="ztree/zTreeStyle.css" rel="stylesheet">
    <script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" src="crowd/my-menu.js"></script>

    <script type="text/javascript">
        /*     $(function () {
                 // 1. 准备生成树形结构的json 数据
                 $.ajax({
                     "url": "menu/get/whole/tree.json",
                     "type": "post",
                     "dataType": "json",
                     "success": function (response) {
                         const result = response.result;
                         if (result === "SUCCESS") {
                             // 2. 创建json对象来存储zTree所做的设置
                             const setting = {
                                 "view": {
                                     "addDiyDom": myAddDiyDom
                                 },
                                 "data": {
                                     "key": {"url": "maomi"}
                                 }
                             };
                             const zNodes = response.data;
                             // 3. 初始化树形结构
                             $.fn.zTree.init($("#treeDemo"), setting, zNodes)
                         }
                         if (result === "FAILED") {
                             layer.msg(response.message);
                         }
                     }
                 });
             });*/

        $(function () {
            // 调用专门封装好的函数初始化树形结构
            generateTree();

            // 给添加子节点按钮绑定单击响应函数
            $("#treeDemo").on("click", ".addBtn", function () {
                
                // 将当前节点的 id，作为新节点的 pid 保存到全局变量
                window.pid = this.id;
                // 打开模态框
                $("#menuAddModal").modal("show");
                return false;
            });

            $("#menuSaveBtn").click(function () {
                // 收集表单项中用户输入的数据
                const name = $.trim($("#menuAddModal [name=name]").val());
                const url = $.trim($("#menuAddModal [name=url]").val());
                // 单选按钮要定位到“被选中”的那一个
                const icon = $("#menuAddModal [name=icon]:checked").val();
                // 发送 Ajax 请求
                $.ajax({
                    "url": "menu/save.json",
                    "type": "post",
                    "data": {
                        "pid": window.pid,
                        "name": name,
                        "url": url,
                        "icon": icon
                    },
                    "dataType": "json",
                    "success": function (response) {
                        const result = response.result;
                        if (result === "SUCCESS") {
                            layer.msg("操作成功！");
                            // 重新加载树形结构，注意：要在确认服务器端完成保存操作后再刷新
                            // 否则有可能刷新不到最新的数据，因为这里是异步的
                            generateTree();
                        }
                        if (result === "FAILED") {
                            layer.msg("操作失败！" + response.message);
                        }
                    },
                    "error": function (response) {
                        layer.msg(response.status + " " + response.statusText);
                    }
                });
                // 关闭模态框
                $("#menuAddModal").modal("hide");
                // 清空表单
                // jQuery 对象调用 click()函数，里面不传任何参数，相当于用户点击了一下
                $("#menuResetBtn").click();
            });
        });



    </script>
</head>
<body>

<%@include file="include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <%--ztree动态生成节点所依附的静态节点--%>
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/modal-menu-add.jsp"%>
</body>
</html>