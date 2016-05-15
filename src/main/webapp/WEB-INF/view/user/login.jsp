<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/weui.min.css" />
<script src="<%=request.getContextPath()%>/static/js/jquery-1.8.1.min.js"></script>
</head>
<body>
	<div style="width:100％;height:40px;background-color:black;color:white;text-align:center;padding-top:10px;">Cunle.me</div>
	<div class="container" style="padding:10px;">
		<div class="weui_msg">
		    <div class="weui_icon_area"><i class="weui_icon_msg weui_icon_warn"></i></div>
		    <div class="weui_text_area">
		        <h2 class="weui_msg_title">操作失败</h2>
		        <p class="weui_msg_desc">不正确的Token</p>
		    </div>
		</div>
		<a href="<%=request.getContextPath()%>/login.html">登录</a>
	</div>
</body>
</html>