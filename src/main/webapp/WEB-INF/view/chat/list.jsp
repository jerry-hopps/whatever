<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/weui.min.css" />
<script src="<%=request.getContextPath()%>/static/js/jquery-1.8.1.min.js"></script>
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
	<div style="width:100％;height:40px;background-color:black;color:white;text-align:center;padding-top:10px;">Cunle.me</div>
	<div class="weui_panel weui_panel_access">
		<div class="weui_panel_bd">
			<c:forEach items="${chats}" var="chat">
				<a href="<%=request.getContextPath()%>/chat/<c:out value="${chat.id}"/>.html" class="weui_media_box weui_media_appmsg">
					<div class="weui_media_hd">
						<img class="weui_media_appmsg_thumb" src="<%=request.getContextPath()%>/static/images/<c:out value="${chat.id}"/>.jpg" alt="">
					</div>
					<div class="weui_media_bd">
						<h4 class="weui_media_title"><c:out value="${chat.chatOwner}" /></h4>
						<p class="weui_media_desc">与<c:out value="${chat.chatOwner}"/>的聊天记录</p>
					</div> <span class="weui_panel_ft"></span>
				</a>
			</c:forEach>
		</div>
	</div>
</body>
<script type="text/javascript">
	
</script>
</html>