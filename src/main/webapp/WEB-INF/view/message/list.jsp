<!DOCTYPE>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/weui.min.css" />
<script src="<%=request.getContextPath()%>/static/js/jquery-1.8.1.min.js"></script>
<script>
	function changeHeight(){
		var h=window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
		$("#container").css("height", h-70);
	}
	
	window.onresize = function(){
		changeHeight();
	}
	
	$(document).ready(function(){
		changeHeight();
	});
</script>
<style type="text/css">
body {
	background: #dddddd;
	font-size: 11pt;
	padding: 0px;
	margin: 0px;
}

.avatar-send {
	width: 40px;
	height: 32px;
	padding-top: 5px;
	float: right;
	margin-top: 0px;
	color: black;
	margin-right:10px;
	font-weight: bold;
}

.send {
	background: #09BB07;
	border-radius: 5px; /* 圆角 */
	background: #09BB07;
	padding: 8px;
	margin: 0 0 0 auto;
	float: right;
}

.arrow-send {
	width: 0;
	height: 0;
	font-size: 0;
	border: solid 8px;
	border-color: #dddddd #dddddd #dddddd #09BB07;
	float: right;
	margin-right: 5px;
	margin-top: 5px;
}

.avatar-receive {
	width: 60px;
	height: 32px;
	padding-top: 5px;
	float: left;
	margin-top: 0px;
	color: black;
	font-weight: bold;
	margin-left:10px;
}

.receive {
	background: white;
	border-radius: 5px; /* 圆角 */
	margin: 0 auto 0 0;
	padding: 8px;
	float: left;
}

.arrow-receive {
	float: left;
	margin-top: 5px;
	width: 0;
	height: 0;
	font-size: 0;
	border: solid 8px;
	border-color: #dddddd white #dddddd #dddddd;
}
</style>
</head>
<body style="overflow:hidden;">
	<div style="width:100％;height:40px;color:white;background-color:black;text-align:center;padding:10px 0 0 10px;">
	<a href="<%=request.getContextPath()%>/chat/list.html" class="weui_btn weui_btn_mini weui_btn_default" style="background-color:black;color:white;float:left;">返回</a>
	<span style="margin-left:-30px;">与<c:out value="${chat.chatOwner}" />的聊天记录</span>
	</div>
	<div id="container" style="overflow:auto;">
		<c:forEach items="${messages}" var="message">
			<div style="width: 100%; margin-top:10px;">
				<c:choose>
				    <c:when test="${message.sender == receiver}">
				       <c:set var="direction" scope="request" value="send"/>
				    </c:when>
				    <c:otherwise>
				       <c:set var="direction" scope="request" value="receive"/>
				    </c:otherwise>
				</c:choose>
				<div class="avatar-<c:out value="${direction}"/>"><c:out value="${message.sender}" /></div>
				<div class="arrow-<c:out value="${direction}"/>"></div>
				<div class="<c:out value="${direction}"/>"><c:out value="${message.content}" /></div>
				<div style="clear:both"></div>
			</div>
		</c:forEach>
	</div>
	
</body>
<script type="text/javascript">
	
</script>
</html>