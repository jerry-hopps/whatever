<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>Wecord</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
		<link rel="stylesheet" href="/static/css/chat.css"/>
		<script src="/static/lib/jquery-1.8.1.min.js"></script>
		<script src="/static/lib/mustache.min.js"></script>
		<script id="template" type="x-tmpl-mustache">
			{{#messages}}
				<div class="{{type}}"><div class="arrow"></div>{{&text}}</div>
			{{/messages}}
		</script>
	</head>
	<body>
		<div class="container">
			<nav class="navbar navbar-default">
				<div class="container-fluid">
					<!-- Brand and toggle get grouped for better mobile display -->
					<div class="navbar-header">
						<a class="btn btn-default navbar-btn" href="<%=request.getContextPath()%>/logout.html" role="button">登出</a>
						<a class="navbar-brand" href="#">Wecord</a>
					</div>
				</div>
			</nav>
			<div class="row">
				<div class="col-md-3">
					<div class="list-group">
					<c:forEach items="${chats}" var="chat">
						<a href="#" class="list-group-item chat-item" data="<c:out value="${chat.id}"/>">
							<img class="weui_media_appmsg_thumb" src="http://www.ileqi.com.cn/static/images/<c:out value="${chat.id}"/>.jpg" alt="" style="width: 32px; height: 32px;">
							<c:out value="${chat.chatOwner}" />
						</a>
					</c:forEach>
					</div>
				</div>
				<div class="col-md-9">
					<div class="panel panel-default">
						<div class="panel-heading">聊天记录</div>
						<div class="panel-body" id="messages">
						</div>
					</div>
				</div>
			</div>
		</div>
		<script language="JavaScript">
			var activeItem = null;

			$(".chat-item").click(function(){
				var chatId = $(this).attr('data');

				if(activeItem != null && activeItem != undefined){
					activeItem.removeClass("active");
				}
				$(this).addClass("active");
				activeItem = $(this);
				$.ajax({
					type: "get",
					url: "<%=request.getContextPath()%>/chat/" + chatId + ".json",
					dataType: "json",
					success: function(data){
						if(data.success == true){
							var template = $('#template').html();
							var rendered = Mustache.render(template, {messages: data.messages});
							$("#messages").html(rendered);
						}
					},
					error: function(data) {
						alert("调用失败...."+data.responseText);
					}
				})
			});

			var chatItems = $(".chat-item");
			if(chatItems.length > 0 ) $(chatItems[0]).click();
		</script>
	</body>
</html>