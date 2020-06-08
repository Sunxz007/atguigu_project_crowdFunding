<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@include file="/WEB-INF/include-head.jsp" %>
    <title>尚筹网</title>
    <script type="text/javascript">
        $(function () {
            $("#toRightBtn").click(function () {
                $("select:eq(0)>option:selected").appendTo($("select:eq(1)"));
            });
            $("#toLeftBtn").click(function () {
                $("select:eq(1)>option:selected").appendTo($("select:eq(0)"));
            })

            $("#submitBtn").click(function () {
                // 在提交前选中全部要分配的角色的option
                $("select:eq(1)>option").prop("selected", "selected");
            })
        })

    </script>
</head>
<body>

<%@include file="include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form role="form" class="form-inline" action="assign/do/role/assign.html" method="post">
                        <input type="hidden" name="adminId" value="${param.adminId}">
                        <input type="hidden" name="pageNum" value="${param.pageNum}">
                        <input type="hidden" name="keyword" value="${param.keyword}">
                        <div class="form-group">
                            <label for="unAssignedRole">未分配角色列表</label><br>
                            <select class="form-control" multiple="" id="unAssignedRole" size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach items="${unAssignedRole}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li class="btn btn-default glyphicon glyphicon-chevron-right" id="toRightBtn"> </li>
                                <br>
                                <li class="btn btn-default glyphicon glyphicon-chevron-left" id="toLeftBtn"
                                    style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label for="assignedRole">已分配角色列表</label><br>
                            <select class="form-control" multiple="multiple" id="assignedRole" size="10" style="width:100px;overflow-y:auto;" name="roleIdList">
                                <c:forEach items="${assignedRole}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button id="submitBtn" type="submit" class="btn btn-lg btn-success" style="width: 150px" > 保存</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>