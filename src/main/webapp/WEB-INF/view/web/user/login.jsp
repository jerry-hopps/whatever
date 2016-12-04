<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="http://www.ileqi.com.cn/static/js/jquery-1.8.1.min.js"></script>
</head>
<body>
	<div class="container" style="padding:10px;">
		<div class="weui_cells weui_cells_form">
			<div class="weui_cell">
		        <div class="weui_cell_hd">
		            <label class="weui_label">openid</label>
		        </div>
		        <div class="weui_cell_bd weui_cell_primary">
		            <input class="weui_input" type="text" id="openid" value="<c:out value="${openid}"/>">
		        </div>
		    </div>
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
		<a class="weui_btn weui_btn_primary" id="loginbtn">登录</a>
	</div>
</body>
<script type="text/javascript">
$('#loginbtn').click(function() {
    var param = {
    	openid: $("#openid").val(),
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