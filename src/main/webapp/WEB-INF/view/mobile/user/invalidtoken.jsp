<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/weui.min.css" />
<script src="<%=request.getContextPath()%>/static/js/jquery-1.8.1.min.js"></script>
</head>
<body>
	<div id="container" style="overflow:auto;">
		<div class="weui_msg">
		    <div class="weui_icon_area"><i class="weui_icon_warn weui_icon_msg"></i></div>
		    <div class="weui_text_area">
		        <h2 class="weui_msg_title">操作失败</h2>
		        <p class="weui_msg_desc">提供了不正确的Token</p>
		    </div>
		    <div class="weui_extra_area">
		        <a href="<%=request.getContextPath()%>/login.html">直接登录</a>
		    </div>
		</div>
	</div>
</body>
<script type="text/javascript">
</script>
</html>