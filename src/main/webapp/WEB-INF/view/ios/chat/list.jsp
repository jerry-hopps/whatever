<!DOCTYPE html>
<html class="with-statusbar-overlay">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/lib/framework7/css/framework7.ios.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/lib/framework7/css/framework7.ios.colors.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/todo7.css">
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
            <div class="right"><a href="#" class="link icon-only open-popup"><i class="icon icon-plus">+</i></a></div>
          </div>
        </div>
        <div class="pages">
          <div data-page="index" class="page">
            <div class="page-content">
              <div class="list-block media-list todo-items-list"></div>
            </div>
          </div>
        </div>
			<div class="toolbar tabbar">
				<div class="toolbar-inner">
					<a href="#tab1" class="tab-link"> <i class="icon"><span class="badge bg-green">15</span></i></a>
					<a href="#tab2" class="tab-link"> <i class="icon">Article</i></a>
					<a href="#tab3" class="tab-link"> <i class="icon">Me</i></a>
				</div>
			</div>
		</div>
    <!-- Popup to add new task           -->
    <div class="popup">
      <div class="view view-popup navbar-fixed">
        <div class="navbar">
          <div class="navbar-inner">
            <div class="left"></div>
            <div class="center sliding">New Task</div>
            <div class="right"><a href="#" class="link close-popup">Cancel</a></div>
          </div>
        </div>
        <div class="pages">
          <div class="page">
			  <div class="page-content messages-content">
			    <div class="messages">
			      <!-- 时间戳 -->
			      <div class="messages-date">Sunday, Feb 9 <span>12:58</span></div>
			 
			      <!-- 发送的消息 (默认为绿色背景，在右边) -->
			      <div class="message message-sent">
			        <!-- Bubble with text -->
			        <div class="message-text">Hello</div>
			      </div>
			 
			      <!-- 另一条发送的消息 -->
			      <div class="message message-with-tail message-sent">
			        <!-- 文本气泡 -->
			        <div class="message-text">How are you?</div>
			      </div>
			 
			      <!-- 接收的信息(默认为灰色背景，在左边) -->
			      <div class="message message-with-avatar message-received">
			        <!-- Sender name -->
			        <div class="message-name">Kate</div>
			 
			        <!-- 文本气泡 -->
			        <div class="message-text">I am fine, thanks</div>
			 
			        <!-- 发送者头像 -->
			        <div style="background-image:url(http://ww2.sinaimg.cn/crop.0.0.1242.1242.1024/005EWUXPjw8eto7cdd42wj30yi0yiabz.jpg)" class="message-avatar"></div>
			      </div>
			 
			      <!-- 另一个时间戳 -->
			      <div class="messages-date">Sunday, Feb 3 <span>11:58</span></div>
			 
			      <!-- Sent message with image -->
			      <div class="message message-pic message-sent">
			        <!-- Bubble with image -->
			        <div class="message-text"><img src="http://lorempixel.com/300/300/"></div>
			        <!-- Message label -->
			        <div class="message-label">Delivered 2 days ago</div>
			      </div>
			    </div>
			  </div>
			</div>    
        </div>
      </div>
    </div>
</div>
    <!-- Template-->
    <script id="todo-item-template" type="text/template">
	  <ul>{{#each this}}
        <li class="item item-{{id}}">
            <div class="item-content">
                <div class="item-media">
                    <img src="http://www.ileqi.com.cn:8080/whatever/static/images/1.jpg" width="48px" height="48px">
                </div>
                <div class="item-inner">
                    <div class="item-title-row">
                        <div class="item-title">{{chatOwner}}</div>
                        <div class="item-after"></div>
                    </div>
					<div class="item-subtitle">{{chatOwner}}</div>
                </div>
            </div>
        </li>{{/each}}
      </ul>
    </script>
    <!-- Path to Framework7 Library JS-->
    <script type="text/javascript" src="<%=request.getContextPath()%>/lib/framework7/js/framework7.min.js"></script>
    <!-- Path to your app js-->
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/todo7.js"></script>
  </body>
</html>