<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/weui.min.css" />
<script src="<%=request.getContextPath()%>/static/js/jquery-1.8.1.min.js"></script>
</head>
<body>
	<div style="width:100％;height:40px;background-color:black;color:white;text-align:center;padding-top:10px;">Cunle.me</div>
	<div class="container" style="padding:10px;">
		<div class="weui_cells weui_cells_form">
		    <div class="weui_cell">
		        <div class="weui_cell_hd">
		            <label class="weui_label">电子邮件</label>
		        </div>
		        <div class="weui_cell_bd weui_cell_primary">
		            <input class="weui_input" type="email" id="username" placeholder="请输入电子邮件">
		        </div>
		    </div>
		    <div class="weui_cell">
		        <div class="weui_cell_hd">
		            <label class="weui_label">密码</label>
		        </div>
		        <div class="weui_cell_bd weui_cell_primary">
		            <input class="weui_input" type="password" id="password">
		        </div>
		    </div>
		</div>
		<a href="javascript:;" class="weui_btn weui_btn_primary" id="loginbtn">登录</a>
	</div>
</body>
<script type="text/javascript">
$('#loginbtn').click(function() {
    var param = {
        username : $("#username").val(),
        password : $("#password").val()
    };
    $.ajax({ 
        type: "post", 
        url: "<%=request.getContextPath()%>/checkLogin.json", 
        data: param, 
        dataType: "json", 
        success: function(data) { 
            if(data.success == false){
                alert(data.errorMsg);
            }else{
                window.location.href = "<%=request.getContextPath()%>/chat/list.html";
            }
        },
        error: function(data) { 
            alert("调用失败...."+data.responseText); 
        }
    });
});
</script>
</html>