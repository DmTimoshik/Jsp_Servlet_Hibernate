<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="user" value="${sessionScope.current}" />
<c:set var="user" value="${sessionScope.current}" />
<c:if test="${user.firstName == null}">
<c:redirect url="index.jsp"/>
</c:if>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add users</title>



</head>
<body>
<div align="center" style="padding-left: 300px;">
<div align="right" style="margin-right:20px"> Admin <c:out value="${user.firstName}" />&nbsp;(<a href="<c:url value="/controller?action=logout"/>">Logout</a>)</div>
<div align="left" style=" margin-left:30px "><h1>Add user</h1></div>
<div align="left" style=" margin-top:20px">
<form method=post action="controller">
<input type="hidden" name="action" value="add" />
	<table>		
		<tr>
		<td>Login</td>
		<td><input type=text name=login size=40  value="${returnLogin}"></td>
		<td><font color="#CC0000"> * </font></td>
		</tr>
		<tr>
		<td>Password</td>
		<td><input type=password name=pass size=40 value="${returnPass}"></td>
		<td><font color="#CC0000"> * </font></td>
		</tr>
		<tr>
		<td>Password again</td>
		<td><input type=password name=passAgain size=40 value="${returnPass}"></td>
		<td><font color="#CC0000"> * </font></td>
		</tr>
		<tr>
		<td>Email</td>
		<td><input type=text name=email size=40 value="${returnEmail}"></td>
		<td><font color="#CC0000"> * </font></td>
		</tr>
		<tr>
		<td>First name</td>
		<td><input type=text name=first size=40 value="${returnFirst}"></td>
		<td><font color="#CC0000"> * </font></td>
		</tr>
		<tr>
		<td>Last name</td>
		<td><input type=text name=last size=40  value="${returnLast}"></td>
		<td><font color="#CC0000"> * </font></td>
		</tr>
		<tr>
		<td>Birthday</td>
		<!-- required="required" -->
		<td><input type=text name=birth size=40  value="yyyy-mm-dd"   ></td>
		<td><font color="#CC0000"> * </font></td>
		</tr>
		<tr>
		<td>Role</td>
		<td><select name="role" size="1">
		<option selected="selected" value="User">User</option>
		<option value="Admin">Admin</option>
		</select></td>
		<td><font color="#CC0000"> * </font></td>
		</tr>
		<tr>
		<td align="left"><input type=submit name="Ok" value=" Ok ">
		<input type=submit name="Cancel" value=" Cancel "></td>
		</tr>		
	</table>
	<c:if test="${wrong != null}">
		<p style="color: red;font-size:20px;">Error - ${wrong}</p>
	</c:if>
	
</form>
</div>
</div>
</body>
</html>