<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="test" uri="/WEB-INF/table.tld"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>AdminPage</title>
<script type="text/javascript">
	function confirmation() {
		if (confirm("Are you sure?")) {
			return true;
		} else {
			return false;
		}
	}
</script>
</head>
<body>
	<div align="center" style="padding-left: 100px;">
		<%-- <c:if test="true">Hello world!</c:if> --%>
		<c:set var="user" value="${sessionScope.current}" />
		<div align="right" style="margin-right: 20px;">
			Admin
			<c:out value="${user.firstName}" />
			(<a href="<c:url value="/controller?action=logout"/>">Logout</a>)
		</div>
		<div align="left" style="margin-left: 20px; margin-top: 50px;">
			<a href="add">Add new user</a>
		</div>
		<div align="left"
			style="margin-left: 20px; margin-top: 20px; margin-right: 40px">
			<table  width="800px" border="1" frame="void" cellspacing="0"
				cellpadding="5">
				<tr bgcolor="#cccccc">
					<td>Login</td>
					<td>First Name</td>
					<td>Last Name</td>
					<td>Age</td>
					<td>Role</td>
					<td>Actions</td>
				</tr>
				<test:tableTag />
			</table>

		</div>
	</div>
</body>
</html>