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
        const checkboxTd = "<td><input type='checkbox'></td>";
        const roleNameTd = "<td>" + roleName + "</td>";
        const checkBtn = "<button type='button' class='btn btn-success btn-xs'><i class=' glyphicon glyphicon-check'></i></button>";
        const pencilBtn = "<button type='button' class='btn btn-primary btn-xs'><i class=' glyphicon glyphicon-pencil'></i></button>";
        const removeBtn = "<button type='button' class='btn btn-danger btn-xs'><i class=' glyphicon glyphicon-remove'></i></button>";

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
}

//
function paginationCallBack(pageIndex, JQuery) {

}