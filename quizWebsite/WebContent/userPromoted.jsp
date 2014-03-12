<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,quizWebsite.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
* {
 font-family: "Trebuchet MS", Helvetica, sans-serif;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Promoted!</title>
</head>
<body>
<%
	int id = Integer.parseInt(request.getParameter("id"));
	UserManager.promoteUser(id);
%>
<h1>User Promoted!</h1>
<a href=userPage.jsp>Home</a>
</body>
</html>