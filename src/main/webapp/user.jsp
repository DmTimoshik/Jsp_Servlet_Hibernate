<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UserPage</title>
</head>
<body>
<div align="center" style="margin-top:100px">
	<c:set var="user" value="${sessionScope.current}" />
	<h1>Hello, <c:out value="${user.firstName}" />!</h1>
	Click <a href="<c:url value="/controller?action=logout"/>">here</a> to logout
</div>
</body>
</html>