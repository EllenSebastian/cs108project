<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Logged out</title>
</head>
<body>
<%
session.setAttribute(quizWebsite.Constants.session_currentUser,null);
%>
<h1> You have been logged out.</h1>
<a href=loginPage.jsp>Log in</a></body>

<form action=LoginServlet method="post">
<input type = "submit" value="Continue as a nonregistered user">
</form>
</html>