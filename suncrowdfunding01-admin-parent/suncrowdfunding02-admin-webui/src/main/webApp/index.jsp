<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>$Title$</title>
</head>
<base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>
<body>
<script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="layer/layer.js"></script>
<script type="text/javascript">
    $(function () {
        $("#btn").click(function () {

            const admin = {
                "id": 1234567,
                "logAcct": "ajaxTest",
                "userPswd": "123456789",
                "userName": "ajaxTest",
                "email": "123@123.com"
            };
            const request = JSON.stringify(admin);
            $.ajax({
                "url": "send/ajaxTest.json",
                "type": "post",
                "contentType": "application/json;charset=UTF-8",
                "data": request,
                "dataType": "json",
                "success": function (response) {
                    console.log(response);
                },
                "error": function (response) {
                    console.log(response);
                }
            });
        });
        $("#btn1").click(function () {
            layer.msg("aassads");
        })
    })
</script>
<a href="test/ssm.html">
    测试ssm
</a>
<br/>
<button id="btn"> AjaxTest</button>

<br/>
<br>
<button id="btn1"> layerTest</button>
</body>
</html>
