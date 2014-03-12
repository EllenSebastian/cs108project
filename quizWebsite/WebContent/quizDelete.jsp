<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,quizWebsite.*, java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Deleted!</title>
</head>
<body>
<%
	Connection con = (Connection) application
			.getAttribute("Connection");
	int id = Integer.parseInt(request.getParameter("id"));
	UserManager.promoteUser(id);
	mysqlManager.deleteQuiz(id,con);

%>
<h1>Quiz Deleted!</h1>
<a href=userPage.jsp>Home</a>
</body>
</html>