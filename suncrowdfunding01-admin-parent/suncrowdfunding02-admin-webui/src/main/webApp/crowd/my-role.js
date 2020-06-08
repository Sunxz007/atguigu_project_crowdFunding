// 声明专门的函数用来在分配Auth的模态框中显示Auth的树形结构
function fillAuthTree() {
    // 1. 发送Ajax请求查询Auth数据
    const ajaxReturn = $.ajax({
        "url": "assign/get/all/auth.json",
        "type": "post",
        "dateType": "json",
        "async": false
    });

    if (ajaxReturn.status !== 200) {
        layer.msg("请求出错！响应状态码是：" + ajaxReturn.status + "说明是：" + ajaxReturn.statusText);
        return;
    }
    // 2. 从响应结构中取数据中取得Auth的Jason数据
    // 从服务器中取得的list不需要组装成树形结构，交给ZTree去组装
    const authList = ajaxReturn.responseJSON.data;

    //3. 准备ztree进行设置的Json对象
    const setting = {
        "data": {
            "simpleData": {
                // 开启简单JSON的功能
                "enable": true
            }
        }
    };
    // 4. 生成树形结构
    $.fn.zTree.init($("#treeDemo"), setting, authList);

    // 5. 查询已分配的Auth的id组成的数组

    
    //6. 根据authIdArray把树形结构勾对应的节点勾选上

}


//执行分页，生成分页效果，任何时候调用这个函数都会重新加载
function generatePage() {
    // 1. 获取分页数据
    const pageInfo = getPageInfoRemote();

    // 2. 填充表格
    fillTableBody(pageInfo);

}

// 远程访问服务器端程序获取Pageinfo数据
function getPageInfoRemote() {

    const ajaxResult = $.ajax({
        "url": "role/get/page/info.json",
        "type": "post",
        "data": {
            "pageNum": window.pageNum,
            "pageSize": window.pageSize,
            "keyword": window.keyword
        },
        "async": false,
        "dataType": "json"
    });
    console.log(ajaxResult);
    // 判断当前响应码是否为200，说明发生了错误或其他意外情况，显示提示信息，让当前函数停止执行；
    let status = ajaxResult.status;
    if (status !== 200) {
        layer.msg("响应失败！响应状态码=" + status + " 说明信息=" + ajaxResult.statusText);
        return null;
    }
    // 如果响应状态码为200，说明响应处理成功，获取pageInfo
    let responseJSON = ajaxResult.responseJSON;

    // 从返回的json数据串数据中返回result
    let result = responseJSON.result;

    // 判断result是否成功
    if (result === "FAILED") {
        layer.msg(responseJSON.message);
    }
    // 确认成功后获取pageInfo
    return responseJSON.data;
}

//填充表格
function fillTableBody(pageInfo) {
    //清除tbody 中的旧内容
    const pageBody = $("#rolePageBody");
    pageBody.empty();
    //清空导航条，当页面无数据时不显示缓存的页码条
    $("#Pagination").empty();

    //判断pageInfo 对象是否有效
    if (pageInfo === null || pageInfo === undefined || pageInfo.list === null || pageInfo.list.length === 0) {
        pageBody.append("<tr><td colspan='4'> 抱歉，没有查询到你要搜索的数据！</td></tr>");
        return;
    }
    // 使用pageInfo 的list 属性填充tbody
    for (let i = 0; i < pageInfo.list.length; i++) {
        const role = pageInfo.list[i];
        const roleId = role.id;
        const roleName = role.name;

        const numberTd = "<td>" + (i + 1) + "</td>";
        const checkboxTd = "<td><input id=" + roleId + " class='itemBox' type='checkbox'></td>";
        const roleNameTd = "<td>" + roleName + "</td>";
        const checkBtn = "<button id=" + roleId + "type='button' class='btn btn-success btn-xs checkBtn'><i class=' glyphicon glyphicon-check'></i></button>";
        const pencilBtn = "<button id=" + roleId + " type='button'  class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";
        const removeBtn = "<button  id=" + roleId + " type='button' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";

        const buttonTd = "<td>" + checkBtn + " " + pencilBtn + " " + removeBtn + "</td>";
        const tr = "<tr>" + numberTd + checkboxTd + roleNameTd + buttonTd + "</tr>";
        pageBody.append(tr);
    }
    // 生成分页导航条
    generateNavigator(pageInfo);
}

// 生成分页导航条码
function generateNavigator(pageInfo) {
    // 获取总记录数

    const totalRecord = pageInfo.total;

    //声明相关属性
    const properties = {
        "num_edge_entries": 3,    // 边缘页数
        "num_display_entries": 5, // 主体页数
        "callback": pageinationCallBack,
        "items_per_page": pageInfo.pageSize,
        "current_page": pageInfo.pageNum - 1,
        "prev_text": "上一页",
        "next_text": "下一页"
    };
    // 调用pagination()函数
    $("#Pagination").pagination(totalRecord, properties);
}

//调用回调函数
function pageinationCallBack(pageIndex, JQuery) {

    //修改window 对象的pageNum 属性
    window.pageNum = pageIndex + 1;

    //调用分页函数
    generatePage();

    return false;
}


// 显示确认模态框
function showConfirmModal(roleArray) {
    //打开模态框
    $("#confirmModal").modal("show");
    const roleNameSpan=$("#roleNameSpan");
    // 清除旧数据
    roleNameSpan.empty();

    //在全局中创建一个存储删除id的数组
    window.roleIdArray = [];

    //遍历roleArray数组
    for (let i = 0; i <roleArray.length ; i++) {
        const role = roleArray[i];
        const roleName= role.roleName;
        roleNameSpan.append(roleName+"</br>");
        window.roleIdArray.push(role.roleId);
    }
}