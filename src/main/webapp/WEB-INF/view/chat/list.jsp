<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, minimal-ui" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/weui.min.css" />
<script src="<%=request.getContextPath()%>/static/js/jquery-1.8.1.min.js"></script>
<script>
	function changeHeight(){
		var h=window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
		var w=window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
		$("#container").css("height", h-50);
		$(".weui_media_box").css("width", w-60);
	}
	
	window.onresize = function(){
		changeHeight();
	}
	
	$(document).ready(function(){
		changeHeight();
	});
</script>
<style>
body {
	background: #dddddd;
	font-size: 11pt;
	padding: 0px;
	margin: 0px;
}
</style>
</head>
<body>
	<div style="width:100％;height:40px;color:white;background-color:black;text-align:center;padding:10px 0 0 10px;">
		<span style="margin-left:30px;">Cunle.me</span>
		<a href="<%=request.getContextPath()%>/logout.html" class="weui_btn weui_btn_mini weui_btn_default" style="background-color:black;color:white;float:right;">登出</a>
	</div>
	<div id="container" class="weui_panel weui_panel_access" style="width:100%;overflow:auto;-webkit-overflow-scrolling: touch;">
			<c:forEach items="${chats}" var="chat">
				<a href="<%=request.getContextPath()%>/chat/<c:out value="${chat.id}"/>.html" class="weui_media_box weui_media_appmsg">
					<div class="weui_media_hd">
						<img class="weui_media_appmsg_thumb" src="<%=request.getContextPath()%>/static/images/<c:out value="${chat.id}"/>.jpg" alt="">
					</div>
					<div class="weui_media_bd">
						<h4 class="weui_media_title"><c:out value="${chat.chatOwner}" /></h4>
						<p class="weui_media_desc">与<c:out value="${chat.chatOwner}"/>的聊天记录</p>
					</div>
				</a>
			</c:forEach>
	</div>
</body>
<script type="text/javascript">
document.documentElement.webkitRequestFullScreen();
</script>
</html>