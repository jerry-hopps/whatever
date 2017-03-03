<!DOCTYPE>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="/static/css/chat.css"/>
    <link rel="shortcut icon" href="/favicon.ico">
    <link rel="stylesheet" href="<c:url value="${assets.css}"/>"/>

    <style>
        #root {
            margin-top: 0px;
        }
    </style>
    <title>React Todos</title>
</head>
<body>
    <div class="container">
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <a class="navbar-brand" href="#">Wecord</a>
                    <ul class="nav navbar-nav">
                        <li><a href="/chat/list.html">Chat</a></li>
                        <li class="active"><a href="/todos/all">Todos</a></li>
                    </ul>
                    <a class="btn btn-default navbar-btn right" href="<%=request.getContextPath()%>/logout.html" role="button">登出</a>
                </div>
            </div>
        </nav>
        <div class="row">
            <div class="col-md-12 menu">
                <div id="root"></div>
            </div>
        </div>
    </div>

    <footer class="footer">
        <div class="container">
            <p class="text-muted">Powered by www.ileqi.com.cn | copyright reserved © 1997-2016</p>
        </div>
    </footer>
    <script type="text/javascript" src="<c:url value="${assets.js}"/>"></script>
</body>
</html>
