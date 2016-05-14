<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<html>
<head>
<script src="<%=request.getContextPath()%>/static/js/jquery-1.8.1.min.js"></script>
</head>
<body>
<c:forEach items="${chats}" var="chat"> 
<td><a href="/list.jsp"><c:out value="${chat.id}" /></a></td> 
<td><a href="/list.jsp"><c:out value="${chat.chatOwner}" /></a></td> 
<td><a href="/list.jsp"><c:out value="${chat.groupChat}" /></a></td> 
</tr> 
</c:forEach>
</body>
<script type="text/javascript">
</script>
</html>