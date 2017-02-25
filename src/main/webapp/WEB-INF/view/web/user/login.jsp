<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Wecord</title>
    <link rel="stylesheet" href="/static/css/chat.css"/>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<script src="/static/lib/jquery-1.8.1.min.js"></script>
</head>
<body>
	<div class="container">
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<a class="navbar-brand" href="#">Wecord</a>
				</div>
			</div>
		</nav>
		<div class="row">
			<div class="col-md-8">
				<img src="/static/images/cover.jpg" style="width: 100%;"/>
			</div>
			<div class="col-md-4">
				<div class="panel panel-default">
					<div class="panel-heading">用户登陆</div>
					<div class="panel-body">
						<div class="form-group" style="display: none;">
							<label for="openid">OPENID</label>
							<input type="text" class="form-control" id="openid" value="<c:out value="${openid}"/>">
						</div>
						<div class="form-group">
							<label for="username">电子邮件</label>
							<input type="email" class="form-control" id="username" placeholder="请输入电子邮件">
						</div>
                        <div class="form-group">
                            <label for="password">密码</label>
                            <input type="password" class="form-control" id="password">
                        </div>
                        <div class="form-group">
                            <input type="checkbox" id="rememberMe" style="display: inline">  <label for="rememberMe"> 下次自动登陆</label>
                        </div>
						<button type="submit" id="loginbtn" class="btn btn-default">登陆</button>
					</div>
				</div>
                <div class="provider_signin">
                    <form id="fb_signin" action="<c:url value="/signin/facebook"/>" method="POST">
                        <button type="submit" class="submit">
                            <img src="/static/images/fblogin.png"/>
                        </button>
                    </form>
                </div>
			</div>
		</div>
		<hr/>
		<div class="row">
			<div class="col-md-12 text-center">

			</div>
		</div>
	</div>

    <footer class="footer">
        <div class="container">
            <p class="text-muted">Powered by www.ileqi.com.cn | copyright reserved © 1997-2016</p>
        </div>
    </footer>
</body>
<script type="text/javascript">
$('#loginbtn').click(function() {
    var param = {
    	openid: $("#openid").val(),
        username : $("#username").val(),
        password : $("#password").val(),
        rememberMe: $("#rememberMe").val()=="on"
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
                window.location.href = "<%=request.getContextPath()%>/index.html";
            }
        },
        error: function(data) { 
            alert("调用失败...."+data.responseText); 
        }
    });
});

$(document).ready(function(){
	$.ajax({
		type: "get",
		url: "<%=request.getContextPath()%>/loginStatus.json",
		dataType: "json",
		success: function(data){
			if(data.status == true){
				window.location.href = "<%=request.getContextPath()%>/index.html";
			}
			else{
                $.ajax({
                    type: "POST",
                    url: "<%=request.getContextPath()%>/autologin.json",
                    dataType: "json",
                    success: function(data){
                        if(data.success == true){
                            window.location.href = "<%=request.getContextPath()%>/index.html";
                        }
                    },
                    error: function(data) {
                        alert("调用失败...."+data.responseText);
                    }
                });
            }
		},
		error: function(data) {
			alert("调用失败...."+data.responseText);
		}
	});
});
</script>
</html>
