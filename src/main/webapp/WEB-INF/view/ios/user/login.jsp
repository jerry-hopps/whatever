<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html class="with-statusbar-overlay">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style"
	content="black-translucent">
<title>Memories</title>
<script src="<%=request.getContextPath()%>/static/js/jquery-1.8.1.min.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/lib/framework7/css/framework7.ios.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/lib/framework7/css/framework7.ios.colors.min.css">
<!-- Favicon-->
<link href="static/img/icon-57.png" rel="shortcut icon">
<!-- iOS 7 iPad (retina) -->
<link href="static/img/icon-152.png" sizes="152x152"
	rel="apple-touch-icon">
<!-- iOS 6 iPad (retina) -->
<link href="static/img/icon-144.png" sizes="144x144"
	rel="apple-touch-icon">
<!-- iOS 7 iPhone (retina) -->
<link href="static/img/icon-120.png" sizes="120x120"
	rel="apple-touch-icon">
<!-- iOS 6 iPhone (retina) -->
<link href="static/img/icon-114.png" sizes="114x114"
	rel="apple-touch-icon">
<!-- iOS 7 iPad -->
<link href="static/img/icon-76.png" sizes="76x76" rel="apple-touch-icon">
<!-- iOS 6 iPad -->
<link href="static/img/icon-72.png" sizes="72x72" rel="apple-touch-icon">
<!-- iOS 6 iPhone -->
<link href="static/img/icon-57.png" sizes="57x57" rel="apple-touch-icon">
</head>
<body>
	<!-- Statusbar overlay-->
	<div class="statusbar-overlay"></div>
	<!-- Views-->
	<div class="views">
		<div class="view view-main navbar-fixed">
			<div class="navbar">
				<div class="navbar-inner">
					<div class="left"></div>
					<div class="center sliding">Memories</div>
					<div class="right"></div>
				</div>
			</div>
			<div class="pages">
				<div data-page="index" class="page">
					<div class="page-content">
						<div class="list-block">
							<ul>
								<!-- Text inputs -->
								<li>
									<div class="item-content">
										<div class="item-media">
											<i class="icon icon-form-email"></i>
										</div>
										<div class="item-inner">
											<div class="item-title label">E-mail</div>
											<div class="item-input">
												<input id="openid" type=hidden value="<c:out value="${openid}"/>">
												<input id="username" type="email" placeholder="E-mail">
											</div>
										</div>
									</div>
								</li>
								<li>
									<div class="item-content">
										<div class="item-media">
											<i class="icon icon-form-password"></i>
										</div>
										<div class="item-inner">
											<div class="item-title label">Password</div>
											<div class="item-input">
												<input id="password" type="password" placeholder="Password">
											</div>
										</div>
									</div>
								</li>
							</ul>
						</div>
						<div class="row">
							<div class="col-100">
								<a id="loginbtn" href="#" class="button button-big button-submit active">Submit</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
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
	<!-- Path to Framework7 Library JS-->
	<script type="text/javascript" src="<%=request.getContextPath()%>/lib/framework7/js/framework7.min.js"></script>
</body>
</html>