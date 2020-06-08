<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@include file="/WEB-INF/include-head.jsp" %>
    <link href="ztree/zTreeStyle.css" rel="stylesheet">
    <script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
    <title>尚筹网</title>
</head>
<link rel="stylesheet" href="css/pagination.css">
<script src="jquery/jquery.pagination.js"></script>
<script type="text/javascript" src="crowd/my-role.js"></script>
<script type="text/javascript">
    $(function () {
        // 1. 为分页数据操作准备数据
        window.pageNum = 1;
        window.pageSize = 5;
        window.keyword = "";
        // 2. 调用执行分页的函数
        generatePage();

        // 3. 查询按钮绑定查询函数
        $("#searchBtn").click(function () {

            //获取关键词，给赋值给对应的全局变量
            window.keyword = $("#keywordInput").val();
            // 启用分页函数刷新页面
            generatePage();
        });

        // 4. 点击新增按钮打开模态框
        $("#showAddModalBtn").click(function () {
            $("#addModal").modal("show");
        });

        // 5. 给新增模态框中的按钮绑定单击响应函数
        $("#saveRoleBtn").click(function () {
            // 获取用户在文本框中输入的角色名称
            const roleName = $.trim($("#addModal [name=roleName]").val());
            // 发送ajax请求
            $.ajax({
                "url": "role/save.json",
                "type": "post",
                "data": {"name": roleName},
                "dataType": "JSON",
                "success": function (response) {
                    const result = response.result;

                    if (result === "SUCCESS") {
                        layer.msg("操作成功");
                        // 关闭模态框
                        $("#addModal").modal("hide");
                        // 清理模态框数据
                        $("#addModal [name=roleName]").val("");
                        //定位到最后一页
                        window.pageNum = 99999999;
                        //重新加载分页
                        generatePage();
                    }
                    if (result === "FAILED") {
                        layer.msg("操作失败");
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
        });

        //给页面上的编辑绑定函数打开模态框
        //先找到动态生成的元素附着的静态元素
        $("#rolePageBody").on("click", ".pencilBtn", function () {
            //打开模态框
            $("#editModal").modal("show");

            // 获取表格中当前的id数据
            let roleName = $(this).parent().prev().text();

            // 获取当前角色的id
            window.roleId = this.id;

            // 使用roleName的值设置模态框中的文本框
            $("#editModal [name=roleName]").val(roleName);

        });

        // 7. 更新按钮绑定单机响应函数
        $("#updateRoleBtn").click(function () {

            //从文本框中获取新的角色名称
            let roleName = $("#editModal [name=roleName]").val();
            $.ajax({
                "url": "role/update.json",
                "data": {
                    "id": window.roleId,
                    "name": roleName
                },
                "dataType": "JSON",
                "success": function (response) {
                    const result = response.result;

                    if (result === "SUCCESS") {
                        layer.msg("操作成功");
                        // 清理模态框数据
                        $("#editModal [name=roleName]").val("");
                        //重新加载分页
                        generatePage();
                    }
                    if (result === "FAILED") {
                        layer.msg("操作失败");
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            // 关闭模态框
            $("#editModal").modal("hide");
        });

        //8. 点击确认模态框中的确认按钮删除
        $("#removeRoleBtn").click(function () {
            const roleIdArray = window.roleIdArray;
            JSON.stringify(roleIdArray);

            $.ajax({
                "url": "role/remove/by/role/id/array.json",
                "type": "post",
                "data": JSON.stringify(roleIdArray),
                "contentType": "application/json;charset=UTF-8",
                "dataType": "JSON",
                "success": function (response) {
                    const result = response.result;

                    if (result === "SUCCESS") {
                        layer.msg("操作成功");
                        // 清理模态框数据
                        $("#roleNameSpan").empty();
                        //重新加载分页
                        generatePage();
                    }
                    if (result === "FAILED") {
                        layer.msg("操作失败");
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            $("#confirmModal").modal("hide");
        });

        // 9. 给单条删除按钮绑定单击响应函数
        $("#rolePageBody").on("click", ".removeBtn", function () {
            //创建role对象
            const roleArray = [{
                roleId: this.id,
                roleName: $(this).parent().prev().text()
            }];

            // 调用专门的函数打开模态框
            showConfirmModal(roleArray);
        });

        // 10. 给总checkbox绑定单击响应函数
        $("#summaryBox").click(function () {
            // 获取自身状态
            const currentStatus = this.checked;
            // 将自身状态同步到其他多选框
            $(".itemBox").prop("checked", currentStatus);
        });

        // 11. 全不选的反向操作
        $("#rolePageBody").on("click", ".itemBox", function () {

            // 获取当前已经选中的itemBox 数量
            const checkedBoxCount = $(".itemBox:checked").length;

            // 获取全部.itemBox 的数量
            const totalBoxCount = $(".itemBox").length;

            // 使用二者的比较结果设置总的checkBox
            $("#summaryBox").prop("checked", checkedBoxCount === totalBoxCount);
        });

        // 12. 给批量删除绑定删除操作
        $("#batchRemoveBtn").click(function () {

            let roleArray = [];

            // 遍历当前被勾选的 itemBox
            $(".itemBox:checked").each(function () {
                // 使用this引用当前遍历得到的多选框
                const roleId = this.id;

                // 通过Dom操作获取角色名称
                const roleName = $(this).parent().next().text();
                roleArray.push({
                    "roleId": roleId,
                    "roleName":roleName
                });
            });

            if(roleArray.length===0){
                layer.msg("当前没有选中的删除项目");
                return;
            }
            // 调用专门的函数打开模态框
            showConfirmModal(roleArray);
        });

        // 13 给分配权限按钮绑定单击响应事件
        $("#rolePageBody").on("click",".checkBtn",function () {
            // 把当前角色id存入变量
            window.roleId = this.id;
            $("#assignModal").modal("show");
            // 在模态框中加载Auth 的树形结构
            fillAuthTree();
        })
    })
</script>
<body>

<%@include file="include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" name="keywordInput" id="keywordInput"
                                       type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" id="searchBtn" class="btn btn-warning"><i
                                class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button type="button" id="batchRemoveBtn" class="btn btn-danger"
                            style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button type="button" class="btn btn-primary" style="float:right;"
                            id="showAddModalBtn"><i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody">
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"></div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
<%@include file="/WEB-INF/modal-role-add.jsp" %>
<%@include file="/WEB-INF/modal-role-edit.jsp" %>
<%@include file="/WEB-INF/modal-role-confirm.jsp" %>
<%@include file="/WEB-INF/modal-role-assign-auth.jsp"%>
</body>
</html>