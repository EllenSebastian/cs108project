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
<title>Site Statistics</title>
</head>
<body>
<h1>Site Statistics</h1>
<%
int numUsers = UserManager.numAllUsers();
int numTaken = Activity.numQuizzesTaken();
System.out.println("<p>Number of Users: " + numUsers);
System.out.println("<p>Number of Quizzes Taken: " + numTaken);
%>
<a href=userPage.jsp>Home</a>
</body>
</html>