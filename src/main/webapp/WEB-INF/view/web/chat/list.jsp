<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>Wecord</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
		<link rel="stylesheet" href="/static/css/chat.css"/>
        <link rel="stylesheet" href="/static/css/bootstrap-tagsinput.css"/>
		<script id="template" type="x-tmpl-mustache">
			{{#messages}}
				<div class="{{direction}}">
				    <div class="arrow"></div>
				    {{&text}}
				    {{#islink}}
                    <input class="tagsinput" type="text" value="{{tags}}" data="{{id}}" data-role="tagsinput"/>
				    {{/islink}}
				</div>
			{{/messages}}
		</script>
        <script id="tag_template" type="x-tmpl-mustache">
            {{#tags}}
            <li class="list-group-item tag-item" data="{{.}}">{{{.}}}</li>
            {{/tags}}
        </script>
        <script id="link_template" type="x-tmpl-mustache">
            {{#links}}
            <li class="list-group-item tag-item" data="{{id}}">{{{content}}}</li>
            {{/links}}
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
				<div class="col-md-3 menu">
                    <div>
                        <!-- Nav tabs -->
                        <ul class="nav nav-tabs" role="tablist" id="tablist">
                            <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">聊天</a></li>
                            <li role="presentation"><a href="#links" aria-controls="links" role="tab" data-toggle="tab">链接</a></li>
                            <%--<li role="presentation"><a href="#photos" aria-controls="photos" role="tab" data-toggle="tab">Photos</a></li>--%>
                        </ul>

                        <!-- Tab panes -->
                        <div class="tab-content">
                            <div role="tabpanel" class="tab-pane active" id="home">
                                <div class="list-group">
                                    <c:forEach items="${chats}" var="chat">
                                        <a href="#" class="list-group-item chat-item" data="<c:out value="${chat.id}"/>">
                                            <img class="weui_media_appmsg_thumb" src="http://www.ileqi.com.cn/assets/images/<c:out value="${chat.id % 12}"/>.jpg" alt="" style="width: 32px; height: 32px;">
                                            <c:out value="${chat.chatOwner}" />
                                        </a>
                                    </c:forEach>
                                </div>
                            </div>
                            <div role="tabpanel" class="tab-pane" id="links">
                                <ul class="list-group" id="taglist">
                                </ul>
                            </div>
                            <div role="tabpanel" class="tab-pane" id="photos">3</div>
                        </div>
                    </div>
				</div>
				<div class="col-md-9">
					<div class="panel panel-default">
						<div class="panel-heading" id="right-header"></div>
						<div class="panel-body" id="right-content">
						</div>
					</div>
				</div>
			</div>
		</div>
        <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <script src="/static/lib/mustache.min.js"></script>
        <script src="/static/lib/bootstrap-tagsinput.js"></script>
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
                            $("#right-header").html("聊天记录");
							$("#right-content").html(rendered);

                            $(".tagsinput").each(function(idx, ele){
                                $(ele).tagsinput('refresh');
                            });

                            $(".bootstrap-tagsinput").click(function(){
                                var input = $($(this).find("input")[0]);
                                input.show();
                                input.focus();
                            });

                            $(".bootstrap-tagsinput input").blur(function(){
                                $(this).hide();
                            });

                            $(".tagsinput").on("itemAdded", function(event){
                                console.log("dsdsds");
                                $.ajax({
                                    type: "post",
                                    url: "<%=request.getContextPath()%>/message/link/tags.json",
                                    data: {message_id: $(this).attr("data"), tagname: event.item},
                                    dataType: "json",
                                    success: function(data){
                                        console.log("itemAdded " + event.item + " for" + $(this).attr("data"));
                                    }
                                });
                            });

                            $(".tagsinput").on("beforeItemRemove", function(event){
                                if(!confirm("Want to delete tag \"" + event.item + "\"?" )){
                                    event.cancel = true;
                                }
                                else{
                                    $.ajax({
                                        type: "delete",
                                        url: "<%=request.getContextPath()%>/message/link/tags.json?" + $.param({message_id: $(this).attr("data"), tagname: event.item}),
                                        dataType: "json",
                                        success: function(data){
                                            console.log("itemDeleted " + event.item + " for" + $(this).attr("data"));
                                        }
                                    });
                                }

                            });

                            $(".tagsinput").on("itemRemoved", function(event){
                                console.log("itemRemoved " + event.item + " for" + $(this).attr("data"));
                            });
						}
					},
					error: function(data) {
						alert("调用失败...."+data.responseText);
					}
				});
			});

			var chatItems = $(".chat-item");
			if(chatItems.length > 0 ) $(chatItems[0]).click();
        </script>
        <script language="JavaScript">
            var activeTag = null;

            $("#tablist a").click(function (e) {
                e.preventDefault();
                $(this).tab('show');

                var func = window[$(this).attr("aria-controls")];
                if(func != null && func != undefined && typeof(func) === "function"){
                    func.call();
                }
            });

            function home(){
                var chatItems = $(".chat-item");
                if(chatItems.length > 0 ) $(chatItems[0]).click();
            }

            function links(){
                $.ajax({
                    type: "get",
                    url: "<%=request.getContextPath()%>/message/link/tags.json",
                    dataType: "json",
                    success: function(data){
                        var template = $('#tag_template').html();
                        var rendered = Mustache.render(template, {tags: data});
                        $("#taglist").html(rendered);

                        $(".tag-item").click(function(){
                            var tagName = $(this).attr('data');

                            if(activeTag != null && activeTag != undefined){
                                activeTag.removeClass("active");
                            }
                            $(this).addClass("active");
                            activeTag = $(this);
                            $.ajax({
                                type: "get",
                                url: "/message/links.json?tagname="+tagName,
                                dataType: "json",
                                success: function(data){
                                    var template = $('#link_template').html();
                                    var rendered = Mustache.render(template, {links: data});
                                    $("#right-header").html("链接");
                                    $("#right-content").html(rendered);
                                },
                                error: function(data) {
                                    alert("调用失败...."+data.responseText);
                                }
                            })
                        });

                        var tagItems = $(".tag-item");
                        if(tagItems.length > 0 ) $(tagItems[0]).click();
                    },
                    error: function(){
                        alert("调用失败...."+data.responseText);
                    }
                });
            }
		</script>
	</body>
</html>