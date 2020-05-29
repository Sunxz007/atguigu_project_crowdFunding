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
</body>
</html>