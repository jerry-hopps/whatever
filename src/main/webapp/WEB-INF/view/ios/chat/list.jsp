<!DOCTYPE html>
<html class="with-statusbar-overlay">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style"
	content="black-translucent">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/lib/framework7/css/framework7.ios.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/lib/framework7/css/framework7.ios.colors.min.css">
<%-- <link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/todo7.css"> --%>
<!-- Favicon-->
<link href="img/icon-57.png" rel="shortcut icon">
<!-- iOS 7 iPad (retina) -->
<link href="img/icon-152.png" sizes="152x152" rel="apple-touch-icon">
<!-- iOS 6 iPad (retina) -->
<link href="img/icon-144.png" sizes="144x144" rel="apple-touch-icon">
<!-- iOS 7 iPhone (retina) -->
<link href="img/icon-120.png" sizes="120x120" rel="apple-touch-icon">
<!-- iOS 6 iPhone (retina) -->
<link href="img/icon-114.png" sizes="114x114" rel="apple-touch-icon">
<!-- iOS 7 iPad -->
<link href="img/icon-76.png" sizes="76x76" rel="apple-touch-icon">
<!-- iOS 6 iPad -->
<link href="img/icon-72.png" sizes="72x72" rel="apple-touch-icon">
<!-- iOS 6 iPhone -->
<link href="img/icon-57.png" sizes="57x57" rel="apple-touch-icon">
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
						<div class="list-block media-list todo-items-list"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Template-->
	<script id="todo-item-template" type="text/template">
	  <ul>{{#each this}}
        <li class="item item-{{id}}">
	      <a href="{{id}}.html" class="item-link item-content">
            <div class="item-media">
			  <img src="http://www.ileqi.com.cn:8080/whatever/static/images/1.jpg" width="48px" height="48px">
            </div>
            <div class="item-inner">
              <div class="item-title-row">
              	<div class="item-title">{{chatOwner}}</div>
              </div>
              <div class="item-subtitle">{{chatOwner}}</div>
            </div>
          </a>
        </li>{{/each}}
      </ul>
    </script>
    <script id="message-template" type="text/template">
    		{{#if day}}
			<div class="messages-date">{{day}} {{#if time}}, <span>{{time}}</span>{{/if}}</div>
			{{/if}}
			<div class="message message-{{type}} {{#if hasImage}}message-pic{{/if}} {{#if avatar}}message-with-avatar{{/if}}">
 			   {{#if name}}<div class="message-name">{{name}}</div>{{/if}}
			   <div class="message-text">{{text}}</div>
    		   {{#if avatar}}<div class="message-avatar" style="background-image:url({{avatar}})"></div>{{/if}}
               {{#if label}}<div class="message-label">{{label}}</div>{{/if}}
            </div>
	</script>
	<!-- Path to Framework7 Library JS-->
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/lib/framework7/js/framework7.min.js"></script>
	<!-- Path to your app js-->
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/static/js/todo7.js"></script>
</body>
</html>