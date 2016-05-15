<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
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
		            <input class="weui_input" type="hidden" id="username" value="<c:out value="${email}"/>"><c:out value="${email}"/>
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
		    <div class="weui_cell">
		        <div class="weui_cell_hd">
		            <label class="weui_label">确认密码</label>
		        </div>
		        <div class="weui_cell_bd weui_cell_primary">
		            <input class="weui_input" type="password2" id="password">
		        </div>
		    </div>
		</div>
		<a href="javascript:;" class="weui_btn weui_btn_primary" id="okbtn">设置密码</a>
	</div>
</body>
<script type="text/javascript">
$('#okbtn').click(function() {
	if($("#password").val() != $("#password2").val()){
		alert("请确认两次输入的密码相同.");
		return;
	}
	
    var param = {
        username : $("#username").val(),
        password : $("#password").val()
    };
    $.ajax({ 
        type: "post", 
        url: "<%=request.getContextPath()%>/setPassword.json", 
        data: param, 
        dataType: "json", 
        success: function(data) { 
            if(data.success == false){
                alert(data.errorMsg);
            }else{
                //登录成功
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