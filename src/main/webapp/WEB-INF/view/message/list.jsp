<!DOCTYPE>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>与<c:out value="${chat.chatOwner}" />的聊天记录</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/weui.min.css" />
<script src="<%=request.getContextPath()%>/static/js/jquery-1.8.1.min.js"></script>
<script>
	function changeHeight(){
		var h=window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
		$("#container").css("height", h-50);
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

.name-send {
	width: 200px;
	height: 20px;
	color: black;
	padding-bottom: 5px;
	text-align:right;
	margin: 5px 5px 0 auto;
	font-size: 10pt;
}

.avatar-send {
	width: 32px;
	height: 32px;
	color: black;
	text-align:right;
	margin: 0px 5px 0 auto;
	font-size: 10pt;
	float:right;
}

.send {
	background: #09BB07;
	border-radius: 5px; /* 圆角 */
	background: #09BB07;
	padding: 8px;
	margin: 0 0 0 auto;
	float: right;
	max-width:200px;
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

.name-receive {
	width: 200px;
	height: 20px;
	padding-top: 5px;
	padding-bottom: 5px;
	margin-top: 0px;
	color: black;
	text-left;
	margin: 5px auto 0 5px;
	font-size: 10pt;
}

.avatar-receive {
	width: 32px;
	height: 32px;
	margin-top: 0px;
	color: black;
	margin: 5px auto 0 5px;
	font-size: 10pt;
	float:left;
}

.receive {
	background: white;
	border-radius: 5px; /* 圆角 */
	margin: 0 auto 0 0;
	padding: 8px;
	float: left;
	max-width:200px;
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
<!-- 
	<div style="width:100％;height:40px;color:white;background-color:black;text-align:center;padding:10px 0 0 10px;">
		<a href="<%=request.getContextPath()%>/chat/list.html" class="weui_btn weui_btn_mini weui_btn_default" style="background-color:black;color:white;float:left;">返回</a>
		<span style="margin-left:-30px;">与<c:out value="${chat.chatOwner}" />的聊天记录</span>
	</div>
 -->
	<div id="container" style="overflow:auto;-webkit-overflow-scrolling: touch;">
		<c:forEach items="${messages}" var="message">
			<c:choose>
			    <c:when test="${message.sender == receiver}">
			       <c:set var="direction" scope="request" value="send"/>
			    </c:when>
			    <c:otherwise>
			       <c:set var="direction" scope="request" value="receive"/>
			    </c:otherwise>
			</c:choose>
			<div class="name-<c:out value="${direction}"/>"><c:out value="${message.sender}" /></div>
			<div style="width: 100%; margin-top:0px;">
				<div class="avatar-<c:out value="${direction}"/>">
				<c:choose>
					<c:when test="${message.sender == receiver}">
						<img src="<%=request.getContextPath()%>/static/images/receiver-<c:out value="${receiver_id}"/>.jpg" style="width:32px;height:32px;">
					</c:when>
				    <c:otherwise>
				    	<img src="<%=request.getContextPath()%>/static/images/<c:out value="${chat.id}"/>.jpg" style="width:32px;height:32px;">
				    </c:otherwise>
			    </c:choose>
				</div>
				<div class="arrow-<c:out value="${direction}"/>"></div>
				<div class="<c:out value="${direction}"/>">
					<c:choose>
						<c:when test="${message.type == 0}">
							<c:out value="${message.content}" />
						</c:when>
						<c:when test="${message.type == 1}">
							<img src="http://localhost:8080/whatever/static/images/<c:out value="${attachments[message.content]}" />" width="100px" height="100px"/>
						</c:when>
						<c:otherwise>
							<a target="_BLANK" href="<c:out value="${fn:substringAfter(fn:replace(fn:replace(message.content, '[', ''), ']', ''), ':')}" />"><c:out value="${fn:split(fn:replace(fn:replace(message.content, '[', ''), ']', ''), ': ')[0]}" /></a>
						</c:otherwise>
					</c:choose>
				</div>
				<div style="clear:both"></div>
			</div>
			<div style="clear:both"></div>
		</c:forEach>
	</div>
</body>
<script type="text/javascript">
	
</script>
</html>